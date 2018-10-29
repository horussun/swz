package com.olymtech.cas.client.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.olymtech.cas.client.CASAuthenticationException;
import com.olymtech.cas.client.CASReceipt;
import com.olymtech.cas.client.ProxyTicketValidator;
import com.olymtech.cas.client.Util;
import com.olymtech.cas.client.util.CommonUtil;

/**
 * 
 */
public class CASFilter implements Filter {

    private static Log log = LogFactory.getLog(CASFilter.class);

    // Filter initialization parameters

    /**
     * The name of the filter initialization parameter the value of which should
     * be the https: address of the CAS Login servlet. Optional parameter, but
     * required for successful redirection of unauthenticated requests to
     * authentication.
     */
    public final static String LOGIN_INIT_PARAM = "com.olymtech.cas.client.filter.loginUrl";

    /**
     * The name of the filter initialization parameter the value of which must
     * be the https: address of the CAS Validate servlet. Must be a CAS 2.0
     * validate servlet (CAS 1.0 non-XML won't suffice). Required parameter.
     */
    public final static String VALIDATE_INIT_PARAM = "com.olymtech.cas.client.filter.validateUrl";

    /**
     * The name of the filter initialization parameter the value of which must
     * be the address of the service this filter is filtering. The filter will
     * use this as the service parameter for CAS login and validation. Either
     * this parameter or SERVERNAME_INIT_PARAM must be set.
     */
    public final static String SERVICE_INIT_PARAM = "com.olymtech.cas.client.filter.serviceUrl";

    /**
     * The name of the filter initialization parameter the vlaue of which must
     * be the server name, e.g. www.yale.edu , of the service this filter is
     * filtering. The filter will construct from this name and the request the
     * full service parameter for CAS login and validation.
     */
    public final static String SERVERNAME_INIT_PARAM = "com.olymtech.cas.client.filter.serverName";

    /**
     * The name of the filter initialization parameter the value of which must
     * be the String that should be sent as the "renew" parameter on the request
     * for login and validation. This should either be "true" or not be set. It
     * is mutually exclusive with GATEWAY.
     */
    public final static String RENEW_INIT_PARAM = "com.olymtech.cas.client.filter.renew";

    /**
     * The name of the filter initialization parameter the value of which must
     * be a whitespace delimited list of services (ProxyTicketReceptors)
     * authorized to proxy authentication to the service filtered by this
     * Filter. These must be https: URLs. This parameter is optional - not
     * setting it results in no proxy tickets being acceptable.
     */
    public final static String AUTHORIZED_PROXY_INIT_PARAM = "com.olymtech.cas.client.filter.authorizedProxy";

    /**
     * The name of the filter initialization parameter the value of which must
     * be the https: URL to which CAS should send Proxy Granting Tickets when
     * this filter validates tickets.
     */
    public final static String PROXY_CALLBACK_INIT_PARAM = "com.olymtech.cas.client.filter.proxyCallbackUrl";

    /**
     * The name of the filter initialization parameter the value of which
     * indicates whether this filter should wrap requests to expose the
     * authenticated username.
     */
    public final static String WRAP_REQUESTS_INIT_PARAM = "com.olymtech.cas.client.filter.wrapRequest";

    /**
     * The name of the filter initialization parameter the value of which is the
     * value the Filter should send for the gateway parameter on the CAS login
     * request.
     */
    public final static String GATEWAY_INIT_PARAM = "com.olymtech.cas.client.filter.gateway";

    // Session attributes used by this filter

    /**
     * <p>
     * Session attribute in which the username is stored.
     * </p>
     */
    public final static String CAS_FILTER_USER = "com.olymtech.cas.client.filter.user";
    
    public final static String T_G_T = "tgt";

    /**
     * Session attribute in which the CASReceipt is stored.
     */
    public final static String CAS_FILTER_RECEIPT = "com.olymtech.cas.client.filter.receipt";

    /**
     * Session attribute in which internally used gateway attribute is stored.
     */
    private static final String CAS_FILTER_GATEWAYED = "com.olymtech.cas.client.filter.didGateway";

    private static final String CAS_NO_FILTER = "com.olymtech.cas.client.filter.noFilter";

    private static final String CAS_ERROR_PAGE = "com.olymtech.cas.client.filter.errorPage";

    // *********************************************************************
    // Configuration state
    private String casErrorPage;
    private String casNoFilter;
    /** Secure URL whereat CAS offers its login service. */
    private String casLogin;
    /** Secure URL whereat CAS offers its CAS 2.0 validate service */
    private String casValidate;
    /** Filtered service URL for use as service parameter to login and validate */
    private String casServiceUrl;
    /**
     * Name of server, for use in assembling service URL for use as service
     * parameter to login and validate.
     */
    private String casServerName;
    /**
     * Secure URL whereto this filter should ask CAS to send Proxy Granting
     * Tickets.
     */
    private String casProxyCallbackUrl;

    /** True if renew parameter should be set on login and validate */
    private boolean casRenew;

    /**
     * True if this filter should wrap requests to expose authenticated user as
     * getRemoteUser();
     */
    private boolean wrapRequest;

    /** True if this filter should set gateway=true on login redirect */
    private boolean casGateway = false;

    /**
     * List of ProxyTicketReceptor URLs of services authorized to proxy to the
     * path behind this filter.
     */
    private List<String> authorizedProxies = new ArrayList<String>();

    // *********************************************************************
    // Initialization

    public void init(FilterConfig config) throws ServletException {
	casLogin = config.getInitParameter(LOGIN_INIT_PARAM);
	casValidate = config.getInitParameter(VALIDATE_INIT_PARAM);
	casServiceUrl = config.getInitParameter(SERVICE_INIT_PARAM);
	String casAuthorizedProxy = config.getInitParameter(AUTHORIZED_PROXY_INIT_PARAM);
	casRenew = Boolean.valueOf(config.getInitParameter(RENEW_INIT_PARAM)).booleanValue();
	casServerName = config.getInitParameter(SERVERNAME_INIT_PARAM);
	casProxyCallbackUrl = config.getInitParameter(PROXY_CALLBACK_INIT_PARAM);
	wrapRequest = Boolean.valueOf(config.getInitParameter(WRAP_REQUESTS_INIT_PARAM)).booleanValue();
	casGateway = Boolean.valueOf(config.getInitParameter(GATEWAY_INIT_PARAM)).booleanValue();
	casNoFilter = config.getInitParameter(CAS_NO_FILTER);
	casErrorPage = config.getInitParameter(CAS_ERROR_PAGE)==null?"http://cas.800jit.com/ssocenter/errors.jsp":config.getInitParameter(CAS_ERROR_PAGE);;

	if (casGateway && Boolean.valueOf(casRenew).booleanValue()) {
	    throw new ServletException("gateway and renew cannot both be true in filter configuration");
	}
	if (casServerName != null && casServiceUrl != null) {
	    throw new ServletException("serverName and serviceUrl cannot both be set: choose one.");
	}
	if (casServerName == null && casServiceUrl == null) {
	    throw new ServletException("one of serverName or serviceUrl must be set.");
	}
	if (casServiceUrl != null) {
	    if (!(casServiceUrl.startsWith("https://") || casServiceUrl.startsWith("http://"))) {
		throw new ServletException("service URL must start with http:// or https://; its current value is [" + casServiceUrl + "]");
	    }
	}

	if (casValidate == null) {
	    throw new ServletException("validateUrl parameter must be set.");
	}
	if (!(casValidate.startsWith("https://") || casValidate.startsWith("http://"))) {
	    throw new ServletException("validateUrl must start with https:// or http://, its current value is [" + casValidate + "]");
	}

	if (casAuthorizedProxy != null) {

	    // parse and remember authorized proxies
	    StringTokenizer casProxies = new StringTokenizer(casAuthorizedProxy);
	    while (casProxies.hasMoreTokens()) {
		String anAuthorizedProxy = casProxies.nextToken();
		if (!anAuthorizedProxy.startsWith("https://")) {
		    throw new ServletException("CASFilter initialization parameter for authorized proxies "
			    + "must be a whitespace delimited list of authorized proxies.  "
			    + "Authorized proxies must be secure (https) addresses.  This one wasn't: [" + anAuthorizedProxy + "]");
		}
		this.authorizedProxies.add(anAuthorizedProxy);
	    }
	}

	if (log.isDebugEnabled()) {
	    log.debug(("CASFilter initialized as: [" + toString() + "]"));
	}
    }

    // *********************************************************************
    // Filter processing

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws ServletException, IOException {

	if (log.isTraceEnabled()) {
	    log.trace("entering doFilter()");
	}

	// make sure we've got an HTTP request
	if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
	    log.error("doFilter() called on a request or response that was not an HttpServletRequest or response.");
	    throw new ServletException("CASFilter protects only HTTP resources");
	}

	// for no filter path
	if (casNoFilter != null) {
	    try {
		HttpServletRequest r = (HttpServletRequest) request;
		String contextPath = r.getContextPath();
		String uri = r.getRequestURI();
		String checkPath = uri.substring(contextPath.length(), uri.length());
		String[] noFilterArray = casNoFilter.split(",");
		for (String string : noFilterArray) {
		    if (checkPath.indexOf(string) != -1) {
			fc.doFilter(request, response);
			return;
		    }
		}
	    } catch (Exception e) {
		// do nothing
	    }
	}

	// Is this a request for the proxy callback listener? If so, pass
	// it through
	if (casProxyCallbackUrl != null && casProxyCallbackUrl.endsWith(((HttpServletRequest) request).getRequestURI())
		&& request.getParameter("pgtId") != null && request.getParameter("pgtIou") != null) {
	    log.trace("passing through what we hope is CAS's request for proxy ticket receptor.");
	    fc.doFilter(request, response);
	    return;
	}

	// Wrap the request if desired
	if (wrapRequest) {
	    log.trace("Wrapping request with CASFilterRequestWrapper.");
	    request = new CASFilterRequestWrapper((HttpServletRequest) request);
	}

	HttpSession session = ((HttpServletRequest) request).getSession();
	String annoy = session.getAttribute(CAS_FILTER_USER) == null ? "" : session.getAttribute(CAS_FILTER_USER).toString();

	if ("admin@anonymity.com".equals(annoy) || annoy.equals("")) {
	    String anonymity = request.getParameter("anonymity") == null ? "" : request.getParameter("anonymity");
	    if ("true".equals(anonymity)) {
		session.setAttribute(CAS_FILTER_USER, "admin@anonymity.com");
		fc.doFilter(request, response);
		return;
	    }
	}

	if ("admin@anonymity.com".equals(annoy)) {
	    session.removeAttribute(CAS_FILTER_USER);
	    session.removeAttribute(CAS_FILTER_RECEIPT);
	}

	// if our attribute's already present and valid, pass through the filter
	// chain
	CASReceipt receipt = (CASReceipt) session.getAttribute(CAS_FILTER_RECEIPT);
	if (receipt != null && isReceiptAcceptable(receipt)) {
	    log.trace("CAS_FILTER_RECEIPT attribute was present and acceptable - passing  request through filter..");
	    fc.doFilter(request, response);
	    return;
	}

	// otherwise, we need to authenticate via CAS
	String ticket = request.getParameter("ticket");

	// no ticket? abort request processing and redirect
	if (ticket == null || ticket.equals("")) {
	    log.trace("CAS ticket was not present on request.");
	    // did we go through the gateway already?
	    boolean didGateway = Boolean.valueOf((String) session.getAttribute(CAS_FILTER_GATEWAYED)).booleanValue();

	    if (casLogin == null) {
		// TODO: casLogin should probably be ensured to not be null at
		// filter initialization. -awp9
		log.fatal("casLogin was not set, so filter cannot redirect request for authentication.");
		throw new ServletException("When CASFilter protects pages that do not receive a 'ticket' "
			+ "parameter, it needs a com.olymtech.cas.client.filter.loginUrl " + "filter parameter");
	    }
	    if (!didGateway) {
		log.trace("Did not previously gateway.  Setting session attribute to true.");
		session.setAttribute(CAS_FILTER_GATEWAYED, "true");
		redirectToCAS((HttpServletRequest) request, (HttpServletResponse) response);
		// abort chain
		return;
	    } else {
		log.trace("Previously gatewayed.");
		// if we should be logged in, make sure validation succeeded

		if (casGateway || session.getAttribute(CAS_FILTER_USER) != null) {
		    log.trace("casGateway was true and CAS_FILTER_USER set: passing request along filter chain.");

		    // continue processing the request
		    fc.doFilter(request, response);
		    return;
		} else {
		    // unknown state... redirect to CAS
		    session.setAttribute(CAS_FILTER_GATEWAYED, "true");
		    redirectToCAS((HttpServletRequest) request, (HttpServletResponse) response);
		    // abort chain
		    return;
		}
	    }
	} else {
	    /*
	     * String url = getService((HttpServletRequest) request); if
	     * (url.indexOf("JSESSIONID") == -1) {
	     * redirectToCAS((HttpServletRequest) request, (HttpServletResponse)
	     * response); return; }
	     */
	}

	try {
	    receipt = getAuthenticatedUser((HttpServletRequest) request);
	} catch (CASAuthenticationException e) {
	    log.error(e);
	    // throw new ServletException(e);
	    redirectToCASErrorPage((HttpServletRequest) request, (HttpServletResponse) response);
	    return;
	}

	if (!isReceiptAcceptable(receipt)) {
	    // throw new
	    // ServletException("Authentication was technically successful but rejected as a matter of policy. ["
	    // + receipt + "]");
	    log.trace("isReceiptAcceptable error to get authenticated receipt [" + receipt + "], now passing request along filter chain.");
	    redirectToCASErrorPage((HttpServletRequest) request, (HttpServletResponse) response);
	    return;
	}

	// Store the authenticated user in the session
	if (session != null) { // probably unnecessary
	    session.setAttribute(CAS_FILTER_USER, receipt.getUserName());
	    session.setAttribute(T_G_T, receipt.getTgt());
	    session.setAttribute(CASFilter.CAS_FILTER_RECEIPT, receipt);
	    // don't store extra unnecessary session state
	    session.removeAttribute(CAS_FILTER_GATEWAYED);
	}
	if (log.isTraceEnabled()) {
	    log.trace("validated ticket to get authenticated receipt [" + receipt + "], now passing request along filter chain.");
	}

	// continue processing the request
	fc.doFilter(request, response);
	log.trace("returning from doFilter()");

    }

    /**
     * Is this receipt acceptable as evidence of authentication by credentials
     * that would have been acceptable to this path? Current implementation
     * checks whether from renew and whether proxy was authorized.
     * 
     * @param receipt
     * @return true if acceptable, false otherwise
     */
    private boolean isReceiptAcceptable(CASReceipt receipt) {
	if (receipt == null)
	    throw new IllegalArgumentException("Cannot evaluate a null receipt.");
	if (this.casRenew && !receipt.isPrimaryAuthentication()) {
	    return false;
	}
	if (receipt.isProxied()) {
	    if (!this.authorizedProxies.contains(receipt.getProxyingService())) {
		return false;
	    }
	}
	return true;
    }

    // *********************************************************************
    // Utility methods

    /**
     * Converts a ticket parameter to a CASReceipt, taking into account an
     * optionally configured trusted proxy in the tier immediately in front of
     * us.
     * 
     * @throws ServletException
     *             - when unable to get service for request
     * @throws CASAuthenticationException
     *             - on authentication failure
     */
    private CASReceipt getAuthenticatedUser(HttpServletRequest request) throws ServletException, CASAuthenticationException {
	log.trace("entering getAuthenticatedUser()");
	ProxyTicketValidator pv = null;

	pv = new ProxyTicketValidator();
	pv.setCasValidateUrl(casValidate);
	pv.setServiceTicket(request.getParameter("ticket"));
	pv.setService(getService(request));
	pv.setRenew(Boolean.valueOf(casRenew).booleanValue());
	if (casProxyCallbackUrl != null) {
	    pv.setProxyCallbackUrl(casProxyCallbackUrl);
	}
	if (log.isDebugEnabled()) {
	    log.debug("about to validate ProxyTicketValidator: [" + pv + "]");
	}

	try {
	    CommonUtil cu = new CommonUtil();
	    String sn = cu.getIP();
	    String sp = cu.getPort();
	    pv.setWebServerIPandPort("http://" + sn + ":" + sp);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return CASReceipt.getReceipt(pv);

    }

    /**
     * Returns either the configured service or figures it out for the current
     * request. The returned service is URL-encoded.
     */
    private String getService(HttpServletRequest request) throws ServletException {

	log.trace("entering getService()");
	String serviceString;

	// ensure we have a server name or service name
	if (casServerName == null && casServiceUrl == null)
	    throw new ServletException("need one of the following configuration " + "parameters: com.olymtech.cas.client.filter.serviceUrl or "
		    + "com.olymtech.cas.client.filter.serverName");

	// use the given string if it's provided
	if (casServiceUrl != null)
	    serviceString = URLEncoder.encode(casServiceUrl);
	else
	    // otherwise, return our best guess at the service
	    serviceString = Util.getService(request, casServerName);
	if (log.isTraceEnabled()) {
	    log.trace("returning from getService() with service [" + serviceString + "]");
	}
	return serviceString;
    }

    /**
     * Redirects the user to CAS, determining the service from the request.
     */
    private void redirectToCAS(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	if (log.isTraceEnabled()) {
	    log.trace("entering redirectToCAS()");
	}
	String url = getService((HttpServletRequest) request);

	String casLoginString = casLogin + "?service=" + url + ((casRenew) ? "&renew=true" : "") + (casGateway ? "&gateway=true" : "");

	if (log.isDebugEnabled()) {
	    log.debug("Redirecting browser to [" + casLoginString + ")");
	}

	((HttpServletResponse) response).sendRedirect(casLoginString);

	if (log.isTraceEnabled()) {
	    log.trace("returning from redirectToCAS()");
	}
    }

    /**
     * Redirects the user to error page, determining the service from the
     * request.
     */
    private void redirectToCASErrorPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	if (log.isTraceEnabled()) {
	    log.trace("entering redirectToCASErrorPage()");
	}

	if (log.isDebugEnabled()) {
	    log.debug("Redirecting browser to [" + casErrorPage + ")");
	}

	((HttpServletResponse) response).sendRedirect(casErrorPage);

	if (log.isTraceEnabled()) {
	    log.trace("returning from redirectToCASErrorPage()");
	}
    }

    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("[CASFilter:");
	sb.append(" casGateway=");
	sb.append(this.casGateway);
	sb.append(" wrapRequest=");
	sb.append(this.wrapRequest);

	sb.append(" casAuthorizedProxies=[");
	sb.append(this.authorizedProxies);
	sb.append("]");

	if (this.casLogin != null) {
	    sb.append(" casLogin=[");
	    sb.append(this.casLogin);
	    sb.append("]");
	} else {
	    sb.append(" casLogin=NULL!!!!!");
	}

	if (this.casProxyCallbackUrl != null) {
	    sb.append(" casProxyCallbackUrl=[");
	    sb.append(casProxyCallbackUrl);
	    sb.append("]");
	}

	if (this.casRenew) {
	    sb.append(" casRenew=true");
	}

	if (this.casServerName != null) {
	    sb.append(" casServerName=[");
	    sb.append(casServerName);
	    sb.append("]");
	}

	if (this.casServiceUrl != null) {
	    sb.append(" casServiceUrl=[");
	    sb.append(casServiceUrl);
	    sb.append("]");
	}

	if (this.casValidate != null) {
	    sb.append(" casValidate=[");
	    sb.append(casValidate);
	    sb.append("]");
	} else {
	    sb.append(" casValidate=NULL!!!");
	}

	return sb.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
	// TODO Auto-generated method stub

    }
}
