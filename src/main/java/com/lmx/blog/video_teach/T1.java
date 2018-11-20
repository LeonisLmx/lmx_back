package com.lmx.blog.video_teach;

public class T1 implements Runnable {

    private /*volatile*/ int count = 10;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "count=" + --count);
    }

    public static void main(String[] args) {
        T1 t = new T1();
        for(int i=0;i<5;i++){
            new Thread(t,"THREAD" + i).start();
        }
    }
}
