package com.gupaoedu.tcc.dal.repository;


import com.gupaoedu.tcc.dal.persistence.OrderMapper;
import com.gupaoedu.tcc.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;


@Repository
public class OrderRepository {

    @Autowired
    OrderMapper orderMapper;

    public void createOrder(Order order) {
        orderMapper.insert(order);

    }

    public void updateOrder(Order order) {
        order.updateVersion();
        int effectCount = orderMapper.update(order);

        if (effectCount < 1) {
            throw new OptimisticLockingFailureException("update order failed");
        }
    }

    public Order findByMerchantOrderNo(String merchantOrderNo) {
        return orderMapper.findByMerchantOrderNo(merchantOrderNo);
    }
}
