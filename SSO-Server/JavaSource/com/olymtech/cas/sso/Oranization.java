package com.olymtech.cas.sso;

import java.io.Serializable;

/** 实体类 */

public class Oranization implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String org_id ;
	String minicrm_company_id;
	String org_name ;

	public String getMinicrm_company_id() {
		return minicrm_company_id;
	}

	public void setMinicrm_company_id(String minicrm_company_id) {
		this.minicrm_company_id = minicrm_company_id;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
}
