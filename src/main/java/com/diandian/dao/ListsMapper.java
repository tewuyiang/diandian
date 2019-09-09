package com.diandian.dao;

import com.diandian.model.Lists;
import com.diandian.model.ListsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ListsMapper {
    long countByExample(ListsExample example);

    int deleteByExample(ListsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Lists record);

    int insertSelective(Lists record);

    List<Lists> selectByExample(ListsExample example);

    Lists selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Lists record, @Param("example") ListsExample example);

    int updateByExample(@Param("record") Lists record, @Param("example") ListsExample example);

    int updateByPrimaryKeySelective(Lists record);

    int updateByPrimaryKey(Lists record);
}