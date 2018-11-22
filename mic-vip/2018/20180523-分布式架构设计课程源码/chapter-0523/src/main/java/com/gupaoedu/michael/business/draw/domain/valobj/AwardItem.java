package com.gupaoedu.michael.business.draw.domain.valobj;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class AwardItem {

    private int probability;       //概率
    private int totalNum;          //数量
    private AwardPrize awardPrize; //奖品


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

    public AwardPrize getAwardPrize() {
        return awardPrize;
    }

    public void setAwardPrize(AwardPrize awardPrize) {
        this.awardPrize = awardPrize;
    }
}
