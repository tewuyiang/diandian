package com.diandian.dao;

import com.diandian.model.Roomdetail;
import com.diandian.model.RoomdetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoomdetailMapper {
    long countByExample(RoomdetailExample example);

    int deleteByExample(RoomdetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Roomdetail record);

    int insertSelective(Roomdetail record);

    List<Roomdetail> selectByExample(RoomdetailExample example);

    Roomdetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Roomdetail record, @Param("example") RoomdetailExample example);

    int updateByExample(@Param("record") Roomdetail record, @Param("example") RoomdetailExample example);

    int updateByPrimaryKeySelective(Roomdetail record);

    int updateByPrimaryKey(Roomdetail record);
}