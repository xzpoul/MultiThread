package com.xuz.ReentrantLock.produceConsumer.conditionTest;


/**
 * Run
 *
 * @author 18736
 * @version 1.0
 * 2021/5/9 16:15
 **/
public class Run {
    public static void main(String[] args) throws InterruptedException {
        MyService myService = new MyService();
        ThreadA a = new ThreadA(myService);
        a.setName("A");
        a.start();
        ThreadB b = new ThreadB(myService);
        b.setName("B");
        b.start();
    }
}
