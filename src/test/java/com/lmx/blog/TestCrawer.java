package com.lmx.blog;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lmx.blog.common.HttpClient;
import com.lmx.blog.model.result.JuejinEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 刘明新
 * @date 2018/11/8 下午4:52
 */
public class TestCrawer {

    public static void main(String[] args) {
        try{
            String response = HttpClient.Get("https://timeline-merger-ms.juejin.im/v1/get_entry_by_rank?src=web&before=20&limit=20&category=5562b419e4b00c57d9b94ae2");
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
