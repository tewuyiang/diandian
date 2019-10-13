package com.diandian.service;

import com.diandian.model.custom.SingledetailCustom;

import java.util.List;

public interface SingledetailService {

    /**
     * 通过房间考勤明细获取获取所有个人明细
     * 单次检测考勤模式下
     * @param roomdetailId
     * @return
     */
    List<SingledetailCustom> selectSingledetailsByRoomdetailId(Integer roomdetailId) throws Exception;


    /**
     * 查询某个用户在某个房间内的全部明细
     * 在单次测距模式下
     * @param roomId
     * @param userId
     * @return
     * @throws Exception
     */
    List<SingledetailCustom> selectOnStudentDetails(Integer roomId, Integer userId) throws Exception;
}
