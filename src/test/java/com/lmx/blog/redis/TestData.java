package com.lmx.blog.redis;

import com.lmx.blog.model.result.ZsetTestModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestData {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testHyper(){
        /*for(int i=0;i<100;i++){
            redisTemplate.opsForHyperLogLog().add("test","tests" + i);
        }
        for(int i=0;i<100;i++){
            redisTemplate.opsForHyperLogLog().add("user","user" + i);
        }*/
        System.out.printf("%d %d\n", 200, redisTemplate.opsForHyperLogLog().size("test","user"));
        System.out.printf("%d\n", redisTemplate.opsForHyperLogLog().size("test"));
        System.out.printf("%d\n", redisTemplate.opsForHyperLogLog().size("user"));
    }

    @Test
    public void testList(){
        ZsetTestModel zsetTestModel = new ZsetTestModel();
        for(int i=0;i<300;i++) {
            zsetTestModel.setAuthor("user" + i);
            zsetTestModel.setCount(i);
            redisTemplate.opsForList().leftPush("test_list",zsetTestModel);
            if(redisTemplate.opsForList().size("test_list") > 200){
                redisTemplate.opsForList().rightPop("test_list");
            }
        }
        List<ZsetTestModel> list = redisTemplate.opsForList().range("test_list",0,300);
        System.out.println(list.size() + "----------");
        list.forEach((n) -> {
            System.out.println(n.getCount());
        });
    }
}
