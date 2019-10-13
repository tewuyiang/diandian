package com.diandian.dao;

import com.diandian.model.Msgtype;
import com.diandian.model.MsgtypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MsgtypeMapper {
    long countByExample(MsgtypeExample example);

    int deleteByExample(MsgtypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Msgtype record);

    int insertSelective(Msgtype record);

    List<Msgtype> selectByExample(MsgtypeExample example);

    Msgtype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Msgtype record, @Param("example") MsgtypeExample example);

    int updateByExample(@Param("record") Msgtype record, @Param("example") MsgtypeExample example);

    int updateByPrimaryKeySelective(Msgtype record);

    int updateByPrimaryKey(Msgtype record);
}