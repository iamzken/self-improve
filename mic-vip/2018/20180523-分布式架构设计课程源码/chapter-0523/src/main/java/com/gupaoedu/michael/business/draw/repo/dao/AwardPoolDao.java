package com.gupaoedu.michael.business.draw.repo.dao;

import com.gupaoedu.michael.business.draw.repo.dao.pojo.AwardPoolPO;

import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface AwardPoolDao {

    /**
     * 根据lotteryId批次号获取奖品池
     * @param lotteryId
     * @return
     */
    List<AwardPoolPO> getAwardPoolByLotteryId(int lotteryId);
}
