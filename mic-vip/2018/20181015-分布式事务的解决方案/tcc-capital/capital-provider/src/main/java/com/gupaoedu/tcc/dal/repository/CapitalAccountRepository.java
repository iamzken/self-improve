package com.gupaoedu.tcc.dal.repository;

import com.gupaoedu.tcc.dal.entity.CapitalAccount;
import com.gupaoedu.tcc.dal.persistence.CapitalAccountMapper;
import com.gupaoedu.tcc.exception.InsufficientBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CapitalAccountRepository {

    @Autowired
    CapitalAccountMapper capitalAccountMapper;

    public CapitalAccount findByUserId(long userId) {

        return capitalAccountMapper.findByUserId(userId);
    }

    public void save(CapitalAccount capitalAccount) {
        int effectCount = capitalAccountMapper.update(capitalAccount);
        if (effectCount < 1) {
            throw new InsufficientBalanceException();
        }
    }
}
