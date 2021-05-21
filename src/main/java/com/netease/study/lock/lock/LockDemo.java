package com.netease.study.lock.lock;

import java.util.concurrent.atomic.AtomicInteger;

// 两个线程，对 i 变量进行递增操作
public class LockDemo {

    //  此处并不是变量可见性的问题,这里使用volatile还是会导致线程安全问题.
    // volatile只能保证可见性,不能保证原子性操作
    // 例如 两个线程都读的是1,分别修改它不就出问题了吗
    // volatile int i = 0;
    AtomicInteger i = new AtomicInteger(0);

    public void add() {
        // TODO xx00
        // i++;// 三个步骤   // Terminal out目录下 java -p -v LockDemo.class 查看反编译
        i.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo ld = new LockDemo();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(ld.i);
    }

}
