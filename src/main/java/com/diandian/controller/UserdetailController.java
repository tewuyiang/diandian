package com.diandian.controller;

import com.diandian.service.UserdetailService;
import com.diandian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        userdetailService.selectUserdetailsByRoomdetailId(roomdetailId);
    }
}
