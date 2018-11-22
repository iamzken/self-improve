package com.gupao.course.demo.serialize;

import java.util.Random;

public class ThreadLocalTest implements Runnable {

	private Student student;

	private static final ThreadLocal<Student> threadLocal = new ThreadLocal<Student>();

	public static void main(String[] args) {
		ThreadLocalTest demo = new ThreadLocalTest();
		Thread t1 = new Thread(demo, "t1");
//		Thread t2 = new Thread(demo, "t2");
		t1.start();
//		t2.start();
	}

	@Override
	public void run() {
		accessStudent();
	}

	private void accessStudent() {
		String currThreadName = Thread.currentThread().getName();
		System.out.println("当前线程名称： " + currThreadName);

		Random randomNum = new Random();
		int age = randomNum.nextInt(100);
		System.out.println("线程 " + currThreadName + " set age to：" + age);

		getStu().setAge(age);
		System.out.println("线程 " + currThreadName + " first read age： " + getStu().getAge());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("线程 " + currThreadName + " second read age： " + getStu().getAge());
	}

	protected Student getStu() {
		student = (Student) threadLocal.get();
		if (student == null) {
			student = new Student();
			threadLocal.set(student);
		}
		return student;
	}
}
