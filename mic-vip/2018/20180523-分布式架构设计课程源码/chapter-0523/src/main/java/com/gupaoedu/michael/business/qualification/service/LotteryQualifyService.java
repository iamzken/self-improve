package com.gupaoedu.michael.business.qualification.service;

import com.gupaoedu.michael.business.draw.domain.valobj.DrawLotteryContext;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface LotteryQualifyService {

    /**
     * 资格判断
     * @param context
     * @return
     */
    boolean checkLotteryCondition(DrawLotteryContext context);
}
