/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.jasig.cas;

/**
 * Class that exposes the CAS version. Fetches the "Implementation-Version"
 * manifest attribute from the jar file.
 * 
 * @author Dmitriy Kopylenko
 * @version $Revision: 1.1 $ $Date: 2010/05/26 05:42:31 $
 * @since 3.0
 */
public final class CasVersion {

    /**
     * Private constructor for CasVersion. You should not be able to instanciate
     * this class.
     */
    private CasVersion() {
        // this class is not instantiable
    }

    /**
     * Return the full CAS version string.
     * 
     * @see java.lang.Package#getImplementationVersion
     */
    public static String getVersion() {
        return CasVersion.class.getPackage().getImplementationVersion();
    }
}
