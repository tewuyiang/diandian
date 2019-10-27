package com.diandian.dao.custom;

import com.diandian.model.Roomapply;
import org.apache.ibatis.annotations.Select;

public interface RoomapplyCustomMapper {


    /**
     * 根据消息id查询具体的房间申请
     * @param id
     * @return
     */
    @Select("select * from roomapply where typeid = #{value}")
    Roomapply selectByTypeId(Integer id)throws Exception;
}
