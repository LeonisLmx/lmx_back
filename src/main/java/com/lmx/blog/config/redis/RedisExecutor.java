package com.lmx.blog.config.redis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisExecutor {

    private static Logger logger = LoggerFactory.getLogger(RedisExecutor.class);

    @Autowired
    private RedisTemplate redisTemplate;

    private static RedisTemplate redisTemplateDao;
    // 初始化调用
    @PostConstruct
    public void init() {
        redisTemplateDao = redisTemplate;
    }

    /**
     * 插入String记录
     * @param key
     * @param value
     */
    public void addString(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void addObject(String key,String name, Object value){
        String json = (new GsonBuilder()).serializeNulls().create().toJson(value);
        redisTemplate.opsForHash().put(key,name,json);
    }


    /**
     * 根据key删除记录
     * @param key
     */
    public void deleteByKey(String key){
        if(redisTemplate.opsForValue().get(key) != null){
            redisTemplate.delete(key);
        }
    }

    /**
     * 根据key查找记录
     * @param key
     * @return
     */
    public static String getByKey(String key){
        String result = null;
        if(redisTemplateDao.opsForValue().get(key) != null){
            result = redisTemplateDao.opsForValue().get(key).toString();
        }
        return result;
    }

    public <T> T getObjectByKey(String key, Class cls){
        T result = null;
        if(redisTemplate.opsForValue().get(key) != null) {
            String json = redisTemplate.opsForValue().get(key).toString();
            result = (T)new Gson().fromJson(json,cls);
        }

        return result;
    }

    /**
     * 插入map记录(key-value)
     * @param key
     * @param name
     * @param obj
     */
    public void addMap(String key, String name, Object obj){
        String val = null;
        if(obj instanceof String){
            val = obj.toString();
        } else {
            val = (new GsonBuilder()).serializeNulls().create().toJson(obj);
        }
        redisTemplate.opsForHash().put(key,name,val);
    }

    public void addMap(String key, Map map){
        map.forEach((k,v)->{
            addMap(key, k.toString(), v);
        });
    }

//    /**
//     * 插入map记录(map)
//     * @param key
//     * @param map
//     */
//    public void addMap (String key, Map<String,String> map){
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            redisTemplate.opsForHash().put(key,entry.getKey(), entry.getValue());
//        }
//    }

    /**
     * 根据key和属性name删除map记录
     * @param key
     * @param name
     */
    public void deleteMap(String key,String name){
        if(redisTemplate.opsForHash().get(key,name) != null){
            redisTemplate.opsForHash().delete(key,name);
        }
    }

    /**
     * 根据key查找map记录
     * @param key
     * @param name
     * @return
     */
    public  String getMap(String key,String name){
        String result = null;
        if(redisTemplate.opsForHash().get(key,name) != null){
            result = redisTemplate.opsForHash().get(key,name).toString();
        }
        return result;
    }

    public static <T> T getMap(String key, String name, Class cls){
        T result = null;
        if(redisTemplateDao.opsForHash().get(key,name) != null){
            String json = redisTemplateDao.opsForHash().get(key,name).toString();
            result = (T)new Gson().fromJson(json,cls);
        }
        return result;
    }

    /**
     * 添加List记录
     * @param key
     * @param value
     */
    public void addList(String key,String value){
        redisTemplate.opsForList().leftPush(key,value);
    }

    public void addListByObject(String key, Object value){
        if(value instanceof List){
            ((List)value).forEach((v)->{
                String json = (new GsonBuilder()).serializeNulls().create().toJson(value);
                redisTemplate.opsForList().leftPush(key, json);
            });
        } else {
            String json = (new GsonBuilder()).serializeNulls().create().toJson(value);
            redisTemplate.opsForList().leftPush(key, json);
        }

    }
    /**
     * 查找List记录
     * @param key
     */
    public List<Object> getList(String key,Long start,Long end){
        List list = null;
        if(redisTemplate.opsForList().range(key,start,end) != null){
            list= redisTemplate.opsForList().range(key,start,end);
        }
        return list;
    }

    public <T> List<T> getList(String key, Class cls,Long start,Long end){
        List list = new ArrayList<T>();
        if(redisTemplate.opsForList().range(key,start,end) != null){
            List jsonList = redisTemplate.opsForList().range(key,start,end);
            jsonList.forEach((v)->{
                list.add(new Gson().fromJson(v.toString(),cls));
            });
        }
        return list;
    }


    /**
     * 设置过期时间-单位（天）
     * @param key
     * @param temp
     */
    public void setExpireDays(String key,int temp){
        redisTemplate.expire(key,temp, TimeUnit.DAYS);
    }

    /**
     * 设置过期时间-单位（分）
     * @param key
     * @param temp
     */
    public void setExpireMinutes(String key,int temp){
        redisTemplate.expire(key,temp, TimeUnit.MINUTES);
    }

    /**
     * 设置过期时间-单位（秒）
     * @param key
     * @param temp
     */
    public void setExpireSeconds(String key,int temp){
        redisTemplate.expire(key,temp, TimeUnit.SECONDS);
    }

    // 设置key并设置过期时间
    public static void addKeyAndExpireTimes(String key,String value,Long hours,TimeUnit timeUnit){
        redisTemplateDao.opsForValue().set(key,value,hours,timeUnit);
    }

    // 添加zset列表
    public void addZSet(String key,Object value,double l){
        redisTemplate.opsForZSet().add(key,value,l);
    }

    // 获取排序值
    public Set<Object> getZSet(String key,Long start,Long end,int index){
        if(index == -1){
            // 倒叙
            return redisTemplate.opsForZSet().reverseRange(key,start,end);
        }else{
            // 正序
            return redisTemplate.opsForZSet().range(key,start,end);
        }
    }

    // 获取指定的排名
    public Long getRankByObject(String key,Object object){
        return redisTemplate.opsForZSet().rank(key,object);
    }

    // 自定义增长zset的值
    public void addScore(String key,Object object,Long increase){
        redisTemplate.opsForZSet().incrementScore(key,object,increase);
    }

    // 获取指定对象目前的排序分
    public Double getScore(String key,Object object){
        return redisTemplate.opsForZSet().score(key,object);
    }
}
