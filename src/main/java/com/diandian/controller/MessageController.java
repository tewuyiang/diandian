package com.diandian.controller;

import com.diandian.model.Lists;
import com.diandian.model.custom.MsgtypeCustom;
import com.diandian.service.MessageService;
import com.diandian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息控制器
 */

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 创建一条申请加入房间的消息
     * 根据房间的number
     */
    @ResponseBody
    @PostMapping("/applyJoinRoomByNumber/{userId}/{number}/{remarkname}")
    public R applyJoinRoomByNumber(@PathVariable("userId") Integer userId,
                                   @PathVariable("number") String number,
                                   @PathVariable("remarkname") String remarks) throws Exception {
        System.out.println(remarks);
        System.out.println("武器");
        return messageService.createJoinRoomMsgByNumber(userId, number, remarks) > 0 ?
                R.ok() : R.error("发生异常");
    }


    /**
     * 获取所有申请加入我的房间的消息
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @GetMapping("/getApplyMyRoomsMsgs/{userId}")
    public R getApplyMyRoomsMsgs(@PathVariable("userId") Integer userId) throws Exception {
        List<MsgtypeCustom> messages = messageService.selectApplyMyRoomsMessage(userId);
        if (messages == null || messages.size() <= 0) {
            return R.no();
        }
        return R.ok(messages);
    }


    /**
     * 处理房间申请请求
     *
     * @param msgId  消息id
     * @param result 处理结果
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/dealRoomapplyMessage")
    public R dealRoomapplyMessage(Integer msgId, Integer result) throws Exception {
        return messageService.updateAfterDealRoomapply(msgId, result) > 0 ? R.ok() : R.error();
    }


    /**
     * 接收消息
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/receiveMessage/{userId}")
    public R receiveMessage(@PathVariable("userId") Integer userId) throws Exception {
        List<MsgtypeCustom> messages;
        // 查询用户未读消息
        messages = messageService.updateAndselectUnDealMessage(userId);
        if (messages == null || messages.size() <= 0) {
            return R.no();
        }
        return R.ok(messages.size());
    }

}
