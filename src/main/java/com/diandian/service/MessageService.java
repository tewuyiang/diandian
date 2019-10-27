package com.diandian.service;

import com.diandian.model.Msgtype;
import com.diandian.model.Roomapply;
import com.diandian.model.custom.MsgtypeCustom;
import com.diandian.utils.R;

import java.util.List;

public interface MessageService {

    /**
     * 申请加入房间，根据房间number
     * @param userId
     * @param number
     * @return
     */
    Integer createJoinRoomMsgByNumber(Integer userId, String number, String remarks) throws Exception;


    /**
     * 插入一条消息记录
     * @param msgtype
     * @return
     * @throws Exception
     */
    Integer insertMessageType(Msgtype msgtype) throws Exception;


    /**
     * 插入一条申请加入房间的详细记录
     * @param roomapply
     * @return
     * @throws Exception
     */
    Integer insertRoomApply(Roomapply roomapply) throws Exception;


    /**
     * 获取所有申请加入我的房间的消息
     * @param userId
     * @return
     */
    List<MsgtypeCustom> selectApplyMyRoomsMessage(Integer userId) throws Exception;


    /**
     * 处理房间申请请求
     * @param msgId 消息id
     * @param result 处理结果
     * @throws Exception
     */
    Integer updateAfterDealRoomapply(Integer msgId, Integer result) throws Exception;


    /**
     * 获取用户未读消息
     * @param userId
     * @return
     * @throws Exception
     */
    List<MsgtypeCustom> updateUnreadMessageByUserId(Integer userId) throws Exception;
}
