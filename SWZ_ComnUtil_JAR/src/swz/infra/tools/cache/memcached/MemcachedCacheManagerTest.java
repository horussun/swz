package swz.infra.tools.cache.memcached;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import swz.infra.tools.cache.ICacheManager;
import swz.infra.tools.cache.IMemcachedCache;

public class MemcachedCacheManagerTest
{
	ICacheManager<IMemcachedCache> manager;

	@Before
	public void setUp() throws Exception
	{
		manager = CacheUtil.getCacheManager(IMemcachedCache.class,
				MemcachedCacheManager.class.getName());
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testStartAndStop()
	{
		try
		{
			manager.start();
			manager.stop();
		}
		catch(Exception ex)
		{
			org.junit.Assert.assertFalse(true);
			ex.printStackTrace();
			//AuditorFactory.getInstance().getVrwsAuditor().iWarn(this, "testStartAndStop", "", ex);
		}
	}

}
