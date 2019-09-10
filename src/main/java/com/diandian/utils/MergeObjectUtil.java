package com.diandian.utils;

import java.lang.reflect.Field;
/**
 * �ϲ�����
 * @author zhangwenke
 *
 */
public class MergeObjectUtil{
	
	public static <T> T mergeObject(T origin, T destination) {
		if (!origin.getClass().equals(destination.getClass()))
			return origin;
		Field[] fields = origin.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			try {
				fields[i].setAccessible(true);
				Object value = fields[i].get(origin);
				if (null != value) {
					fields[i].set(destination, value);
				}
				fields[i].setAccessible(false);
			} catch (Exception e) {
			}
		}
		return destination;
	}
}
