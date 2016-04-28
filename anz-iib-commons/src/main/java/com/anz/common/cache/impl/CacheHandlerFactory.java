/**
 * 
 */
package com.anz.common.cache.impl;

import javax.cache.Cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles global and local cache Cache Handler Factory -> Cache Handler ->
 * Caching Provider -> Cache Manager -> Cache
 * 
 * @author sanketsw
 * 
 */
public class CacheHandlerFactory {

	private static final Logger logger = LogManager.getLogger();

	private static CacheHandlerFactory _inst = null;

	/**
	 * private constructor of singleton
	 */
	private CacheHandlerFactory() {
	}

	/**
	 * @return access to singleton instance
	 */
	public static CacheHandlerFactory getInstance() {
		if (_inst == null) {
			_inst = new CacheHandlerFactory();
		}
		return _inst;
	}

	/**
	 * @param cacheName
	 * @param objectKey
	 * @return get the object from local or global cache
	 * @throws Exception
	 */
	public String lookupCache(String cacheName, String objectKey) {

		try {

			Cache<String, String> localCache = LocalCacheHandler.getInstance()
					.getCache(cacheName);
			String cachedObject = localCache != null ? localCache
					.get(objectKey) : null;

			if (cachedObject != null) {
				logger.info("found in the local cache: {} objectKey: {}",
						cacheName, objectKey);
				return cachedObject;
			}

			Cache<String, String> globalCache = GlobalCacheHandler
					.getInstance().getCache(cacheName);
			String globalCachedObject = globalCache != null ? globalCache
					.get(objectKey) : null;

			if (globalCachedObject != null) {
				logger.info("found in the global cache: {} objectKey: {}",
						cacheName, objectKey);
				localCache.put(objectKey, globalCachedObject);
				return globalCachedObject;
			}
		} catch (Exception e) {
			logger.warn(e);
		}
		logger.info("Not found in any cache: {} objectKey: {}", cacheName,
				objectKey);
		return null;
	}

	/**
	 * @param cacheName
	 * @param objectKey
	 * @param objectValue
	 * @throws Exception
	 */
	public void updateCache(String cacheName, String objectKey,
			String objectValue) {

		logger.info("updating local and global cache: {} objectKey: {}",
				cacheName, objectKey);
		try {

			Cache<String, String> localCache = LocalCacheHandler.getInstance()
					.getCache(cacheName);

			localCache.remove(objectKey);
			localCache.put(objectKey, objectValue);

			Cache<String, String> globalCache = GlobalCacheHandler
					.getInstance().getCache(cacheName);

			globalCache.remove(objectKey);
			globalCache.put(objectKey, objectValue);
		} catch (Exception e) {
			logger.warn(e);
		}

	}

}
