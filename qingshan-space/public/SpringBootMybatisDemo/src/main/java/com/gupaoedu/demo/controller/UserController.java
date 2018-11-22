package com.gupaoedu.demo.controller;

import com.gupaoedu.demo.entity.User;
import com.gupaoedu.demo.service.UserService;
import com.gupaoedu.demo.util.LayuiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 查询用户列表
     * @return
     */
    @RequestMapping("/getUserList")
    @ResponseBody
    public LayuiData getUserList (HttpServletRequest request){
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
        List<User> userList = userService.getUserList(name,page,limit);
        int count = userService.getUserCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        layuiData.setData(userList);
       return layuiData;
    }

    /**
     * 去新增用户界面
     * @return
     */
    @RequestMapping("/toUser")
    public String toUser (){

        return "userAdd";
    }

    /**
     * 新增
     * @param name
     * @param age
     * @return
     */
    @RequestMapping("/userAdd")
    @Transactional
    @ResponseBody
    public Integer userAdd (String name,Integer age){
        User user = new User();
        user.setAge(age);
        user.setName(name);
        int num = userService.add(user);
        return num;
    }

    @RequestMapping("/userList")
    public String userList1() {
        return "userListPage";
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer id) {

       int num = userService.delete(id);

        return num;
    }

    /**
     * 去查看界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toDetail")
    public String toDetail(Integer id, Model model) {

        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "userDetail";
    }
    /**
     * 去修改界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Model model) {

        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "userUpdate";
    }
    /**
     * 根据id修改用户信息
     * @return
     */
    @RequestMapping("/userUpdate")
    @Transactional
    @ResponseBody
    public Integer userUpdate (Integer id,String name,Integer age){
        User user = new User();
        user.setAge(age);
        user.setName(name);
        user.setId(id);
        int num = userService.update(user);
        return num;
    }
}
