package com.diandian.service.impl;

import com.diandian.dao.MsgtypeMapper;
import com.diandian.dao.RoomapplyMapper;
import com.diandian.dao.UserMapper;
import com.diandian.dao.custom.RoomCustomMapper;
import com.diandian.dao.custom.UserCustomMapper;
import com.diandian.exception.DataOperateException;
import com.diandian.exception.ParamException;
import com.diandian.model.Msgtype;
import com.diandian.model.Roomapply;
import com.diandian.model.User;
import com.diandian.model.custom.RoomCustom;
import com.diandian.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoomCustomMapper roomCustomMapper;
    @Autowired
    private MsgtypeMapper msgtypeMapper;
    @Autowired
    private RoomapplyMapper roomapplyMapper;
    @Autowired
    private UserCustomMapper userCustomMapper;

    /**
     * 申请加入房间，根据房间number
     * @param userId
     * @param number
     * @return
     */
    @Override
    public Integer createJoinRoomMsgByNumber(Integer userId, String number) throws Exception {
        if (userId == null || number == null || "".equals(number)) {
            throw new ParamException();
        }
        // 查询房间是否存在
        RoomCustom room = roomCustomMapper.selectRoomByRoomNumber(number);
        if (room == null) {
            throw new ParamException("房间不存在！");
        }
        // 特判用户不能加入自己的房间
        if (room.getUserid() == userId) {
            throw new DataOperateException("无法加入自己的房间！");
        }
        // 特判用户是否已经在房间中
        List<RoomCustom> rooms2 = userCustomMapper.selectJoinRoomsByUserId(userId);
        for (RoomCustom r : rooms2) {
            if (r.getId() == room.getId())
                throw new DataOperateException("您已在房间中！");
        }

        // 生成一条消息记录
        Msgtype msgtype = new Msgtype();
        msgtype.setReceiveuser(room.getUserid());
        msgtype.setType(1); // 1表示申请加入房间的消息
        msgtype.setSenduser(userId);
        if (insertMessageType(msgtype) <= 0) {
            throw new DataOperateException("更新数据失败！");
        }
        // 生成申请加入房间的详细记录
        Roomapply roomapply = new Roomapply();
        roomapply.setRoomid(room.getId());
        roomapply.setTypeid(msgtype.getId());
        if (roomapplyMapper.insert(roomapply) <= 0) {
            throw new DataOperateException("更新数据失败！");
        }
        return 1;
    }

    /**
     * 插入一条消息记录
     * @param msgtype
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertMessageType(Msgtype msgtype) throws Exception {
        if (msgtype == null || msgtype.getReceiveuser() == null ||
                msgtype.getSenduser() == null || msgtype.getType() == null) {
            throw new ParamException();
        }
        // 查询发送消息的用户是否存在
        Integer senduserId = msgtype.getSenduser();
        User senduser = userMapper.selectByPrimaryKey(senduserId);
        if (senduser == null) {
            throw new ParamException();
        }
        // 查询接收消息的用户是否存在
        Integer receiveUserId = msgtype.getReceiveuser();
        User reuser = userMapper.selectByPrimaryKey(receiveUserId);
        if (reuser == null) {
            throw new ParamException();
        }
        return msgtypeMapper.insertSelective(msgtype);
    }


    /**
     * 插入一条申请加入房间的详细记录
     * @param roomapply
     * @return
     * @throws Exception
     */
    public Integer insertRoomApply(Roomapply roomapply) throws Exception{
        if (roomapply == null || roomapply.getRoomid() == null ||
                roomapply.getTypeid() == null) {
            throw new ParamException();
        }
        // 判断房间是否存在
        RoomCustom room = roomCustomMapper.selectRoomById(roomapply.getId());
        if (room == null) {
            throw new ParamException();
        }
        return roomapplyMapper.insertSelective(roomapply);
    }


}
