/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */

package org.jasig.cas.ticket.registry;

/**
 * Strategy interface to denote the start of cleaning the registry.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2010/05/26 05:42:31 $
 * @since 3.0
 * <p>
 * This is a published and supported CAS Server 3 API.
 * </p>
 */
public interface RegistryCleaner {

    /**
     * Method to kick-off the cleaning of a registry.
     */
    void clean();
}
