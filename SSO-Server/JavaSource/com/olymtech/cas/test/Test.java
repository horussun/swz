package com.olymtech.cas.test;

import java.util.UUID;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xx = "http://shipper.800jit.com/serviceportal/application/common/jsp/netbusiness.jsp";
		String yy = "/vrwsadp,/themes/,/tool/,/application/common/jsp/netbusiness.jsp,/application/common/jsp/netbusiness_en.jsp";
		String[] yyy = yy.split(",");
		for (String string : yyy) {
			if (xx.indexOf(string) != -1) {
				System.out.println(string);
			}
		}
	}
	
	

}
