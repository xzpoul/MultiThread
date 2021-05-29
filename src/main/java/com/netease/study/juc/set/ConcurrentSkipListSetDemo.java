package com.netease.study.juc.set;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentSkipListSetDemo {
    public static void main(String[] args) {
        // 可设置比较方式
        ConcurrentSkipListSet<String> skipListSet = new ConcurrentSkipListSet<>(String::compareTo);
        skipListSet.add("aa");
        skipListSet.add("ca");
        skipListSet.add("aa");
        skipListSet.add("da");


        Iterator<String> iterator = skipListSet.iterator();
        while (iterator.hasNext()) {
            // 不能一边遍历一边删除
            iterator.remove(); // IllegalStateException
            System.out.println(iterator.next()); // ConcurrentModificationException
        }
    }
}
