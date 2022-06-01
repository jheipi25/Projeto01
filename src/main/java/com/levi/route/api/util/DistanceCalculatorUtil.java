package com.levi.route.api.util;

import com.levi.route.api.entity.GeoPoint;

public class DistanceCalculatorUtil {

	/**
	 * Calculates distance between two coordinates using the Haversine formula
	 *
	 * @param point1 first coordinate
	 * @param point2 second coordinate
	 * @return distance between the 2 given coordinates in meters
	 */
	public static double distance(GeoPoint point1, GeoPoint point2) {
		double startLat = point1.getLat();
		double startLong = point1.getLng();
		double endLat = point2.getLat();
		double endLong = point2.getLng();

		double dLat  = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat   = Math.toRadians(endLat);

		double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return 6371 * c;
	}

	private static double haversine(double val) {
			return Math.pow(Math.sin(val / 2), 2);
	}
	
}
