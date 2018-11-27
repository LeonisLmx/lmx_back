package com.lmx.blog;

import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void testTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse("2018-11",new ParsePosition(0)));
        Map<Object,Integer> map = getMonthInfo(calendar);
        /*Map<Object,Integer> map = getMonthInfo(Calendar.getInstance());*/
        System.out.println(map.toString());
    }

    private static Map getMonthInfo(Calendar calendar) {
        Map<Object, Integer> map = new HashMap<>();
        int workDays = 0;
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        try {
            calendar.set(Calendar.DATE, 1);//从每月1号开始
            for (int i = 0; i < days; i++) {
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
                    workDays++;
                }
                calendar.add(Calendar.DATE, 1);
            }
            map.put("workDaysAmount", workDays);//工作日
            map.put("year", calendar.get(Calendar.YEAR));//实时年份
            map.put("month", calendar.get(Calendar.MONTH));//实时月份
            map.put("daysAmount", days);//自然日
            map.put("weeksAmount", calendar.getActualMaximum(Calendar.WEEK_OF_MONTH));//周
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
