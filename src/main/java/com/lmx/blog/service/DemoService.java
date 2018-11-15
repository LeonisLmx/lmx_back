package com.lmx.blog.service;

import com.lmx.blog.common.RedisUtil;
import com.lmx.blog.mapper.ArticleDetailMapper;
import com.lmx.blog.model.ArticleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DemoService {

    @Autowired
    private ArticleDetailMapper articleDetailMapper;

    @Autowired private RedisUtil redisUtil;

    @Cacheable(value = "cacheOne",key = "'user_' + #id")
    public ArticleDetail findById(Long id){
        System.err.println("获取数据并放入缓存");
        return articleDetailMapper.selectPrimaryKey(id);
    }

    @CacheEvict(value = "cacheOne",key = "'user_' + #id")
    public void deleteById(Long id){
        System.out.println("删除缓存中数据");
    }

    @CachePut(value = "cacheOne",key = "'user_' + #articleDetail.id")
    public ArticleDetail save(ArticleDetail articleDetail){
        System.out.println("更新缓存中数据");
        articleDetailMapper.update(articleDetail);
        return articleDetailMapper.selectPrimaryKey(1L);
    }

    public ArticleDetail addRedisTemplate(){
        if(redisUtil.hmget("user_1").size() == 0){
            ArticleDetail articleDetail = articleDetailMapper.selectPrimaryKey(1L);
            Map<String,Object> map = new HashMap<>();
            map.put("key",articleDetail);
            redisUtil.hmset("user_1",map);
            return articleDetail;
        }else{
            return (ArticleDetail) redisUtil.hmget("user_1").get("key");
        }
    }
}
