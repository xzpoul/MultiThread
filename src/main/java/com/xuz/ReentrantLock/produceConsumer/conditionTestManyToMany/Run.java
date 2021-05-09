package com.xuz.ReentrantLock.produceConsumer.conditionTestManyToMany;


/**
 * Run
 *
 * @author 18736
 * @version 1.0
 * 2021/5/9 16:15
 **/
public class Run {
    public static void main(String[] args) throws InterruptedException {
        MyService myService = new MyService();
        ThreadA[] threadA = new ThreadA[10];
        ThreadB[] threadB = new ThreadB[10];

        for (int i = 0; i < 10; i++) {
            threadA[i] = new ThreadA(myService);
            threadB[i] = new ThreadB(myService);
            threadA[i].start();
            threadB[i].start();
        }
        // 使用condition.signal()运行后出现假死
        // 根据第3章中的notifyAll()解决方案,可以使用signalAll()方法来解决.
        // 将MyService.java类中的两处signal()代码改成signalAll()后,程序得到正确运行
    }
}
