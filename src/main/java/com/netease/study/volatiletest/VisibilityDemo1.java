package com.netease.study.volatiletest;

import java.util.concurrent.TimeUnit;

public class VisibilityDemo1 {
    // 状态标识
    private static boolean is = true;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (VisibilityDemo1.is) {
                    synchronized (this) {
                        // 虚拟机优化保守，即遵循happens-before，多线程不一定发生什么
                        // 所以 synchronized就没有优化重排序，结果正常
                        i++;
                    }
                }
                System.out.println(i);
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 设置is为false，使上面的线程结束while循环
        VisibilityDemo1.is = false;
        System.out.println("被置为false了.");
    }
}
