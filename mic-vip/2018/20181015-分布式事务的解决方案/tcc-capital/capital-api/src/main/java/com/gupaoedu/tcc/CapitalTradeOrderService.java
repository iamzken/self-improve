package com.gupaoedu.tcc;

import com.gupaoedu.tcc.dto.CapitalTradeOrderDto;
import org.mengyun.tcctransaction.api.Compensable;


public interface CapitalTradeOrderService {

    @Compensable
    String record(CapitalTradeOrderDto tradeOrderDto);

}
