package com.gupaoedu.tcc.dal.persistence;


import com.gupaoedu.tcc.dto.Order;

public interface OrderMapper {

    int insert(Order order);

    int update(Order order);

    Order findByMerchantOrderNo(String merchantOrderNo);
}
