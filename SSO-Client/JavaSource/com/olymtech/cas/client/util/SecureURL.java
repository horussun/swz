package com.olymtech.cas.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A class housing some utility functions exposing secure URL validation and
 * content retrieval. The rules are intended to be about as restrictive as a
 * common browser with respect to server-certificate validation.
 */
public class SecureURL {

    private static Log log = LogFactory.getLog(SecureURL.class);

    /**
     * For testing only...
     */
    public static void main(String args[]) throws IOException {
	System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
	System.out.println(SecureURL.retrieve(args[0]));
    }

    /**
     * Retrieve the contents from the given URL as a String, assuming the URL's
     * server matches what we expect it to match.
     */
    public static String retrieve(String url) throws IOException {
	if (log.isTraceEnabled()) {
	    log.trace("entering retrieve(" + url + ")");
	}
	BufferedReader r = null;
	try {
	    URL u = new URL(url);
	    /*
	    if (!u.getProtocol().equals("https")) {
		// IOException may not be the best exception we could throw here
		// since the problem is with the URL argument we were passed,
		// not
		// IO. -awp9
		log.error("retrieve(" + url + ") on an illegal URL since protocol was not https.");
		throw new IOException("only 'https' URLs are valid for this method");
	    }
	    */
	    URLConnection uc = u.openConnection();
	    uc.setRequestProperty("Connection", "close");
	    r = new BufferedReader(new InputStreamReader(uc.getInputStream()));
	    String line;
	    StringBuffer buf = new StringBuffer();
	    while ((line = r.readLine()) != null)
		buf.append(line + "\n");
	    return buf.toString();
	} finally {
	    try {
		if (r != null)
		    r.close();
	    } catch (IOException ex) {
		// ignore
	    }
	}
    }
}
