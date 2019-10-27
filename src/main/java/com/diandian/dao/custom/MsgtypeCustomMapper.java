package com.diandian.dao.custom;

import com.diandian.model.Msgtype;
import com.diandian.model.custom.MsgtypeCustom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MsgtypeCustomMapper {
    /**
     * 根据sendUserID和receiveUserId获取消息
     * @return
     */
    @Select("select * from msgtype where senduser = #{sendId} and receiveuser = #{receiveId}")
    Msgtype selectByTowUserId(@Param("sendId") Integer sendId, @Param("receiveId") Integer receiveId)throws Exception;


    /**
     * 根据sendUserID和receiveUserId获取一条房间申请
     * @param sendId
     * @param receiveId
     * @return
     * @throws Exception
     */
    MsgtypeCustom selectMsgAndRoomApplyByThreeId(@Param("sendId") Integer sendId, @Param("receiveId") Integer receiveId,
                                               @Param("roomId") Integer roomId) throws Exception;


    /**
     * 获取所有申请加入我的房间的消息
     * @param userId
     * @return
     */
    List<MsgtypeCustom> selectApplyRoomsByUserId(Integer userId) throws Exception;


    /**
     * 获取用户未读取的消息
     * @param userId
     * @return
     */
    @Select("select * from msgtype where receiveuser = #{value} and isread = 0")
    List<MsgtypeCustom> selectUnreadMsgByUserId(Integer userId)throws Exception;
}
