package com.xuz.ReentrantLock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuzhou
 * @version 1.0
 * @date 2021/5/7 22:23
 */
public class UserConditionWaitNotifyOK {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    /**
     * UseConditionWaitNotifyError
     * <p>
     * // 如同上位注释所说明的,以下方法会抛出监视器错误,
     * // 解决办法是必须在condition.await()方法之前调用lock.lock()代码获得同步监视器
     * public void await() {
     * try {
     * // todo lock.lock();
     * lock.lock();
     * System.out.println("await before");
     * condition.await();
     * System.out.println("await after");
     * } catch (InterruptedException e) {
     * e.printStackTrace();
     * } finally {
     * lock.unlock();
     * }
     * }
     */

    public void await() {
        try {
            lock.lock();
            System.out.println("await time:" + System.currentTimeMillis());
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
            System.out.println("signal time:" + System.currentTimeMillis());
            condition.signal();
        } finally {
            lock.unlock();
        }
    }


}
