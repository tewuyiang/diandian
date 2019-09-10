package com.diandian.controller.attendance.websocket;

import com.alibaba.fastjson.JSONObject;
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
import java.util.Map;

/**
 * 被考勤用户点击签到后的处理类
 */

@Controller
@ServerEndpoint("/Attendance/checkOne/{roomID}/{studentID}")
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
    // 当前学生签到时长
    private double attTimeLong;

    public OneStudentCheck() {
        attTimeLong = 0;
    }

    public Session getSession() {
        return session;
    }
    public MapPoint getStudentLoca() {
        return studentLoca;
    }


    /**
     * 学生开始签到，发起连接
     *
     * @param session
     * @param studentID
     */
    @OnOpen
    public void join(Session session, @PathParam("roomID") Integer roomID,
                     @PathParam("studentID") Integer studentID) {
        // 记录信息
        this.session = session;
        this.studentID = studentID;
        this.roomID = roomID;

        JSONObject jsonObject = null;
        // 加入考勤房间，若考勤开始，成功加入，否则无法加入
        if (addToRoom()) {
            jsonObject = MessageUtils.messageToJson("start", "success", "成功加入考勤房间");
        } else {
            jsonObject = MessageUtils.messageToJson("start", "failure", "考勤未开始");
        }
        // 回送结果
        sendMessage(session, jsonObject.toJSONString());
    }


    /**
     * 接收到客户端发来的消息
     * @param message
     */
    @OnMessage
    public void message(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        String type = (String) jsonObject.get("type");

        // 若发送来的是位置信息
        if ("location".equals(type)) {
            // 则更新客户位置
            newLocation(jsonObject);
            // 计算距离并更新信息
            computeDistanceAndUpdate(room.getTeacherLoca(), studentLoca);
        }
    }


    /**
     * 考勤结束
     */
    @OnClose
    public void finish() {
        // 用户退出考勤房间
        dropOutRoom();
    }


    private void computeDistanceAndUpdate(MapPoint teacher, MapPoint student) {
        JSONObject message = null;

        if (teacher == null || student == null) {
            message = MessageUtils.messageToJson("location", "failure", )
        }
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
    private boolean addToRoom() {
        // 获取所有考勤房间的集合
        Map<Integer, AttendanceRoom> roomMap = AttendanceRoom.roomMap;

        // 获取用户想要加入的考勤房间
        room = roomMap.get(roomID);
        // 房间不在集合中，表示考勤未开始
        if (room == null) {
            return false;
        }
        // 若考勤房间存在，表示已经开始考勤
        // 将当前用户加入房间
        room.getStudentList().add(this);

        return true;
    }


    /**
     * 获取到教师位置时的处理类
     * @param jsonObject
     */
    private void newLocation(JSONObject jsonObject) {
        // 存储学生位置，latitude：纬度   longitude：经度
        if (studentLoca == null) {
            studentLoca = new MapPoint((double) jsonObject.get("latitude"),
                    (double) jsonObject.get("longitude"));
        } else {
            studentLoca.setLatitude((double) jsonObject.get("latitude"));
            studentLoca.setLongitude((double) jsonObject.get("longitude"));
        }
    }


    /**
     * 退出考勤房间
     */
    private void dropOutRoom() {
        room.getStudentList().remove(this);
    }
}
