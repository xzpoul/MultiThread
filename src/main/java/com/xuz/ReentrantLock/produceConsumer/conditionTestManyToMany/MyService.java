package com.xuz.ReentrantLock.produceConsumer.conditionTestManyToMany;

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
                System.out.println("有可能**连续");
                condition.await();
            }
            System.out.println("打印 *");
            hasValue = true;
            //            造成程序假死
//            condition.signal();
            condition.signalAll();
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
                System.out.println("有可能oo连续");
                condition.await();
            }
            System.out.println("打印 o");
            hasValue = false;
//            造成程序假死
//            condition.signal();
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
