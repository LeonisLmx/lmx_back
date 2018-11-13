package com.lmx.blog.service;

import com.lmx.blog.common.SnowFlakeGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 刘明新
 * @date 2018/11/8 下午6:05
 */
@Service
public class Commonservice {

    // 雪花算法ID
    public static Long getNextId() {
        return new SnowFlakeGenerator(0, 0).nextId();
    }

    // 实体类转换为map
    public static Map<String,Object> convertBeanToMap(Object bean) throws IntrospectionException,IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map<String,Object> returnMap = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
       return returnMap;
    }

    // str to list
    public static List strToList(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        List<String> list = new LinkedList<>();
        String[] array = str.split(",");
        for(int i=0;i<array.length;i++){
            list.add(array[i]);
        }
        return list;
    }

    public static String getDateCompareNow(Calendar calendar) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        String strdate = null;
        if (year > 0) {
            strdate = year + "年前";
        } else if (year == 0) {
            int month = cal.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
            if (month > 0) {
                strdate = month + "月前";
            } else if (month == 0) {
                int day = cal.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);
                if (day > 0) {
                    strdate = day + "天前";
                } else if (day == 0) {
                    int hour = cal.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY);
                    if (hour > 0) {
                        strdate = hour + "小时前";
                    } else if (hour == 0) {
                        int minute = cal.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY);
                        if (minute > 0) {
                            strdate = minute + "分钟前";
                        } else if (minute == 0) {
                            strdate = "刚刚";
                        }
                    }
                    // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); //
                    // 格式化对象
                    // strdate = sdf.format(calendar.getTime()); // 输出格式化的日期
                    // strdate = DateUtil.dateToString(date,"HH:mm");
                }
            }
        } else {
            return "-1"; // 返回了一个负数
        }
        return strdate;
    }
}
