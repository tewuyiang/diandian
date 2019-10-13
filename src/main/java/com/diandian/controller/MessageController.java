package com.diandian.controller;

import com.diandian.model.custom.RoomapplyCostom;
import com.diandian.service.MessageService;
import com.diandian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 消息控制器
 */

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 根据消息类型表id获取消息记录
     * @return
     * @throws Exception
     */
//    @ResponseBody
//    @GetMapping("/getMessageByTypeId/{msgId}")
//    public R getRoomApplyByMsgId(@PathVariable("msgId") Integer msgId) throws Exception{
////        RoomapplyCostom roomapplyCostom =  messageService.selectRoomApplyByMsgId(msgId);
////        return roomapplyCostom == null ? R.no("无此消息!") : R.ok(roomapplyCostom);
//    }


    /**
     * 获取用户申请加入房间的消息
     * 根据房间的number
     */
    @ResponseBody
    @GetMapping("/applyJoinRoomByNumber/{userId}/{number}")
    public R applyJoinRoomByNumber(@PathVariable("userId") Integer userId,
                                   @PathVariable("number") String number) throws Exception{
        return messageService.createJoinRoomMsgByNumber(userId, number) > 0 ? R.ok() : R.error("发生异常");
    }

}
