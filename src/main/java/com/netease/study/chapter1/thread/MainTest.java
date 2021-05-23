package com.netease.study.chapter1.thread;

public class MainTest {
  // �������
  public static String content = "��";
  
  public static void main(String[] args) {
    // �߳�1 - д������
    new Thread(() -> {
      try {
        while (true) {
          content = "��ǰʱ��" + String.valueOf(System.currentTimeMillis());
          Thread.sleep(1000L);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();

    // �߳�2 - ��ȡ����
    new Thread(() -> {
      try {
        while (true) {
          Thread.sleep(1000L);
          System.out.println(content);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }
}
