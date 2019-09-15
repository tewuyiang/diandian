package com.diandian.service;

import com.diandian.model.Lists;
import com.diandian.model.Room;
import com.diandian.model.custom.RoomCustom;
import com.diandian.model.custom.UserCustom;
import com.diandian.utils.R;

import java.util.List;

public interface RoomService {
    /**
     * 根据房间号查询房间内的所有用户
     * @param roomID
     * @return
     */
    List<UserCustom> selectUsersInRoomByRoomId(Integer roomID) throws Exception;


    /**
     * 根据房间的id查询房间信息
     * @param roomId
     * @return
     * @throws Exception
     */
    Room selectRoomByRoomId(Integer roomId) throws Exception;


    /**
     * 创建一个新的放房间
     * @param room
     * @return
     */
    Integer insertRoom(Room room) throws Exception;


    /**
     * 修改房间信息
     * @param room
     */
    Room updateRoom(Room room) throws Exception;


    /**
     * 删除房间(标记删除 == 假删)
     * @param roomId
     * @return
     */
    Integer deleteRoomByRoomId(Integer roomId) throws Exception;


    /**
     * 根据房间号码查询房间
     * @param roomNumber
     * @return
     */
    RoomCustom selectRoomByRoomNumber(String roomNumber) throws Exception;


    /**
     * 加入考勤房间
     * @param lists
     * @return
     * @throws Exception
     */
    Integer insertLists(Lists lists) throws Exception;


    /**
     * 用户退出考勤房间
     * @param roomId 房间号
     * @param userId 用户id
     * @return
     */
    Integer deleteUserToRoom(Integer roomId, Integer userId) throws Exception;
}
