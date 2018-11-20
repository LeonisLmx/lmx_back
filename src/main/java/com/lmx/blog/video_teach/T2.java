package com.lmx.blog.video_teach;

public class T2 {

    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName() + "m1 start...");
        try{
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "m1 end...");
    }

    public void m2(){
        try{
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "m2");
    }

    public static void main(String[] args) {
        T2 t = new T2();
        new Thread(t::m1,"t1").start();
        new Thread(()->t.m2(),"t2").start();
    }
}
