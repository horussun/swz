/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.uportal.org/license.html
 */
package org.jasig.cas.audit.spi;

import org.aspectj.lang.JoinPoint;
import com.github.inspektr.audit.spi.AuditResourceResolver;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.util.AopUtils;

/**
 * Converts the Credentials object into a String resource identifier.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2010/05/26 05:42:31 $
 * @since 3.1.2
 *
 */
public final class CredentialsAsFirstParameterResourceResolver implements AuditResourceResolver {

    public String[] resolveFrom(final JoinPoint joinPoint, final Object retval) {
        final Credentials credentials = (Credentials) AopUtils.unWrapJoinPoint(joinPoint).getArgs()[0];
        return new String[] { "supplied credentials: " + credentials.toString() };
    }

    public String[] resolveFrom(final JoinPoint joinPoint, final Exception exception) {
        final Credentials credentials = (Credentials) AopUtils.unWrapJoinPoint(joinPoint).getArgs()[0];
        return new String[] { "supplied credentials: " + credentials.toString() };
    }
}
