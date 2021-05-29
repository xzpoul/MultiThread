package com.netease.study.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;


// 它是基于数组的阻塞循环队列， 此队列按 FIFO（先进先出）原则对元素进行排序。
public class ArrayBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(3, false);
        //    final Object[] items;
        //    int takeIndex;
        //    int putIndex;
        //    int count;
        //    final ReentrantLock lock;
        //    private final Condition notEmpty;
        //    private final Condition notFull;

        // 1. 基于数组实现存储
        // 2. 线程安全 Condition+ReentrantLock(wait/notify基于锁的一个变种)等待/通知机制 (问与wait/notify/notifyAll的区别)
        //  底层实现即 AQS(记录等待的对象)+进行线程通信->等待/唤醒park/unpark
        // 3.  为什么while而不用if判断,防止伪唤醒
        // while(count==items.length)
        //    notFull.await()

        // 构造时需要指定容量(量力而行),可以选择是否需要公平（最先进入阻塞的，先操作）
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3, false);
        // 1秒消费数据一个
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("取到数据：" + queue.poll()); // poll非阻塞
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
            }
        }).start();

        Thread.sleep(3000L); // 让前面的线程跑起来

        // 三个线程塞数据
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                     queue.put(Thread.currentThread().getName()); // put阻塞(如果当前的队列已经塞满了数据，线程不会继续往下执行，等待其他线程把
                    // 队列的数据拿出去// )
//                    queue.offer(Thread.currentThread().getName()); // offer非阻塞，满了返回false
                    System.out.println(Thread.currentThread() + "塞入完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
