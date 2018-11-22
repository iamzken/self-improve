package com.gupaoedu.michael.business.draw.domain.valobj;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class AwardPool {


    private int userType; //用户类型、新用户/老用户

    private Date beginTime;

    private Date endTime;

    private List<AwardItem> awardItems; //奖池中包含的奖品

    public boolean matchUserType(int userType){
        if(getUserType()==userType) {
            return true;
        }
        return false;
    }

    /**
     * 根据概率计算奖项
     * @return
     */
    public AwardItem doDraw(){
        int sumOfProbablity = 0;
        for(AwardItem award: awardItems) {
            sumOfProbablity += award.getProbability();
        }
        int randomNumber = ThreadLocalRandom.current().nextInt(sumOfProbablity);
        int range = 0;
        for(AwardItem award: awardItems) {
            range += award.getProbability();
            if(randomNumber<range) {
                return award;
            }
        }
        return null;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<AwardItem> getAwardItems() {
        return awardItems;
    }

    public void setAwardItems(List<AwardItem> awardItems) {
        this.awardItems = awardItems;
    }
}
