/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/index.html
 */
package org.jasig.cas.client.util;

import org.jasig.cas.client.validation.Assertion;

/**
 * Static holder that places Assertion in a threadlocal.
 *
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2010/06/09 08:44:10 $
 * @since 3.0
 */
public class AssertionHolder {

    /**
     * ThreadLocal to hold the Assertion for Threads to access.
     */
    private static final ThreadLocal threadLocal = new ThreadLocal();


    /**
     * Retrieve the assertion from the ThreadLocal.
     */
    public static Assertion getAssertion() {
        return (Assertion) threadLocal.get();
    }

    /**
     * Add the Assertion to the ThreadLocal.
     */
    public static void setAssertion(final Assertion assertion) {
        threadLocal.set(assertion);
    }

    /**
     * Clear the ThreadLocal.
     */
    public static void clear() {
        threadLocal.set(null);
    }
}
