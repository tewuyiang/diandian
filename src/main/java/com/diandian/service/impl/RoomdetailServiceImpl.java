package com.diandian.service.impl;

import com.diandian.dao.RoomMapper;
import com.diandian.dao.RoomdetailMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.Roomdetail;
import com.diandian.service.RoomdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomdetailServiceImpl implements RoomdetailService {

    @Autowired
    private RoomdetailMapper roomdetailMapper;


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
}
