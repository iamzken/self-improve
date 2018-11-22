package com.gupaoedu.tcc.dal.persistence;


import com.gupaoedu.tcc.dal.entity.CapitalAccount;

public interface CapitalAccountMapper {

    CapitalAccount findByUserId(long userId);

    int update(CapitalAccount capitalAccount);
}
