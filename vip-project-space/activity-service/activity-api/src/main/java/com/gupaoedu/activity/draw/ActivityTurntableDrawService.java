package com.gupaoedu.activity.draw;


import com.gupaoedu.activity.commons.ResultResp;
import com.gupaoedu.activity.draw.bean.ActivityTurntableDrawReq;
import com.gupaoedu.activity.draw.bean.AwardDrawRecordBean;

import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface ActivityTurntableDrawService {

    /**
     * 转盘抽奖
     *
     * @param activityTurntableDrawReq
     * @return
     */
    ResultResp<AwardDrawRecordBean> doDraw(ActivityTurntableDrawReq activityTurntableDrawReq);


    /**
     * 查询用户剩余抽奖次数
     * @param activityTurntableDrawReq
     * @return
     */
    Integer queryRemainDrawCount(ActivityTurntableDrawReq activityTurntableDrawReq);

    /**
     * 查询抽奖记录
     * @param activityTurntableDrawReq
     * @return
     */
    List<AwardDrawRecordBean> queryAwardDrawRecord(ActivityTurntableDrawReq activityTurntableDrawReq);
}
