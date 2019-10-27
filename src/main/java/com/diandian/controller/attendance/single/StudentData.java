package com.diandian.controller.attendance.single;

import java.util.Date;

/**
 * 记录考勤过程中需要的学生数据
 */
public class StudentData {
    // 学生id
    private Integer studentId;
    // 学生签到时间
    private Date attTime;
    // 学生签到状态
    private short status = 4;

    public StudentData(Integer studentId) {
        this.studentId = studentId;
    }



    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Date getAttTime() {
        return attTime;
    }

    public void setAttTime(Date attTime) {
        this.attTime = attTime;
    }

    @Override
    public String toString() {
        return "StudentData{" +
                "studentId=" + studentId +
                ", attTime=" + attTime +
                ", status=" + status +
                '}';
    }
}
