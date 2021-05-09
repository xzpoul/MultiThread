package com.xuz.ReentrantLock.produceConsumer.conditionTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock实现生产者消费者模式:一对一交替打印
 *
 * @author xuzhou
 * @version 1.0
 * @date 2021/5/9 16:24
 */
public class MyService {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private boolean hasValue = false;

    public void set() {
        try {
            lock.lock();
            while (hasValue == true) {
                condition.await();
            }
            System.out.println("打印 *");
            hasValue = true;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void get() {
        try {
            lock.lock();
            while (hasValue == false) {
                condition.await();
            }
            System.out.println("打印 o");
            hasValue = false;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
