package com.netease.study.juc.map;

import java.util.HashMap;

// 测试将对象作为key
public class ObjectHashMapDemo {

    public static void main(String[] args) {
        HashMap<User, String> map = new HashMap<>();
        // 同一个引用,引用不同的对象
        User user = new User("tony");
        map.put(user, "test");
        System.out.println(map.get(user)); // 1、 输出什么  test

        user = new User("tony");
        System.out.println(map.get(user));  // 2、 输出什么 null

        // 如何判断同一个key?
        // 用user.equals 对比
    }
}

class User {
    public String name;

    public User(String name) {
        this.name = name;
    }


    // 重写以下两个方法才可以

    // 重写equals
    @Override
    public boolean equals(Object obj) {
        return name.equals(((User) obj).name);
    }

    // 求hashCode时,会用到hashCode
    //   static final int hash(Object key) {
    //        int h;
    //        return key == null ? 0 : (h = key.hashCode()) ^ h >>> 16;
    //    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

