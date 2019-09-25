package com.diandian.utils.attendance;

import java.awt.Point;

@SuppressWarnings("serial")
public class MapPoint extends Point {

	private double latitude;
	private double longitude;

	public MapPoint(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "MapPoint{" +
				"latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}
}
