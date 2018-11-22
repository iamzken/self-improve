package com.gupaoedu.vip.pattern.decorator.passport.upgrede;

import com.gupaoedu.vip.pattern.decorator.passport.old.ISigninService;
import com.gupaoedu.vip.pattern.decorator.passport.old.ResultMsg;

/**
 * Created by Tom on 2018/3/17.
 */
public interface ISigninForThirdService extends ISigninService {


    public ResultMsg loginForQQ(String openId);

    public ResultMsg loginForWechat(String openId);

    public ResultMsg loginForToken(String token);

    public ResultMsg loginForTelphone(String telphone,String code);

    public ResultMsg loginForRegist(String username,String password);


}
