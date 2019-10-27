package com.diandian.service.impl;

import com.diandian.dao.StatisticsMapper;
import com.diandian.dao.custom.StatisticsCustomMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.Statistics;
import com.diandian.model.custom.StatisticsCustom;
import com.diandian.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsCustomMapper statisticsCustomMapper;
    @Autowired
    private StatisticsMapper statisticsMapper;


    /**
     * 根据学生id和房间id
     * 获取此学生在此房间的考情总体情况
     * @param roomId
     * @param userId
     * @return
     */
    @Override
    public Statistics selectByRoomIdAndUserId(Integer roomId, Integer userId) throws Exception {
        if (roomId == null || userId == null) {
            throw new ParamException();
        }
        return statisticsCustomMapper.selectByRoomIdAndUserId(roomId, userId);
    }


    /**
     * 根据学生id和房间id
     * 修改学生在房间中的考勤状态统计
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateByRoomIdAndUserId(Statistics statistics) throws Exception {
        if (statistics == null) {
            throw new ParamException();
        }
        // 查询数据库，获取statistic表的id
        Statistics s = selectByRoomIdAndUserId(statistics.getRoomid(), statistics.getUserid());
        if (s == null) {
            throw new ParamException();
        }
        statistics.setId(s.getId());
        return updateByStatisticId(statistics);
    }


    /**
     * 根据statistic表的id
     * 修改学生在房间中的考勤状态统计
     * @param statistics
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateByStatisticId(Statistics statistics) throws Exception {
        if (statistics == null || statistics.getId() == null ||
                statistics.getUserid() == null || statistics.getRoomid() == null) {
            throw new ParamException();
        }
        return statisticsMapper.updateByPrimaryKeySelective(statistics);
    }


    /**
     * 插入一条新的statistic
     * @param statistics
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertStatistic(Statistics statistics) throws Exception {
        if (statistics == null || statistics.getRoomid() == null ||
                statistics.getUserid() == null) {
            throw new ParamException();
        }
        return statisticsMapper.insertSelective(statistics);
    }


    /**
     * 根据roomId获取房间内全体学生的考勤统计情况
     * @return
     */
    @Override
    public List<StatisticsCustom> selectStatisticsByRoomId(Integer roomId) throws Exception {
        if (roomId == null) {
            throw new ParamException();
        }
        return statisticsCustomMapper.selectByRoomId(roomId);
    }


}
