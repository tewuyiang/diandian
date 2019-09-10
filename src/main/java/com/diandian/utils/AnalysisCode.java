package com.diandian.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AnalysisCode {

	/**
	 * 根据code获取openid
	 * @param code
	 * @return
	 */
	public static Map<Object, Object> userOpenid(String code) {
		Map<Object, Object> map = new HashMap<>();

		// code不存在
		if (code == null || code.length() == 0) {
			map.put("status", 0);
			map.put("msg", "code不存在");
			return map;
		}

		// 小程序的id
		String wxspAppid = "wx40729f9bedd7140d";
		// 小程序的安全密匙
		String wxspSecret = "88bb9194d6f95ad343caec11a4b556d7";
		//
		String grant_type = "authorization_code";


		String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type="
				+ grant_type;
		String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
		JSONObject json = JSON.parseObject(sr);
		String session_key = json.get("session_key").toString();
		String openid = (String) json.get("openid");

		map.put("session_key", session_key);
		map.put("openid", openid);

		return map;
	}
}
