package com.oak.utils;

public class DateFormatUtils {
	
	private DateFormatUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String getJavaFormatFromMomentJsFormat(String moment) {
		switch (moment) {
			case "YYYY-MM-DD HH:mm:ss":
				return "yyyy-MM-dd HH:mm:ss";
			case "YYYY-MM-DD HH:mm":
				return "yyyy-MM-dd HH:mm";
			case "YYYY-MM-DD":
				return "yyyy-MM-dd";
			default:
				return moment;
		}
	}
	
	public static boolean hasTimeComponent(String javaOrMoment) {
		return javaOrMoment.contains("H") || javaOrMoment.contains("m") || javaOrMoment.contains("s");
	}
}
