#第一章 Java多线程技能
重点掌握关键技术点:

1. 线程的启动
2. 如何使线程暂停
3. 如何使线程停止
4. 线程的优先级
5. 线程安全相关的问题

## 1.1 进程和多线程的概念及线程的优点
  查看"windows任务管理器"中的列表,完全可以将运行在内存中的exe文件理解成进程,
   进程是受操作系统管理的基本运行单元.
   
   线程可以理解成是在进程中独立运行的子任务,比如QQ(消息,视频等)
   使用多线程技术优点:可以在同一时间内运行更多不同种类的任务.

## 1.2 使用多线程
  一个进程正在运行时至少会有1个线程在运行,调用main()方法的线程即如此,且是由JVM创建的.

## 1.2.1 继承Thread类
  实现多线程编程方式:
  1. 继承Thread类
  2. 实现Runnable接口
  
  public class Thread implements Runnable
  Thread类实现了Runnable接口,它们之间具有多态关系.
  
  继承Thread类创建线程时,最大的局限就是不支持多继承.
  为了支持多继承,可以实行Runnable接口的方式.
  
  线程具有随机性
  
  Thread.java类中的start()方法通知"线程规划器"此线程已经准备好就绪,等待调用线程对象的run()方法.
  这个过程其实就是让系统安排一个时间来调用Thread中的run()方法,也就是使线程得到运行.
  启动线程,具有异步执行的效果.(执行start()顺序不代表线程启动的顺序)
  如果调用代码thread.run()就不是异步执行了,而是同步,那么此线程对象并不交给"线程规划器"来进行处理,
  而是由主线程来调用run()方法,也就是必须等run()方法中的代码执行完以后才可以执行后面的代码.
  
## 1.2.2 实现Runnable接口
  如果预创建的线程类已经有一个父类了,这时就不能再继承Thread类了,
  因为Java不支持多继承,所以需要实现Runnable接口类应对.
  
## 1.2.3 实例变量与线程安全
  在某些JVM中,i--的操作要分成3步:
  1. 取得原i值
  2. 计算i-1
  3. 对i赋值
  
  术语:非线程安全
  主要指: 多个线程对同一对象中的同一个实例变量进行操作时会出现值被更改、值不同步的情况，进而影响程序执行流程。

## 1.2.4 留意i--与System.out.println()的异常
 
 ```java 
    public class MyThread extends Thread{
        private int i = 5;
        @Override
        public void run(){
               i--;
            System.out.println("i="+i +"threadName="Thread.currentThread().getName());
       }
  }

    public class MyThread extends Thread{
        private int i = 5;
        @Override
        public void run(){
            System.out.println("i="+(i--) +"threadName="Thread.currentThread().getName());
            // 注意：代码i-- 由前面单独一行改成再println()方法中直接打印
       }
  }

    public void println(Object x) {
        String s = String.valueOf(x);
        synchronized(this) {
            this.print(s);
            this.newLine();
        }
    }
 ```
  测试目的：虽然println()方法在内部是同步的，但i--的操作却是在进入println()之前发生的，
  所以有发生非线程安全问题的概率。 
  
## 1.3 currentThread()方法
  返回当前代码段正在被哪个线程调用的信息。
## 1.4 isAlive()方法
   检查线程是否处于活动状态
   活动状态： 线程已经启动且尚未终止。
   线程正处于正在运行或准备开始运行的状态，就认为线程是“存活”的。
   new Thread isAlive=false
   start      isAlive=true
   run        isAlive=true
## 1.5 sleep()
   指定毫秒数让当前“正在执行的线程”休眠（暂停执行）
## 1.6 getId()
  取得线程的唯一标识

## 1.7 停止线程
  在Java中有以下3种可以终止正在运行的线程：
  1. 使用退出标志，使线程正常退出，也就是当run方法完成后线程终止。
  2. 使用stop方法终止线程，不推荐，因为stop和suspend及resume一样，可能产生不可预料的结果。
  3. 使用interrupt中断线程。
  
### 1.7.1 停止不了的线程
  调用interrupt()方法不像for+break那样，马上停止循环。
  interrupt仅仅是在当前线程中打了一个停止标记，并不是真正停止线程。

### 1.7.2 判断线程是否是停止状态
   Java的SDK中，Thread.java类里提供了两种方法。
   1. this.interrupted() 静态方法
   ```java
   public static boolean interrupted()
  ```
   测试当前线程是否已经中断。线程的中断状态由该方法清除。
   换句话说，如果连续两次调用该方法，则第二次调用将返回false
   （在第一次调用已清除了其中断线程之后，且第二次调用检查完中断状态前，当前线程再次中断的情况除外）。
   2. this.isInterrupted()
    ```java
      public boolean isInterrupted()
     ```
    测试线程Thread对象是否已经是中断状态，但不清除状态标志。
 
### 1.7.3 能停止的线程---异常法
     ```java
     @Override
     public void run() {
     try{
       for () {
         if(this.interrupted()){
            throw new InterruptedException();
         }
       }
       }catch(InterruptedExecption e){
           e.printStackTrace();      
       }
     
     }
     
     ```
### 1.7.4 在沉睡中停止
  先sleep后interrupt()
  如果在sleep状态下停止某一线程，会进入catch语句，并且清除停止状态值，即this.isInterrupted()返回false
  
  先interrupt()后sleep
  先停止，再遇到了sleep，进入catch
  
### 1.7.5 能停止的线程---暴力停止
  stop()停止线程是非常暴力的
  
  stop()已经作废，因为强制线程停止则可能使一些清理性的工作得不到完成。
  另一个情况就是对锁定的对象进行“解锁”，导致数据得不到同步的处理，出席那数据不一致的问题。
 
### 1.7.5 释放锁的不良后果 
  不正确的线程中止
  stop： 中止线程，并清除监控器锁的信息，但是可能导致线程安全问题，JDK不建议用。
  com.netease.study.chapter1.thread.StopThread

### 1.7.6 使用return停止线程
   建议使用 抛异常 的方式实现线程的停止，因为在catch块中还可以将异常上抛，
   使得线程停止的事件得以传播。
       ```java
       @Override
       public void run() {
       try{
         for () {
           if(this.interrupted()){
            return;
           }
         }
         }catch(InterruptedExecption e){
             e.printStackTrace();      
         }
       
       }
       
       ```
## 1.8 暂停线程
  暂停线程意味着此线程还可以恢复运行。
  在Java多线程中，可以使用suspend()方法暂停线程，使用resume()方法恢复线程的执行

### 1.8.1 suspend和resume方法的使用（略）

### 1.8.2 suspend和resume方法的缺点--独占
  
  如果使用不当，极易造成公共的同步对象的独占，使得其他线程无法访问公共同步对象，
  即无法释放锁。
  
  即a线程suspend 同步方法A，b线程无法获取同步方法A的锁，除非resume a线程。
  
  同样如果锁对象相同，如果suspend后调用println()（synchronized）也会一直等待，无法获取锁。、
 
### 1.8.3 suspend和resume方法的缺点--不同步

   暂停、恢复拆分了代码逻辑单元，使得数据不同步。
   
## 1.9 yield方法
   yield 让路，屈服的意思
   
   yield()方法的作用是放弃当前的CPU资源，将它让给其他的任务去占用CPU执行事件。
   但放弃的时间不确定，有可能刚刚放弃，马上又获得CPU时间片。
   
   thread.yield() 将CPU让给其他资源导致速度变慢。
   
   
## 1.10 线程的优先级
   操作系统中，线程可以划分优先级，优先级较高的线程得到的CPU资源较多，也就是CPU尽量执行优先级较高的线程对象任务。
   Java中，优先级分为：1~10个等级
   
   ```java
    public final static int MIN_PRIORITY = 1;
    public final static int NORM_PRIORITY = 5;
    public final static int MAX_PRIORITY = 10;
   ```
   
### 1.10.1 线程优先级的继承特性
  在Java中，线程的优先级具有继承性，比如A线程启动B线程，则B线程的优先级与A是一样的。
### 1.10.2 线程优先级的规则性
  CPU尽量将执行资源让给优先级比较高的线程
### 1.10.3 线程优先级的随机性
   不要把线程的优先级与运行结果的顺序作为衡量标准，优先级较高的线程不一定每次都先执行完run()方法中的任务，
   也就是说，线程优先级与打印顺序无关。
### 1.10.4 看谁运行的快
   优先级高的运行的快 
   
## 1.11 守护线程
   在Java线程中有两种线程，一种是用户线程，另一种是守护线程。
   守护线程，特性有"陪伴"的含义，
   当进程中不存在非守护线程了，则守护线程自动销毁。
   典型的守护线程就是GC垃圾回收线程。
  