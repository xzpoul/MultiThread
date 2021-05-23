package com.netease.study.chapter1.thread;

/** �̷߳��ʾ�� */
public class Demo7 {
	/** threadLocal������ÿ���̶߳���һ���������������� */
	public static ThreadLocal<String> value = new ThreadLocal<>();

	/**
	 * threadlocal����
	 * 
	 * @throws Exception
	 */
	public void threadLocalTest() throws Exception {

		// threadlocal�̷߳��ʾ��
		value.set("�������߳����õ�123"); // ���߳�����ֵ
		String v = value.get();
		System.out.println("�߳�1ִ��֮ǰ�����߳�ȡ����ֵ��" + v);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String v = value.get();
				System.out.println("�߳�1ȡ����ֵ��" + v);
				// ���� threadLocal
				value.set("�����߳�1���õ�456");

				v = value.get();
				System.out.println("��������֮���߳�1ȡ����ֵ��" + v);
				System.out.println("�߳�1ִ�н���");
			}
		}).start();

		Thread.sleep(5000L); // �ȴ������߳�ִ�н���

		v = value.get();
		System.out.println("�߳�1ִ��֮�����߳�ȡ����ֵ��" + v);

	}

	public static void main(String[] args) throws Exception {
		new Demo7().threadLocalTest();
	}
}
