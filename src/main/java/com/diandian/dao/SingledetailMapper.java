package com.diandian.dao;

import com.diandian.model.Singledetail;
import com.diandian.model.SingledetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SingledetailMapper {
    long countByExample(SingledetailExample example);

    int deleteByExample(SingledetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Singledetail record);

    int insertSelective(Singledetail record);

    List<Singledetail> selectByExample(SingledetailExample example);

    Singledetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Singledetail record, @Param("example") SingledetailExample example);

    int updateByExample(@Param("record") Singledetail record, @Param("example") SingledetailExample example);

    int updateByPrimaryKeySelective(Singledetail record);

    int updateByPrimaryKey(Singledetail record);
}