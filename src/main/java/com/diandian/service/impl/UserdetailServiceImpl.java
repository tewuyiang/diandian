package com.diandian.service.impl;

import com.diandian.dao.RoomdetailMapper;
import com.diandian.dao.UserMapper;
import com.diandian.dao.UserdetailMapper;
import com.diandian.dao.custom.UserdetailCustomMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.Roomdetail;
import com.diandian.model.Userdetail;
import com.diandian.model.custom.UserdetailCustom;
import com.diandian.service.UserdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserdetailServiceImpl implements UserdetailService {

    @Autowired
    private UserdetailMapper userdetailMapper;
    @Autowired
    private UserdetailCustomMapper userdetailCustomMapper;
    @Autowired
    private RoomdetailMapper roomdetailMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     * 插入一条数据到userdetail表中
     */
    @Override
    public Integer insertUserdetail(Userdetail userdetail) throws Exception {
        if (userdetail == null) {
            throw new ParamException("信息获取异常");
        }
        return userdetailMapper.insertSelective(userdetail);
    }


    /**
     * 根据房间明细号，查询房间中某一次考勤的全部用户明细
     * @param roomdetailId
     * @return
     * @throws Exception
     */
    @Override
    public List<UserdetailCustom> selectUserdetailsByRoomdetailId(Integer roomdetailId) throws Exception {
        if (roomdetailId == null) {
            throw new ParamException();
        }
        // 查询这次考勤是否存在
        Roomdetail roomdetail = roomdetailMapper.selectByPrimaryKey(roomdetailId);
        if (roomdetail == null) {
            return null;
        }
        return userdetailCustomMapper.selectUserdetailsByRoomdetailId(roomdetailId);
    }


    /**
     * 查询某个用户在某个房间内的全部记录
     * @param roomId
     * @param userId
     */
    @Override
    public List<UserdetailCustom> selectOnStudentDetails(Integer roomId, Integer userId) throws Exception {
        if (roomId == null || userId == null) {
            throw new ParamException();
        }
        return userdetailCustomMapper.selectOnStudentDetails(roomId, userId);
    }
}
