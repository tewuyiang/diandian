package com.diandian.controller.attendance.single;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diandian.constants.AttConstant;
import com.diandian.model.Room;
import com.diandian.model.Roomdetail;
import com.diandian.model.Singledetail;
import com.diandian.model.Statistics;
import com.diandian.model.custom.UserCustom;
import com.diandian.service.RoomService;
import com.diandian.service.RoomdetailService;
import com.diandian.service.SingledetailService;
import com.diandian.service.StatisticsService;
import com.diandian.utils.DateTimeUtil;
import com.diandian.utils.attendance.MapPoint;
import com.diandian.utils.attendance.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * 教师考勤处理类（单次测距）
 * 持续获取教师的位置信息，并记录
 * 待考勤结束后，记录考勤最终结果
 */
@ServerEndpoint(value = "/attendance/single/teacher", configurator = SpringConfigurator.class)
public class TeacherCheck {
    // 存储所有当前正在考勤的房间（考虑线程安全），设置为包访问权限
    static Map<Integer, TeacherCheck> roomMap = new ConcurrentHashMap<>();

    // 教师的学生列表(记录考勤过程中，所有的学生数据),键是学生id
    private Map<Integer, StudentData> studentsData;
    // 与教师客户端的会话
    private Session teacherSession;
    // 当前房间的id
    private Integer roomId;
    // 当前房间的教师id
    private Integer teacherId;
    // 考勤开始时间
    private Date beginTime;
    // 考勤结束时间
    private Date endTime;
    // 房间的考勤距离
    private Double distance;
    // 教师设置的迟到时间,默认5分钟
    private Integer lateTime = Integer.MAX_VALUE;
    // 存储教师当前的位置信息
    private MapPoint teacherLocation;

    /*---------以下是操作数据库的业务逻辑类的对象------------*/
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomdetailService roomdetailService;
    @Autowired
    private SingledetailService singledetailService;
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 获取处理类的属性，均为包访问权限
     */
    Map<Integer, StudentData> getStudentsData() {
        return studentsData;
    }

    MapPoint getTeacherLocation() {
        return teacherLocation;
    }

    Double getDistance() {
        return distance;
    }

    Integer getLateTime() {
        return lateTime;
    }

    Date getBeginTime() {
        return beginTime;
    }


    /**
     * 教师发起连接
     *
     * @param session
     */
    @OnOpen
    public void start(Session session) {
        this.teacherSession = session;
        System.out.println("新客户端发起连接");
    }


    /**
     * 接收消息并进行相应的处理
     *
     * @param message
     */
    @OnMessage
    public void message(String message) {
        // 将接收的数据转换成json对象
        JSONObject jsonObject = MessageUtils.messageToJson(message);
        // 获取消息的类型
        String type = jsonObject.getString("type");

        // 判断消息类型，并进行相应的处理
        if (AttConstant.START.equals(type)) {
            // 消息类型为教师开始考勤
            // 则获取客户端传来的房间号
            Integer roomId = jsonObject.getInteger("data");
            if (roomId != null) {
                // 获取设置的迟到范围,单位为分钟
                Integer lateT = jsonObject.getInteger("lateTime");
                // 默认为5分钟
                lateTime = (lateT == null ? Integer.MAX_VALUE : lateT);

                // 判断此房间能否成功开始考勤
                // 并向客户端发送相应的反馈
                judgeStart(roomId);

            } else {
                // 房间号未正常获取到
                JSONObject response = MessageUtils.messageToJson(AttConstant.START,
                        AttConstant.FAILURE, "房间信息获取失败，请重试！");
                sendMessage(response.toJSONString());
            }

        } else if (AttConstant.LOCATION.equals(type)) {
            // 获取到的消息类型是位置信息
            // 则更新教师的位置
            newLocation(jsonObject);

        } else if (AttConstant.STATUS.equals(type)) {
            // 若接收到的消息是修改学生的考勤状态
            // 获取学生id以及修改后的状态
            Integer studentId = jsonObject.getInteger("studentId");
            Short status = jsonObject.getShort("status");
            // 记录教师修改后的学生的考勤状态
            changeStudentStatus(studentId, status);

        } else if (AttConstant.END.equals(type)) {
            // 若获取的信息是结束考勤,则向客户端回送允许结束信息，客户端自行断开连接
            // 客户端在未收到此消息时断开连接，则为异常断开
            JSONObject msg = MessageUtils.messageToJson(AttConstant.END,
                    AttConstant.SUCCESS, "允许结束考勤!");
            sendMessage(msg.toJSONString());
        }
    }


    /**
     * 结束考勤
     * 将数据记录进数据库中
     */
    @OnClose
    public void finish() {
        // 开始时间不为null，表示考勤成功开始
        if (beginTime != null) {
            endTime = new Date();
            // 保存数据进数据库中
            saveData();
            // 清除学生数据
            studentsData.clear();
            // 移除考勤房间列表
            roomMap.remove(roomId);
        }
        System.out.println("客户端断开连接" + roomId);
    }


    /**
     * 判断房间能否成功开始考勤
     * 若成功开始，则记录相应的数据
     * 并向客户端反馈结果
     *
     * @param id 房间id
     */
    private void judgeStart(Integer id) {
        JSONObject message = null;

        try {
            // 首先查询房间考勤集合中是否有此房间
            // 若存在，则此房间上一次考勤未退出，用户重复开始考勤
            if (roomMap.get(id) != null) {
                MessageUtils.messageToJson(AttConstant.START, AttConstant.FAILURE,
                        "上次考勤未关闭！");
                return;
            }
            // 查询数据库中的房间信息
            Room room = roomService.selectRoomByRoomId(id);
            // 若房间不存在，发送反馈信息
            if (room == null) {
                message = MessageUtils.messageToJson(AttConstant.START,
                        AttConstant.FAILURE, "房间信息获取失败！");
                return;
            }
            // 判断房间人数，若房间人数为0，则不允许开始考勤
            if (room.getPersoncount() <= 0) {
                message = MessageUtils.messageToJson(AttConstant.START,
                        AttConstant.FAILURE, "房间人数为0，无法开始考勤！");
                return;
            }
            // 获取房间中所有学生的信息
            List<UserCustom> studentsInfo = roomService.selectUsersInRoomByRoomId(id);
            // 创建学生数据列表
            if (studentsData == null) {
                studentsData = new ConcurrentHashMap<>();
            }
            // 在处理类中记录下房间中的所有学生
            for (UserCustom userCustom : studentsInfo) {
                StudentData data = new StudentData(userCustom.getId());
                studentsData.put(userCustom.getId(), data);
            }
            // 记录相关信息
            this.beginTime = new Date();    // 开始时间
            this.roomId = id;               // 房间号
            this.teacherId = room.getUserid();  // 教师id
            this.distance = room.getDistance(); // 考勤距离
            // 将当前房间加入房间总集中
            roomMap.put(id, this);

            // 封装成功开始的回送数据
            message = MessageUtils.messageToJson(AttConstant.START,
                    AttConstant.SUCCESS, studentsInfo);

            System.out.println("成功开始考勤，房间号：" + roomId + "考勤距离：" + distance + "迟到时间：" + lateTime);
        } catch (Exception e) {
            e.printStackTrace();
            // 回送异常数据
            message = MessageUtils.messageToJson(AttConstant.START,
                    AttConstant.FAILURE, "无法获取房间信息!");
        } finally {
            // 回送消息到客户端
            sendMessage(message.toJSONString());
        }
    }


    /**
     * 更新教师的位置信息
     *
     * @param location 封装位置信息的json数据
     */
    private void newLocation(JSONObject location) {
        // 获取经纬度，latitude：纬度   longitude：经度
        Double latitude = location.getDouble("latitude");
        Double longitude = location.getDouble("longitude");

        System.out.println("教师号：" + teacherId + "房间号:" + roomId +
                ",获取到教师位置，经度：" + latitude + "\t 纬度：" + longitude);

        // 位置信息获取异常，提示错误信息
        if (latitude == null || longitude == null) {
            JSONObject message = MessageUtils.messageToJson(AttConstant.LOCATION,
                    AttConstant.FAILURE, "位置信息获取异常");
            sendMessage(message.toJSONString());
            return;
        }

        // 存储老师的位置
        if (teacherLocation == null) {
            teacherLocation = new MapPoint(latitude, longitude);
        } else {
            teacherLocation.setLatitude(latitude);
            teacherLocation.setLongitude(longitude);
        }
//        System.out.println(teacherLocation);
    }


    /**
     * 修改学生的考勤状态
     *
     * @param studentId 学生id
     * @param status    修改后的状态
     */
    private void changeStudentStatus(Integer studentId, Short status) {
        JSONObject message = null;

        try {
            if (status == null || studentId == null) {
                message = MessageUtils.messageToJson(AttConstant.STATUS,
                        AttConstant.FAILURE, "信息获取异常！", studentId);
                return;
            }
            // 获取学生信息
            StudentData studentData = studentsData.get(studentId);
            // 学生不存在
            if (studentData == null) {
                message = MessageUtils.messageToJson(AttConstant.STATUS,
                        AttConstant.FAILURE, "学生信息异常！", studentId);
                return;
            }
            // 状态信息有误
            if (status < 0 || status > 4) {
                message = MessageUtils.messageToJson(AttConstant.STATUS,
                        AttConstant.FAILURE, "状态信息异常！", studentId);
                return;
            }
            // 更新用户状态
            studentData.setStatus(status);
            studentData.setAttTime(new Date());
            // 将签到时间封装到数据中
            message.put("attTime", DateTimeUtil.datetimeToString(studentData.getAttTime()));
            message = MessageUtils.messageToJson(AttConstant.STATUS,
                    AttConstant.SUCCESS, status, studentId);
        } finally {
            // 向教师客户端回送消息
            sendMessage(message.toJSONString());
        }
    }


    /**
     * 保存考勤的数据到数据库中
     */
    private void saveData() {
        // 构建roomDetail数据
        Roomdetail roomdetail = new Roomdetail();
        roomdetail.setRoomid(roomId);
        roomdetail.setBegintime(beginTime);
        roomdetail.setEndtime(endTime);
        roomdetail.setNumber(studentsData.size());
        try {
            // 更新数据库roomDetail表
            roomdetailService.insertRoomdetail(roomdetail);
            // 为每一位学生更新考勤记录
            Set<Integer> keys = studentsData.keySet();
            for (Integer key : keys) {
                StudentData studentData = studentsData.get(key);
                // 为学生新一条singleDetail记录
                Singledetail singledetail = new Singledetail();
                singledetail.setUserid(key);
                singledetail.setRoomdetailid(roomdetail.getId());
                singledetail.setAttendstatus(studentData.getStatus());
                singledetail.setAttendtime(studentData.getAttTime());

                // 插入数据到singleDetail数据库
                singledetailService.insertSingledetail(singledetail);

                // 查询学生总体情况统计表
                Statistics statistics = statisticsService.selectByRoomIdAndUserId(roomId, key);
                if (statistics == null) {
                    // 学生状态统计为null，表示这是第一次考勤
                    // 直接生成一条新数据，插入数据库
                    statistics = new Statistics();
                    statistics.setUserid(key);
                    statistics.setRoomid(roomId);
                    statistics.setAbsentee(0);
                    statistics.setLeaved(0);
                    statistics.setArrive(0);
                    statistics.setLate(0);
                    // 更新状态
                    updateStatistics(studentData.getStatus(), statistics);
                    // 插入新数据
                    statisticsService.insertStatistic(statistics);
                } else {
                    updateStatistics(studentData.getStatus(), statistics);
                    // 更新学生总体情况统计表
                    statisticsService.updateByStatisticId(statistics);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 修改学生考勤统计情况
     *
     * @param statistics
     */
    @SuppressWarnings("all")
    private void updateStatistics(Short status, Statistics statistics) {
        // 学生的相应状态+1
        switch (status) {
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
        }
    }


    /**
     * 向教师客户端发送消息
     *
     * @param message
     */
    public void sendMessage(String message) {
        try {
            teacherSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
