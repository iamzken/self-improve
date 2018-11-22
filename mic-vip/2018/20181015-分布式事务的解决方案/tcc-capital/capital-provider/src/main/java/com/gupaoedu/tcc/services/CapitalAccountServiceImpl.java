package com.gupaoedu.tcc.services;

import com.gupaoedu.tcc.CapitalAccountService;
import com.gupaoedu.tcc.dal.repository.CapitalAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("capitalAccountService")
public class CapitalAccountServiceImpl implements CapitalAccountService {


    @Autowired
    CapitalAccountRepository capitalAccountRepository;

    @Override
    public BigDecimal getCapitalAccountByUserId(long userId) {
        return capitalAccountRepository.findByUserId(userId).getBalanceAmount();
    }
}
