/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.jasig.cas.authentication.principal;

import java.net.URL;

import org.springframework.util.Assert;

/**
 * The Credentials representing an HTTP-based service. HTTP-based services (such
 * as web applications) are often represented by the URL entry point of the
 * application.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2010/05/26 05:42:31 $
 * @since 3.0
 */
public class HttpBasedServiceCredentials implements Credentials {

    /** Unique Serializable ID. */
    private static final long serialVersionUID = 3904681574350991665L;

    /** The callbackURL to check that identifies the application. */
    private final URL callbackUrl;

    /** String form of callbackUrl; */
    private final String callbackUrlAsString;

    /**
     * Constructor that takes the URL of the HTTP-based service and creates the
     * Credentials object. Caches the value of URL.toExternalForm so updates to
     * the URL will not be reflected in a call to toString().
     * 
     * @param callbackUrl the URL representing the service
     * @throws IllegalArgumentException if the callbackUrl is null.
     */
    public HttpBasedServiceCredentials(final URL callbackUrl) {
        Assert.notNull(callbackUrl, "callbackUrl cannot be null");
        this.callbackUrl = callbackUrl;
        this.callbackUrlAsString = callbackUrl.toExternalForm();
    }

    /**
     * @return Returns the callbackUrl.
     */
    public final URL getCallbackUrl() {
        return this.callbackUrl;
    }

    /**
     * Returns the String version of the URL, based on the original URL
     * provided. i.e. this caches the value of URL.toExternalForm()
     */
    public final String toString() {
        return "[callbackUrl: " + this.callbackUrlAsString + "]";
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
            * result
            + ((this.callbackUrlAsString == null) ? 0 : this.callbackUrlAsString
                .hashCode());
        return result;
    }

    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final HttpBasedServiceCredentials other = (HttpBasedServiceCredentials) obj;
        if (this.callbackUrlAsString == null) {
            if (other.callbackUrlAsString != null)
                return false;
        } else if (!this.callbackUrlAsString.equals(other.callbackUrlAsString))
            return false;
        return true;
    }
    
    
}
