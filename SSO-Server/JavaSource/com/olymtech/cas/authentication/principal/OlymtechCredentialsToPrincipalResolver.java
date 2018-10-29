package com.olymtech.cas.authentication.principal;

import org.jasig.cas.authentication.principal.AbstractPersonDirectoryCredentialsToPrincipalResolver;
import org.jasig.cas.authentication.principal.Credentials;

public class OlymtechCredentialsToPrincipalResolver extends AbstractPersonDirectoryCredentialsToPrincipalResolver {

	protected String extractPrincipalId(final Credentials credentials) {
		final OlymtechCredentials olymtechCredentials = (OlymtechCredentials) credentials;
		return olymtechCredentials.getUsername();
	}

	/**
	 * Return true if Credentials are OlymtechCredentials, false otherwise.
	 */
	public boolean supports(final Credentials credentials) {
		return credentials != null && OlymtechCredentials.class.isAssignableFrom(credentials.getClass());
	}
}
