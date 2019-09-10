package com.diandian.service.impl;

import com.diandian.dao.RoomMapper;
import com.diandian.dao.custom.ListsCustomMapper;
import com.diandian.dao.custom.RoomCustomMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.Room;
import com.diandian.model.custom.RoomCustom;
import com.diandian.model.custom.UserCustom;
import com.diandian.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RoomCustomMapper roomCustomMapper;
    @Autowired
    ListsCustomMapper listsCustomMapper;

    /**
     * 根据房间号查询房间内的所有用户
     * @param roomID
     * @return
     */
    @Override
    public List<UserCustom> selectUserByRoomId(Integer roomID) throws Exception {
        if (roomID == null) {
            throw new ParamException("信息获取异常");
        }
        return listsCustomMapper.selectUserByRoomId(roomID);
    }

    /**
     * 根据房间id查询房间信息
     * @param roomId
     */
    @Override
    public Room selectRoomByRoomId(Integer roomId) throws Exception {
        if (roomId == null) {
            throw new ParamException("信息获取异常");
        }
        return roomMapper.selectByPrimaryKey(roomId);
    }
}
