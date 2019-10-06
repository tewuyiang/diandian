package com.diandian.controller;

import com.diandian.model.custom.UserCustom;
import com.diandian.service.UserService;
import com.diandian.utils.AnalysisCode;
import com.diandian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	 * @param code     动态参数
	 * @return openid、session_key 的map
	 * @throws Exception
	 */
	@ResponseBody
	@GetMapping("/login/{nickName}/{code}")
	public Map<String, Object> login(@PathVariable("nickName") String nickName,
									  @PathVariable("code") String code) throws Exception {
		Map<Object, Object> map;
		try {
			map = AnalysisCode.userOpenid(code);
		} catch (Exception e) {
			// 解析异常
			return R.error("解析code失败");
		}
		String openid = (String) map.get("openid");

		// 查询是否已经存储账户信息
		UserCustom user = userService.getUserByOpenid(openid);
		if (user == null) { // 查询为空
			user = new UserCustom();
			user.setNickname(nickName);
			user.setWxid(openid);

			userService.insertUser(user);
			System.out.println("插入用户id:" + user.getId());
		}
		return R.ok(user.getId(), "登录成功！");
	}
}
