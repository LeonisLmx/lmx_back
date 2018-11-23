package com.lmx.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

    @Bean
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");
        mailSender.setUsername("710285449@qq.com");
        mailSender.setPassword("hngtswgqlxyabdbb");
        mailSender.setDefaultEncoding("UTF-8");    // 不加这部分会导致html格式的邮件乱码
        return mailSender;
    }
}
