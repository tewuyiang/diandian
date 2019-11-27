package com.diandian.controller.attendance.single;

import com.alibaba.fastjson.JSONObject;
import com.diandian.constants.AttConstant;
import com.diandian.utils.DateTimeUtil;
import com.diandian.utils.attendance.CheckUtil;
import com.diandian.utils.attendance.MapPoint;
import com.diandian.utils.attendance.MessageUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 学生签到处理类
 * 学生发送位置信息
 * 此类将其与教师的位置信息进行比较
 * 并将比较的结果反馈给学生以及教师
 */
@RestController
@RequestMapping("/attendance/single")
public class StudentSignIn {

    /**
     * 获取房间考勤类的房间列表
     * @RestController 类属于单例模式，所以可以共享这个数据
     * 但是是多线程的
     */
    private Map<Integer,TeacherCheck> roomMap = TeacherCheck.roomMap;


    /**
     * 学生签到处理接口
     * 接收学生客户端的位置消息并判断签到状态
     * @param roomId 要签到的房间id
     * @param studentId 要签到的学生id
     * @param latitude 位置：纬度
     * @param longitude 位置：经度
     */
    @PostMapping(value="/student",produces = "text/html;charset=UTF-8")
    public String checkStudent(Integer roomId, Integer studentId,
                               Double latitude, Double longitude, HttpServletResponse response) {
        JSONObject message = null;
        System.out.println("学生签到:"+roomId + "," + studentId + "," + latitude + "," + longitude);
        // 判断参数是否正确接受
        if (roomId == null || studentId == null || latitude == null || longitude == null) {
            message = MessageUtils.messageToJson(AttConstant.STATUS,
                    AttConstant.FAILURE, "信息获取异常！");
            System.out.println("1:信息获取异常");
            return message.toJSONString();
        }

        // 获取学生签到房间的处理类对象
        TeacherCheck teacherCheck = roomMap.get(roomId);
        // 若房间处理对象不存在房间列表中，或者还未获取到老师的位置，表示考勤未开始
        if (teacherCheck == null || teacherCheck.getTeacherLocation() == null) {
            message = MessageUtils.messageToJson(AttConstant.STATUS,
                    AttConstant.FAILURE, "考勤未开始！");
            System.out.println("2:考勤未开始");
            return message.toJSONString();
        }

        // 判断房间中是否有此学生
        StudentData studentData = teacherCheck.getStudentsData().get(studentId);
        if (studentData == null) {
            message = MessageUtils.messageToJson(AttConstant.STATUS,
                    AttConstant.FAILURE, "您不在此房间中！");
            System.out.println("3:您不在此房间中");
            return message.toJSONString();
        }
        // 若以上条件均不满足，表示数据正常
        // 检测距离以及迟到时间，判断用户的签到状态
        return judgeSignIn(teacherCheck, studentData, new MapPoint(latitude, longitude));
    }


    /**
     * 根据学生发送来的位置信息，以及房间的考勤距离等信息
     * 判断学生的考勤状态
     * @param teacherCheck  此学生对应的教师处理对象
     * @param studentData   存储学生考勤数据的对象
     * @param point         学生的位置
     */
    private String judgeSignIn(TeacherCheck teacherCheck,
                               StudentData studentData, MapPoint point) {
        JSONObject message = null;

        // 获取学生签到时间信息
        Date attTime = studentData.getAttTime();
        // attTime不为null表示已经签到过了，或者老师已经代为操作
        if (attTime != null) {
            // 返回重复操作状态码，并直接返回用户的考勤状态
            message = MessageUtils.messageToJson(AttConstant.STATUS,
                    AttConstant.REPEAT, studentData.getStatus(), studentData.getStudentId());
            System.out.println("4:已经签到");
            return message.toJSONString();
        }

        // 若attTime为null，表示还未签到
        // 计算学生与老师的距离，并判断距离是否在范围内
        double distance = CheckUtil.distanceOf(point, teacherCheck.getTeacherLocation());
        System.out.println(distance);
        // 若在考勤范围内
        if (distance < teacherCheck.getDistance()) {
            // 实际距离小于签到距离，则再判断是否迟到
            // 获取当前时间与开始考勤时间的毫秒差值
            long chaTime = new Date().getTime() - teacherCheck.getBeginTime().getTime();
            long lateTime = teacherCheck.getLateTime() * 60 * 1000;

            // 若签到时间与开始时间的差值，小于设置的迟到时间
            // 表示未迟到，则记录状态为到达，否则记为迟到
            if (chaTime < lateTime) {
                studentData.setStatus(AttConstant.ARRIVE);
                // 封装提示信息
                message = MessageUtils.messageToJson(AttConstant.STATUS,
                        AttConstant.SUCCESS, AttConstant.ARRIVE, studentData.getStudentId());
                System.out.println("5:到达");
            } else {
                studentData.setStatus(AttConstant.LATE);
                // 封装提示信息
                message = MessageUtils.messageToJson(AttConstant.STATUS,
                        AttConstant.SUCCESS, AttConstant.LATE, studentData.getStudentId());
                System.out.println("6:迟到");
            }
            // 修改用户的签到时间
            studentData.setAttTime(new Date());
            // 将签到时间封装到数据中
            message.put("attTime", DateTimeUtil.datetimeToString(studentData.getAttTime()));
            // 签到成功，需要向教师发送成功信息
            teacherCheck.sendMessage(message.toJSONString());
        } else {
            // 若实际距离大于签到距离，表示在考勤距离外
            // 则无法成功签到，返回提示信息
            message = MessageUtils.messageToJson(AttConstant.STATUS,
                    AttConstant.FAILURE, "您不在考勤距离内,请重试！", studentData.getStudentId());
            System.out.println("7:不在距离内");
        }
        return message.toJSONString();
    }

}
