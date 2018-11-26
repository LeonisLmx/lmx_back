package com.lmx.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmx.blog.annotation.RedisCache;
import com.lmx.blog.common.Response;
import com.lmx.blog.config.redis.RedisExecutor;
import com.lmx.blog.model.SendMessage;
import com.lmx.blog.service.EmailService;
import com.lmx.blog.serviceImpl.ArticleDetailSercice;
import com.lmx.blog.serviceImpl.Commonservice;
import com.lmx.blog.serviceImpl.JuejinCrawerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RequestMapping("/user")
@RestController
public class UserController {

    private static int produceTaskMaxNumber = 10;

    private static int pageSize = 20;

    @Autowired private JuejinCrawerService juejinCrawerService;

    @Autowired private ArticleDetailSercice articleDetailSercice;

    @Autowired private EmailService emailService;

    @Autowired private AmqpTemplate amqpTemplate;

    @RequestMapping("/test")
    public Response test(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(6),new ThreadPoolExecutor.DiscardOldestPolicy());
        for(int i = 1;i <= produceTaskMaxNumber;i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        juejinCrawerService.getArticle();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
        return Response.ok(true);
    }

    @RequestMapping("/achieve")
    public Response achieve(){
        System.out.println("方法执行-----------");
        return Response.ok(articleDetailSercice.getPrimaryKeyById(1L));
    }

    @RedisCache(type = Response.class)
    @RequestMapping("/getList")
    public Response getListArticles(Integer pageNum,Integer isOrigin,String text){
        System.out.println("执行了----------------------------------");
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> lists = articleDetailSercice.queryAllArticles(isOrigin,text);
        PageInfo page = new PageInfo(lists);
        return Response.ok(page);
    }

    @RedisCache(type = Response.class)
    @RequestMapping("/sendEmail")
    public Response sendEmailToAuthor(String content, HttpServletRequest request){
        // 获取当前的IP地址，防止恶意刷IP地址
        String ip = Commonservice.getIp(request);
        if(RedisExecutor.getByKey(ip) != null){
            return Response.ok(false);
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setContent(content);
        sendMessage.setFromIp(request.getRemoteAddr());
        sendMessage.setCreateTime(new Date());
        // 记录发送的消息
        amqpTemplate.convertAndSend("send_message",sendMessage);
        RedisExecutor.addKeyAndExpireTimes(ip,content,5L,TimeUnit.MINUTES);
        ExecutorService singPool = Executors.newSingleThreadExecutor();
        singPool.execute(new Runnable() {
            @Override
            public void run() {
                emailService.sendSimpleEmail("18262388063@sina.cn","游客来件",content);
            }
        });
        return Response.ok(true);
    }
}
