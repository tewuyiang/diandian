package com.diandian.service;

import com.diandian.model.Room;
import com.diandian.model.custom.UserCustom;

import java.util.List;

public interface RoomService {
    /**
     * 根据房间号查询房间内的所有用户
     * @param roomID
     * @return
     */
    List<UserCustom> selectUserByRoomId(Integer roomID) throws Exception;


    /**
     * 根据房间的id查询房间信息
     * @param roomId
     * @return
     * @throws Exception
     */
    Room selectRoomByRoomId(Integer roomId) throws Exception;
}
