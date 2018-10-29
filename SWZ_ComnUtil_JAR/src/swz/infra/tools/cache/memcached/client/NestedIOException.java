package swz.infra.tools.cache.memcached.client;

import java.io.*;

public class NestedIOException extends IOException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Create a new <code>NestedIOException</code> instance.
     * @param cause object of type throwable
     */
    public NestedIOException( Throwable cause ) {
        super( cause.getMessage() );
        super.initCause( cause );
    }

    public NestedIOException( String message, Throwable cause ) {
        super( message );
        initCause( cause );
    }
}
