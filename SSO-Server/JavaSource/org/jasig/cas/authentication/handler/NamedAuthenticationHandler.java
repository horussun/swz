/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.uportal.org/license.html
 */
package org.jasig.cas.authentication.handler;

/**
 * Offers AuthenticationHandlers a way to identify themselves by a 
 * user-configured name.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2010/05/26 05:42:31 $
 * @since 3.2.1
 *
 */
public interface NamedAuthenticationHandler extends AuthenticationHandler {
    
    String getName();
}
