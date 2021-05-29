package com.netease.study.juc.set;

import java.util.HashSet;
import java.util.Iterator;

public class HashSetDemo {
    //     static final long serialVersionUID = -5024744406713321676L;
    //    private transient HashMap<E, Object> map;
    //    private static final Object PRESENT = new Object();

    // HashSet本质就是HashMap

    // HashSet即对象存于key
    //  public boolean add(E e) {
    //        return this.map.put(e, PRESENT) == null;
    //    }

    // 遍历HashSet即遍历HashMap的keySet
    // public Iterator<E> iterator() {
    //        return this.map.keySet().iterator();
    //    }

    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("aa");
        hashSet.add("ca");
        hashSet.add("aa");
        hashSet.add("da");

        Iterator<String> iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
