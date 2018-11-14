package com.lmx.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmx.blog.annotation.RedisCache;
import com.lmx.blog.common.Response;
import com.lmx.blog.model.ArticleDetail;
import com.lmx.blog.service.ArticleDetailSercice;
import com.lmx.blog.service.Commonservice;
import com.lmx.blog.service.JuejinCrawerService;
import com.sun.org.apache.regexp.internal.RE;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequestMapping("/user")
@RestController
public class UserController {

    private static int produceTaskMaxNumber = 10;

    private static int pageSize = 20;

    @Autowired private JuejinCrawerService juejinCrawerService;

    @Autowired private ArticleDetailSercice articleDetailSercice;

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
        return Response.ok(articleDetailSercice.getPrimaryKeyById(1L));
    }

    @RedisCache(type = Response.class)
    @RequestMapping("/getList")
    public Response getListArticles(Integer pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> lists = articleDetailSercice.queryAllArticles();
        PageInfo page = new PageInfo(lists);
        return Response.ok(page);
    }
}
