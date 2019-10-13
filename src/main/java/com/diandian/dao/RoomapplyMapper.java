package com.diandian.dao;

import com.diandian.model.Roomapply;
import com.diandian.model.RoomapplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoomapplyMapper {
    long countByExample(RoomapplyExample example);

    int deleteByExample(RoomapplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Roomapply record);

    int insertSelective(Roomapply record);

    List<Roomapply> selectByExample(RoomapplyExample example);

    Roomapply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Roomapply record, @Param("example") RoomapplyExample example);

    int updateByExample(@Param("record") Roomapply record, @Param("example") RoomapplyExample example);

    int updateByPrimaryKeySelective(Roomapply record);

    int updateByPrimaryKey(Roomapply record);
}