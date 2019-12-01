package com.diandian.service.impl;

import com.diandian.dao.RoomMapper;
import com.diandian.dao.RoomdetailMapper;
import com.diandian.dao.custom.RoomCustomMapper;
import com.diandian.dao.custom.RoomdetailCustomMapper;
import com.diandian.dao.custom.SingledetailCustomMapper;
import com.diandian.dao.custom.StatisticsCustomMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.Roomdetail;
import com.diandian.model.custom.RoomCustom;
import com.diandian.model.custom.RoomdetailCustom;
import com.diandian.service.RoomdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomdetailServiceImpl implements RoomdetailService {

    @Autowired
    private RoomdetailMapper roomdetailMapper;
    @Autowired
    private RoomdetailCustomMapper roomdetailCustomMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RoomCustomMapper roomCustomMapper;
    @Autowired
    private SingledetailCustomMapper singledetailCustomMapper;
    @Autowired
    private StatisticsCustomMapper statisticsCustomMapper;


    /**
     * 添加一条新的roomdetail记录
     */
    @Override
    public Integer insertRoomdetail(Roomdetail roomdetail) throws Exception {
        if (roomdetail == null) {
            throw new ParamException("信息获取异常");
        }
        return roomdetailMapper.insertSelective(roomdetail);
    }


    /**
     * 根据房间id查询房间考勤明细
     * @param roomId
     * @return
     * @throws Exception
     */
    @Override
    public List<RoomdetailCustom> selectRoomdetailByRoomId(Integer roomId) throws Exception {
        if (roomId == null) {
            throw new ParamException();
        }

        // 查询房间是否存在
        RoomCustom room = roomCustomMapper.selectRoomById(roomId);
        if (room == null || room.getDel() == 0) {
            return null;
        }
        return roomdetailCustomMapper.selectRoomdetailByRoomId(roomId);
    }


    /**
     * 通过房间id删除房间考勤明细
     * @param roomId
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteRoomdetailByRoomId(Integer roomId) throws Exception {
        if (roomId == null) {
            throw new ParamException();
        }
        // 删除房间中的所有个人明细
        List<RoomdetailCustom> roomdetails = roomdetailCustomMapper.selectRoomdetailByRoomId(roomId);
        if (roomdetails != null) {
            for (RoomdetailCustom roomdetail : roomdetails) {
                singledetailCustomMapper.deleteByRoomdetailId(roomdetail.getId());
            }
        }
        // 删除房间内每个用户的统计情况
        statisticsCustomMapper.deleteByRoomId(roomId);
        // 删除房间中的所有考勤记录
        return roomdetailCustomMapper.deleteByRoomId(roomId);
    }


}
