package com.olymtech.cas.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.olymtech.cas.authentication.principal.OlymtechCredentials;
import com.olymtech.cas.util.ConstantValue;
import com.olymtech.cas.util.SSOUtil;
import com.olymtech.common.cache.MemCachedFactory;

/**
 */
public final class DirectLoginController extends AbstractController {

	/** The CORE to which we delegate for all CAS functionality. */
	@NotNull
	private CentralAuthenticationService centralAuthenticationService;

	/** CookieGenerator for TGT Cookie */
	@NotNull
	private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;

	/** CookieGenerator for Warn Cookie */
	@NotNull
	private CookieRetrievingCookieGenerator warnCookieGenerator;

	/** Login view name. */
	@NotNull
	private String directLoginView;

	private boolean pathPopulated = false;

	/** Extractors for finding the service. */
	@NotNull
	@Size(min = 1)
	private List<ArgumentExtractor> argumentExtractors;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public DirectLoginController() {
		setCacheSeconds(0);
	}

	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) {

		OlymtechCredentials credentials = new OlymtechCredentials();
		String toUrl = "https://cas.800jit.com/ssocenter/index.jsp";
		credentials.setToUrl(toUrl);
		try {
			if (!this.pathPopulated) {
				final String contextPath = request.getContextPath();
				final String cookiePath = StringUtils.hasText(contextPath) ? contextPath : "/";
				logger.info("Setting path for cookies to: " + cookiePath);
				this.warnCookieGenerator.setCookiePath(cookiePath);
				this.ticketGrantingTicketCookieGenerator.setCookiePath(cookiePath);
				this.pathPopulated = true;
			}

			paramsInit(request, credentials);
			String backURL = credentials.getBackURL();

			String ticketGrantingTicketId = this.ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);
			if (ticketGrantingTicketId != null) {
				if (backURL != null && backURL.length() >= 1)
					return new ModelAndView(new RedirectView(backURL));
				else
					return new ModelAndView(directLoginView);
			}

			Service service = WebUtils.getService(this.argumentExtractors, request);

			ticketGrantingTicketId = this.centralAuthenticationService.createTicketGrantingTicket(credentials);
			putWarnCookieIfRequestParameterPresent(request, response);
			this.ticketGrantingTicketCookieGenerator.addCookie(request, response, ticketGrantingTicketId);

			toUrl = credentials.getToUrl();

			if (backURL != null && backURL.length() >= 1)
				toUrl = backURL;
			request.setAttribute("service", toUrl);
			service = WebUtils.getService(this.argumentExtractors, request);

			final String serviceTicketId = this.centralAuthenticationService.grantServiceTicket(ticketGrantingTicketId, service);

			if (toUrl.indexOf("?") != -1)
				toUrl += "&ticket=" + serviceTicketId;
			else
				toUrl += "?ticket=" + serviceTicketId;

			// request.getSession().setAttribute("wheretogo", toUrl);
			// request.getSession().setAttribute("firstpage",
			// ((OlymtechCredentials) credentials).getErrorURL());
			MemCachedFactory.getCache().put("cas-loginUserName", credentials.getUsername());
			MemCachedFactory.getCache().put("wheretogo" + credentials.getUsername(), toUrl);
			MemCachedFactory.getCache().put("firstpage" + credentials.getUsername(), credentials.getErrorURL());

		} catch (final Exception e) {
			request.getSession().setAttribute("errorMsg", "errorMsg");
			toUrl = credentials.getToUrl();
			String errorURL = credentials.getErrorURL();
			if (errorURL != null && errorURL.length() >= 1)
				toUrl = errorURL;
			logger.error(e.getMessage(), e);
		} finally {
			// System.out.println(toUrl);
		}

		return new ModelAndView(new RedirectView(toUrl));
	}

	private void paramsInit(final HttpServletRequest request, OlymtechCredentials credentials) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String validContent = request.getParameter("validContent");
		String dyToken = request.getParameter("dyToken");
		String serverPort = request.getParameter("serverPort");
		String serverName = request.getParameter("serverName");
		String validContentSession = (String) request.getSession().getAttribute(ConstantValue._SESSION_VALID_IMG_CONTENT);
		String backURL = request.getParameter("backURL");
		String errorURL = request.getParameter("errorURL");

		if (serverName != null)
			credentials.setServerName(serverName);
		if (serverPort != null)
			credentials.setServerPort(serverPort);
		credentials.setUsername(username);
		credentials.setPassword(password);
		credentials.setValidContent(validContent);
		credentials.setDyToken(dyToken);
		credentials.setSiteNo(request.getParameter("siteNo"));
		credentials.setIpAddress(SSOUtil.getIpAddress(request));
		credentials.setContextPath(request.getContextPath());
		credentials.setOption1(request.getParameter("option1"));
		credentials.setValidContentSession(validContentSession);
		credentials.setBackURL(backURL);
		credentials.setErrorURL(errorURL);
	}

	public void setTicketGrantingTicketCookieGenerator(final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
		this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
	}

	public void setWarnCookieGenerator(final CookieRetrievingCookieGenerator warnCookieGenerator) {
		this.warnCookieGenerator = warnCookieGenerator;
	}

	/**
	 * @param centralAuthenticationService
	 *            The centralAuthenticationService to set.
	 */
	public void setCentralAuthenticationService(final CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}

	public void setDirectLoginView(final String directLoginView) {
		this.directLoginView = directLoginView;
	}

	public void setArgumentExtractors(final List<ArgumentExtractor> argumentExtractors) {
		this.argumentExtractors = argumentExtractors;
	}

	private void putWarnCookieIfRequestParameterPresent(final HttpServletRequest request, final HttpServletResponse response) {
		/*
		 * if (StringUtils.hasText((String)
		 * request.getParameterMap().get("warn"))) {
		 * this.warnCookieGenerator.addCookie(response, "true"); } else {
		 * this.warnCookieGenerator.removeCookie(response); }
		 */
	}
}
