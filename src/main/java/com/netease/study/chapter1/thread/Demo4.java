package com.netease.study.chapter1.thread;

/** ͨ��״̬λ���ж� */
public class Demo4 extends Thread {
  public volatile static boolean flag = true;

  public static void main(String[] args) throws InterruptedException {
    new Thread(() -> {
      try {
        while (flag) { // �ж��Ƿ�����
          System.out.println("������");
          Thread.sleep(1000L);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
    // 3��֮�󣬽�״̬��־��ΪFalse��������������
    Thread.sleep(3000L);
    flag = false;
    System.out.println("�������н���");
  }
}
