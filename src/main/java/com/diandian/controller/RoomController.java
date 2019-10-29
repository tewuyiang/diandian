package com.diandian.controller;

import com.diandian.model.Lists;
import com.diandian.model.Room;
import com.diandian.model.custom.RoomCustom;
import com.diandian.model.custom.UserCustom;
import com.diandian.service.RoomService;
import com.diandian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;


    /**
     * 用户创建一个新房间
     * @param room
     * @return
     */
    @ResponseBody
    @PostMapping("/createRoom")
    public R createRoom(Room room, HttpServletRequest request) throws Exception {
        String contextPath = request.getServletContext().getRealPath("/");
        return roomService.insertRoom(room, contextPath) > 0 ? R.ok(room) : R.error("房间创建失败！");
    }


    /**
     * 根据房间id查找房间
     * @param roomId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/getRoomById/{roomId}")
    public R getRoomById(@PathVariable("roomId") Integer roomId) throws Exception {
        System.out.println("aaa");
        Room room = roomService.selectRoomByRoomId(roomId);
        return room == null ? R.no("房间不存在！") : R.ok(room);
    }


    /**
     * 修改房间信息
     * @param room
     * @return
     */
    @ResponseBody
    @PostMapping("/updateRoom")
    public R updateRoom(Room room) throws Exception{
        Room r = roomService.updateRoom(room);
        return r != null ? R.ok(r) : R.no("更新失败");
    }


    /**
     * 删除房间
     * @param roomId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/deleteRoom/{roomId}")
    public R deleteRoom(@PathVariable("roomId") Integer roomId) throws Exception {
        return roomService.deleteRoomByRoomId(roomId) > 0 ? R.ok() : R.no("删除失败！");
    }


    /**
     * 查询房间内的所有用户
     * @param roomId
     * @return
     */
    @ResponseBody
    @GetMapping("/getUsersInRoom/{roomId}")
    public R getUsersInRoom(@PathVariable("roomId") Integer roomId) throws Exception {
        List<UserCustom> users = roomService.selectUsersInRoomByRoomId(roomId);
        if (users == null || users.size() <= 0) {
            return R.no();
        }
        return R.ok(users);
    }


    /**
     * 根据房间号码搜索房间
     * @param roomNumber
     * @return
     */
    @ResponseBody
    @GetMapping("/getRoomByRoomNumber/{roomNumber}")
    public R getRoomByRoomNumber(@PathVariable("roomNumber") String roomNumber) throws Exception{
        // 查询
        RoomCustom room = roomService.selectRoomByRoomNumber(roomNumber);
        // 判断房间是否存在
        return room == null ? R.no("房间不存在!") : R.ok(room);
    }


    /**
     * 用户加入考勤房间
     * @return
     */
    @ResponseBody
    @PostMapping("/userJoinRoom")
    public R userJoinRoom(Lists lists) throws Exception{
        return roomService.insertLists(lists) > 0 ? R.ok() : R.error();
    }


    /**
     * 用户退出考勤房间
     * @param roomId 房间id
     * @param userId 用户id
     */
    @ResponseBody
    @GetMapping("/userDropOutRoom/{roomId}/{userId}")
    public R userDropOutRoom(@PathVariable("roomId") Integer roomId,
                             @PathVariable("userId") Integer userId) throws Exception{
        return roomService.deleteUserToRoom(roomId, userId) > 0 ? R.ok() : R.error();
    }


    /**
     * 获取房间考勤统计情况
     * 每个学生迟到，旷课，请假...的次数
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getStudentStatistics/{roomId}")
    public R getStatisticsInRoom(@PathVariable("roomId") Integer roomId) throws Exception{
        RoomCustom room = roomService.getStudentStatisticsInRoom(roomId);
        return room == null || room.getAttTimes() == null || room.getAttTimes() == 0
                ? R.no() : R.ok(room);
    }

}
