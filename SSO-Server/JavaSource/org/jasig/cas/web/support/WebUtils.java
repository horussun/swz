package org.jasig.cas.web.support;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.authentication.principal.WebApplicationService;
import org.springframework.util.Assert;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.execution.RequestContext;

import com.olymtech.cas.util.StringUtil;

/**
 * Common utilities for the web tier.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.2.2.2 $ $Date: 2011/03/18 07:04:04 $
 * @since 3.1
 */
public final class WebUtils {

    public static HttpServletRequest getHttpServletRequest(final RequestContext context) {
	Assert.isInstanceOf(ServletExternalContext.class, context.getExternalContext(), "Cannot obtain HttpServletRequest from event of type: "
		+ context.getExternalContext().getClass().getName());

	return (HttpServletRequest) context.getExternalContext().getNativeRequest();
    }

    public static HttpServletResponse getHttpServletResponse(final RequestContext context) {
	Assert.isInstanceOf(ServletExternalContext.class, context.getExternalContext(), "Cannot obtain HttpServletResponse from event of type: "
		+ context.getExternalContext().getClass().getName());
	return (HttpServletResponse) context.getExternalContext().getNativeResponse();
    }

    public static WebApplicationService getService(final List<ArgumentExtractor> argumentExtractors, final HttpServletRequest request) {
	for (final ArgumentExtractor argumentExtractor : argumentExtractors) {
	    final WebApplicationService service = argumentExtractor.extractService(request);

	    if (service != null) {
		return service;
	    }
	}

	return null;
    }

    public static WebApplicationService getService(final List<ArgumentExtractor> argumentExtractors, final RequestContext context) {
	final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
	return getService(argumentExtractors, request);
    }

    /*
     * public static WebApplicationService getDirectService( final
     * List<ArgumentExtractor> argumentExtractors, final HttpServletRequest
     * request) { return getService(argumentExtractors, request); }
     */
    public static WebApplicationService getService(final RequestContext context) {
	return (WebApplicationService) context.getFlowScope().get("service");
    }

    public static void putTicketGrantingTicketInRequestScope(final RequestContext context, final String ticketValue) {
	context.getRequestScope().put("ticketGrantingTicketId", ticketValue);
    }

    public static String getTicketGrantingTicketId(final RequestContext context) {
	final String tgtFromRequest = (String) context.getRequestScope().get("ticketGrantingTicketId");
	final String tgtFromFlow = (String) context.getFlowScope().get("ticketGrantingTicketId");

	return tgtFromRequest != null ? tgtFromRequest : tgtFromFlow;

    }

    public static void putServiceTicketInRequestScope(final RequestContext context, final String ticketValue) {
	context.getRequestScope().put("serviceTicketId", ticketValue);
    }

    public static String getServiceTicketFromRequestScope(final RequestContext context) {
	return context.getRequestScope().getString("serviceTicketId");
    }

    public static String extranctJsessionIdFromUrl(final String url) {
	int point = url.indexOf(";");
	if (point == -1)
	    return null;
	String subUrl = url.substring(point + 1);
	point = subUrl.indexOf("=");
	subUrl = subUrl.substring(point + 1);
	point = subUrl.indexOf("?");
	if (point == -1)
	    point = subUrl.indexOf("&");
	if (point == -1) {
	    // subUrl = subUrl;
	} else
	    subUrl = subUrl.substring(0, point);
	return subUrl;
    }

    /**
     * 获取ipaddress
     * 
     * @param request
     * @return ip
     */
    public static String getIpAddress(HttpServletRequest request) {
	String result = StringUtil.isEmpty(request.getHeader("XForwarded-For")) != true ? request.getHeader("XForwarded-For") : request
		.getHeader("x-forwarded-for");

	if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
	    result = request.getHeader("Proxy-Client-IP");
	}
	if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
	    result = request.getHeader("WL-Proxy-Client-IP");
	}
	if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
	    result = request.getHeader("HTTP_X_FORWARDED_FOR");
	}
	if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
	    result = request.getHeader("REMOTE_ADDR");
	}
	if (result == null || result.length() == 0 || "unknown".equalsIgnoreCase(result)) {
	    result = request.getRemoteAddr();
	}
	return result;
    }
}
