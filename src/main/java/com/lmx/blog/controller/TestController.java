package com.lmx.blog.controller;

import com.lmx.blog.annotation.RedisCache;
import com.lmx.blog.common.Response;
import com.lmx.blog.model.ArticleDetail;
import com.lmx.blog.serviceImpl.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired private DemoService demoService;

    @RequestMapping("/addRedis")
    public Response add(){
        return Response.ok(demoService.findById(1L));
    }

    @RequestMapping("/deleteRedis")
    @RedisCache(type = Response.class)
    public Response delete(String message){
        //demoService.deleteById(1L);
        String a = null;
        System.out.println(a.substring(1));
        return Response.ok("删除成功");
    }

    @RequestMapping("/updateRedis")
    public Response update(){
        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setId(1L);
        articleDetail.setReadNum(200);
        return Response.ok(demoService.save(articleDetail));
    }

    @RequestMapping("/addRedisTemplate")
    public Response addRedisTemplate(){
        return Response.ok(demoService.addRedisTemplate());
    }
}
