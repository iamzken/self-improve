package com.gupaoedu.user;


import com.gupaoedu.user.dto.UserQueryRequest;
import com.gupaoedu.user.dto.UserQueryResponse;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface IUserQueryService {


    /**
     * 根据用户id来查询用户信息
     * @param request
     * @return
     */
    UserQueryResponse getUserById(UserQueryRequest request);

}
