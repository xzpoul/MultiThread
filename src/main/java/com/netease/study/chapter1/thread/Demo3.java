package com.netease.study.chapter1.thread;

/**
 * 示例3 - 线程stop强制性中止，破坏线程安全的示例
 */
public class Demo3 {
    public static void main(String[] args) throws InterruptedException {
        StopThread thread = new StopThread();
        thread.start();
        // 休眠1秒，确保i变量自增成功
        Thread.sleep(1000);
        // 暂停线程
//          thread.stop(); // 错误中止
        thread.interrupt(); // 正确终止
        while (thread.isAlive()) {
            // 确保线程已终止
        } // 输出结果
        thread.print();
    }
}
