package com.lmx.blog;

import com.google.gson.Gson;
import com.lmx.blog.service.EmailService;
import javafx.application.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class Test {

    /*@Autowired(required = true)
    private EmailService emailService;

    public void printNum()
    {
        int[][] arrays =
                {
                        { 1, 2, 3, 4 },
                        { 5, 6, 7, 8 },
                        { 9, 10, 11, 12 },
                        { 13, 14, 15, 16 } };
        // 矩阵行数
        int rows = arrays.length;
        // 矩阵列数
        int columns = arrays[0].length;

        ArrayList<Integer> list = new ArrayList<>();

        // 让循环继续的条件是当前行数大于该圈循环开始的行数的两倍以及当前列数大于该圈循环开始的列数的两倍（每圈循环开始的行数、列数相同）
        int start = 0;// 从(0,0)开始循环,圈数：start=0第一圈，start=1第二圈...以此类推
        while (rows > start * 2 && columns > start * 2)
        {
            // 每一圈最后一行下标
            int endRow = rows - 1 - start;
            // 每一圈最后一列下标
            int endColumn = columns - 1 - start;
            // 开始一圈圈打印，每打印一圈分为四步，从左到右、从上到下、从右到左、从下到上
            // 从左到右，第一步一定会走
            for (int i = start; i <= endColumn; i++)
                list.add(arrays[start][i]);
            // 从上到下，最后一行大于开始行
            if (endRow > start)
            {
                for (int i = start + 1; i <= endRow; i++)
                    list.add(arrays[i][endColumn]);
            }
            // 从右到左，最后一行大于开始行，最后一列大于开始列
            if (endRow > start && endColumn > start)
            {
                for (int i = endColumn - 1; i >= start; i--)
                    list.add(arrays[endRow][i]);
            }
            // 从下到上,至少是三行两列，也就是最后一行大于开始行加2，最后一列大于开始列
            if (endRow >= start + 2 && endColumn > start)
            {
                for (int i = endRow - 1; i > start; i--)
                    list.add(arrays[i][start]);
            }
            // 继续打印下一圈
            start++;
        }
        for (int i = 0; i < list.size(); i++)
        {
            System.out.print(list.get(i) + ",");
        }
    }

    @org.junit.Test
    public void testAuto(){
        Long startTime = System.currentTimeMillis();
        Long sum = 0L;
        for(Integer i = 0;i<Long.MAX_VALUE;i++){
            sum += i;
        }
        System.out.println(sum);
        Long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime)/1000);
    }*/

//    @org.junit.Test
//    public void sendSimpleMail() throws Exception {
//        emailService.sendSimpleEmail("805288035@qq.com","邮件发送主题","Hello World！");
//    }

    public static void main(String[] args) {
        String result = "{good_num=1365, create_time=2018-09-25 13:03:37.0, author=SnailClimb, modify_time=2018-12-19 20:00:01.0, xuehua_id=1064805571649077248, title=可能是一份最适合你的后端面试指南（部分内容前端同样适用）| 掘金技术征文, hot=1, type=post, article_url=https://juejin.im/post/5ba591386fb9a05cd31eb85f, tags=Java,前端,掘金技术征文,数据库,面试, is_origin=-1, author_url=https://juejin.im/user/59fbb2daf265da4319559f3a, message_num=34, id=1841}";
        Map<String,Object>  map = new Gson().fromJson(result,Map.class);
        System.out.println(map);
    }
}
