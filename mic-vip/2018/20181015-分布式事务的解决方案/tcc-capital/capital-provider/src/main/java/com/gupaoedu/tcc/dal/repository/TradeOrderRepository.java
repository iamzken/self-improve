package com.gupaoedu.tcc.dal.repository;

import com.gupaoedu.tcc.dal.entity.TradeOrder;
import com.gupaoedu.tcc.dal.persistence.TradeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

@Repository
public class TradeOrderRepository {

    @Autowired
    TradeOrderMapper tradeOrderMapper;

    public void insert(TradeOrder tradeOrder) {
        tradeOrderMapper.insert(tradeOrder);
    }

    public void update(TradeOrder tradeOrder) {
        tradeOrder.updateVersion();
        int effectCount = tradeOrderMapper.update(tradeOrder);
        if (effectCount < 1) {
            throw new OptimisticLockingFailureException("update trade order failed");
        }
    }

    public TradeOrder findByMerchantOrderNo(String merchantOrderNo) {
        return tradeOrderMapper.findByMerchantOrderNo(merchantOrderNo);
    }

}
