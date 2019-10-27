package com.diandian.service;

import com.diandian.model.Statistics;
import com.diandian.model.custom.StatisticsCustom;

import java.util.List;

public interface StatisticsService {

    /**
     * 根据学生id和房间id
     * 获取此学生在此房间的考情总体情况
     * @param roomId
     * @param userId
     * @return
     */
    Statistics selectByRoomIdAndUserId(Integer roomId, Integer userId) throws Exception;


    /**
     * 根据学生id和房间id
     * 修改学生在房间中的考勤状态统计
     * @return
     * @throws Exception
     */
    Integer updateByRoomIdAndUserId(Statistics statistics) throws Exception;


    /**
     * 根据statistic表的id
     * 修改学生在房间中的考勤状态统计
     * @param statistics
     * @return
     * @throws Exception
     */
    Integer updateByStatisticId(Statistics statistics)throws Exception;


    /**
     * 插入一条新的考勤统计情况
     * @param statistics
     * @return
     * @throws Exception
     */
    Integer insertStatistic(Statistics statistics) throws Exception;

    /**
     * 根据roomId获取房间内全体学生的考勤统计情况
     * @return
     */
    List<StatisticsCustom> selectStatisticsByRoomId(Integer roomId) throws Exception;
}
