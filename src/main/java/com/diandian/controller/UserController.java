package com.diandian.controller;

import com.diandian.model.User;
import com.diandian.model.custom.RoomCustom;
import com.diandian.service.UserService;
import com.diandian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据主键id更新用户信息
     *
     * @param user
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/updateUserById")
    public Map<String, Object> updateUserById(User user) throws Exception {
        return userService.updateUserById(user) > 0 ? R.ok() : R.no("用户不存在！");
    }


    /**
     * 根据主键id查询用户信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/selectUserById/{id}")
    public Map<String, Object> selectUserById(@PathVariable("id") Integer id) throws Exception {
        User user = userService.selectUserById(id);
        return user == null ? R.no("用户不存在！") : R.ok(user);
    }


    /**
     * 查询用户创建的所有房间
     * @param userid
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyRoomsById/{userId}")
    public Map<String, Object> getMyRoomsById(@PathVariable("userId") Integer userid) throws Exception {
        //根据用户id查房间
        List<RoomCustom> rooms = userService.selectRoomsByUserId(userid);

        //房间不为null
        if (rooms != null && rooms.size() > 0) {
            return R.ok(rooms);
        }
        return R.no("房间数为零！");
    }


    /**
     * 根据userid查询用户加入的所有房间
     * @param userId
     * @return
     */
    @ResponseBody
    @GetMapping("/getJoinRoomsByUserId/{userId}")
    public R getJoinRoomsByUserId(@PathVariable("userId") Integer userId) throws Exception {
        // 查找
        List<RoomCustom> rooms = userService.selectJoinRoomsByUserId(userId);

        // 查找为kong
        if (rooms == null || rooms.size() <= 0) {
            return R.no("房间数为0！");
        }
        return R.ok(rooms);
    }

}
