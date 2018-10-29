package com.olymtech.cas.authentication.handler;

import org.jasig.cas.authentication.handler.PasswordEncoder;

import com.olymtech.cas.util.MD5;

public class OlymtechPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(String pwd) {
		// TODO Auto-generated method stub
		return MD5.encrypt(pwd);
	}

}
