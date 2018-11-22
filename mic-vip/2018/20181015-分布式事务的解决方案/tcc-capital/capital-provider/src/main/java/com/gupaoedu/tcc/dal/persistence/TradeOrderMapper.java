package com.gupaoedu.tcc.dal.persistence;


import com.gupaoedu.tcc.dal.entity.TradeOrder;

public interface TradeOrderMapper {

    int insert(TradeOrder tradeOrder);

    int update(TradeOrder tradeOrder);

    TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
