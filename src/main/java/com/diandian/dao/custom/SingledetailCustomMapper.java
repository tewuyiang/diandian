package com.diandian.dao.custom;

import com.diandian.model.custom.SingledetailCustom;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SingledetailCustomMapper {

    /**
     * 通过房间考勤明细获取获取所有个人明细
     * 单次检测考勤模式下
     * @param roomdetailId
     * @return
     */
    List<SingledetailCustom> selectSingledetailsByRoomdetailId(@Param("roomdetailId") Integer roomdetailId, @Param("roomId")Integer roomId)throws Exception;


    /**
     * 查询某个用户在某个房间内的全部明细
     * 在单次测距模式下
     * @param roomId
     * @param userId
     * @return
     * @throws Exception
     */
    List<SingledetailCustom> selectOnStudentDetails(@Param("roomId")Integer roomId, @Param("userId")Integer userId)throws Exception;

    /**
     * 根据房间考勤明细号删除个人明细
     * @param roomdetailId
     * @return
     * @throws Exception
     */
    @Delete("DELETE FROM singledetail WHERE roomdetailid=#{roomdetailId}")
    Integer deleteByRoomdetailId(Integer roomdetailId) throws Exception;
}
