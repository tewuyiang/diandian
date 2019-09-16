package com.diandian.controller;

import com.diandian.model.custom.RoomdetailCustom;
import com.diandian.service.RoomdetailService;
import com.diandian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roomdetail")
public class RoomdetailController {

    @Autowired
    private RoomdetailService roomdetailService;


    /**
     * 根据用户房间id查询房间考勤明细
     * @param roomId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/getRoomdetail/{roomId}")
    public R getRoomdetail(@PathVariable("roomId") Integer roomId) throws Exception{
        List<RoomdetailCustom> roomdetails = roomdetailService.selectRoomdetailByRoomId(roomId);
        // 判断查询结果
        if (roomdetails == null || roomdetails.size() <= 0) {
            return R.no();
        }
        return R.ok(roomdetails);
    }
}
