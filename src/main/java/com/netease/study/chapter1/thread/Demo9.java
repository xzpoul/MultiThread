package com.netease.study.chapter1.thread;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** �̳߳ص�ʹ�� */
public class Demo9 {

	/**
	 * ���ԣ� �ύ15��ִ��ʱ����Ҫ3�������,���̳߳ص�״��
	 * 
	 * @param threadPoolExecutor ���벻ͬ���̳߳أ�����ͬ�Ľ��
	 * @throws Exception
	 */
	public void testCommon(ThreadPoolExecutor threadPoolExecutor) throws Exception {
		// ���ԣ� �ύ15��ִ��ʱ����Ҫ3������񣬿�������С��2������Ӧ�Ĵ������
		for (int i = 0; i < 15; i++) {
			int n = i;
			threadPoolExecutor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("��ʼִ�У�" + n);
						Thread.sleep(3000L);
						System.err.println("ִ�н���:" + n);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			System.out.println("�����ύ�ɹ� :" + i);
		}
		// �鿴�߳��������鿴���еȴ�����
		Thread.sleep(500L);
		System.out.println("��ǰ�̳߳��߳�����Ϊ��" + threadPoolExecutor.getPoolSize());
		System.out.println("��ǰ�̳߳صȴ�������Ϊ��" + threadPoolExecutor.getQueue().size());
		// �ȴ�15�룬�鿴�߳������Ͷ��������������ϣ��ᱻ���������߳��������߳��Զ����٣�
		Thread.sleep(15000L);
		System.out.println("��ǰ�̳߳��߳�����Ϊ��" + threadPoolExecutor.getPoolSize());
		System.out.println("��ǰ�̳߳صȴ�������Ϊ��" + threadPoolExecutor.getQueue().size());
	}

	/**
	 * 1���̳߳���Ϣ�� �����߳�����5���������10���޽���У����������߳��������̴߳��ʱ�䣺5�룬 ָ���ܾ����Ե�
	 * 
	 * @throws Exception
	 */
	private void threadPoolExecutorTest1() throws Exception {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		testCommon(threadPoolExecutor);
		// Ԥ�ƽ�����̳߳��߳�����Ϊ��5,�������������������Ľ�������еȴ���ִ��
	}

	/**
	 * 2�� �̳߳���Ϣ�� �����߳�����5���������10�����д�С3�����������߳��������̴߳��ʱ�䣺5�룬 ָ���ܾ����Ե�
	 * 
	 * @throws Exception
	 */
	private void threadPoolExecutorTest2() throws Exception {
		// ����һ�� �����߳�����Ϊ5���������Ϊ10,�ȴ����������3 ���̳߳أ�Ҳ�����������13������
		// Ĭ�ϵĲ������׳�RejectedExecutionException�쳣��java.util.concurrent.ThreadPoolExecutor.AbortPolicy
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.err.println("�����񱻾ܾ�ִ����");
					}
				});
		testCommon(threadPoolExecutor);
		// Ԥ�ƽ����
		// 1�� 5������ֱ�ӷ����߳̿�ʼִ��
		// 2�� 3���������ȴ�����
		// 3�� ���в����ã���ʱ�ӿ�5���߳���ִ������(5��û��ɾ�����)
		// 4�� ���к��̳߳ض����ˣ�ʣ��2������û��Դ�ˣ����ܾ�ִ�С�
		// 5�� ����ִ�У�5�������������ִ�У�������ʱ������5���߳�
	}

	/**
	 * 3�� �̳߳���Ϣ�� �����߳�����5���������5���޽���У����������߳��������̴߳��ʱ�䣺5��
	 * 
	 * @throws Exception
	 */
	private void threadPoolExecutorTest3() throws Exception {
		// ��Executors.newFixedThreadPool(int nThreads)һ����
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		testCommon(threadPoolExecutor);
		// Ԥ�ƽ᣺�̳߳��߳�����Ϊ��5���������������������Ľ�������еȴ���ִ��
	}

	/**
	 * 4�� �̳߳���Ϣ��
	 * �����߳�����0���������Integer.MAX_VALUE��SynchronousQueue���У����������߳��������̴߳��ʱ�䣺60��
	 * 
	 * @throws Exception
	 */
	private void threadPoolExecutorTest4() throws Exception {

		// SynchronousQueue��ʵ����������һ�������Ķ��У���Ϊ������Ϊ������Ԫ��ά���洢�ռ䡣���������в�ͬ���ǣ���ά��һ���̣߳���Щ�߳��ڵȴ��Ű�Ԫ�ؼ�����Ƴ����С�
		// ��ʹ��SynchronousQueue��Ϊ�������е�ǰ���£��ͻ��˴������̳߳��ύ����ʱ��
		// ���̳߳�����û�п��е��߳��ܹ���SynchronousQueue����ʵ����ȡһ������
		// ��ô��Ӧ��offer�������þͻ�ʧ�ܣ�������û�б����빤�����У���
		// ��ʱ��ThreadPoolExecutor���½�һ���µĹ������߳����ڶ���������ʧ�ܵ�������д��������ʱ�̳߳صĴ�С��δ�ﵽ������̳߳ش�СmaximumPoolSize����

		// ��Executors.newCachedThreadPool()һ����
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		testCommon(threadPoolExecutor);
		// Ԥ�ƽ����
		// 1�� �̳߳��߳�����Ϊ��15���������������������Ľ�������еȴ���ִ��
		// 2�� ��������ִ�н�����60�������������ִ�У������߳�ȫ�������٣��صĴ�С�ָ�Ϊ0
		Thread.sleep(60000L);
		System.out.println("60����ٿ��̳߳��е�������" + threadPoolExecutor.getPoolSize());
	}

	/**
	 * 5�� ��ʱִ���̳߳���Ϣ��3���ִ�У�һ�������񣬵����ִ�� <br/>
	 * �����߳�����5���������Integer.MAX_VALUE��DelayedWorkQueue��ʱ���У����������߳��������̴߳��ʱ�䣺0��
	 * 
	 * @throws Exception
	 */
	private void threadPoolExecutorTest5() throws Exception {
		// ��Executors.newScheduledThreadPool()һ����
		ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(5);
		threadPoolExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("����ִ�У�����ʱ�䣺" + System.currentTimeMillis());
			}
		}, 3000, TimeUnit.MILLISECONDS);
		System.out.println(
				"��ʱ�����ύ�ɹ���ʱ���ǣ�" + System.currentTimeMillis() + ", ��ǰ�̳߳����߳�������" + threadPoolExecutor.getPoolSize());
		// Ԥ�ƽ����������3���ִ��һ��
	}

	/**
	 * 6�� ��ʱִ���̳߳���Ϣ���̶̹߳�����5 ��<br/>
	 * �����߳�����5���������Integer.MAX_VALUE��DelayedWorkQueue��ʱ���У����������߳��������̴߳��ʱ�䣺0��
	 * 
	 * @throws Exception
	 */
	private void threadPoolExecutorTest6() throws Exception {
		ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(5);
		// ������ִ��ĳһ�������̳߳��ṩ�����ֵ��ȷ�ʽ�����ﵥ����ʾһ�¡����Գ���һ����
		// ���Գ������ύ��������Ҫ3�����ִ����ϡ������ֲ�ͬ���ȷ�ʽ������
		// Ч��1�� �ύ��2���ʼ��һ��ִ�У�֮��ÿ���1�룬�̶�ִ��һ��(��������ϴ�ִ�л�δ��ϣ���ȴ���ϣ���Ϻ�����ִ��)��
		// Ҳ����˵����������ǣ�3����ִ��һ�Σ����㷽ʽ��ÿ��ִ�����룬���ʱ��1�룬ִ�н��������Ͽ�ʼ��һ��ִ�У�����ȴ���
		threadPoolExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("����-1 ��ִ�У�����ʱ�䣺" + System.currentTimeMillis());
			}
		}, 2000, 1000, TimeUnit.MILLISECONDS);

		// Ч��2���ύ��2���ʼ��һ��ִ�У�֮��ÿ���1�룬�̶�ִ��һ��(��������ϴ�ִ�л�δ��ϣ���ȴ���ϣ�����һ��ִ����Ϻ��ٿ�ʼ��ʱ���ȴ�1��)��
		// Ҳ����˵��������ӵ�Ч���������ǣ�4��ִ��һ�Ρ� �����㷽ʽ��ÿ��ִ��3�룬���ʱ��1�룬ִ�����Ժ��ٵȴ�1�룬������ 3+1��
		threadPoolExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("����-2 ��ִ�У�����ʱ�䣺" + System.currentTimeMillis());
			}
		}, 2000, 1000, TimeUnit.MILLISECONDS);
	}

	/**
	 * 7�� ��ֹ�̣߳��̳߳���Ϣ�� �����߳�����5���������10�����д�С3�����������߳��������̴߳��ʱ�䣺5�룬 ָ���ܾ����Ե�
	 * 
	 * @throws Exception
	 */
	private void threadPoolExecutorTest7() throws Exception {
		// ����һ�� �����߳�����Ϊ5���������Ϊ10,�ȴ����������3 ���̳߳أ�Ҳ�����������13������
		// Ĭ�ϵĲ������׳�RejectedExecutionException�쳣��java.util.concurrent.ThreadPoolExecutor.AbortPolicy
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.err.println("�����񱻾ܾ�ִ����");
					}
				});
		// ���ԣ� �ύ15��ִ��ʱ����Ҫ3������񣬿�������С��2������Ӧ�Ĵ������
		for (int i = 0; i < 15; i++) {
			int n = i;
			threadPoolExecutor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("��ʼִ�У�" + n);
						Thread.sleep(3000L);
						System.err.println("ִ�н���:" + n);
					} catch (InterruptedException e) {
						System.out.println("�쳣��" + e.getMessage());
					}
				}
			});
			System.out.println("�����ύ�ɹ� :" + i);
		}
		// 1�����ֹ�̳߳�
		Thread.sleep(1000L);
		threadPoolExecutor.shutdown();
		// �ٴ��ύ��ʾʧ��
		threadPoolExecutor.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("׷��һ������");
			}
		});
		// �������
		// 1�� 10������ִ�У�3�����������еȴ���2�����񱻾ܾ�ִ��
		// 2������shutdown�󣬲������µ����񣬵ȴ�13����ִ�н���
		// 3�� ׷�ӵ��������̳߳عرպ��޷����ύ���ᱻ�ܾ�ִ��
	}

	/**
	 * 8�� ������ֹ�̣߳��̳߳���Ϣ�� �����߳�����5���������10�����д�С3�����������߳��������̴߳��ʱ�䣺5�룬 ָ���ܾ����Ե�
	 * 
	 * @throws Exception
	 */
	private void threadPoolExecutorTest8() throws Exception {
		// ����һ�� �����߳�����Ϊ5���������Ϊ10,�ȴ����������3 ���̳߳أ�Ҳ�����������13������
		// Ĭ�ϵĲ������׳�RejectedExecutionException�쳣��java.util.concurrent.ThreadPoolExecutor.AbortPolicy
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.err.println("�����񱻾ܾ�ִ����");
					}
				});
		// ���ԣ� �ύ15��ִ��ʱ����Ҫ3������񣬿�������С��2������Ӧ�Ĵ������
		for (int i = 0; i < 15; i++) {
			int n = i;
			threadPoolExecutor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("��ʼִ�У�" + n);
						Thread.sleep(3000L);
						System.err.println("ִ�н���:" + n);
					} catch (InterruptedException e) {
						System.out.println("�쳣��" + e.getMessage());
					}
				}
			});
			System.out.println("�����ύ�ɹ� :" + i);
		}
		// 1�����ֹ�̳߳�
		Thread.sleep(1000L);
		List<Runnable> shutdownNow = threadPoolExecutor.shutdownNow();
		// �ٴ��ύ��ʾʧ��
		threadPoolExecutor.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("׷��һ������");
			}
		});
		System.out.println("δ�����������У�" + shutdownNow.size());

		// �������
		// 1�� 10������ִ�У�3�����������еȴ���2�����񱻾ܾ�ִ��
		// 2������shutdownnow�󣬶����е�3���̲߳���ִ�У�10���̱߳���ֹ
		// 3�� ׷�ӵ��������̳߳عرպ��޷����ύ���ᱻ�ܾ�ִ��
	}

	public static void main(String[] args) throws Exception {
//		new Demo9().threadPoolExecutorTest1();
//		new Demo9().threadPoolExecutorTest2();
//		new Demo9().threadPoolExecutorTest3();
//		new Demo9().threadPoolExecutorTest4();
//		new Demo9().threadPoolExecutorTest5();
//		new Demo9().threadPoolExecutorTest6();
//		new Demo9().threadPoolExecutorTest7();
		new Demo9().threadPoolExecutorTest8();
	}
}
