package com.lmx.blog;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 刘明新
 * @date 2018/11/8 下午4:52
 */
public class TestCrawer {

    /*public static void main(String[] args) {
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
    }*/

    @Test
    public void testSerizable(){
        double result = Math.pow(2,64);
        DecimalFormat decimalFormat=new DecimalFormat("0");
        System.out.println(decimalFormat.format(result));
    }

    @Test
    public void testList(){
        List<Integer> list = new LinkedList<>();
        for(int i=0;i<300;i++){
            list.add(0,i);
        }
        list = list.subList(0,200);
        System.out.println(list.size());
        list.forEach((v) -> {
            System.out.println(v);
        });
    }
}
