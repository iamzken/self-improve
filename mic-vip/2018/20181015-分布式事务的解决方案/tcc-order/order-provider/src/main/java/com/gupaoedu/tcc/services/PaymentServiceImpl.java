package com.gupaoedu.tcc.services;

import com.gupaoedu.tcc.CapitalTradeOrderService;
import com.gupaoedu.tcc.PaymentService;
import com.gupaoedu.tcc.dal.repository.OrderRepository;
import com.gupaoedu.tcc.dto.CapitalTradeOrderDto;
import com.gupaoedu.tcc.dto.Order;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    CapitalTradeOrderService capitalTradeOrderService;

    @Autowired
    OrderRepository orderRepository;

    @Override
    @Compensable(confirmMethod = "confirmMakePayment", cancelMethod = "cancelMakePayment", asyncConfirm = true)
    public void makePayment(Order order, BigDecimal capitalPayAmount) {
        orderRepository.createOrder(order);//创建订单
        if (order.getStatus().equals("DRAFT")) {
            order.pay(capitalPayAmount);
            try {
                orderRepository.updateOrder(order);
            } catch (OptimisticLockingFailureException e) {
            }
        }

        capitalTradeOrderService.record(buildCapitalTradeOrderDto(order));
    }

    public void confirmMakePayment(Order order, BigDecimal capitalPayAmount) {
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Order foundOrder = orderRepository.findByMerchantOrderNo(order.getMerchantOrderNo());

        if (foundOrder != null && foundOrder.getStatus().equals("PAYING")) {
            order.confirm();
            orderRepository.updateOrder(order);
        }
    }

    public void cancelMakePayment(Order order, BigDecimal capitalPayAmount) {

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Order foundOrder = orderRepository.findByMerchantOrderNo(order.getMerchantOrderNo());

        if (foundOrder != null && foundOrder.getStatus().equals("PAYING")) {
            order.cancelPayment();
            orderRepository.updateOrder(order);
        }
    }

    private CapitalTradeOrderDto buildCapitalTradeOrderDto(Order order) {

        CapitalTradeOrderDto tradeOrderDto = new CapitalTradeOrderDto();
        tradeOrderDto.setAmount(order.getCapitalPayAmount());
        tradeOrderDto.setMerchantOrderNo(order.getMerchantOrderNo());
        tradeOrderDto.setSelfUserId(order.getPayerUserId());
        tradeOrderDto.setOppositeUserId(order.getPayeeUserId());
        tradeOrderDto.setOrderTitle(String.format("order no:%s", order.getMerchantOrderNo()));

        return tradeOrderDto;
    }
}
