package com.diandian.controller;

import com.diandian.model.custom.UserdetailCustom;
import com.diandian.service.UserdetailService;
import com.diandian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userdetail")
public class UserdetailController {

    @Autowired
    private UserdetailService userdetailService;

    /**
     * 查询房间内某一次考勤的全部用户明细（userdetail）
     * @param roomdetailId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/getUserdetails/{roomDetailId}")
    public R getUserdetailsByRoomdetailId(@PathVariable("roomDetailId") Integer roomdetailId) throws Exception {
        List<UserdetailCustom> userdetails = userdetailService.selectUserdetailsByRoomdetailId(roomdetailId);
        // 判断数据是否非空
        if (userdetails == null || userdetails.size() <= 0) {
            return R.no();
        }
        return R.ok(userdetails);
    }


    /**
     * 查询某个用户在某个房间内的全部明细
     * @param roomId
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/getOnStudentDetails/{roomId}/{userId}")
    public R getOnStudentDetails(@PathVariable("roomId") Integer roomId,
                                 @PathVariable("userId") Integer userId) throws Exception{
        List<UserdetailCustom> userdetails = userdetailService.selectOnStudentDetails(roomId, userId);
        // 判断非空
        if (userdetails == null || userdetails.size() <= 0) {
            return R.no();
        }
        return R.ok(userdetails);
    }
}
