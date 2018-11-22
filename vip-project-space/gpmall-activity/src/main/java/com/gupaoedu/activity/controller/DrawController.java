package com.gupaoedu.activity.controller;

import com.gupaoedu.activity.commons.ResultResp;
import com.gupaoedu.activity.controller.support.ResponseData;
import com.gupaoedu.activity.draw.ActivityTurntableDrawService;
import com.gupaoedu.activity.draw.bean.ActivityTurntableDrawReq;
import com.gupaoedu.activity.draw.bean.AwardDrawRecordBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */


@RestController
public class DrawController extends BaseController{


    @Autowired
    ActivityTurntableDrawService activityTurntableDrawService;


    @PostMapping("/doDraw")
    public ResponseData doDraw(){
        ActivityTurntableDrawReq drawReq=new ActivityTurntableDrawReq();
        drawReq.setUid(Integer.parseInt(getUid()));
        ResultResp<AwardDrawRecordBean> resp= activityTurntableDrawService.doDraw(drawReq);
        ResponseData data=new ResponseData();
        data.setCode(resp.getReturnCodeEnum().getCode());
        data.setMessage(resp.getReturnCodeEnum().getMsg());
        data.setData(resp.getResult());
        return data;
    }
}
