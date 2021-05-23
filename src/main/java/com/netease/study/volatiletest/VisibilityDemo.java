package com.netease.study.volatiletest;

import java.util.concurrent.TimeUnit;

// 1、 jdk.1.8.0_144/jre/bin/server  放置hsdis-i286.dll动态链接库

//  VM options 测试代码 将运行模式设置为-server(运行期的编译优化只有服务器模式下才会出现这个问题),变成死循环。
//  没加默认就是client模式，就是正常（可见性问题） 没生效???

// 2、 通过设置JVM的参数，打印出jit编译的内容 （这里说的编译非class文件），通过可视化工具jitwatch进行查看
// -server -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:+LogCompilation -XX:LogFile=jit.log

//  关闭jit优化-Djava.compiler=NONE
public class VisibilityDemo {

    // volatile 就不会再重排序
    // 内存模型的意义就在于通过它可以推测到程序运行的结果
    private boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        VisibilityDemo demo1 = new VisibilityDemo();
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                int i = 0;
                // class ->  运行时jit编译  -> 汇编指令 -> 重排序
                while (demo1.flag) { // 指令重排序
                    i++;
                }
                System.out.println(i);
            }
        });
        thread1.start();

        TimeUnit.SECONDS.sleep(2);
        // 设置is为false，使上面的线程结束while循环
        demo1.flag = false;
        System.out.println("被置为false了.");
    }

    /**
     * 从内存结构到内存模型
     *
     * JVM 内存结构
     *
     *  thread1                 thread2
     *
     *  线程栈工作内存(cpu1缓存) 线程栈工作内存(cpu2缓存)
     *
     *
     *                   主内存
     *
     * 因为有这种内存结构,在多线程下数据交互会有各种情况出现
     *
     *  Java内存模型
     *
     *  内存模型描述程序的可能行为(或者说java语言中做了一些规范,规定什么时候读内存数据
     *  应该读到什么样子的内容.)
     *
     *  java编程语言内存模型通过检查执行跟踪操作中的每个读操作,
     *  并根据根据某些规则检查该读操作观察到的写操作是否有效来工作.
     *
     *
     *  只要程序的素有执行产生的结果都可以由内存模型预测,具体的实现者任意实现,
     *  包括操作的重新排序和删除不必要的同步.
     *
     *  !!!重点: 内存模型决定了程序在每个点上可以读取什么值
     *
     *
     *
     */
}
