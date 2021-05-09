package com.xuz.ReentrantLock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 4.1.4 正确使用Condition实现等待/通知
 *
 * @author xuzhou
 * @version 1.0
 * @date 2021/5/7 22:24
 */
public class MyService {
    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void await() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " await time:" + System.currentTimeMillis());
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signal() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " signal time:" + System.currentTimeMillis());
            //Wakes up one waiting thread.
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

}
