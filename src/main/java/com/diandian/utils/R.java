package com.diandian.utils;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class R extends HashMap<String, Object> {


	public static R ok(String... message) {
		R r = new R();
		r.put("code", 1);
		String msg = "";
		if (message == null || message.length == 0 || StringUtils.isBlank(message[0])) {
			msg = "请求成功！";
		} else {
			msg = message[0];
		}
		r.put("message", msg);
		return r;
	}

	public static <T> R ok(T data, String ... message) {
		R r = new R();
		r.put("code", 1);
		String msg = "";
		if (message == null || message.length == 0 || StringUtils.isBlank(message[0])) {
			msg = "请求成功！";
		} else {
			msg = message[0];
		}
		r.put("message", msg);
		r.put("data", data);
		return r;
	}

	public static <T> R ok(T data, T parameter, String ... message) {
		R r = new R();
		r.put("code", 1);
		String msg = "";
		if (message == null || message.length == 0 || StringUtils.isBlank(message[0])) {
			msg = "请求成功！";
		} else {
			msg = message[0];
		}
		r.put("message", msg);
		r.put("data", data);
		r.put("parameter", parameter);
		return r;
	}
	public static <T> R no(String ... message) {
		R r = new R();
		r.put("code", 0);
		String msg = "";
		if (message == null || message.length == 0 || StringUtils.isBlank(message[0])) {
			msg = "数据为空";
		} else {
			msg = message[0];
		}
		r.put("message", msg);
		return r;
	}


	public static R error(String... message) {
		R r = new R();
		r.put("code", -1);
		String msg = "";
		if (message == null || message.length == 0 || StringUtils.isBlank(message[0])) {
			msg = "发生异常！";
		} else {
			msg = message[0];
		}
		r.put("message", msg);
		return r;
	}

	public static R error(int code, String... message) {
		R r = new R();
		r.put("code", code);
		String msg = "";
		if (message == null || message.length == 0 || StringUtils.isBlank(message[0])) {
			msg = "发生异常！";
		} else {
			msg = message[0];
		}
		r.put("message", msg);
		return r;
	}
	
}
