package com.netease.study.lock.sync;

// 锁消除(jit)
public class ObjectSyncDemo4 {
    public void test3(Object arg) {
        StringBuilder builder = new StringBuilder();
        builder.append("a");
        builder.append(arg);
        builder.append("c");
        System.out.println(arg.toString());
    }

    public void test2(Object arg) {
        String a = "a";
        String c = "c";
        System.out.println(a + arg + c);
    }


    public void test1(Object arg) {
        // jit 优化, 消除了锁

        // 相对保守的优化,从方法角度看,这段代码没有需要用到锁的,只有在这个线程栈里面使用.
        // 所以oracle才会给予锁消除优化
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("a");
        stringBuffer.append(arg);
        stringBuffer.append("c");
        // System.out.println(stringBuffer.toString());
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000000; i++) {
            new ObjectSyncDemo4().test1("123");
        }
    }

    /**
     * 锁消除
     *
     * StringBuffer 是线程安全的字符串拼接,
     * 每个append方法都有锁( public synchronized StringBuffer append(String str) )
     *
     * // jit 优化,不进入监视器,消除了锁
     * // 通过jitwatch查看
     *
     */
}
