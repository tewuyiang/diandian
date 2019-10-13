package com.diandian.controller;

import com.diandian.model.custom.SingledetailCustom;
import com.diandian.service.SingledetailService;
import com.diandian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 单次检测模式下的考勤记录操作
 */
@RestController
@RequestMapping("/singledetail")
public class SingledetailController {

    @Autowired
    private SingledetailService singledetailService;

    /**
     * 通过房间考勤明细获取所有个人明细
     * 在单次检测考勤模式下
     *
     * @param roomdetailId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/SingledetailsByRId/{roomdetailId}")
    public R getSingledetailsByRoomdetailId(@PathVariable("roomdetailId") Integer roomdetailId) throws Exception {
        List<SingledetailCustom> singledetails =
                singledetailService.selectSingledetailsByRoomdetailId(roomdetailId);
        if (singledetails == null || singledetails.size() <= 0) {
            return R.no();
        }
        return R.ok(singledetails);
    }


    /**
     * 查询某个用户在某个房间内的全部明细
     * 在单次测距模式下
     *
     * @param roomId
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/getOnStudentDetails/{roomId}/{userId}")
    public R getOnStudentDetails(@PathVariable("roomId") Integer roomId,
                                 @PathVariable("userId") Integer userId) throws Exception {
        List<SingledetailCustom> singledetails = singledetailService.selectOnStudentDetails(roomId, userId);
        // 判断非空
        if (singledetails == null || singledetails.size() <= 0) {
            return R.no();
        }
        return R.ok(singledetails);
    }


}
