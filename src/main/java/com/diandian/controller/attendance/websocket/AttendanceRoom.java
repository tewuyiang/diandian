package com.diandian.controller.attendance.websocket;

import com.alibaba.fastjson.JSONObject;
import com.diandian.constants.AttConstant;
import com.diandian.model.Room;
import com.diandian.model.Roomdetail;
import com.diandian.model.Userdetail;
import com.diandian.model.custom.UserCustom;
import com.diandian.service.RoomService;
import com.diandian.service.RoomdetailService;
import com.diandian.service.UserdetailService;
import com.diandian.utils.CloseUtil;
import com.diandian.utils.attendance.CheckUtil;
import com.diandian.utils.attendance.MapPoint;
import com.diandian.utils.attendance.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 老师点击开始考勤的处理类
 * configurator = SpringConfigurator.class是为了使该类可以通过Spring注入。
 */
@ServerEndpoint(value = "/Attendance/start",configurator = SpringConfigurator.class)
public class AttendanceRoom {
    // 存储所有考勤房间，一个AttendanceRoom对象表示一个房间
    public static Map<Integer, AttendanceRoom> roomMap = new ConcurrentHashMap<>();
    // 当前考勤房间的所有学生处理类列表
    private List<OneStudentCheck> studentList;
    // 记录房间内所有学生的考勤时间
    private Map<Integer, AttendanceData> studentDataMap;
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
    // 考勤开始时间
    private Date beginTime;
    // 考勤结束时间
    private Date endTime;

    // 操作数据库的service对象
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomdetailService roomdetailService;
    @Autowired
    private UserdetailService userdetailService;


    public AttendanceRoom() {
        // 使用线程安全的list集合
        studentList = new CopyOnWriteArrayList<>();
        // 使用线程安全的map集合
        studentDataMap = new ConcurrentHashMap<>();

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
    public Session getTeacherSession() {
        return teacherSession;
    }
    public Map<Integer, AttendanceData> getStudentDataMap() {
        return studentDataMap;
    }



    /**
     * 开启考勤房间，开始考勤
     *
     * @param teacherSession 教师客户端与服务器的会话
     * @OnOpen 此注解声明的方法将在与客户端连接时自动触发
     */
    @OnOpen
    public void start(Session teacherSession) {
        // 记录信息
        this.teacherSession = teacherSession;
        System.out.println("新客户端发起连接");
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

        if (AttConstant.START.equals(type)) {
            // 接收到开始消息
            Integer id = jsonObject.getInteger("data");
            System.out.println("新用户开始考勤，房间号：" + id);

            if (id != null) {
                getStartMessage(id);
                // 记录考勤开始时间
                beginTime = new Date();
            }else{
                // 房间号未正常获取到
                JSONObject msg = MessageUtils.messageToJson(AttConstant.START, AttConstant.FAILURE,
                        "房间信息获取失败");
                sendMessage(teacherSession, msg.toJSONString());
            }

        } else if (AttConstant.LOCATION.equals(type)) {
            // 若接受到的消息为位置坐标
            // 更新老师的位置
            newLocation(jsonObject);

        } else if (AttConstant.END.equals(type)) {
            // 接收到的消息为结束考勤
            // 回送允许结束提示信息
            JSONObject msg = MessageUtils.messageToJson(AttConstant.END,
                    AttConstant.SUCCESS, "允许结束考勤");
            sendMessage(teacherSession, msg.toJSONString());
        }
    }


    /**
     * 结束考勤，或者意外断开连接
     * @OnClose 此注解声明的方法将在连接断开时触发
     */
    @OnClose
    public void finish() {
        System.out.println("结束考勤，房间号：" + this.roomID);

        if (beginTime != null){
            // 记录结束时间
             endTime = new Date();
            // 则更新将本次考勤的信息更新进数据库
            saveData();
        }
        // 关闭考勤房间，清除所有用户
        closeRoom();
    }


    /**
     * 客户端确认开始考勤后，服务器回送考勤房间信息
     */
    public void getStartMessage(Integer id) {
        JSONObject message = null;

        try {
            // 若房间已经在roomMap中，发送反馈信息
            if (roomMap.get(id) != null) {
                message = MessageUtils.messageToJson(AttConstant.START,
                        AttConstant.ERROR, "上次考勤未正常退出！");
                return;
            }

            // 根据房间id查询数据库中房间的信息
            Room room = roomService.selectRoomByRoomId(id);
            // 若房间不存在，发送反馈信息
            if (room == null) {
                message = MessageUtils.messageToJson(AttConstant.START,
                        AttConstant.ERROR, "房间号有误！");
                return;
            }

            // 保存房间信息
            roomID = id;
            teacherID = room.getUserid();  // 老师id
            distance = room.getDistance(); // 考勤距离

            // 数据库查询房间内的所有用户，并进行封装
            List<UserCustom> students = roomService.selectUsersInRoomByRoomId(roomID);

            // 若房间中没有学生，无法开始考勤
            if (students == null || students.size() <= 0) {
                // 封装开始失败的回送数据
                message = MessageUtils.messageToJson(AttConstant.START,
                        AttConstant.FAILURE, "房间人数为0，无法开始考勤！");
            } else {
                // 若房间人数不为0
                // 将房间内的所有学生的考勤数据记录在studentDataMap中
                for (UserCustom student : students) {
                    studentDataMap.put(student.getId(), new AttendanceData());
                }
                // 封装成功开始的回送数据
                message = MessageUtils.messageToJson(AttConstant.START,
                        AttConstant.SUCCESS, students);

                // 将当前开始考勤的房间加入到集合中
                roomMap.put(roomID, this);

            }
        } catch (Exception e) {
            // 查询数据库时出现异常
            e.printStackTrace();
            message = MessageUtils.messageToJson( AttConstant.START,
                    AttConstant.ERROR, "无法获取房间信息");
        } finally {
            // 回送消息给客户端教师客户端
            sendMessage(this.teacherSession, message.toJSONString());
        }
    }



    /**
     * 向客户端发送数据
     * @param session 发送给的客户端会话
     * @param message 消息
     */
    private void sendMessage(Session session, String message) {
        try {
            System.out.println(message);
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 获取到教师位置时的处理类
     * @param location 位置信息
     */
    private void newLocation(JSONObject location) {
        // 获取经纬度，latitude：纬度   longitude：经度
        Double latitude = location.getDouble("latitude");
        Double longitude = location.getDouble("longitude");

        System.out.println("教师号：" + teacherID + "房间号:" + roomID +
                ",获取到教师位置，经度：" + latitude + "\t 纬度：" + longitude);

        if (latitude == null || longitude == null) {
            throw new RuntimeException("位置信息获取异常");
        }

        // 存储老师的位置
        if (teacherLoca == null) {
            teacherLoca = new MapPoint(latitude, longitude);
        } else {
            teacherLoca.setLatitude(latitude);
            teacherLoca.setLongitude(longitude);
        }
    }



    /**
     * 结束考勤时，将数据更新进数据库
     */
    private void saveData() {
        // 构建roomdetail数据
        Roomdetail roomdetail = new Roomdetail();
        roomdetail.setRoomid(roomID);
        roomdetail.setBegintime(beginTime);
        roomdetail.setEndtime(endTime);

        // 统计本次考勤参与人数
        int count = 0;
        Set<Integer> ids = studentDataMap.keySet();
        AttendanceData data = null;
        for (Integer id : ids) {
            data = studentDataMap.get(id);
            // 若此学生开始时间为null，表示未签到
            if (data.getBeginTime() != null) {
                count++;
            }
        }
        roomdetail.setNumber(count);

        try {
            // 在数据库roomdetail表中记录数据
            roomdetailService.insertRoomdetail(roomdetail);

            // 将每个学生的数据记录到userdetail表中
            for (Integer id : ids) {
                data = studentDataMap.get(id);
                Userdetail userdetail = new Userdetail();
                String timeLong = new Double(data.getAttTimeLong()*1.0/60).toString();
                userdetail.setPresenttime(timeLong);
                userdetail.setAttendtime(data.getBeginTime());
                userdetail.setRoomdetailid(roomdetail.getId());
                userdetail.setUserid(id);
                userdetailService.insertUserdetail(userdetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 关闭考勤房间
     */
    private void closeRoom() {
        // 通知还在考勤的客户端，教师结束考勤
        JSONObject message = MessageUtils.messageToJson(AttConstant.END,
                AttConstant.SUCCESS, "考勤已结束");
        for (OneStudentCheck oneStudentCheck : studentList) {
            sendMessage(oneStudentCheck.getSession(), message.toJSONString());
        }
        // 清除学生考勤数据
        studentDataMap.clear();
        // 将当前房间从房间集合中删除
        roomMap.remove(roomID);
    }




    /**
     *  测距方法（弃用）
     * @param student
     */
    @Deprecated
    private void computeDistance(OneStudentCheck student) {

        JSONObject message = null;
        // 获取学生位置
        MapPoint studentLoca = student.getStudentLoca();

        // 还未获取学生的位置，视为学生未签到，此时距离为null
        if (studentLoca == null) {
            message = MessageUtils.messageToJson(AttConstant.LOCATION,
                    AttConstant.FAILURE, null, student.getStudentID());
        } else {
            // 测距
            double distance = CheckUtil.distanceOf(teacherLoca, studentLoca);
            // 在考勤范围内
            if (distance <= this.distance) {
                // 考勤成功，此学生考勤维持时间增加
                studentDataMap.get( student.getStudentID() ).addTimeLong();

                // 构建回送消息
                message = MessageUtils.messageToJson(AttConstant.LOCATION,
                        AttConstant.SUCCESS, distance, student.getStudentID());

            } else {
                message = MessageUtils.messageToJson(AttConstant.LOCATION,
                        AttConstant.FAILURE, distance, student.getStudentID());
            }
        }

        // 向老师客户端回送消息
        sendMessage(teacherSession, message.toJSONString());
        // 向学生客户端回送消息
        sendMessage(student.getSession(), message.toJSONString());
    }


}
