package com.diandian.dao.custom;

import com.diandian.model.custom.RoomCustom;
import com.diandian.model.custom.UserCustom;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserCustomMapper {

    /**
     * 根据openid查询用户信息
     * @param openid
     * @return
     * @throws Exception
     */
    @Select("select * from user where wxid = #{openid}")
    UserCustom getUserByOpenid(String openid) throws Exception;


    /**
     * 根据userid查询用户创建的所有房间
     * @param userid
     * @return
     */
    @Select("select * from room where userid = #{userid} and del = 1")
    List<RoomCustom> selectRoomsByUserId(Integer userid) throws Exception;


    /**
     * 根据userId查询用户加入的所有房间
     * @param userId
     * @return
     */
    @Select("select room.* from lists,room where lists.userid = #{userId} " +
            "and lists.del = 1 and room.del = 1 and lists.roomid = room.id")
    List<RoomCustom> selectJoinRoomsByUserId(Integer userId) throws Exception;
}
