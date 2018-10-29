/**
 * 
 */
package swz.infra.tools.cache.memcached;

import org.apache.log4j.Logger;

import swz.infra.tools.cache.memcached.client.ErrorHandler;
import swz.infra.tools.cache.memcached.client.MemCachedClient;

/**
 * 基本的出错处理
 *
 */
public class MemcachedErrorHandler implements ErrorHandler
{	
	private static final Logger logger = Logger.getLogger(MemcachedErrorHandler.class);
	public void handleErrorOnDelete(MemCachedClient client, Throwable error,
			String cacheKey)
	{
		logger.error(new StringBuilder("ErrorOnDelete, cacheKey: ")
				.append(cacheKey).toString(),error);
	}

	public void handleErrorOnFlush(MemCachedClient client, Throwable error)
	{
		logger.error("ErrorOnFlush",error);
	}

	public void handleErrorOnGet(MemCachedClient client, Throwable error,
			String cacheKey)
	{
		logger.error(new StringBuilder("ErrorOnGet, cacheKey: ")
			.append(cacheKey).toString(),error);
	}

	public void handleErrorOnGet(MemCachedClient client, Throwable error,
			String[] cacheKeys)
	{
		logger.error(new StringBuilder("ErrorOnGet, cacheKey: ")
			.append(cacheKeys).toString(),error);
	}

	public void handleErrorOnInit(MemCachedClient client, Throwable error)
	{
		logger.error("ErrorOnInit",error);
	}

	public void handleErrorOnSet(MemCachedClient client, Throwable error,
			String cacheKey)
	{
		logger.error(new StringBuilder("ErrorOnSet, cacheKey: ")
			.append(cacheKey).toString(),error);
	}

	public void handleErrorOnStats(MemCachedClient client, Throwable error)
	{
		logger.error("ErrorOnStats",error);
	}

}
