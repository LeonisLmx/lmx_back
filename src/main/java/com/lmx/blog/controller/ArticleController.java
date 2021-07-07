package com.lmx.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmx.blog.common.Response;
import com.lmx.blog.config.redis.RedisExecutor;
import com.lmx.blog.mapper.ArticleDescriptionMapper;
import com.lmx.blog.serviceImpl.ArticleDetailSercice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequestMapping("/article")
@RestController
public class ArticleController{

    private final Integer pageSize = 20;

    private final Long intervalTime = 60000L;

    @Autowired
    private RedisExecutor redisExecutor;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ArticleDescriptionMapper articleDescriptionMapper;

    @Autowired
    private ArticleDetailSercice articleDetailSercice;

    private final String TEST_REDIS_KEY = "TEST_REDIS_KEY";

    // 获取排行榜
    @RequestMapping("/giveLike")
    public Response addNum(Long start,Long end,int index){
        String uuid = UUID.randomUUID().toString();
        Boolean flag = redisExecutor.getLock(TEST_REDIS_KEY, uuid,30);
        if(flag) {
            Set<Object> strings = redisExecutor.getZSet("zset", start + 1, end + 1, index);
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

    @RequestMapping("/to_author")
    public Response toAuthor(String author,Integer pageNumer){
        if(StringUtils.isBlank(author)){
            return Response.ok("author 不能为空");
        }
        Double count = redisTemplate.opsForZSet().score("zset",author);
        PageHelper.startPage(pageNumer,pageSize);
        List<Map<String,Object>> list = articleDetailSercice.queryAllArticles(null,null,author);
        PageInfo pageInfo = new PageInfo(list);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("count",count);
        resultMap.put("list",pageInfo.getList());
        resultMap.put("current",pageNumer * pageSize > count?count:pageNumer * pageSize);
        return Response.ok(resultMap);
    }

    // 限流方式第一种
    @RequestMapping("/limit_flow")
    public Response limitFlow(){
        Long currentTime = System.currentTimeMillis();
        System.out.println(currentTime);
        if(redisTemplate.hasKey("limit")) {
            Integer count = redisTemplate.opsForZSet().rangeByScore("limit", currentTime - intervalTime, currentTime).size();
            System.out.println(count);
            if (count != null && count > 5) {
                return Response.ok("每分钟最多只能访问5次");
            }
        }
        redisTemplate.opsForZSet().add("limit",UUID.randomUUID().toString(),currentTime);
        return Response.ok("访问成功");
    }
}
