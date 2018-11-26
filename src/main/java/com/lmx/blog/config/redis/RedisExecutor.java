package com.lmx.blog.config.redis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisExecutor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private static RedisTemplate<String, String> redisTemplateDao;
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
    public List<Object> getList(String key){
        List list = null;
        if(redisTemplate.opsForList().range(key,0,-1) != null){
            list= redisTemplate.opsForList().range(key,0,-1);
        }
        return list;
    }

    public <T> List<T> getList(String key, Class cls){
        List list = new ArrayList<T>();
        if(redisTemplate.opsForList().range(key,0,-1) != null){
            List jsonList = redisTemplate.opsForList().range(key,0,-1);
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

}
