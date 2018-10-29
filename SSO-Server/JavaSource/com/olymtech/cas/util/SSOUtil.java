package com.olymtech.cas.util;

import javax.servlet.http.HttpServletRequest;


public class SSOUtil {
	/**
	 * 获取ipaddress
	 * 
	 * @param request
	 * @return ip
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String result = StringUtil.isEmpty(request.getHeader("XForwarded-For")) != true ? request.getHeader("XForwarded-For") : request
				.getHeader("x-forwarded-for");

		if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
			result = request.getHeader("Proxy-Client-IP");
		}
		if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
			result = request.getHeader("WL-Proxy-Client-IP");
		}
		if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
			result = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
			result = request.getHeader("REMOTE_ADDR");
		}
		if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
			result = request.getRemoteAddr();
		}
		return result;
	}
}
