package com.olymtech.cas.web;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;

public class LoginStatus {
	
	public boolean isTGTExist(final HttpServletRequest request) {
		CookieRetrievingCookieGenerator crc = CookieGen(request);
		if (crc.retrieveCookieValue(request) == null )
			return false;
		else
			return true;
	}

	private CookieRetrievingCookieGenerator CookieGen(final HttpServletRequest request) {
		CookieRetrievingCookieGenerator crc = new CookieRetrievingCookieGenerator();
		String contextPath = request.getContextPath();
		crc.setCookiePath(contextPath);
		crc.setCookieName("CASTGC");
		return crc;
	}
	
	public String getTGT (final HttpServletRequest request) {
		try {
			CookieRetrievingCookieGenerator crc = CookieGen(request);
			return crc.retrieveCookieValue(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
