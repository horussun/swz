/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.uportal.org/license.html
 */
package org.jasig.cas.util;

import org.aspectj.lang.JoinPoint;


/**
 * Utility class to assist with AOP operations.
 *
 * @author Marvin S. Addison
 * @version $Revision: 1.1 $ $Date: 2010/05/26 05:42:30 $
 * @since 3.4
 *
 */
public final class AopUtils {

    /**
     * Unwraps a join point that may be nested due to layered proxies.
     *
     * @param point Join point to unwrap.
     * @return Innermost join point; if not nested, simply returns the argument.
     */
    public static JoinPoint unWrapJoinPoint(final JoinPoint point) {
        JoinPoint naked = point;
        while (naked.getArgs().length > 0 && naked.getArgs()[0] instanceof JoinPoint) {
            naked = (JoinPoint) naked.getArgs()[0];
        }
        return naked;
    }
}
