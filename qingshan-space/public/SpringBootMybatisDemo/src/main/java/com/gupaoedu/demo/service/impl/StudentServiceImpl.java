package com.gupaoedu.demo.service.impl;

import com.gupaoedu.demo.entity.Student;
import com.gupaoedu.demo.entity.User;
import com.gupaoedu.demo.mapper.StudentMapper;
import com.gupaoedu.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public List<Student> getStudentList(String name , int page, int limit) {

        return studentMapper.getStudentList(name,page,limit);
    }

    @Override
    public Student getStudentById(Integer sid) {
        return studentMapper.getStudentById(sid);
    }
    @Override
    public int add(Student student) {
        return studentMapper.add(student);
    }

    @Override
    public int update(Student student) {
        return studentMapper.update(student);
    }

    @Override
    public int delete(Integer sid) {
        return studentMapper.delete(sid);
    }

    @Override
    public int getStudentCount() {
        return studentMapper.getStudentCount();

    }
}