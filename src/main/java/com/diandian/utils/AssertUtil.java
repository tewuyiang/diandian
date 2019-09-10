package com.diandian.utils;

import org.apache.commons.lang3.StringUtils;

import com.diandian.exception.ParamException;

public class AssertUtil {
	
	public static void isNotEmpty(String value) {
		if (StringUtils.isBlank(value)) {
			throw new ParamException(0, "�ֶβ���Ϊ��");
		}
	}
	
	public static void isNotEmpty(String value, String message) {
		if (StringUtils.isBlank(value)) {
			throw new ParamException(0, message);
		}
	}
	
	public static void isNotEmpty(String value, int code, String message) {
		if (StringUtils.isBlank(value)) {
			throw new ParamException(0, message);
		}
	}
	
	public static void isNotNull(Object value) {
		if (value == null) {
			throw new ParamException(0, "�ֶβ���Ϊ��");
		}
	}
	
	public static void isNotNull(Object value, String message) {
		if (value == null) {
			throw new ParamException(0, message);
		}
	}
	
	public static void isNotNull(Object value, int code, String message) {
		if (value == null) {
			throw new ParamException(code, message);
		}
	}
	
}
