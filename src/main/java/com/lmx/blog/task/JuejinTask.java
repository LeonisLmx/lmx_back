package com.lmx.blog.task;

import com.lmx.blog.service.JuejinCrawerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘明新
 * @date 2018/11/8 下午4:46
 */
@Component
public class JuejinTask {

    @Autowired
    private JuejinCrawerService juejinCrawerService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void crawerJuejin(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(6),new ThreadPoolExecutor.DiscardOldestPolicy());
        for(int i = 1;i <= 10;i++) {
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
    }
}
