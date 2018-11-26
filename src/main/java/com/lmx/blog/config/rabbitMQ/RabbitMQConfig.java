package com.lmx.blog.config.rabbitMQ;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class RabbitMQConfig {

    @Bean
    public Queue requestQueue(){
        return new Queue("request");
    }

    @Bean
    public Queue messageQueue(){
        return new Queue("send_message");
    }
}
