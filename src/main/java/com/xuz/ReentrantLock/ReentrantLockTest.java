package com.xuz.ReentrantLock;

/**
 * @author xuzhou
 * @version 1.0
 * @date 2021/5/7 21:36
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        MyService myService = new MyService();

        ThreadB a = new ThreadB(myService);
        a.setName("A");
        a.start();

        ThreadA b = new ThreadA(myService);
        b.setName("B");
        b.start();

    }
}
