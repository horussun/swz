package org.jasig.cas.util;

import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.beans.factory.DisposableBean;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Scott Battaglia
 * @since 3.1
 */
public final class HttpClient implements Serializable, DisposableBean {

    /** Unique Id for serialization. */
    private static final long serialVersionUID = -5306738686476129516L;

    /** The default status codes we accept. */
    private static final int[] DEFAULT_ACCEPTABLE_CODES = new int[] { HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_NOT_MODIFIED,
	    HttpURLConnection.HTTP_MOVED_TEMP, HttpURLConnection.HTTP_MOVED_PERM, HttpURLConnection.HTTP_ACCEPTED };

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    private static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(100);

    /**
     * List of HTTP status codes considered valid by this AuthenticationHandler.
     */
    @NotNull
    @Size(min = 1)
    private int[] acceptableCodes = DEFAULT_ACCEPTABLE_CODES;

    @Min(0)
    private int connectionTimeout = 5000;

    @Min(0)
    private int readTimeout = 5000;

    /**
     * Note that changing this executor will affect all httpClients. While not
     * ideal, this change was made because certain ticket registries were
     * persisting the HttpClient and thus getting serializable exceptions.
     * 
     * @param executorService
     */
    public void setExecutorService(final ExecutorService executorService) {
	Assert.notNull(executorService);
	EXECUTOR_SERVICE = executorService;
    }

    /**
     * Sends a message to a particular endpoint. Option of sending it without
     * waiting to ensure a response was returned.
     * <p>
     * This is useful when it doesn't matter about the response as you'll
     * perform no action based on the response.
     * 
     * @param url
     *            the url to send the message to
     * @param message
     *            the message itself
     * @param async
     *            true if you don't want to wait for the response, false
     *            otherwise.
     * @return boolean if the message was sent, or async was used. false if the
     *         message failed.
     */
    public boolean sendMessageToEndPoint(final String url, final String message, final boolean async) {

	//System.out.println("sendMessageToEndPoint:" + url);
	Future<Boolean> result = EXECUTOR_SERVICE.submit(new MessageSender(url, message, this.readTimeout, this.connectionTimeout));

	if (async) {
	    return true;
	}

	try {
	    return result.get();
	} catch (final Exception e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public static String tempUrl(String url) {
	String temp = "";
	try {
	    temp = url.substring(0, url.indexOf("//") + 2);
	    url = url.substring(url.indexOf("//") + 2, url.length());
	    temp = temp + url.substring(0, url.indexOf("/") + 1);
	    url = url.substring(url.indexOf("/") + 1, url.length());
	    temp = temp + url.substring(0, url.indexOf("/"));
	} catch (Exception e) {
	    temp = url;
	}

	return temp;
    }

    public boolean isValidEndPoint(final String url) {
	try {
	    final URL u = new URL(url);
	    return isValidEndPoint(u);
	} catch (final MalformedURLException e) {
	    log.error(e.getMessage(), e);
	    return false;
	}
    }

    public boolean isValidEndPoint(final URL url) {
	HttpURLConnection connection = null;
	try {
	    connection = (HttpURLConnection) url.openConnection();
	    connection.setConnectTimeout(this.connectionTimeout);
	    connection.setReadTimeout(this.readTimeout);

	    connection.connect();

	    final int responseCode = connection.getResponseCode();

	    for (final int acceptableCode : this.acceptableCodes) {
		if (responseCode == acceptableCode) {
		    if (log.isDebugEnabled()) {
			log.debug("Response code from server matched " + responseCode + ".");
		    }
		    return true;
		}
	    }

	    if (log.isDebugEnabled()) {
		log.debug("Response Code did not match any of the acceptable response codes.  Code returned was " + responseCode);
	    }
	} catch (final IOException e) {
	    log.error(e.getMessage(), e);
	} finally {
	    if (connection != null) {
		connection.disconnect();
	    }
	}
	return false;
    }

    /**
     * Set the acceptable HTTP status codes that we will use to determine if the
     * response from the URL was correct.
     * 
     * @param acceptableCodes
     *            an array of status code integers.
     */
    public final void setAcceptableCodes(final int[] acceptableCodes) {
	this.acceptableCodes = acceptableCodes;
    }

    public void setConnectionTimeout(final int connectionTimeout) {
	this.connectionTimeout = connectionTimeout;
    }

    public void setReadTimeout(final int readTimeout) {
	this.readTimeout = readTimeout;
    }

    public void destroy() throws Exception {
	EXECUTOR_SERVICE.shutdown();
    }

    private static final class MessageSender implements Callable<Boolean> {

	private String url;

	private String message;

	private int readTimeout;

	private int connectionTimeout;

	public MessageSender(final String url, final String message, final int readTimeout, final int connectionTimeout) {
	    this.url = url;
	    this.message = message;
	    this.readTimeout = readTimeout;
	    this.connectionTimeout = connectionTimeout;
	}

	public Boolean call() throws Exception {
	    HttpURLConnection connection = null;
	    BufferedReader in = null;
	    try {
		if (log.isDebugEnabled()) {
		    log.debug("Attempting to access " + url);
		}
		final URL logoutUrl = new URL(url);
		final String output = "logoutRequest=" + URLEncoder.encode(message, "UTF-8");

		connection = (HttpURLConnection) logoutUrl.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setReadTimeout(readTimeout);
		connection.setConnectTimeout(connectionTimeout);
		connection.setRequestProperty("Content-Length", Integer.toString(output.getBytes().length));
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		if (WebUtils.extranctJsessionIdFromUrl(url) != null) {
		    String jsessionid = "JSESSIONID=" + WebUtils.extranctJsessionIdFromUrl(url);
		    //System.out.println(url);
		    //System.out.println(jsessionid);
		    connection.setRequestProperty("Cookie", jsessionid);
		}

		final DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
		printout.writeBytes(output);
		printout.flush();
		printout.close();

		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		while (in.readLine() != null) {
		    // nothing to do
		}

		if (log.isDebugEnabled()) {
		    log.debug("Finished sending message to" + url);
		}
		return true;
	    } catch (final SocketTimeoutException e) {
		log.warn("Socket Timeout Detected while attempting to send message to [" + url + "].");
		return false;
	    } catch (final Exception e) {
		log.warn("Error Sending message to url endpoint [" + url + "].  Error is [" + e.getMessage() + "]");
		return false;
	    } finally {
		if (in != null) {
		    try {
			in.close();
		    } catch (final IOException e) {
			// can't do anything
		    }
		}
		if (connection != null) {
		    connection.disconnect();
		}
	    }
	}

    }
}
