package com.gupaoedu.demo.mapper;
import com.gupaoedu.demo.entity.Student;

import java.util.List;

public interface StudentMapper {

   Student getStudentById(Integer sid);

    public List<Student> getStudentList(String name, int page, int limit);

    public int add(Student student);

   public int update(Student student);

    public int delete(Integer sid);

    int getStudentCount();
}