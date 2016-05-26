package com.restfiddle.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author abidk
 * 
 */
public class CommonUtil {

    private CommonUtil() {}

    /**
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
	return (null == object) ? true : false;
    }

    /**
     * @param object
     * @return
     */
    public static boolean isNotNull(Object object) {
	return !isNull(object);
    }

    /**
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
	return StringUtils.isEmpty(string);
    }

    public static boolean isNotEmpty(String string) {
	return StringUtils.isNotEmpty(string);
    }
}
