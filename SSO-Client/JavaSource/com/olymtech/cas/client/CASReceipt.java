package com.olymtech.cas.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Encapsulates information about the fruits of authentication.
 * 
 * @author andrew.petro@yale.edu
 * @version $Revision: 1.3.2.2 $ $Date: 2011/03/24 10:25:01 $
 */
public class CASReceipt {

    private static Log log = LogFactory.getLog(CASReceipt.class);

    /**
     * Get a CASReceipt from a ProxyTicketValidator. While the ptv properties
     * must be set, you may or may not have already called ptv.validate(). If
     * the ProxyTicketValidator has not already been validated, this method will
     * validate the ptv.
     * 
     * @param ptv
     *            - a ProxyTicketValidator from which to harvest the receipt.
     * @return CASReceipt encapsulating fruits of authentication.
     * @throws CASAuthenticationException
     *             - if ticket validation fails for any reason.
     */
    public static CASReceipt getReceipt(ProxyTicketValidator ptv) throws CASAuthenticationException {

	if (log.isTraceEnabled()) {
	    log.trace("entering getReceipt(ProxyTicketValidator=[" + ptv + "])");
	}

	if (!ptv.isAuthenticationSuccesful()) {
	    try {
		ptv.validate();
	    } catch (Exception e) {
		CASAuthenticationException casException = new CASAuthenticationException("Unable to validate ProxyTicketValidator [" + ptv + "]", e);
		log.error(casException);
		e.printStackTrace();
		throw casException;
	    }
	}

	if (!ptv.isAuthenticationSuccesful()) {
	    log.error("validation of [" + ptv + "] was not successful.");
	    throw new CASAuthenticationException("Unable to validate ProxyTicketValidator [" + ptv + "]");
	}

	CASReceipt receipt = new CASReceipt();
	receipt.casValidateUrl = ptv.getCasValidateUrl();
	receipt.pgtIou = ptv.getPgtIou();
	receipt.userName = ptv.getUser();
	receipt.proxyCallbackUrl = ptv.getProxyCallbackUrl();
	receipt.proxyList = ptv.getProxyList();
	receipt.primaryAuthentication = ptv.isRenew();
	receipt.tgt = ptv.getTgt();

	if (!receipt.validate()) {
	    throw new CASAuthenticationException("Validation of [" + ptv + "] did not result in an internally consistent CASReceipt.");
	}

	if (log.isTraceEnabled()) {
	    log.trace("returning from getReceipt() with return value [" + receipt + "]");
	}
	return receipt;
    }
    
    private String tgt;

    public String getTgt() {
        return tgt;
    }

    public void setTgt(String tgt) {
        this.tgt = tgt;
    }

    /**
     * The CAS Server validation service URL against which the ticket was
     * validated.
     */
    private String casValidateUrl;

    /** The PGTIOU, if any. */
    private String pgtIou;

    /** Was authentication by presentation of primary credentials. */
    private boolean primaryAuthentication = false;

    /** The URL, if any, to which the CAS server sent the PGT,PGTIOU pair. */
    private String proxyCallbackUrl;

    /**
     * List of services through which authentication was proxied, if any, from
     * most recent back to service ticket recipient.
     */
    private List proxyList = new ArrayList();

    /** The authenticated username. */
    private String userName;

    /**
     * Do-nothing constructor. You probably want to call
     * getReceipt(ProxyTicketValidator);
     */
    public CASReceipt() {
	// does nothing
    }

    /**
     * Get the URL of the CAS server ticket validation service against which the
     * ticket leading to this receipt was authenticated.
     * 
     * @return the URL of the CAS ticket validation service.
     */
    public String getCasValidateUrl() {
	return this.casValidateUrl;
    }

    /**
     * Get the Proxy Granting Ticket IOU, if any, associated with the
     * authentication represented by this receipt.
     * 
     * @return the PGTIOU, or NULL.
     */
    public String getPgtIou() {
	return this.pgtIou;
    }

    /**
     * Get the Proxy Callback URL, if any.
     * 
     * @return the Proxy Callback URL, or NULL if none was set.
     */
    public String getProxyCallbackUrl() {
	return this.proxyCallbackUrl;
    }

    /**
     * Get the list of proxies, if any, in the authentication chain. List is in
     * order from closest proxying service back to the original recipient of a
     * Service Ticket. See also the convenience method proxyingService().
     * 
     * @return an unmodifiable view on the proxy list.
     */
    public List getProxyList() {
	return Collections.unmodifiableList(this.proxyList);
    }

    /**
     * Get the authenticated username.
     * 
     * @return the authenticated username.
     */
    public String getUserName() {
	return this.userName;
    }

    /**
     * Was the authentication accomplished by presentation of primary
     * credentials (e.g. a password). (As opposed to presentation of secondary
     * credentials, such as a Single Sign On session cookie.) That is, was the
     * renew parameter set to true on the request to validate the (service)
     * ticket.
     * 
     * @return true if authentication was by primary credentials, false
     *         otherwise.
     */
    public boolean isPrimaryAuthentication() {
	return this.primaryAuthentication;
    }

    /**
     * Was authentication proxied by another service.
     * 
     * @return true if authentication was proxied by another service, false
     *         otherwise.
     */
    public boolean isProxied() {
	return (!this.proxyList.isEmpty());
    }

    /**
     * Get the URL of the service proxying authentication to this application,
     * if any.
     * 
     * @return the URL of the service proxying authentication to this
     *         application, or NULL.
     */
    public String getProxyingService() {
	if (proxyList.isEmpty()) {
	    return null;
	}
	return (String) proxyList.get(0);
    }

    /**
     * @param casValidateUrl
     *            The casValidateUrl to set.
     */
    public void setCasValidateUrl(String casValidateUrl) {
	this.casValidateUrl = casValidateUrl;
    }

    /**
     * @param pgtIou
     *            The pgtIou to set.
     */
    public void setPgtIou(String pgtIou) {
	this.pgtIou = pgtIou;
    }

    /**
     * @param primaryAuthentication
     *            The primaryAuthentication to set.
     */
    public void setPrimaryAuthentication(boolean primaryAuthentication) {
	this.primaryAuthentication = primaryAuthentication;
    }

    /**
     * @param proxyCallbackUrl
     *            The proxyCallbackUrl to set.
     */
    public void setProxyCallbackUrl(String proxyCallbackUrl) {
	this.proxyCallbackUrl = proxyCallbackUrl;
    }

    /**
     * @param proxyList
     *            The proxyList to set.
     */
    public void setProxyList(List proxyList) {
	this.proxyList = proxyList;
    }

    /**
     * @param userName
     *            The userName to set.
     */
    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("[");
	sb.append(CASReceipt.class.getName());
	sb.append(" userName=[");
	sb.append(this.userName);
	sb.append("]");
	sb.append(" casValidateUrl=[");
	sb.append(this.casValidateUrl);
	sb.append("]");
	sb.append(" proxyCallbackUrl=[");
	sb.append(this.proxyCallbackUrl);
	sb.append("]");
	sb.append(" pgtIou=[");
	sb.append(this.pgtIou);
	sb.append("]");
	sb.append(" casValidateUrl=[");
	sb.append(this.casValidateUrl);
	sb.append("]");
	sb.append(" proxyList=[");
	sb.append(this.proxyList);
	sb.append("]");

	sb.append("]");
	return sb.toString();
    }

    /**
     * Is this CASReceipt internally complete and consistent. Issues logging
     * messages at error level recording reasons why receipt is invalid, if any.
     * 
     * @return true if internally consistent, false otherwise.
     */
    private boolean validate() {
	boolean valid = true;
	if (this.userName == null) {
	    log.error("Receipt was invalid because userName was null. Receipt:[" + this + "]");
	    valid = false;
	}
	if (this.casValidateUrl == null) {
	    log.error("Receipt was invalid because casValidateUrl was null.  Receipt:[" + this + "]");
	    valid = false;
	}
	if (this.proxyList == null) {
	    log.error("receipt was invalid because " + "proxyList was null.  Receipt:[" + this + "]");
	    valid = false;
	}

	// Commented out because this is a recoverable error.
	// if ((this.pgtIou == null) != (this.proxyCallbackUrl == null)) {
	// log
	// .error("If we have a PGTIOU, there must have been a URL to which the PGT itself was sent.  "
	// + "However, here, one but not the other was null.  PGTIOU=["
	// + pgtIou
	// + "] and proxyCallbackUrl=["
	// + this.proxyCallbackUrl + "]");
	// valid = false;
	// }

	if (this.primaryAuthentication && !this.proxyList.isEmpty()) {
	    log.error("If authentication was by primary credentials then it could not have been proxied. "
		    + "Yet, primaryAuthentication is true where proxyList is not empty.  Receipt:[" + this + "]");
	    valid = false;
	}

	return valid;
    }
}
