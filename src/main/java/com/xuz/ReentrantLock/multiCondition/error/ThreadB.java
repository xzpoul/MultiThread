package com.xuz.ReentrantLock.multiCondition.error;


/**
 * @author xuzhou
 * @version 1.0
 * @date 2021/5/7 21:46
 */
public class ThreadB extends Thread {

    private MyService myService;

    public ThreadB(MyService myService) {
        super();
        this.myService = myService;
    }

    @Override
    public void run() {
        myService.awaitB();
    }
}
