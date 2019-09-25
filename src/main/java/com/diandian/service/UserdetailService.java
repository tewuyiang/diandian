package com.diandian.service;

import com.diandian.model.Userdetail;
import com.diandian.model.custom.UserdetailCustom;

import java.util.List;

public interface UserdetailService {

    /**
     * 插入一条新数据到userdetail表中
     * @param userdetail
     */
    Integer insertUserdetail(Userdetail userdetail) throws Exception;


    /**
     * 根据房间明细号，查询房间中某一次考勤的全部用户明细
     * @param roomdetailId
     * @return
     */
    List<UserdetailCustom> selectUserdetailsByRoomdetailId(Integer roomdetailId) throws Exception;


    /**
     * 查询某个用户在某个房间内的全部记录
     * @param roomId
     * @param userId
     * @return
     */
    List<UserdetailCustom> selectOnStudentDetails(Integer roomId, Integer userId) throws Exception;
}
