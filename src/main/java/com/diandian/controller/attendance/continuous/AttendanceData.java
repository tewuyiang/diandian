package com.diandian.controller.attendance.continuous;

import com.diandian.constants.AttConstant;

import java.util.Date;

/**
 * 记录考勤过程中需要的一些数据
 */
public class AttendanceData {
    // 参与考勤的时长
    private Integer attTimeLong;
    // 记录开始签到时间
    private Date beginTime;

    public AttendanceData() {
        attTimeLong = 0;
    }

    public Integer getAttTimeLong() {
        return attTimeLong;
    }

    public void setAttTimeLong(Integer attTimeLong) {
        this.attTimeLong = attTimeLong;
    }

    public void addTimeLong() {
        attTimeLong += AttConstant.INTEVAL_TIME;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
}
