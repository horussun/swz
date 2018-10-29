package swz.infra.tools.cache;

import org.apache.log4j.Logger;

import swz.infra.tools.cache.MemCachedFactory;

public class CachedHelper {
	private static final Logger logger = Logger.getLogger(CachedHelper.class);

	public static void init() {
		logger.info("start memcached");
		MemCachedFactory.init("mclient1");
	}

	public static boolean isMemCachedStart() {
		return MemCachedFactory.getCache() == null ? false : true;
	}
	
	public static Object getValue(String key) {
		return MemCachedFactory.getCache().get(key);
	}
	
	public static void putValue(String key,Object value) {
		MemCachedFactory.getCache().put(key, value);
	}
	
	public static void removeValue(String key) {
		MemCachedFactory.getCache().remove(key);
	}

}
