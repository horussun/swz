package com.olymtech.cas.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2645691041554770185L;

	public void init() throws ServletException {
		String appCode = getInitParameter("current_app_code");
	}
}
