package com.lmx.blog.config.rocketMQ;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

public class Consumer {

    private MQConsumer mqConsumer;

    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("lmx_blog_group");

    @PostConstruct
    public void init() throws MQClientException {
        consumer.setInstanceName("lmx_blog_instanceName");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("test_transcation","*");
        consumer.start();
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                return null;
            }
        });
    }

    @PreDestroy
    public void destory(){
        consumer.shutdown();
    }
}
