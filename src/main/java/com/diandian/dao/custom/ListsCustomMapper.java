package com.diandian.dao.custom;

import com.diandian.model.Lists;
import com.diandian.model.custom.UserCustom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ListsCustomMapper {

    /**
     * 查询房间内的所用用户
     * @param roomID
     * @return
     * @throws Exception
     */
    List<UserCustom> selectUsersInRoomByRoomId(Integer roomID) throws Exception;


    /**
     * 根据userid和roomid查询list表中的一条记录
     * @param roomId
     * @param userId
     * @return
     */
    @Select("select * from lists where roomid = #{roomId} and userid = #{userId}")
    Lists selectByRoomIdAndUserId(@Param("roomId") Integer roomId, @Param("userId") Integer userId);


    /**
     * 将list表中的一条数据的del属性改为0，表示删除
     * @param roomId
     * @param userId
     * @return
     */
    @Update("update lists set del = 0 where roomid = #{roomId} and userid = #{userId}")
    Integer updateListToDelete(@Param("roomId") Integer roomId, @Param("userId") Integer userId);
}
