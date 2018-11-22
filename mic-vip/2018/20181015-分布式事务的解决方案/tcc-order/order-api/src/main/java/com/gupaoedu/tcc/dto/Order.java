package com.gupaoedu.tcc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class Order implements Serializable {

    private static final long serialVersionUID = -5908730245224893590L;
    private long id;

    private long payerUserId;

    private long payeeUserId;

    private BigDecimal capitalPayAmount;

    private String status = "DRAFT";

    private String merchantOrderNo;

    private long version = 1l;

    public Order() {

    }

    public Order(long payerUserId, long payeeUserId) {
        this.payerUserId = payerUserId;
        this.payeeUserId = payeeUserId;
        this.merchantOrderNo = UUID.randomUUID().toString();
    }

    public long getPayerUserId() {
        return payerUserId;
    }

    public long getPayeeUserId() {
        return payeeUserId;
    }

    public void pay(BigDecimal capitalPayAmount) {
        this.capitalPayAmount = capitalPayAmount;
        this.status = "PAYING";
    }

    public BigDecimal getCapitalPayAmount() {
        return capitalPayAmount;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void confirm() {
        this.status = "CONFIRMED";
    }

    public void cancelPayment() {
        this.status = "PAY_FAILED";
    }

    public long getVersion() {
        return version;
    }

    public void updateVersion() {
        version++;
    }
}
