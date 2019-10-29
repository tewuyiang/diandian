package com.diandian.utils.attendance;

import java.awt.Point;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class CheckUtil {

	private static final double EARTH_RADIUS = 637_1393.00D;
	private static final double RADIAN = Math.PI / 180.00D;
	private static final double HALF = 0.5D;

	public static double distanceOf(MapPoint point1, MapPoint point2) {
		double lat1 = point1.getLatitude();
		double lon1 = point1.getLongitude();
		double lat2 = point2.getLatitude();
		double lon2 = point2.getLongitude();
		double x, y, a, b, distance;
		lat1 *= RADIAN;
		lat2 *= RADIAN;
		x = lat1 - lat2;
		y = lon1 - lon2;
		y *= RADIAN;
		a = Math.sin(x * HALF);
		b = Math.sin(y * HALF);
		distance = EARTH_RADIUS * Math.asin(Math.sqrt(a * a + Math.cos(lat1) * Math.cos(lat2) * b * b)) / HALF;
		return new BigDecimal(distance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double getDistance(Double lat1,Double lng1,Double lat2,Double lng2) {
		// 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
		double radiansAX = Math.toRadians(lng1); // A经弧度
		double radiansAY = Math.toRadians(lat1); // A纬弧度
		double radiansBX = Math.toRadians(lng2); // B经弧度
		double radiansBY = Math.toRadians(lat2); // B纬弧度

		double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
				+ Math.sin(radiansAY) * Math.sin(radiansBY);
		double acos = Math.acos(cos); // 反余弦值
		return EARTH_RADIUS * acos; // 最终结果
	}

	public static void main(String[] args) {
		double v1 = distanceOf(new MapPoint(28.68202, 115.85794),
				new MapPoint(28.753306, 115.861918));
		System.out.println(v1);

		double distance = getDistance(28.68202, 115.85794,
                28.753306, 115.861918);
		System.out.println("距离" + distance  + "米");
	}
}
