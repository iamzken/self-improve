package com.gupaoedu.entity;

import java.io.Serializable;

/**
 * @Author: qingshan
 * @Date: 2018/10/25 16:20
 * @Description: 咕泡学院，只为更好的你
 */
public class Merchant implements Serializable {
    int id; // 商户编号
    String name; // 商户名称
    String address; // 商户地址
    String accountNo; // 商户账号
    String accountName; // 户名
    String state; // 状态 1 激活 2 关闭
    String stateStr; // 状态中文

    public Merchant() {
    }

    public Merchant(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateStr() {
        if(null == state){
            return "";
        }else if("1".equals(state.toString())){
            return "激活";
        }else if ("0".equals(state.toString())){
            return "关闭";
        }else{
            return "未知";
        }
    }

    public void setStateStr(String stateStr) {
        this.stateStr = state;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", accountName='" + accountName + '\'' +
                ", state='" + state + '\'' +
                ", stateStr='" + stateStr + '\'' +
                '}';
    }
}
