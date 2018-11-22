package com.gupaoedu.michael.business.draw.repo.dao;

import com.gupaoedu.michael.business.draw.repo.dao.pojo.AwardItemPO;
import com.gupaoedu.michael.business.draw.repo.dao.pojo.AwardPoolPO;

import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface AwardItemDao {

    List<AwardItemPO> getAwardItemByAwardPoolId(int awardPoolId);
}
