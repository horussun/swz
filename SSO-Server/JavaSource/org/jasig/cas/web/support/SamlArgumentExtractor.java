/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.jasig.cas.web.support;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.authentication.principal.SamlService;
import org.jasig.cas.authentication.principal.WebApplicationService;

/**
 * Retrieve the ticket and artifact based on the SAML 1.1 profile.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2010/05/26 05:42:30 $
 * @since 3.1
 */
public final class SamlArgumentExtractor extends AbstractSingleSignOutEnabledArgumentExtractor {

    public WebApplicationService extractServiceInternal(final HttpServletRequest request) {
        return SamlService.createServiceFrom(request, getHttpClientIfSingleSignOutEnabled());
    }
}
