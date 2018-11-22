package com.gupaoedu.demo.service;


import com.gupaoedu.demo.entity.Student;
import com.gupaoedu.demo.entity.User;

import java.util.List;

public interface StudentService {

    public List<Student> getStudentList(String name, int page, int limit);

    Student getStudentById(Integer id);

    public int add(Student student);

    public int update(Student student);

    public int delete(Integer id);

    int getStudentCount();
}
