package com.gupaoedu.sso.controller;

import com.gupaoedu.common.annotation.Anoymous;
import com.gupaoedu.common.constants.GpmallWebConstant;
import com.gupaoedu.sso.controller.support.ResponseData;
import com.gupaoedu.sso.controller.support.ResponseEnum;
import com.gupaoedu.user.IUserCoreService;
import com.gupaoedu.user.dto.UserLoginRequest;
import com.gupaoedu.user.dto.UserLoginResponse;
import com.gupaoedu.user.dto.UserRegisterRequest;
import com.gupaoedu.user.dto.UserRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */


@RestController
public class UserController extends BaseController{

    @Autowired
    IUserCoreService userCoreService;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Anoymous
    @PostMapping("/login")
    public ResponseData doLogin(String username, String password,
                                     HttpServletResponse response){
        ResponseData data=new ResponseData();
        UserLoginRequest request=new UserLoginRequest();
        request.setPassword(password);
        request.setUserName(username);
        UserLoginResponse userLoginResponse=userCoreService.login(request);
        response.addHeader("Set-Cookie",
                "access_token="+userLoginResponse.getToken()+";Path=/;HttpOnly");

        data.setMessage(userLoginResponse.getMsg());
        data.setCode(userLoginResponse.getCode());
        data.setData(GpmallWebConstant.GPMALL_ACTIVITY_ACCESS_URL);
        return data;
    }


    @GetMapping("/register")
    @Anoymous
    public @ResponseBody
    ResponseData register(String username, String password, String mobile){
        ResponseData data=new ResponseData();

        UserRegisterRequest request=new UserRegisterRequest();
        request.setMobile(mobile);
        request.setUsername(username);
        request.setPassword(password);
        try {
            UserRegisterResponse response = userCoreService.register(request);
            //异步化解耦
            kafkaTemplate.send("test",response.getUid());
            data.setMessage(response.getMsg());
            data.setCode(response.getCode());
        }catch(Exception e) {
            data.setMessage(ResponseEnum.FAILED.getMsg());
            data.setCode(ResponseEnum.FAILED.getCode());
        }
        return data;
    }

}
