/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/index.html
 */
package org.jasig.cas.client.proxy;

import java.io.Serializable;

/**
 * Interface to abstract the retrieval of a proxy ticket to make the
 * implementation a black box to the client.
 *
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2010/06/09 08:44:10 $
 * @since 3.0
 */
public interface ProxyRetriever extends Serializable {

    /**
     * Retrieves a proxy ticket for a specific targetService.
     *
     * @param proxyGrantingTicketId the ProxyGrantingTicketId
     * @param targetService         the service we want to proxy.
     * @return the ProxyTicket Id if Granted, null otherwise.
     */
    String getProxyTicketIdFor(String proxyGrantingTicketId,
                               String targetService);
}
