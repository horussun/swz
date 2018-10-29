package swz.infra.tools.cache;

import java.util.HashMap;

import swz.infra.tools.cache.memcached.CacheUtil;
import swz.infra.tools.cache.memcached.MemcachedCacheManager;

public class MemCachedFactory {
	private static ICacheManager<IMemcachedCache> m_manager;
	private static IMemcachedCache m_cache;

	public static IMemcachedCache getCache() {
		return m_cache;
	}

	/**
	 * 系统初始化时候调用一下！ 其他不需要调用，因为整个应用只需要一个cache实例即可
	 * 
	 * @param cacheClient
	 *            传入 client or cluster name
	 */
	@SuppressWarnings("unchecked")
	public static void init(String cacheClient) {
		if (m_cache == null || m_manager == null) {
			m_manager = CacheUtil.getCacheManager(IMemcachedCache.class, MemcachedCacheManager.class.getName());
			// manager.setConfigFile("memcached1.xml");// 可以指定配置文件名
			m_manager.start();
			try {
				if (cacheClient == null || cacheClient.length() == 0) {
					cacheClient = "mclient1";
				}
				m_cache = m_manager.getCache(cacheClient);
				HashMap<String, String> valueMap = new HashMap<String, String>();
				valueMap.put("1", "memcached is ready!");
				m_cache.put("key", valueMap);
			} finally {
			}
		}
	}

	/**
	 * 处理消亡时候Cache管理器停止
	 */
	protected void finalize() throws Throwable {
		super.finalize();
		m_manager.stop();
	}

}
