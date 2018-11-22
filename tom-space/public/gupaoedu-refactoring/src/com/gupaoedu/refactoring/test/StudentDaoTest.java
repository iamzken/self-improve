package com.gupaoedu.refactoring.test;

import com.gupaoedu.refactoring.now.StudentDao;

public class StudentDaoTest {

	public static void main(String[] args) {
		
		Long totalCount = new StudentDao().getCount();
		System.out.println(totalCount);
		
	}
	
	
}
