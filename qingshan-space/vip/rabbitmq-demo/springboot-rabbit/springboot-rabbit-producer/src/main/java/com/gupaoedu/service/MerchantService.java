package com.gupaoedu.service;


import com.gupaoedu.entity.Merchant;

import java.util.List;

public interface MerchantService {

    public List<Merchant> getMerchantList(String name, int page, int limit);

    Merchant getMerchantById(Integer id);

    public int add(Merchant merchant);

    public int update(Merchant merchant);

    public int updateState(Merchant merchant);

    public int delete(Integer id);

    int getMerchantCount();
}
