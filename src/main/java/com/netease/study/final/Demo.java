package com.study.finaldemo;

/**
 * final在JMM中的处理
 */
public class Demo {
    public static final String a = "hello tony";

    public static void main(String[] args) {
    }
}

// 官方示例，可能会读取到y的值为0
class FinalFieldExample {
    final int x;
    int y;
    static FinalFieldExample f;

    /**
     * final在该对象的构造函数中设置对象的字段，
     * 当线程看到该对象时，始终看到该对象的final字段的正确构造版本。
     * 如 x为final字段 f.x一定最新，
     */
    public FinalFieldExample() {
        x = 3;
        y = 4;
    }

    static void writer() {
        f = new FinalFieldExample();
    }

    static void reader() {
        if (f != null) {
            int i = f.x;  // guaranteed to see 3 肯定是3
            int j = f.y;  // could see 0 可能看到0
        }
    }
}

// 官方示例，new A().f() 可能返回 -1, 0, or 1.
class A {
    final int x;

    A() {
        x = 1;
    }

    int f() {
        return d(this, this);
    }

    int d(A a1, A a2) {
        int i = a1.x;
        g(a1);
        int j = a2.x;
        return j - i;
    }

    static void g(A a) {
        // uses reflection to change a.x to 2
        // 使用反射技术修改A.x为2
    }
}