package com.diandian.dao.custom;

import com.diandian.model.Lists;
import com.diandian.model.custom.UserCustom;
import org.apache.ibatis.annotations.Select;

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
     * @param roomid
     * @param userid
     * @return
     */
    @Select("select * from lists where roomid = #{roomid} and userid = #{userid}")
    Lists selectByRoomIdAndUserId(Integer roomid, Integer userid);
}
