package com.diandian.service;

import com.diandian.model.Singledetail;
import com.diandian.model.custom.SingledetailCustom;
import com.diandian.utils.R;

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


    /**
     * 插入一条新的考勤记录
     * @param singledetail
     * @return
     * @throws Exception
     */
    Integer insertSingledetail(Singledetail singledetail) throws Exception;


    /**
     * 修改学生的某次考勤状态
     * @param singledetailId
     * @return
     */
    Integer updateStudentStatus(Integer singledetailId, Short status) throws Exception;
}
