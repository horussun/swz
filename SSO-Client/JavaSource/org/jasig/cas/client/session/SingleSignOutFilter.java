package org.jasig.cas.client.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;

/**
 * 
 */
public final class SingleSignOutFilter extends AbstractConfigurationFilter {

    /**
     * The name of the artifact parameter. This is used to capture the session
     * identifier.
     */
    private String artifactParameterName = "ticket";

    private static SessionMappingStorage SESSION_MAPPING_STORAGE = new HashMapBackedSessionMappingStorage();
    private static Log log = LogFactory.getLog(SingleSignOutFilter.class);

    public void init(final FilterConfig filterConfig) throws ServletException {
	if (!isIgnoreInitConfiguration()) {
	    setArtifactParameterName(getPropertyFromInitParams(filterConfig, "artifactParameterName", "ticket"));
	}
	init();
    }

    public void init() {
	CommonUtils.assertNotNull(this.artifactParameterName, "artifactParameterName cannot be null.");
	CommonUtils.assertNotNull(SESSION_MAPPING_STORAGE, "sessionMappingStorage cannote be null.");
    }

    public void setArtifactParameterName(final String artifactParameterName) {
	this.artifactParameterName = artifactParameterName;
    }

    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException,
	    ServletException {

	final HttpServletRequest request = (HttpServletRequest) servletRequest;
	final String logoutRequest = CommonUtils.safeGetParameter(request, "logoutRequest");

	if (CommonUtils.isNotBlank(logoutRequest)) {
	    final String sessionIdentifier = XmlUtils.getTextForElement(logoutRequest, "SessionIndex");
	    if (CommonUtils.isNotBlank(sessionIdentifier)) {
		final HttpSession session = SESSION_MAPPING_STORAGE.removeSessionByMappingId(sessionIdentifier);

		if (session != null) {
		    try {
			System.out.println("logout: " + request.getContextPath() + " session (" + sessionIdentifier + ") invalidate is sucess!!!");
			session.invalidate();
		    } catch (final IllegalStateException e) {
			e.printStackTrace();
		    }
		}
	    }
	}

	else {
	    final String artifact = CommonUtils.safeGetParameter(request, this.artifactParameterName);
	    final HttpSession session = request.getSession(true);
	    if (CommonUtils.isNotBlank(artifact) && session != null) {

		try {
		    SESSION_MAPPING_STORAGE.removeBySessionById(session.getId());
		} catch (final Exception e) {
		    e.printStackTrace();
		}
		SESSION_MAPPING_STORAGE.addSessionById(artifact, session);
	    }
	}

	filterChain.doFilter(servletRequest, servletResponse);

    }

    public void setSessionMappingStorage(final SessionMappingStorage storage) {
	SESSION_MAPPING_STORAGE = storage;
    }

    public static SessionMappingStorage getSessionMappingStorage() {
	return SESSION_MAPPING_STORAGE;
    }

    public void destroy() {
	// nothing to do
    }
}
