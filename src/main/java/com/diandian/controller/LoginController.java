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
	 * ��¼����
	 * 
	 * @param nickName �ǳ�
	 * @param code     ��̬����
	 * @return openid��session_key ��map
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
			// �����쳣
			return R.error("����codeʧ��");
		}
		String openid = (String) map.get("openid");

		// ��ѯ�Ƿ��Ѿ��洢�˻���Ϣ
		UserCustom user = userService.getUserByOpenid(openid);
		if (user == null) { // ��ѯΪ��
			user = new UserCustom();
			user.setNickname(nickName);
			user.setWxid(openid);

			userService.insertUser(user);
			System.out.println("�����û�id:" + user.getId());
		}
		return R.ok(user.getId(), "��¼�ɹ���");
	}
}
