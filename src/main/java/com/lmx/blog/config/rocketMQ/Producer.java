package com.lmx.blog.config.rocketMQ;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Producer{

    @Autowired
    private MQProducerListerner mqProducerListerner;

    TransactionMQProducer transactionMQProducer = new TransactionMQProducer("lmx_blog_group");

    private MQProducer mqProducer;

    public Producer() {
        this.mqProducer = transactionMQProducer;
    }

    @PostConstruct
    public void init() throws MQClientException {
        transactionMQProducer.setNamesrvAddr("localhost:9876");
        transactionMQProducer.setInstanceName("lmx_blog_instanceName");
        transactionMQProducer.setTransactionListener(mqProducerListerner);
        transactionMQProducer.start();
    }

    @PreDestroy
    public void destory(){
        transactionMQProducer.shutdown();
    }

    public SendResult send(String topic,String message,Object args) throws MQClientException {
        return transactionMQProducer.sendMessageInTransaction(new Message(topic,message.getBytes()),args);
    }
}
