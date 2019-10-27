package com.diandian.dao.custom;

import com.diandian.model.Statistics;
import com.diandian.model.custom.StatisticsCustom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StatisticsCustomMapper {

    /**
     * 通过房间号获取某个房间的统计情况
     * @param roomId
     * @return
     */
    List<StatisticsCustom> selectByRoomId(Integer roomId) throws Exception;


    /**
     * 根据学生id和房间id
     * 获取此学生在此房间的考情总体情况
     * @param roomId
     * @param userId
     * @return
     */
    @Select("select * from statistics where roomid = #{roomId} and userid = #{userId}")
    Statistics selectByRoomIdAndUserId(@Param("roomId") Integer roomId,
                                       @Param("userId") Integer userId) throws Exception;

}
