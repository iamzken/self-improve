package com.gupaoedu.activity.dal.persistence;



import com.gupaoedu.activity.dal.entitys.ActDrawAwardItem;

import java.util.List;

public interface ActDrawAwardItemMapper {


    /**
     * 查询所有奖项
     * @return
     */
    List<ActDrawAwardItem> queryAwardItem();

}