package com.diandian.utils.attendance;

import java.awt.Point;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class CheckUtil {

	private static final double EARTH_RADIUS = 637_1393.00D;
	private static final double RADIAN = Math.PI / 180.00D;
	private static final double HALF = 0.5D;

	public static boolean checkMap(String latitude, String longitude, String othermap, double d) {
		System.out.println("自己地址信息；"+latitude+"\t"+longitude);
		System.out.println("他人地址信息："+othermap);
		// 判断两个地址是否为null
		if (latitude == null || latitude.length() == 0 || StringUtils.isBlank(latitude) || longitude == null
				|| longitude.length() == 0 || StringUtils.isBlank(longitude) || othermap == null
				|| othermap.length() == 0 || StringUtils.isBlank(othermap))
			return false;

		double mylatitude = Double.parseDouble(latitude);
		double mylongitude = Double.parseDouble(longitude);
		String[] str = othermap.split("|");
		double otherlatitude = Double.parseDouble(str[0]);
		double otherlongitude = Double.parseDouble(str[1]);
		// 比较两个地之间的差距
		Point point1 = new MapPoint(mylatitude, mylongitude);
		Point point2 = new MapPoint(otherlatitude, otherlongitude);
		double destance = distanceOf(point1, point2);
		System.out.println("距离："+destance);
		// 比较两个地之间的差距
		if(destance > d)
			return false;
		else
			return true;
	}

	public static double distanceOf(Point point1, Point point2) {
		double lat1 = point1.getX();
		double lon1 = point1.getY();
		double lat2 = point2.getX();
		double lon2 = point2.getY();
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
}
