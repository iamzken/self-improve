package com.gupaoedu.michael.business.draw.repo.dao.pojo;

import com.gupaoedu.michael.business.draw.domain.valobj.AwardPrize;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class AwardItemPO {
    private int id;
    private int probability;       //概率
    private int totalNum;          //数量
    private int awardPoolId;       //奖品池id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getAwardPoolId() {
        return awardPoolId;
    }

    public void setAwardPoolId(int awardPoolId) {
        this.awardPoolId = awardPoolId;
    }
}
