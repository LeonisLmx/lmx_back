package com.lmx.blog.serviceImpl;

import co.matrix.huskar.mqs.rocketmq.HuskarRocketMQMessageListener;
import co.matrix.huskar.mqs.rocketmq.NameAddrUtils;
import co.matrix.huskar.mqs.rocketmq.NamespaceUtil;
import com.alibaba.fastjson.JSONObject;
import com.lmx.blog.model.MapEsData;
import com.lmx.blog.service.MapEsDataRepository;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

@Service
@HuskarRocketMQMessageListener(consumerGroup = "GID_blog", topic = "q_matrix_to_local", consumeMode = ConsumeMode.ORDERLY)
public class RocketMQTopicListener implements RocketMQListener<MessageExt> {

    private static Logger logger = LoggerFactory.getLogger(RocketMQTopicListener.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Autowired
    private MapEsDataRepository mapEsDataRepository;

    private String namespace = "";

    private final Long time = 10_000L;

    private volatile Long last_time = null;

    @PostConstruct
    public void initNameSpace(){
        this.namespace = NameAddrUtils.parseInstanceIdFromEndpoint(rocketMQTemplate.getProducer().getNamesrvAddr());
        logger.info(namespace);
        if(!esTemplate.indexExists("huskar_message")){
            esTemplate.createIndex("huskar_message");
        }
    }

    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            String msg = new String(messageExt.getBody(), "UTF-8");
            logger.info("current message is {} and created is  {}",msg,last_time);
            MapEsData map = JSONObject.parseObject(msg,MapEsData.class);
            Long created = map.getCreatedAt();
            logger.info("current created is {}",last_time);
            redisTemplate.opsForZSet().add("jiankong", msg,created);
            mapEsDataRepository.save(map);
            Integer size = 0;
            //Redis 实现滑动窗口
            synchronized (RocketMQTopicListener.class) {
                if (last_time == null) {
                    size = redisTemplate.opsForZSet().rangeByScore("jiankong", created - time, created).size();
                    logger.info("current size 1 is {}", size);
                    if (size > 20) {
                        last_time = created;
                        rocketMQTemplate.asyncSend(NamespaceUtil.wrapNamespace(namespace, "q_blog_to_huskar:order_form"),
                                MessageBuilder.withPayload(redisTemplate.opsForZSet().rangeByScore("jiankong", created - time, created)).build(), new SendCallback() {
                                    @Override
                                    public void onSuccess(SendResult sendResult) {

                                    }

                                    @Override
                                    public void onException(Throwable e) {

                                    }
                                });
                        logger.info("start send Message to topic once");
                        return;
                    }
                } else {
                    if (created - last_time < time) {
                        size = redisTemplate.opsForZSet().rangeByScore("jiankong", last_time, created).size();
                        logger.info("current size 2 is {}", size);
                        if (size > 20) {
                            rocketMQTemplate.send(NamespaceUtil.wrapNamespace(namespace, "q_blog_to_huskar:order_form"),
                                    MessageBuilder.withPayload(msg).build());
                            last_time = created;
                            logger.info("start send Message to topic twice");
                        }
                    } else {
                        last_time = null;
                        size = redisTemplate.opsForZSet().rangeByScore("jiankong", created - time, created).size();
                        logger.info("current size 3 is {}", size);
                        if (size > 20) {
                            rocketMQTemplate.send(NamespaceUtil.wrapNamespace(namespace, "q_blog_to_huskar:order_form"),
                                    MessageBuilder.withPayload(msg).build());
                            logger.info("start send Message to topic third");
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
