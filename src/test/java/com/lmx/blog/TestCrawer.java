package com.lmx.blog;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lmx.blog.common.HttpClient;
import com.lmx.blog.model.result.JuejinEntity;
import com.lmx.blog.service.JuejinCrawerService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author 刘明新
 * @date 2018/11/8 下午4:52
 */
public class TestCrawer {

    public static void main(String[] args) {
        try{
            String response = HttpClient.Get("www.baidu.com");
            response = response.substring(response.indexOf("\"entrylist\":") + 12,response.lastIndexOf("}}"));
            List<JuejinEntity> reponseMap = new Gson().fromJson(response,new TypeToken<ArrayList<JuejinEntity>>(){}.getType());
            System.out.println(reponseMap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testRandom(){
        int a = new Random().nextInt(30);
        Float random = new Random().nextFloat();
        Float f = a + random;
        System.out.println(f);
    }
}
