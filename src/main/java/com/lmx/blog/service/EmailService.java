package com.lmx.blog.service;

public interface EmailService {

    public void sendSimpleEmail(String to,String subject,String content);

    public void sendHtmlEmail(String to,String subject,String content);

    public void sendAttachmentsEmail(String to,String subject,String content,String filePath);

    public void sendInlineResoureceEmail(String to,String subject,String content,String rscPath,String rscId);
}
