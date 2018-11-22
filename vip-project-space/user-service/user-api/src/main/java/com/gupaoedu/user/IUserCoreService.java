package com.gupaoedu.user;

import com.gupaoedu.user.dto.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface IUserCoreService {

    /**
     * 用户登录
     * @param request
     * @return
     */
    UserLoginResponse login(UserLoginRequest request);


    /**
     * 校验过程
     * @param request
     * @return
     */
    CheckAuthResponse validToken(CheckAuthRequest request);


    /*
     * 注册
     */
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);
}
