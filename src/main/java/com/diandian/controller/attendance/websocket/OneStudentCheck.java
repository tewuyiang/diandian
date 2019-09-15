package com.diandian.controller.attendance.websocket;

import com.alibaba.fastjson.JSONObject;
import com.diandian.constants.AttConstant;
import com.diandian.exception.ParamException;
import com.diandian.utils.JMPackage.MessageUtil;
import com.diandian.utils.attendance.CheckUtil;
import com.diandian.utils.attendance.MapPoint;
import com.diandian.utils.attendance.MessageUtils;
import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 被考勤用户点击签到后的处理类
 */

@Controller
@ServerEndpoint("/Attendance/checkOne")
public class OneStudentCheck {
    // 用户当前与服务器的会话
    private Session session;
    // 用户所在的房间的id
    private Integer roomID;
    // 签到的用户的id
    private Integer studentID;
    // 用户所在的考勤房间
    private AttendanceRoom room;
    // 当前学生的位置坐标
    private MapPoint studentLoca;


    public OneStudentCheck() {
    }


    public Session getSession() {
        return session;
    }
    public MapPoint getStudentLoca() {
        return studentLoca;
    }
    public Integer getStudentID() {
        return studentID;
    }



    /**
     * 学生开始签到，发起连接
     *
     * @param session
     */
    @OnOpen
    public void join(Session session) {
        // 记录信息
        this.session = session;

        // 回送连接成功消息
        JSONObject message = MessageUtils.messageToJson( AttConstant.CONNECT,
                AttConstant.SUCCESS, "连接成功");
        sendMessage(session, message.toJSONString());
    }



    /**
     * 接收到客户端发来的消息
     * @param message
     */
    @OnMessage
    public void message(String message) {
        // 将接收到的json数据转为json对象
        JSONObject jsonObject = JSONObject.parseObject(message);
        String type = (String) jsonObject.get("type");


        if (AttConstant.START.equals(type)) {
            // 若发送的是开始签到消息
            // 获取房间id和用户id
            Integer roomid = jsonObject.getInteger("roomId");
            Integer studentId = jsonObject.getInteger("studentId");

            if (roomid != null && studentId != null) {
                // 记录数据
                roomID = roomid;
                studentID = studentId;
                // 加入考勤房间，若考勤开始，成功加入，否则无法加入
                JSONObject msg = addToRoom();
                // 回送结果
                sendMessage(session, msg.toJSONString());
            }

        } else if (AttConstant.LOCATION.equals(type)) {
            // 若发送来的是位置信息
            // 则更新学生客户位置
            newLocation(jsonObject);
            // 计算距离并回送消息
            computeDistance(room.getTeacherLoca(), studentLoca);
        }
    }



    /**
     * 用户断开连接，可能是退出了房间
     */
    @OnClose
    public void finish() {
        // 退出房间
        dropOutRoom();
    }



    /**
     * 向客户端发送数据
     *
     * @param session 发送给的客户端会话
     * @param message 消息
     */
    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 加入考勤房间
     */
    private JSONObject addToRoom() {
        // 获取所有考勤房间的集合
        Map<Integer, AttendanceRoom> roomMap = AttendanceRoom.roomMap;
        JSONObject msg = null;

        // 获取用户想要加入的考勤房间
        room = roomMap.get(roomID);

        if (room == null || room.getTeacherLoca() == null) {
            // 房间不在集合中, 或者还未获取到老师的位置
            // 视为考勤未开始
            msg = MessageUtils.messageToJson(AttConstant.START,
                    AttConstant.FAILURE, "考勤未开始");
        } else {
            // 若考勤房间存在，表示已经开始考勤
            // 将当前用户加入房间
            room.getStudentList().add(this);
            msg = MessageUtils.messageToJson(AttConstant.START,
                    AttConstant.SUCCESS, "成功加入考勤房间");
        }
        return msg;
    }



    /**
     * 获取到学生位置，更新位置信息
     * @param location 封装位置信息的json对象
     */
    private void newLocation(JSONObject location) {
        // 获取经纬度
        Double latitude = (Double) location.get("latitude");
        Double longitude = (Double) location.get("longitude");

        // 位置信息为空
        if (latitude == null || longitude == null) {
            try {
                throw new RuntimeException("位置信息有误");
            } catch (RuntimeException e) {
                e.printStackTrace();
                return;
            }
        }

        // 存储学生位置，latitude：纬度   longitude：经度
        if (studentLoca == null) {
            studentLoca = new MapPoint(latitude, longitude);

            AttendanceData studentData = room.getStudentDataMap().get(studentID);
            // 学生数据不存在，直接返回
            if (studentData == null) return;

            // 记录当前学生开始签到的时间
            if (studentData.getBeginTime() == null) {
                studentData.setBeginTime(new Date());
            }

        } else {
            studentLoca.setLatitude(latitude);
            studentLoca.setLongitude(longitude);
        }
    }



    /**
     * 退出考勤房间
     */
    private void dropOutRoom() {
        room.getStudentList().remove(this);
    }



    /**
     * 接收到学生发来的位置，更新状态
     * 进行计算并分别向学生和老师回送消息
     * @param teacherLoca 老师位置坐标
     * @param studentLoca 学生位置坐标
     */
    private void computeDistance(MapPoint teacherLoca, MapPoint studentLoca) {

        JSONObject message = null;

        if (studentLoca == null) {
            // 理论上不会有这种情况
            message = MessageUtils.messageToJson(AttConstant.LOCATION,
                    AttConstant.FAILURE, null, studentID);
        } else {
            // 测距
            double distance = CheckUtil.distanceOf(teacherLoca, studentLoca);
            // 在考勤范围内
            if (distance <= room.getDistance()) {
                AttendanceData data = room.getStudentDataMap().get(studentID);
                // 房间不存在，直接返回
                if (data == null)  return;
                // 考勤成功，此学生考勤维持时间增加
                data.addTimeLong();

                // 构建回送消息
                message = MessageUtils.messageToJson(AttConstant.LOCATION,
                        AttConstant.SUCCESS, distance, studentID);

            } else {
                message = MessageUtils.messageToJson(AttConstant.LOCATION,
                        AttConstant.FAILURE, distance, studentID);
            }
        }
        // 向老师客户端回送消息
        sendMessage(room.getTeacherSession(), message.toJSONString());
        // 向学生客户端回送消息
        sendMessage(session, message.toJSONString());
    }


}
