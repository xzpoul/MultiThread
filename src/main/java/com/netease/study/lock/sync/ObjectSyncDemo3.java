package com.netease.study.lock.sync;

// 锁粗化(运行时的 jit 编译优化)
// jit 编译后的汇编内容, jitwatch可视化工具进行查看
public class ObjectSyncDemo3 {
    int i;

    public void test1(Object arg) {
        synchronized (this) {
            i++;
        }
        synchronized (this) {
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000000; i++) {
            new ObjectSyncDemo3().test1("a");
        }
    }

    /**
     * 当循环调用很多次某段代码,即热点代码,JVM认为可以代码可以被优化
     *
     * 如上图 2次锁过程 synchronized 可以优化为1次synchronized(锁粗化,如锁的范围扩大了)
     *
     * jit 编译后的汇编内容, jitwatch可视化工具进行查看
     */
}
