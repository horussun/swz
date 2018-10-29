package org.jasig.cas.web.flow;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.web.bind.CredentialsBinder;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;
import org.springframework.web.util.CookieGenerator;
import org.springframework.webflow.execution.RequestContext;

import com.olymtech.cas.authentication.principal.OlymtechCredentials;
import com.olymtech.cas.util.ConstantValue;
import com.olymtech.cas.util.SSOUtil;
import com.olymtech.common.cache.MemCachedFactory;

/**
 * Action to authenticate credentials and retrieve a TicketGrantingTicket for
 * those credentials. If there is a request for renew, then it also generates
 * the Service Ticket required.
 * 
 * @author Scott Battaglia
 * @since 3.0.4
 */
@SuppressWarnings("deprecation")
public class AuthenticationViaFormAction {

    /**
     * Binder that allows additional binding of form object beyond Spring
     * defaults.
     */
    private CredentialsBinder credentialsBinder;

    /** Core we delegate to for handling all ticket related tasks. */
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;

    @NotNull
    private CookieGenerator warnCookieGenerator;

    /** Extractors for finding the service. */
    @NotNull
    @Size(min = 1)
    private List<ArgumentExtractor> argumentExtractors;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public final void doBind(final RequestContext context, final Credentials credentials) throws Exception {

	final HttpServletRequest request = WebUtils.getHttpServletRequest(context);

	if (this.credentialsBinder != null && this.credentialsBinder.supports(credentials.getClass())) {
	    this.credentialsBinder.bind(request, credentials);
	}
    }

    public final String submit(final RequestContext context, final Credentials credentials, final MessageContext messageContext) throws Exception {

	final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);
	final Service service = WebUtils.getService(context);

	final HttpServletRequest request = WebUtils.getHttpServletRequest(context);

	OlymtechCredentials olyc;
	if (credentials instanceof OlymtechCredentials) {
	    olyc = (OlymtechCredentials) credentials;
	} else
	    return "error";

	// add for some init
	olyc.setSiteNo(request.getParameter("siteNo"));
	olyc.setIpAddress(SSOUtil.getIpAddress(request));
	olyc.setContextPath(request.getContextPath());
	olyc.setOption1(request.getParameter("option1"));
	olyc.setValidContentSession(request.getSession().getAttribute(ConstantValue._SESSION_VALID_IMG_CONTENT)==null?"":(String) request.getSession().getAttribute(ConstantValue._SESSION_VALID_IMG_CONTENT));
	olyc.setBackURL(request.getParameter("backURL"));
	olyc.setErrorURL(request.getParameter("errorURL"));
	olyc.setJSID(request.getParameter("JSID"));
	olyc.setIpAddress(WebUtils.getIpAddress(request));

	if (StringUtils.hasText(context.getRequestParameters().get("renew")) && ticketGrantingTicketId != null && service != null) {

	    try {
		final String serviceTicketId = this.centralAuthenticationService.grantServiceTicket(ticketGrantingTicketId, service, credentials);
		WebUtils.putServiceTicketInRequestScope(context, serviceTicketId);
		putWarnCookieIfRequestParameterPresent(context);
		return "warn";
	    } catch (final TicketException e) {
		if (e.getCause() != null && AuthenticationException.class.isAssignableFrom(e.getCause().getClass())) {
		    populateErrorsInstance(e, messageContext);
		    return "error";
		}
		this.centralAuthenticationService.destroyTicketGrantingTicket(ticketGrantingTicketId);
		if (logger.isDebugEnabled()) {
		    logger.debug("Attempted to generate a ServiceTicket using renew=true with different credentials", e);
		}
	    }
	}

	try {
	    WebUtils.putTicketGrantingTicketInRequestScope(context, this.centralAuthenticationService.createTicketGrantingTicket(credentials));
	    putWarnCookieIfRequestParameterPresent(context);

	    String wheretogo = "http://www.800jit.com";

	    // modify 20100526 for local login
	    if (service == null) {
		String toUrl = olyc.getToUrl();
		request.setAttribute("service", toUrl);
		wheretogo = toUrl;
		String backURL = olyc.getBackURL();
		if (backURL != null && backURL.length() >= 1) {
		    request.setAttribute("service", backURL);
		    wheretogo = backURL;
		}
		final Service localservice = WebUtils.getService(this.argumentExtractors, context);
		context.getFlowScope().put("service", localservice);
	    }
	    //request.getSession().setAttribute("wheretogo", wheretogo);
	    ///request.getSession().setAttribute("firstpage", olyc.getErrorURL());
	    
	    MemCachedFactory.getCache().put("cas-loginUserName", olyc.getUsername());
	    MemCachedFactory.getCache().put("wheretogo"+olyc.getUsername(), wheretogo);
	    MemCachedFactory.getCache().put("firstpage"+olyc.getUsername(), olyc.getErrorURL());
	    
	    return "success";
	} catch (final TicketException e) {
	    if (credentials instanceof OlymtechCredentials) {

		String toUrl = olyc.getToUrl();
		if (olyc.getErrorURL() != null && olyc.getErrorURL().length() > 0)
		    toUrl = olyc.getErrorURL();
		request.setAttribute("service", toUrl);
		final Service localservice = WebUtils.getService(this.argumentExtractors, context);
		context.getFlowScope().put("service", localservice);
	    }
	    request.getSession().setAttribute("errorMsg", "errorMsg");
	    request.getSession().setAttribute("userName", olyc.getUsername());
	    populateErrorsInstance(e, messageContext);
	    return "error";
	} finally {
	}
    }

    private void populateErrorsInstance(final TicketException e, final MessageContext messageContext) {

	try {
	    messageContext.addMessage(new MessageBuilder().error().code(e.getCode()).defaultText(e.getCode()).build());
	} catch (final Exception fe) {
	    logger.error(fe.getMessage(), fe);
	}
    }

    private void putWarnCookieIfRequestParameterPresent(final RequestContext context) {
	final HttpServletResponse response = WebUtils.getHttpServletResponse(context);

	if (StringUtils.hasText(context.getExternalContext().getRequestParameterMap().get("warn"))) {
	    this.warnCookieGenerator.addCookie(response, "true");
	} else {
	    this.warnCookieGenerator.removeCookie(response);
	}
    }

    public final void setCentralAuthenticationService(final CentralAuthenticationService centralAuthenticationService) {
	this.centralAuthenticationService = centralAuthenticationService;
    }

    /**
     * Set a CredentialsBinder for additional binding of the HttpServletRequest
     * to the Credentials instance, beyond our default binding of the
     * Credentials as a Form Object in Spring WebMVC parlance. By the time we
     * invoke this CredentialsBinder, we have already engaged in default binding
     * such that for each HttpServletRequest parameter, if there was a JavaBean
     * property of the Credentials implementation of the same name, we have set
     * that property to be the value of the corresponding request parameter.
     * This CredentialsBinder plugin point exists to allow consideration of
     * things other than HttpServletRequest parameters in populating the
     * Credentials (or more sophisticated consideration of the
     * HttpServletRequest parameters).
     * 
     * @param credentialsBinder
     *            the credentials binder to set.
     */
    public final void setCredentialsBinder(final CredentialsBinder credentialsBinder) {
	this.credentialsBinder = credentialsBinder;
    }

    public final void setWarnCookieGenerator(final CookieGenerator warnCookieGenerator) {
	this.warnCookieGenerator = warnCookieGenerator;
    }

    public void setArgumentExtractors(final List<ArgumentExtractor> argumentExtractors) {
	this.argumentExtractors = argumentExtractors;
    }
}
