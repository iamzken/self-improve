package com.gupaoedu.demo.controller;

import com.gupaoedu.demo.entity.Student;
import com.gupaoedu.demo.service.StudentService;
import com.gupaoedu.demo.util.LayuiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller()
@RequestMapping ( "/stu" )
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 查询学员列表
     * @return
     */
    @RequestMapping("/getStudentList")
    @ResponseBody
    public LayuiData getStudentList (HttpServletRequest request){
        String name = request.getParameter("name");
        if(name==null){
            name="";
        }
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>=1){
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();
        List<Student> studentList = studentService.getStudentList(name,page,limit);
        int count = studentService.getStudentCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        layuiData.setData(studentList);
       return layuiData;
    }

    /**
     * 去新增学员界面
     * @return
     */
    @RequestMapping("/toStudent")
    public String toStudent (){

        return "studentAdd";
    }

    /**
     * 新增
     * @param name
     * @param qq
     * @return
     */
    @RequestMapping("/studentAdd")
    @Transactional
    @ResponseBody
    public Integer studentAdd (String name,String qq,Integer sid){
        Student student = new Student();
        student.setSid(sid);
        student.setQq(qq);
        student.setName(name);
        int num = studentService.add(student);
        return num;
    }

    @RequestMapping("/studentList")
    public String studentList() {
        return "studentListPage";
    }

    /**
     * 根据id删除学员
     * @param sid
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer sid) {

       int num = studentService.delete(sid);

        return num;
    }

    /**
     * 去查看界面
     * @param sid
     * @param model
     * @return
     */
    @RequestMapping("/toDetail")
    public String toDetail(Integer sid, Model model) {
        Student student = studentService.getStudentById(sid);
        model.addAttribute("student",student);
        return "studentDetail";
    }
    /**
     * 去修改界面
     * @param sid
     * @param model
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(Integer sid, Model model) {
        Student student = studentService.getStudentById(sid);
        model.addAttribute("student",student);
        return "studentUpdate";
    }
    /**
     * 根据id修改学员信息
     * @return
     */
    @RequestMapping("/studentUpdate")
    @Transactional
    @ResponseBody
    public Integer studentUpdate (Integer sid,String name,String qq){
        Student student = new Student();
        student.setQq(qq);
        student.setName(name);
        student.setSid(sid);
        int num = studentService.update(student);
        return num;
    }
}