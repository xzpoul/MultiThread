package com.xuz.ReentrantLock.condition;

/**
 * @author xuzhou
 * @version 1.0
 * @date 2021/5/7 22:31
 */
public class Run {

    public static void main(String[] args) throws InterruptedException {
        MyService myService = new MyService();
        ThreadA a = new ThreadA(myService);
        a.start();
        Thread.sleep(3000);
        myService.signal();
        // 程序输出:
        // awaiting中的Thread-0被main线程唤醒
        // Thread-0 await time:1620438361597
        // main signal time:1620438364607
    }
}
