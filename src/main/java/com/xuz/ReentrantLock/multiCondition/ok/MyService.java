package com.xuz.ReentrantLock.multiCondition.ok;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * MustUserMoreCondition_Ok
 *
 * @author xuzhou
 * @version 1.0
 * @date 2021/5/9 16:24
 */
public class MyService {
    private Lock lock = new ReentrantLock();

    private Condition conditionA = lock.newCondition();

    private Condition conditionB = lock.newCondition();

    public void awaitA() {
        try {
            lock.lock();
            System.out.println("begin awaitA 时间为:" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            conditionA.await();
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
            conditionB.await();
            System.out.println("end awaitB 时间为:" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void signalAll_A() {
        try {
            lock.lock();
            System.out.println("signalAll_A时间为 " + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            //Wakes up multi waiting thread.
            conditionA.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void signalAll_B() {
        try {
            lock.lock();
            System.out.println("signalAll_B时间为 " + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            //Wakes up multi waiting thread.
            conditionB.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
