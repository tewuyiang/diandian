package com.diandian.controller;

import com.diandian.model.custom.UserCustom;
import com.diandian.service.UserService;
import com.diandian.utils.AnalysisCode;
import com.diandian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录请求
     *
     * @param nickName 昵称
     * @param code 解析wxid的密匙
     * @param sex 性别
     * @return openid、session_key 的map
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/login/{nickName}/{code}/{sex}")
    public Map<String, Object> login(@PathVariable("nickName") String nickName,
                                     @PathVariable("sex") Short sex,
                                     @PathVariable("code") String code,
                                     HttpSession session) throws Exception {
        Map<Object, Object> map;
        try {
            map = AnalysisCode.userOpenid(code);
        } catch (Exception e) {
            // 解析异常
            return R.error("身份解析失败");
        }
        String openid = (String) map.get("openid");

        // 查询是否已经存储账户信息
        UserCustom user = userService.getUserByOpenid(openid);
        // 若用户不存在，则执行插入操作
        if (user == null) {
            user = new UserCustom();
            user.setNickname(nickName);
            user.setWxid(openid);
            user.setSex(sex);

            userService.insertUser(user);
            System.out.println("插入用户id:" + user.getId());
        }
        session.setAttribute("user",user);
        return R.ok(user.getId(), "登录成功！");
    }
}
