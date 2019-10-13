package com.diandian.model.custom;

import com.diandian.model.Room;
import com.diandian.model.Statistics;

import java.util.List;

public class RoomCustom extends Room {

    // 房间考勤情况统计
    private List<StatisticsCustom> userStatistics;
    // 考勤总次数
    private Integer attTimes;

    public List<StatisticsCustom> getUserStatistics() {
        return userStatistics;
    }

    public void setUserStatistics(List<StatisticsCustom> userStatistics) {
        this.userStatistics = userStatistics;
    }

    public Integer getAttTimes() {
        return attTimes;
    }

    public void setAttTimes(Integer attTimes) {
        this.attTimes = attTimes;
    }
}
