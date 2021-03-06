package swz.infra.tools.cache.memcached;

/**
 * Memcached 内部错误定义
 *
 */
@SuppressWarnings("serial")
public class MemcachedException extends RuntimeException
{
	public MemcachedException() 
	{
		super();
	}

	public MemcachedException(String message) 
	{
		super(message);
	}


	public MemcachedException(String message, Throwable cause) 
	{
	   super(message, cause);
	}

	public MemcachedException(Throwable cause) 
	{
	   super(cause);
	}
}
