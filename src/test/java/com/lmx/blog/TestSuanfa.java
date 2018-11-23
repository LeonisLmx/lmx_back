package com.lmx.blog;

import com.lmx.blog.serviceImpl.Commonservice;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.junit.Test;

import java.util.*;

public class TestSuanfa {

    @Test
    public void breakchain(){
        int A[] = {5,2,4,6,3,7} ;
        List<Integer> list = new LinkedList<>();
        for(int a=0;a<A.length;a++){
            list.add(A[a]);
        }
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        System.out.println(list.get(0) + list.get(1));
    }

    @Test
    public void choseMaxNum(){
        int A[] = {5,2,4,6,3,7} ;
        int N = 3;
        List<Integer> list = new LinkedList<>();
        for(int a=0;a<A.length;a++){
            list.add(A[a]);
        }
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        for(int i=0;i<3;i++){
            System.out.println(list.get(i));
        }
    }

    @Test
    public void maxSquare(){
        int N = 3;
        int M = 5;
        int min = N - M > 0?N:M;
        // 满足第一个条件。恒成立
        for(int L=0;L <= min;L++){

        }
    }

    @Test
    public void testPinyin() throws Exception{
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 输出拼音全部小写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V) ;
        System.out.println(PinyinHelper.toHanYuPinyinString("刘明新",defaultFormat,"",false));
    }

    @Test
    public void maopaoSort(){
        int[] arr={6,3,8,2,9,1};
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-1-i;j++){
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        for(int num:arr){
            System.out.println(num);
        }
    }

    public void sort(int[] a,int low,int high){
        int start = low;
        int end = high;
        int key = a[low];
        while(end>start){
            //从后往前比较
            while(end>start&&a[end]>=key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            if(a[end]<=key){
                int temp = a[end];
                a[end] = a[start];
                a[start] = temp;
            }
            //从前往后比较
            while(end>start&&a[start]<=key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
            if(a[start]>=key){
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
            }
            //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        //递归
        if(start>low) sort(a,low,start-1);//左边序列。第一个索引位置到关键值索引-1
        if(end<high) sort(a,end+1,high);//右边序列。从关键值索引+1到最后一个
    }

    @Test
    public void kuaisuSort(){
        int[] arr = {46,30,82,90,56,17,95,15};
        sort(arr,0,arr.length-1);
        for(int a:arr){
            System.out.println(a);
        }
    }

    @Test
    public void xuanzeSort(){
        int[] arr = {46,30,82,90,56,17,95,15};
        int temp = 0;
        for(int i=0;i<arr.length;i++){
            int min = i;
            for(int j=i+1;j<arr.length;j++){
                if(arr[min] > arr[j]){
                    min = j;
                }
            }
            temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
        for(int a:arr){
            System.out.println(a);
        }
    }

    @Test
    public void charuSort(){
        int[] arr = {46,30,82,90,56,17,95,15};
        int temp = 0;
        int j=0;
        for(int i=1;i<arr.length;i++){
            temp = arr[i];
            //假如temp比前面的值小，则将前面的值后移
            for(j = i ; j > 0 && temp < arr[j-1] ; j --)
            {
                arr[j] = arr[j-1];
            }
            arr[j] = temp;
        }
        for(int a:arr){
            System.out.println(a);
        }
    }

    @Test
    public void testXuehua(){
        System.out.println(Commonservice.getNextId());
    }
}
