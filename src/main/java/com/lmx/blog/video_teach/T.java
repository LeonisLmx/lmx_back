package com.lmx.blog.video_teach;

public class T {

    private static int a = 10;

    // 等同于下面的写法。静态方法锁定的是当前类
    private synchronized static void e(){

    }

    private static void f(){
        synchronized (T.class){

        }
    }
}
