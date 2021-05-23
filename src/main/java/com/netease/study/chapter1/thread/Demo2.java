package com.netease.study.chapter1.thread;

/**
 * ʾ��2 - ���߳�����״̬�л�ʾ�� <br/>
 */
public class Demo2 {
	public static Thread thread1;
	public static Demo2 obj;

	public static void main(String[] args) throws Exception {
		// ��һ��״̬�л� - �½� -> ���� -> ��ֹ
		System.out.println("#######��һ��״̬�л�  - �½� -> ���� -> ��ֹ################################");
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("thread1��ǰ״̬��" + Thread.currentThread().getState().toString());
				System.out.println("thread1 ִ����");
			}
		});
		System.out.println("û����start������thread1��ǰ״̬��" + thread1.getState().toString());
		thread1.start();
		Thread.sleep(2000L); // �ȴ�thread1ִ�н������ٿ�״̬
		System.out.println("�ȴ����룬�ٿ�thread1��ǰ״̬��" + thread1.getState().toString());
		// thread1.start(); TODO ע�⣬�߳���ֹ֮���ٽ��е��ã����׳�IllegalThreadStateException�쳣

		System.out.println();
		System.out.println("############�ڶ��֣��½� -> ���� -> �ȴ� -> ���� -> ��ֹ(sleep��ʽ)###########################");
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {// ���߳�2�ƶ����ȴ�״̬��1500���Զ�����
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("thread2��ǰ״̬��" + Thread.currentThread().getState().toString());
				System.out.println("thread2 ִ����");
			}
		});
		System.out.println("û����start������thread2��ǰ״̬��" + thread2.getState().toString());
		thread2.start();
		System.out.println("����start������thread2��ǰ״̬��" + thread2.getState().toString());
		Thread.sleep(200L); // �ȴ�200���룬�ٿ�״̬
		System.out.println("�ȴ�200���룬�ٿ�thread2��ǰ״̬��" + thread2.getState().toString());
		Thread.sleep(3000L); // �ٵȴ�3�룬��thread2ִ����ϣ��ٿ�״̬
		System.out.println("�ȴ�3�룬�ٿ�thread2��ǰ״̬��" + thread2.getState().toString());

		System.out.println();
		System.out.println("############�����֣��½� -> ���� -> ���� -> ���� -> ��ֹ###########################");
		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (Demo2.class) {
					System.out.println("thread3��ǰ״̬��" + Thread.currentThread().getState().toString());
					System.out.println("thread3 ִ����");
				}
			}
		});
		synchronized (Demo2.class) {
			System.out.println("û����start������thread3��ǰ״̬��" + thread3.getState().toString());
			thread3.start();
			System.out.println("����start������thread3��ǰ״̬��" + thread3.getState().toString());
			Thread.sleep(200L); // �ȴ�200���룬�ٿ�״̬
			System.out.println("�ȴ�200���룬�ٿ�thread3��ǰ״̬��" + thread3.getState().toString());
		}
		Thread.sleep(3000L); // �ٵȴ�3�룬��thread3ִ����ϣ��ٿ�״̬
		System.out.println("�ȴ�3�룬��thread3���������ٿ�thread3��ǰ״̬��" + thread3.getState().toString());

	}
}