package com.xuz.ReentrantLock.multiCondition.ok;


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
        Thread.sleep(3000);
        myService.signalAll_A();
    }
}
