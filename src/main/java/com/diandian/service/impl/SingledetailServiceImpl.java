package com.diandian.service.impl;

import com.diandian.dao.RoomdetailMapper;
import com.diandian.dao.SingledetailMapper;
import com.diandian.dao.custom.SingledetailCustomMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.Roomdetail;
import com.diandian.model.custom.SingledetailCustom;
import com.diandian.service.SingledetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingledetailServiceImpl implements SingledetailService {

    @Autowired
    private SingledetailMapper singledetailMapper;
    @Autowired
    private SingledetailCustomMapper singledetailCustomMapper;
    @Autowired
    private RoomdetailMapper roomdetailMapper;

    /**
     * 通过房间考勤明细获取获取所有个人明细
     * 单次检测考勤模式下
     * @param roomdetailId
     * @return
     */
    @Override
    public List<SingledetailCustom> selectSingledetailsByRoomdetailId(Integer roomdetailId) throws Exception {
        if (roomdetailId == null) {
            throw new ParamException();
        }
        Roomdetail roomdetail = roomdetailMapper.selectByPrimaryKey(roomdetailId);
        if (roomdetail == null) {
            throw new ParamException();
        }
        return singledetailCustomMapper.selectSingledetailsByRoomdetailId(roomdetailId,roomdetail.getRoomid());
    }


    /**
     * 查询某个用户在某个房间内的全部明细
     * 在单次测距模式下
     * @param roomId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<SingledetailCustom> selectOnStudentDetails(Integer roomId, Integer userId) throws Exception {
        if (roomId == null || userId == null) {
            throw new ParamException();
        }
        return singledetailCustomMapper.selectOnStudentDetails(roomId,userId);
    }

}
