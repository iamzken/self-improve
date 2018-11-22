package com.gupaoedu.mapper;
import com.gupaoedu.entity.Merchant;

import java.util.List;

public interface MerchantMapper {

   Merchant getMerchantById(Integer sid);

    public List<Merchant> getMerchantList(String name, int page, int limit);

    public int add(Merchant merchant);

    public int update(Merchant merchant);

    public int updateState(Merchant merchant);

    public int delete(Integer sid);

    int getMerchantCount();
}