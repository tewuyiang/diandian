package com.diandian.controller.attendance.websocket;


import com.alibaba.fastjson.JSONObject;
import com.diandian.model.Room;
import com.diandian.model.custom.UserCustom;
import com.diandian.service.RoomService;
import com.diandian.utils.CloseUtil;
import com.diandian.utils.attendance.MapPoint;
import com.diandian.utils.attendance.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 老师点击开始考勤的处理类
 */
@Controller
@ServerEndpoint("/Attendance/start/{roomID}")
public class AttendanceRoom {
    // 存储所有考勤房间，一个AttendanceRoom对象表示一个房间
    public static Map<Integer, AttendanceRoom> roomMap = new ConcurrentHashMap<>();
    // 当前考勤房间的所有学生列表
    private List<OneStudentCheck> studentList;
    // 与当前房间老师的websocket会话
    private Session teacherSession;
    // 当前的房间id
    private Integer roomID;
    // 当前房间的老师的id
    private Integer teacherID;
    // 老师当前的位置坐标
    private MapPoint teacherLoca;
    // 房间的考勤距离
    private Double distance;

    // 操作数据库的service对象
    @Autowired
    private RoomService roomService;

    public AttendanceRoom() {
        // 使用线程安全的list集合
        studentList = new CopyOnWriteArrayList<>();
    }

    public List<OneStudentCheck> getStudentList() {
        return studentList;
    }
    public MapPoint getTeacherLoca() {
        return teacherLoca;
    }
    public Double getDistance() {
        return distance;
    }

    /**
     * 开启考勤房间，开始考勤
     *
     * @param teacherSession 教师客户端与服务器的会话
     * @param roomID         当前考勤的教室id
     * @OnOpen 此注解声明的方法将在与客户端连接时自动触发
     */
    @OnOpen
    public void start(Session teacherSession, @PathParam("roomID") Integer roomID) {
        // 记录信息
        this.teacherSession = teacherSession;
        this.roomID = roomID;

        JSONObject message = null;

        // 根据房间id查询数据库中房间的信息
        try {
            Room room = roomService.selectRoomByRoomId(roomID);
            // 若房间不存在，直接结束
            if (room == null) {
                CloseUtil.close(teacherSession);
                return;
            } else {
                // 获取房间信息
                this.teacherID = room.getUserid();
                this.distance = room.getDistance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = MessageUtils.messageToJson("start", "failure", "无法获取房间信息");
            sendMessage(this.teacherSession, message.toJSONString());
            return;
        }

        // 将当前开始考勤的房间加入到集合中
        roomMap.put(roomID, this);

        // 开始后，向客户端回送反馈信息
        try {
            // 数据库查询房间内的所有用户，并进行封装
            List<UserCustom> students = roomService.selectUserByRoomId(roomID);
            message = MessageUtils.messageToJson("start", "success", students);
        } catch (Exception e) {
            e.printStackTrace();
            message = MessageUtils.messageToJson("start", "failure", "无法获取房间信息");
        } finally {
            // 回送消息给客户端教师客户端
            sendMessage(this.teacherSession, message.toJSONString());
        }
    }


    /**
     * 接收到消息的处理类
     * @param message 接收到的消息
     * @OnMessage 此注解声明的方法将在接收到消息时自动执行
     */
    @OnMessage
    public void message(String message) {
        // 将接收的数据转换成json对象
        JSONObject jsonObject = MessageUtils.messageToJson(message);
        // 获取消息的类型
        String type = (String) jsonObject.get("type");

        // 若接受吧到的消息为位置坐标
        if ("location".equals(type)) {
            // 更新老师的位置
            newLocation(jsonObject);
        }
    }


    /**
     * 结束考勤
     * @OnClose 此注解声明的方法将在连接断开时触发
     */
    @OnClose
    public void finish() {
        // 关闭考勤房间
        closeRoom();

        // 更新数据库

    }


    /**
     * 向客户端发送数据
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
     * 获取到教师位置时的处理类
     * @param jsonObject
     */
    private void newLocation(JSONObject jsonObject) {

        // 存储老师位置，latitude：纬度   longitude：经度
        if (teacherLoca == null) {
            teacherLoca = new MapPoint((double) jsonObject.get("latitude"),
                    (double) jsonObject.get("longitude"));
        } else {
            teacherLoca.setLatitude((double) jsonObject.get("latitude"));
            teacherLoca.setLongitude((double) jsonObject.get("longitude"));
        }

    }


    /**
     * 关闭考勤房间
     */
    private void closeRoom() {
        // 关闭所有参与考勤的学生的会话，并清空学生列表
        for (OneStudentCheck student : studentList) {
            try {
                student.getSession().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        studentList.clear();

        // 将当前房间从数据库中删除
        roomMap.remove(roomID);
    }


}
