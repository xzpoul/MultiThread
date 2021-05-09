package com.xuz.ReentrantLock.multiCondition.error;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * MustUserMoreCondition_Error
 *
 * @author xuzhou
 * @version 1.0
 * @date 2021/5/9 16:24
 */
public class MyService {
    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void awaitA() {
        try {
            lock.lock();
            System.out.println("begin awaitA 时间为:" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            condition.await();
            System.out.println("end awaitA 时间为:" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void awaitB() {
        try {
            lock.lock();
            System.out.println("begin awaitB 时间为:" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            condition.await();
            System.out.println("end awaitB 时间为:" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void signalAll() {
        try {
            lock.lock();
            System.out.println("signalAll时间为 " + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            //Wakes up multi waiting thread.
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
