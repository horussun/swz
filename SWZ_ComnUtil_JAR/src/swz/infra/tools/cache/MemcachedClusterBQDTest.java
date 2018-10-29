package swz.infra.tools.cache;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import swz.infra.tools.cache.memcached.CacheUtil;
import swz.infra.tools.cache.memcached.MemcachedCacheManager;

public class MemcachedClusterBQDTest
{
	static ICacheManager<IMemcachedCache> manager;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		manager = CacheUtil.getCacheManager(IMemcachedCache.class,
			MemcachedCacheManager.class.getName());
		manager.setConfigFile("memcached_cluster.xml");
		manager.start();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		manager.stop();
	}
	
	@Test
	public void testActiveMode()
	{
		try
		{
			IMemcachedCache cache1 = manager.getCache("mclient0");
			
			cache1.put("key1","value1");
			cache1.put("key2","value2");
			cache1.put("key3","value3");
			cache1.put("key4","value4");
			cache1.put("key5","value5");
			cache1.put("key6","value6");
			
			Thread.sleep(1000);
			
			Assert.assertEquals(cache1.get("key1"),"value1");
			Assert.assertEquals(cache1.get("key2"),"value2");
			Assert.assertEquals(cache1.get("key3"),"value3");
			Assert.assertEquals(cache1.get("key4"),"value4");
			Assert.assertEquals(cache1.get("key5"),"value5");
			Assert.assertEquals(cache1.get("key6"),"value6");
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			//AuditorFactory.getInstance().getVrwsAuditor().iWarn(this, "testActiveMode", "", ex);
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public  void testStandByMode()
	{
		IMemcachedCache cache1 = manager.getCache("mclient0-bck");
		
		cache1.put("key1","value1");
		cache1.put("key2","value2");
		cache1.put("key3","value3");
		cache1.put("key4","value4");
		cache1.put("key5","value5");
		cache1.put("key6","value6");
		
		Assert.assertEquals(cache1.get("key1"),"value1");
		Assert.assertEquals(cache1.get("key2"),"value2");
		Assert.assertEquals(cache1.get("key3"),"value3");
		Assert.assertEquals(cache1.get("key4"),"value4");
		Assert.assertEquals(cache1.get("key5"),"value5");
		Assert.assertEquals(cache1.get("key6"),"value6");
	}
	
	@Test
	public void testCluster()
	{
		try
		{
			IMemcachedCache cache = manager.getCache("mclient0");
			IMemcachedCache cache_bck = manager.getCache("mclient0-bck");
			
					
			cache.put("key1","value1");
			cache.put("key2","value2");
			cache.put("key3","value3");
			cache.put("key4","value4");
			cache.put("key5","value5");
			cache.put("key6","value6");

			Thread.sleep(1000);
			
			Assert.assertEquals(cache_bck.get("key1"),"value1");
			Assert.assertEquals(cache_bck.get("key2"),"value2");
			Assert.assertEquals(cache_bck.get("key3"),"value3");
			Assert.assertEquals(cache_bck.get("key4"),"value4");
			Assert.assertEquals(cache_bck.get("key5"),"value5");
			Assert.assertEquals(cache_bck.get("key6"),"value6");
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			//AuditorFactory.getInstance().getVrwsAuditor().iWarn(this, "testClusterCopy", "", ex);
		}
	}
	
}
