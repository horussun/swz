package com.olymtech.cas.authentication.handler;

import java.sql.Connection;
import java.sql.SQLException;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.olymtech.cas.authentication.handler.support.AbstractJdbcOlymtechAuthenticationHandler;
import com.olymtech.cas.authentication.principal.OlymtechCredentials;
import com.olymtech.cas.sso.OneSiteParmDetail;

public final class OlymtechAuthenticationHandler extends AbstractJdbcOlymtechAuthenticationHandler {

    private SSOUserCheckHandler ssoUserCheckHandler;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public void setSsoUserCheckHandler(SSOUserCheckHandler ssoUserCheckHandler) {
	this.ssoUserCheckHandler = ssoUserCheckHandler;
    }

    @Override
    protected boolean authenticateUsernamePasswordInternal(OlymtechCredentials credentials) throws AuthenticationException {
	// TODO Auto-generated method stub
	// login detail
	final String username = getPrincipalNameTransformer().transform(credentials.getUsername());
	final String password = credentials.getPassword();

	final String encryptedPassword = getPasswordEncoder().encode(password);

	credentials.setUsername(username);
	credentials.setPassword(encryptedPassword);
	credentials.setOriginalPassword(password);

	boolean returnValue = false;

	//Connection conn = null;
	try {
	    //conn = DataSourceUtils.getConnection(this.getDataSource());
	    //returnValue = ssoUserCheckHandler.ssoUserCheck(conn, credentials);

	    //if (returnValue == false) {
		//OneSiteParmDetail ospd = ssoUserCheckHandler.getSiteConfigInfoList(conn, credentials.getSiteNo());
		//credentials.setToUrl(ospd.getLoginPageUrl());// 确定错误后跳转地址
		//return false;
	    //}
		returnValue = true;
		credentials.setToUrl("http://saas.800jit.com/aclient");
		System.out.println("URL:");
		System.out.println(credentials.getUsername());
		System.out.println(credentials.getToUrl());
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    returnValue = false;
	    logger.error(e.getMessage(), e);
	} finally {
	    //DataSourceUtils.releaseConnection(conn, this.getDataSource());
	}

	return returnValue;
    }

}
