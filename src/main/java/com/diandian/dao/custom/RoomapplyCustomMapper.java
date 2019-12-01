package com.diandian.dao.custom;

import com.diandian.model.Roomapply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoomapplyCustomMapper {


    /**
     * 根据消息id查询具体的房间申请
     * @param id
     * @return
     */
    @Select("select * from roomapply where typeid = #{value}")
    Roomapply selectByTypeId(Integer id)throws Exception;

    /**
     * 根据房间号查询房间申请
     * @param roomId
     * @return
     */
    @Select("SELECT*FROM roomapply WHERE roomid=#{roomId}")
    List<Roomapply> selectByRoomId(Integer roomId)throws Exception;

    /**
     * 根据房间id删除房间申请
     * @param roomId
     * @return
     */
    @Delete("DELETE FROM roomapply WHERE roomid=#{roomId}")
    Integer deleteByRoomId(Integer roomId)throws Exception;
}
