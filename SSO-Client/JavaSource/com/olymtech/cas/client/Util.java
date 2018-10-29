package com.olymtech.cas.client;

import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides utility functions in support of CAS clients.
 */
public class Util {

    private static Log log = LogFactory.getLog(Util.class);

    /**
     * Returns a service ID (URL) as a composite of the preconfigured server
     * name and the runtime request, removing the request parameter "ticket".
     */
    public static String getService(HttpServletRequest request, String server) throws ServletException {
	if (log.isTraceEnabled()) {
	    log.trace("entering getService(" + request + ", " + server + ")");
	}

	// ensure we have a server name
	if (server == null) {
	    log.error("getService() argument \"server\" was illegally null.");
	    throw new IllegalArgumentException("name of server is required");
	}

	// now, construct our best guess at the string
	StringBuffer sb = new StringBuffer();
	if (request.isSecure())
	    sb.append("https://");
	else
	    sb.append("http://");
	// sb.append(server);
	// modify 2010.6.24
	sb.append(request.getServerName() + ":" + request.getServerPort());
	sb.append(request.getRequestURI());

	// modify 20100512 different tomcat maybe get request.getQueryString()
	// is "" or null
	if (request.getQueryString() != null && !request.getQueryString().equals("")) {
	    // first, see whether we've got a 'ticket' at all
	    int ticketLoc = request.getQueryString().indexOf("ticket=");

	    // if ticketLoc == 0, then it's the only parameter and we ignore
	    // the whole query string

	    // if no ticket is present, we use the query string wholesale
	    if (ticketLoc == -1)
		sb.append("?" + request.getQueryString());
	    else if (ticketLoc > 0) {
		ticketLoc = request.getQueryString().indexOf("&ticket=");
		if (ticketLoc == -1) {
		    // there was a 'ticket=' unrelated to a parameter named
		    // 'ticket'
		    sb.append("?" + request.getQueryString());
		} else if (ticketLoc > 0) {
		    // otherwise, we use the query string up to "&ticket="
		    sb.append("?" + request.getQueryString().substring(0, ticketLoc));
		}
	    }
	}
	String encodedService = URLEncoder.encode(sb.toString());
	if (log.isTraceEnabled()) {
	    log.trace("returning from getService() with encoded service [" + encodedService + "]");
	}
	return encodedService;
    }
}
