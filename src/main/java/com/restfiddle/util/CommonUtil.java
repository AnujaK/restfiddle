package com.restfiddle.util;

public class CommonUtil {

	public static boolean isNull(Object object) {
		return (null == object) ? true : false;
	}

	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}
}
