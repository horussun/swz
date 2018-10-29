package com.olymtech.cas.authentication.handler.support;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public abstract class AbstractJdbcOlymtechAuthenticationHandler extends AbstractOlymtechAuthenticationHandler {

    @NotNull
    private SimpleJdbcTemplate jdbcTemplate;

    @NotNull
    private DataSource dataSource;

    /**
     * Method to set the datasource and generate a JdbcTemplate.
     * 
     * @param dataSource
     *            the datasource to use.
     */
    public final void setDataSource(final DataSource dataSource) {
	this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	this.dataSource = dataSource;
	/*
	try {
	    Context initContext = new InitialContext();
	    Context envContext = (Context) initContext.lookup("java:/comp/env");
	    DataSource ds = (DataSource) envContext.lookup("jdbc/membership");
	    if (ds != null) {
		System.out.println("use tomcat db config sucess");
		this.jdbcTemplate = new SimpleJdbcTemplate(ds);
		this.dataSource = ds;
	    }
	} catch (NamingException e) {
	    // TODO Auto-generated catch block
	    System.out.println("use tomcat db config fail");
	    e.printStackTrace();
	}
	*/

    }

    /**
     * Method to return the jdbcTemplate
     * 
     * @return a fully created JdbcTemplate.
     */
    protected final SimpleJdbcTemplate getJdbcTemplate() {
	return this.jdbcTemplate;
    }

    protected final DataSource getDataSource() {
	return this.dataSource;
    }

}
