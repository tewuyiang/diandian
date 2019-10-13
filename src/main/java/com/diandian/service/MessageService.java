package com.diandian.service;

import com.diandian.model.Msgtype;
import com.diandian.model.Roomapply;

public interface MessageService {

    /**
     * 申请加入房间，根据房间number
     * @param userId
     * @param number
     * @return
     */
    Integer createJoinRoomMsgByNumber(Integer userId, String number) throws Exception;


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
}
