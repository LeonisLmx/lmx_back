package com.lmx.blog.config.rabbitMQ;

import com.lmx.blog.mapper.IpRequestMapper;
import com.lmx.blog.mapper.SendMessageMapper;
import com.lmx.blog.model.IpRequest;
import com.lmx.blog.model.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestMessageReceiver {

    private static Logger logger   = LoggerFactory.getLogger(RequestMessageReceiver.class);

    @Autowired
    private IpRequestMapper ipRequestMapper;

    @Autowired
    private SendMessageMapper sendMessageMapper;

    @RabbitListener(queues = "request")
    public void processRequest(IpRequest ipRequest){
        ipRequestMapper.insert(ipRequest);
        logger.info("request已消费");
    }

    @RabbitListener(queues = "send_message")
    public void processMessage(SendMessage sendMessage){
        sendMessageMapper.insert(sendMessage);
        logger.info("send_message已消费");
    }
}
