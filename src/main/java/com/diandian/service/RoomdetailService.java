package com.diandian.service;

import com.diandian.model.Roomdetail;
import com.diandian.model.custom.RoomCustom;
import com.diandian.model.custom.RoomdetailCustom;

import java.util.List;

public interface RoomdetailService {

    /**
     * 插入一条roomdetail
     * @param roomdetail
     * @return
     * @throws Exception
     */
    Integer insertRoomdetail(Roomdetail roomdetail) throws Exception;


    /**
     * 根据房间id查询房间考勤明细
     * @param roomId
     * @return
     */
    List<RoomdetailCustom> selectRoomdetailByRoomId(Integer roomId) throws Exception;


    /**
     * 通过房间id删除房间考勤明细
     * @param roomId
     * @return
     * @throws Exception
     */
    Integer deleteRoomdetailByRoomId(Integer roomId) throws Exception;
}
