package com.gupaoedu.michael.business.draw.repo.repository;

import com.gupaoedu.michael.business.draw.domain.aggregate.DrawLottery;
import com.gupaoedu.michael.business.draw.domain.valobj.AwardItem;
import com.gupaoedu.michael.business.draw.domain.valobj.AwardPool;
import com.gupaoedu.michael.business.draw.repo.dao.AwardItemDao;
import com.gupaoedu.michael.business.draw.repo.dao.AwardPoolDao;
import com.gupaoedu.michael.business.draw.repo.dao.AwardPrizeDao;
import com.gupaoedu.michael.business.draw.repo.dao.pojo.AwardItemPO;
import com.gupaoedu.michael.business.draw.repo.dao.pojo.AwardPoolPO;
import com.gupaoedu.michael.business.draw.repo.repository.cache.DrawLotteryCacheAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 *
 */
public class DrawLotteryRepository {

    private AwardItemDao awardItemDao;
    private AwardPoolDao awardPoolDao;
    private AwardPrizeDao awardPrizeDao;

    private DrawLotteryCacheAccess drawLotteryCacheAccess;

    /**
     * 根据lotteryId批次号来检索奖池
     * @param lotteryId
     * @return
     */
    public DrawLottery getDrawLotteryById(int lotteryId){
        //从缓存中load数据
        DrawLottery drawLottery=drawLotteryCacheAccess.get(lotteryId);
        if(drawLottery!=null){
            return drawLottery;
        }
        //从数据库中获取
        drawLottery=getDrawLotteryFromDB(lotteryId);
        drawLotteryCacheAccess.set(lotteryId,drawLottery);
        return drawLottery;
    }

    /**
     * 从数据库load数据  pojo/dto/do/vo/bo..
     * @param lotteryId
     * @return
     */
    private DrawLottery getDrawLotteryFromDB(int lotteryId){
        DrawLottery drawLottery=new DrawLottery();
        List<AwardPoolPO> awardPoolPOS=awardPoolDao.getAwardPoolByLotteryId(lotteryId);
        if(awardPoolPOS==null||awardPoolPOS.isEmpty()){
            throw new RuntimeException("奖品池为空"); //TODO 异常需要统一处理
        }
        List<AwardPool> awardPools=new ArrayList<>();
        awardPoolPOS.parallelStream().forEach(awardPoolPO -> {
            AwardPool awardPool=new AwardPool();
            awardPool.setUserType(awardPoolPO.getUserType());
            awardPool.setBeginTime(awardPoolPO.getBeginTime());
            awardPool.setEndTime(awardPoolPO.getEndTime());
            List<AwardItemPO> awardItemPOS=awardItemDao.getAwardItemByAwardPoolId(awardPoolPO.getId());
            awardPool.setAwardItems(convertPo2Vo(awardItemPOS));
            awardPools.add(awardPool);
        });
        drawLottery.setAwardPools(awardPools);
        return drawLottery;
    }

    private List<AwardItem> convertPo2Vo(List<AwardItemPO> awardItemPOList){
        List<AwardItem> awardItems=new ArrayList<>();
        awardItemPOList.parallelStream().forEach(awardItemPO -> {
            AwardItem awardItem=new AwardItem();
            awardItem.setProbability(awardItemPO.getProbability());
            awardItem.setTotalNum(awardItemPO.getTotalNum());
            awardItems.add(awardItem);
        });
        return awardItems;
    }
}
