package com.netease.study.lock.aqs.jucdemo.lock;


import com.netease.study.lock.aqs.AQSdemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// 自己实现锁
public class NeteaseAqsLock implements Lock {

    AQSdemo aqSdemo = new AQSdemo() {
        @Override
        public boolean tryAcquire() {
            return owner.compareAndSet(null, Thread.currentThread());
        }

        @Override
        public boolean tryRelease() {
            return owner.compareAndSet(Thread.currentThread(), null);
        }
    };

    @Override
    public boolean tryLock() {
        return aqSdemo.tryAcquire();
    }

    @Override
    public void lock() {
        aqSdemo.acquire();
    }

    @Override
    public void unlock() {
        aqSdemo.release();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }

    public static void main(String[] args) {
        // lock Test
    }
}
