package com.gupao.dal.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TcUserMapper {
    int countByExample(TcUserExample example);

    int deleteByExample(TcUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TcUser record);

    int insertSelective(TcUser record);

    List<TcUser> selectByExample(TcUserExample example);

    TcUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TcUser record, @Param("example") TcUserExample example);

    int updateByExample(@Param("record") TcUser record, @Param("example") TcUserExample example);

    int updateByPrimaryKeySelective(TcUser record);

    int updateByPrimaryKey(TcUser record);
}