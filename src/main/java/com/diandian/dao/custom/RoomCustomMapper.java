package com.diandian.dao.custom;

import com.diandian.model.custom.RoomCustom;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RoomCustomMapper {

    /**
     * 根据房间id查找未被删除的房间
     * @param roomId
     * @return
     */
    @Select("select * from room where id = #{roomId} and del = 1")
    RoomCustom selectRoomById(Integer roomId)throws Exception;


    /**
     * 将room表中的一行数据的del修改为0，表示删除房间
     * @param roomId
     * @return
     */
    @Update("update room set del = 0 where id = #{roomId}")
    Integer deleteRoomByRoomId(Integer roomId) throws Exception;


    /**
     * 根据房间号码查询房间
     * @param roomNumber
     * @return
     * @throws Exception
     */
    @Select("select * from room where roomnumber = #{roomNumber} and del = 1")
    RoomCustom selectRoomByRoomNumber(String roomNumber) throws Exception;
}
