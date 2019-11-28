package com.diandian.service.impl;

import com.diandian.dao.ListsMapper;
import com.diandian.dao.MsgtypeMapper;
import com.diandian.dao.RoomapplyMapper;
import com.diandian.dao.UserMapper;
import com.diandian.dao.custom.MsgtypeCustomMapper;
import com.diandian.dao.custom.RoomCustomMapper;
import com.diandian.dao.custom.RoomapplyCustomMapper;
import com.diandian.dao.custom.UserCustomMapper;
import com.diandian.exception.DataOperateException;
import com.diandian.exception.ParamException;
import com.diandian.model.Lists;
import com.diandian.model.Msgtype;
import com.diandian.model.Roomapply;
import com.diandian.model.User;
import com.diandian.model.custom.MsgtypeCustom;
import com.diandian.model.custom.RoomCustom;
import com.diandian.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private MsgtypeCustomMapper msgtypeCustomMapper;
    @Autowired
    private RoomapplyMapper roomapplyMapper;
    @Autowired
    private RoomapplyCustomMapper roomapplyCustomMapper;
    @Autowired
    private UserCustomMapper userCustomMapper;
    @Autowired
    private ListsMapper listsMapper;

    /**
     * 申请加入房间，根据房间number
     * @param userId
     * @param number
     * @return
     */
    @Override
    public Integer createJoinRoomMsgByNumber(Integer userId, String number, String remarks) throws Exception {
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
        if ("".equals(remarks)) {
            remarks = null;
        }
        // 查询是否已经有这条消息，若有，则不新增，而是将消息设置为未读
        Msgtype msg = msgtypeCustomMapper.selectMsgAndRoomApplyByThreeId(userId, room.getUserid(), room.getId());
        if (msg != null) {
            // 若已有这条记录，则直接修改原来的记录，不新增记录
            Roomapply roomapply = ((MsgtypeCustom)msg).getRoomapply();
            // 更新申请加入消息，设置为未处理
            roomapply.setDealresult(null);
            roomapply.setDealtime(null);
            roomapply.setRemarks(remarks);
            roomapplyMapper.updateByPrimaryKey(roomapply);
            // 更新msg
            // 设置消息未读
            msg.setIsread(0);
            msg.setSendtime(new Date());
            msgtypeMapper.updateByPrimaryKey(msg);
        } else {
            // 若没有，生成一条消息记录
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
            roomapply.setRemarks(remarks);
            if (roomapplyMapper.insert(roomapply) <= 0) {
                throw new DataOperateException("更新数据失败！");
            }
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


    /**
     * 获取所有申请加入我的房间的消息
     * @param userId
     * @return
     */
    @Override
    public List<MsgtypeCustom> selectApplyMyRoomsMessage(Integer userId) throws Exception {
        if (userId == null) {
            throw new ParamException();
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new ParamException();
        }
        return msgtypeCustomMapper.selectApplyRoomsByUserId(userId);
    }


    /**
     * 处理房间申请请求
     * @param msgId 消息id
     * @param result 处理结果
     * @throws Exception
     */
    @Override
    public Integer updateAfterDealRoomapply(Integer msgId, Integer result) throws Exception {
        if (msgId == null || result == null || result < 1 || result > 2) {
            throw new ParamException();
        }
        // 获取消息记录
        System.out.println(msgId);
        Msgtype msg = msgtypeMapper.selectByPrimaryKey(msgId);
        // 消息不存在
        if (msg == null) {
            throw new ParamException();
        }
        // 将消息消跟新为已经阅读状态
        if (msg.getIsread() == 0) {
            msg.setIsread(1);
            if (msgtypeMapper.updateByPrimaryKey(msg) <= 0) {
                throw new DataOperateException();
            }
        }

        // 获取申请消息明细
        Roomapply roomapply = roomapplyCustomMapper.selectByTypeId(msg.getId());
        if (roomapply == null) {
            throw new ParamException();
        }

        // 判断处理结果,若处理结果为同意，则将用户加入到房间中，否则不加入
        if (result == 1) {
            // 同意用户加入房间，生成lists表记录
            Lists lists = new Lists();
            lists.setRoomid(roomapply.getRoomid());
            lists.setRemarkname(roomapply.getRemarks());
            lists.setUserid(msg.getSenduser());
            lists.setDel((short)1);
            listsMapper.insert(lists);
        }

        // 修改明细情况
        roomapply.setDealtime(new Date());
        roomapply.setDealresult(result);
        // 更新数据库
        if (roomapplyMapper.updateByPrimaryKeySelective(roomapply) <= 0) {
            throw new DataOperateException();
        }
        return 1;
    }


    /**
     * 获取用户未读取的消息
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<MsgtypeCustom> updateUnreadMessageByUserId(Integer userId) throws Exception {
        if (userId == null) {
            throw new ParamException();
        }
        // 判断是否有此用户
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new ParamException();
        }
        List<MsgtypeCustom> messages = msgtypeCustomMapper.selectUnreadMsgByUserId(userId);
        if (messages != null) {
            for (MsgtypeCustom message : messages) {
                // 将消息状态更新为已读
                message.setIsread(1);
                msgtypeMapper.updateByPrimaryKey(message);
            }
        }
        return messages;
    }


    /**
     * 获取用户未处理的消息
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<MsgtypeCustom> updateAndselectUnDealMessage(Integer userId) throws Exception {
        if (userId == null) {
            throw new ParamException();
        }
        // 判断是否有此用户
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new ParamException();
        }
        List<MsgtypeCustom> messages = msgtypeCustomMapper.selectUnDealMessageByuserId(userId);
        if (messages != null) {
            for (MsgtypeCustom message : messages) {
                // 将消息状态更新为已读
                message.setIsread(1);
                msgtypeMapper.updateByPrimaryKey(message);
            }
        }
        return messages;
    }

}
