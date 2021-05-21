package com.netease.study.lock.sync;

// 锁 方法(静态/非静态),代码块(对象/类)
public class ObjectSyncDemo1 {

    static Object temp = new Object();

    public void test1() {
//        synchronized (temp) {
        synchronized (ObjectSyncDemo1.class) {
            try {
                System.out.println(Thread.currentThread() + " 我开始执行");
                Thread.sleep(3000L);
                System.out.println(Thread.currentThread() + " 我执行结束");
            } catch (InterruptedException e) {
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            new ObjectSyncDemo1().test1();
        }).start();

        Thread.sleep(1000L); // 等1秒钟,让前一个线程启动起来
        new Thread(() -> {
            new ObjectSyncDemo1().test1();
        }).start();
    }

    /**
     * 2021-5-16 笔记 1.2.3 Java锁相关
     *
     * demo1: synchronized test1() 方法加 对象锁,两个线程各自new对象并不能实现同
     *
     * demo2: synchronized static test1() 方法加 类锁,两个线程调用同一类实现同步
     *
     *
     * demo1/2 lock的对象不同
     *
     * demo3:  synchronized (temp) {
     *
     * 方法使用 代码块使用 使用范围不同
     *
     * synchronized 悲观锁,独享 同一线程 只有一个线程能够去操作.
     *
     *
     */
}
