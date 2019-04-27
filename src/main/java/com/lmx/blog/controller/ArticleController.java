package com.lmx.blog.controller;

import com.lmx.blog.common.Response;
import com.lmx.blog.config.redis.RedisExecutor;
import com.lmx.blog.mapper.ArticleDescriptionMapper;
import com.lmx.blog.model.result.ZsetTestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequestMapping("/article")
@RestController
public class ArticleController {

    @Autowired
    private RedisExecutor redisExecutor;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ArticleDescriptionMapper articleDescriptionMapper;

    private final String TEST_REDIS_KEY = "TEST_REDIS_KEY";

    // 初始化zset列表数据
    @PostConstruct
    public void init(){
        Long num = redisTemplate.opsForZSet().size("zset");
        System.out.println(num);
        if(num == 0) {
            List<ZsetTestModel> lists = articleDescriptionMapper.selectCount();
            lists.forEach((n) -> {
                redisExecutor.addZSet("zset", n.getAuthor(), Long.valueOf(n.getCount() + ""));
            });
        }
    }

    // 获取排行榜
    @RequestMapping("/giveLike")
    public Response addNum(Long start,Long end,int index){
        String uuid = UUID.randomUUID().toString();
        Boolean flag = redisExecutor.getLock(TEST_REDIS_KEY, uuid,30);
        if(flag) {
            System.out.println(flag + "--------------");
            Long allList = redisTemplate.opsForZSet().count("zset", 70, 80);
            System.out.println(allList);
            Set<Object> strings = redisExecutor.getZSet("zset", start, end, index);
            List<String> list = new LinkedList<>();
            strings.forEach((v) -> {
                list.add(v.toString());
            });
            redisExecutor.releaseLock(TEST_REDIS_KEY,uuid);
            return Response.ok(list);
        }else{
            return Response.ok("Redis未释放锁");
        }
    }
}
