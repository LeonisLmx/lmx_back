package com.lmx.blog.video_teach;

public class DeadLock {

    synchronized void o1(){
        o2();
    }

    synchronized void o2(){
        o1();
    }

    public static void main(String[] args) {
        DeadLock t = new DeadLock();
        new Thread(t::o1).start();
        new Thread(t::o2).start();
    }
}
