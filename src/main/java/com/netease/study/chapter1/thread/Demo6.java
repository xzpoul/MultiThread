package com.netease.study.chapter1.thread;

import java.util.concurrent.locks.LockSupport;

/** �����߳�Э��ͨ�ŵķ�ʽ��suspend/resume��wait/notify��park/unpark */
public class Demo6 {
	/** ���ӵ� */
	public static Object baozidian = null;

	/** ������suspend/resume */
	public void suspendResumeTest() throws Exception {
		// �����߳�
		Thread consumerThread = new Thread(() -> {
			if (baozidian == null) { // ���û���ӣ������ȴ�
				System.out.println("1������ȴ�");
				Thread.currentThread().suspend();
			}
			System.out.println("2���򵽰��ӣ��ؼ�");
		});
		consumerThread.start();
		// 3��֮������һ������
		Thread.sleep(3000L);
		baozidian = new Object();
		consumerThread.resume();
		System.out.println("3��֪ͨ������");
	}

	/** ������suspend/resume�� suspend��������waitһ���ͷ������ʴ�����д���������� */
	public void suspendResumeDeadLockTest() throws Exception {
		// �����߳�
		Thread consumerThread = new Thread(() -> {
			if (baozidian == null) { // ���û���ӣ������ȴ�
				System.out.println("1������ȴ�");
				// ��ǰ�߳��õ�����Ȼ�����
				synchronized (this) {
					Thread.currentThread().suspend();
				}
			}
			System.out.println("2���򵽰��ӣ��ؼ�");
		});
		consumerThread.start();
		// 3��֮������һ������
		Thread.sleep(3000L);
		baozidian = new Object();
		// ��ȡ�����Ժ��ٻָ�consumerThread
		synchronized (this) {
			consumerThread.resume();
		}
		System.out.println("3��֪ͨ������");
	}

	/** ���³������ù����suspend/resume */
	public void suspendResumeDeadLockTest2() throws Exception {
		// �����߳�
		Thread consumerThread = new Thread(() -> {
			if (baozidian == null) {
				System.out.println("1��û���ӣ�����ȴ�");
				try { // Ϊ����̼߳���һ����ʱ
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// ����Ĺ���ִ����resume����
				Thread.currentThread().suspend();
			}
			System.out.println("2���򵽰��ӣ��ؼ�");
		});
		consumerThread.start();
		// 3��֮������һ������
		Thread.sleep(3000L);
		baozidian = new Object();
		consumerThread.resume();
		System.out.println("3��֪ͨ������");
		consumerThread.join();
	}

	/** ������wait/notify */
	public void waitNotifyTest() throws Exception {
		// �����߳�
		new Thread(() -> {
				synchronized (this) {
					while (baozidian == null) { // ���û���ӣ������ȴ�
					try {
						System.out.println("1������ȴ�");
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("2���򵽰��ӣ��ؼ�");
		}).start();
		// 3��֮������һ������
		Thread.sleep(3000L);
		baozidian = new Object();
		synchronized (this) {
			this.notifyAll();
			System.out.println("3��֪ͨ������");
		}
	}

	/** �ᵼ�³������õȴ���wait/notify */
	public void waitNotifyDeadLockTest() throws Exception {
		// �����߳�
		new Thread(() -> {
			if (baozidian == null) { // ���û���ӣ������ȴ�
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				synchronized (this) {
					try {
						System.out.println("1������ȴ�");
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("2���򵽰��ӣ��ؼ�");
		}).start();
		// 3��֮������һ������
		Thread.sleep(3000L);
		baozidian = new Object();
		synchronized (this) {
			this.notifyAll();
			System.out.println("3��֪ͨ������");
		}
	}

	/** ������park/unpark */
	public void parkUnparkTest() throws Exception {
		// �����߳�
		Thread consumerThread = new Thread(() -> {
			while (baozidian == null) { // ���û���ӣ������ȴ�
				System.out.println("1������ȴ�");
				LockSupport.park();
			}
			System.out.println("2���򵽰��ӣ��ؼ�");
		});
		consumerThread.start();
		// 3��֮������һ������
		Thread.sleep(3000L);
		baozidian = new Object();
		LockSupport.unpark(consumerThread);
		System.out.println("3��֪ͨ������");
	}

	/** ������park/unpark */
	public void parkUnparkDeadLockTest() throws Exception {
		// �����߳�
		Thread consumerThread = new Thread(() -> {
			if (baozidian == null) { // ���û���ӣ������ȴ�
				System.out.println("1������ȴ�");
				// ��ǰ�߳��õ�����Ȼ�����
				synchronized (this) {
					LockSupport.park();
				}
			}
			System.out.println("2���򵽰��ӣ��ؼ�");
		});
		consumerThread.start();
		// 3��֮������һ������
		Thread.sleep(3000L);
		baozidian = new Object();
		// ��ȡ�����Ժ��ٻָ�consumerThread
		synchronized (this) {
			LockSupport.unpark(consumerThread);
		}
		System.out.println("3��֪ͨ������");
	}

	public static void main(String[] args) throws Exception {
		// �Ե���˳����Ҫ��ҲҪ�����Լ�ע�������ͷš���������õ�API�� ����������Ҳ���׵������ù���
//		 new Demo6().suspendResumeTest();
//		 new Demo6().suspendResumeDeadLockTest();
//		 new Demo6().suspendResumeDeadLockTest2();

		// wait/notifyҪ����ͬ���ؼ�������ʹ�ã���ȥ�����������ţ�����һ��Ҫ�ȵ���wait���ٵ���notify���������õȴ���
		// new Demo6().waitNotifyTest();
//		 new Demo6().waitNotifyDeadLockTest();

		// park/unparkû��˳��Ҫ�󣬵���park�������ͷ�����������ͬ��������ʹ��Ҫע��
//		 new Demo6().parkUnparkTest();
		 new Demo6().parkUnparkDeadLockTest();

	}
}
