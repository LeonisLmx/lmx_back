package com.lmx.blog;

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
}
