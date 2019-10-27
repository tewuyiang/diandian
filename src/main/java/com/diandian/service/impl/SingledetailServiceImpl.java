package com.diandian.service.impl;

import com.diandian.dao.RoomdetailMapper;
import com.diandian.dao.SingledetailMapper;
import com.diandian.dao.StatisticsMapper;
import com.diandian.dao.custom.SingledetailCustomMapper;
import com.diandian.dao.custom.StatisticsCustomMapper;
import com.diandian.exception.DataOperateException;
import com.diandian.exception.ParamException;
import com.diandian.model.Roomdetail;
import com.diandian.model.Singledetail;
import com.diandian.model.Statistics;
import com.diandian.model.custom.SingledetailCustom;
import com.diandian.service.SingledetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingledetailServiceImpl implements SingledetailService {

    @Autowired
    private SingledetailMapper singledetailMapper;
    @Autowired
    private SingledetailCustomMapper singledetailCustomMapper;
    @Autowired
    private RoomdetailMapper roomdetailMapper;
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private StatisticsCustomMapper statisticsCustomMapper;

    /**
     * 通过房间考勤明细获取获取所有个人明细
     * 单次检测考勤模式下
     *
     * @param roomdetailId
     * @return
     */
    @Override
    public List<SingledetailCustom> selectSingledetailsByRoomdetailId(Integer roomdetailId) throws Exception {
        if (roomdetailId == null) {
            throw new ParamException();
        }
        Roomdetail roomdetail = roomdetailMapper.selectByPrimaryKey(roomdetailId);
        if (roomdetail == null) {
            throw new ParamException();
        }
        return singledetailCustomMapper.selectSingledetailsByRoomdetailId(roomdetailId, roomdetail.getRoomid());
    }


    /**
     * 查询某个用户在某个房间内的全部明细
     * 在单次测距模式下
     *
     * @param roomId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<SingledetailCustom> selectOnStudentDetails(Integer roomId, Integer userId) throws Exception {
        if (roomId == null || userId == null) {
            throw new ParamException();
        }
        return singledetailCustomMapper.selectOnStudentDetails(roomId, userId);
    }


    /**
     * 插入一条新的singledetail数据
     *
     * @param singledetail
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertSingledetail(Singledetail singledetail) throws Exception {
        if (singledetail.getUserid() == null || singledetail.getRoomdetailid() == null) {
            throw new ParamException();
        }
        return singledetailMapper.insertSelective(singledetail);
    }


    /**
     * 修改学生的某次考勤状态
     * @param singledetailId
     * @return
     */
    @Override
    public Integer updateStudentStatus(Integer singledetailId, Short status) throws Exception {
        if (singledetailId == null) {
            throw new ParamException();
        }
        // 判断是否有这条明细
        Singledetail singledetail = singledetailMapper.selectByPrimaryKey(singledetailId);
        if (singledetail == null) {
            throw new ParamException();
        }
        // 获取被考勤的房间明细，以此获取房间号
        Integer roomId = roomdetailMapper.selectByPrimaryKey(singledetail.getRoomdetailid()).getRoomid();
        // 通过roomId和userId获取学生考勤统计情况
        Statistics statistics = statisticsCustomMapper.selectByRoomIdAndUserId(roomId, singledetail.getUserid());
        if (statistics == null) {
            throw new ParamException();
        }
        // 修改考勤状态统计
        updateStatistics(status, singledetail.getAttendstatus(), statistics);
        if (statisticsMapper.updateByPrimaryKeySelective(statistics) <= 0) {
            throw new DataOperateException();
        }
        // 修改这次考勤的状态
        singledetail.setAttendstatus(status);
        // 更新数据库
        return singledetailMapper.updateByPrimaryKeySelective(singledetail);
    }

    /**
     * 修改学生考勤统计情况
     * @param status 新的考勤状态
     * @param ageStatus 旧的考勤状态
     * @param statistics
     */
    @SuppressWarnings("all")
    private void updateStatistics(Short newStatus, Short oldStatus, Statistics statistics) throws ParamException {
        // 之前的考勤状态-1
        switch (oldStatus) {
            case 1:
                statistics.setArrive(statistics.getArrive() - 1);
                break;
            case 2:
                statistics.setLate(statistics.getLate() - 1);
                break;
            case 3:
                statistics.setLeaved(statistics.getLeaved() - 1);
                break;
            case 4:
                statistics.setAbsentee(statistics.getAbsentee() - 1);
                break;
            default:
                throw new ParamException();
        }
        // 新的考勤状态+1
        switch (newStatus) {
            case 1:
                statistics.setArrive(statistics.getArrive() + 1);
                break;
            case 2:
                statistics.setLate(statistics.getLate() + 1);
                break;
            case 3:
                statistics.setLeaved(statistics.getLeaved() + 1);
                break;
            case 4:
                statistics.setAbsentee(statistics.getAbsentee() + 1);
                break;
            default:
                throw new ParamException();
        }
    }

}
