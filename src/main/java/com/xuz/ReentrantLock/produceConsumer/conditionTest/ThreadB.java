package com.xuz.ReentrantLock.produceConsumer.conditionTest;



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
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            myService.get();
        }
    }
}
