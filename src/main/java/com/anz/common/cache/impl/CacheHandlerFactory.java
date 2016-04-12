/**
 * 
 */
package com.anz.common.cache.impl;

import java.net.URI;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.ICacheDataSource;
import com.anz.common.cache.data.StaticInMemoryDataSource;
import com.anz.common.cache.jcache.JCacheCachingProvider;
import com.google.gson.Gson;

/**
 * @author root
 * @param <K>
 * 
 */
public class CacheHandlerFactory {

	private static final Logger logger = LogManager.getLogger();

	private static CacheHandlerFactory _inst = null;

	private CacheHandlerFactory() {
	}

	public static CacheHandlerFactory getInstance() {
		if (_inst == null) {
			_inst = new CacheHandlerFactory();
		}
		return _inst;
	}
	
	/**
	 * Get from the cache configured here 
	 * this mehtod is for In memeory Cache Manager implemented using JCache
	 * 
	 * @param cacheName
	 * @param objectKey
	 * @param cachePojoClass
	 * @param b
	 * @return
	 */
	public <K> K lookupIIBCache(String cacheName, String objectKey,
			Class<K> cachePojoClass) {

		String cacheManagerURI = EXtremeScaleCacheManager.URI;
		logger.info(cacheManagerURI);
		ICacheDataSource dataSource = StaticInMemoryDataSource.getInstance();

		return lookupCache(cacheName, objectKey, cachePojoClass,
				cacheManagerURI, dataSource);
	}

	/**
	 * Get from the cache configured here 
	 * this mehtod is for In memeory Cache Manager implemented using JCache
	 * 
	 * @param cacheName
	 * @param objectKey
	 * @param cachePojoClass
	 * @param b
	 * @return
	 */
	public <K> K lookupInMemoryCache(String cacheName, String objectKey,
			Class<K> cachePojoClass) {

		String cacheManagerURI = InMemoryCacheManager.URI;
		logger.info(cacheManagerURI);
		ICacheDataSource dataSource = StaticInMemoryDataSource.getInstance();

		return lookupCache(cacheName, objectKey, cachePojoClass,
				cacheManagerURI, dataSource);
	}

	/**
	 * Generic method to handle cachemanager and datasource
	 * @param cacheName
	 * @param objectKey
	 * @param cachePojoClass
	 * @param cacheManagerURI
	 * @param dataSource
	 * @return
	 */
	public <K> K lookupCache(String cacheName, String objectKey,
			Class<K> cachePojoClass, String cacheManagerURI,
			ICacheDataSource dataSource) {
		K cachePojo = null;
		String json = null;
		try {
			CachingProvider provider = Caching.getCachingProvider(JCacheCachingProvider.class.getName());
			CacheManager cacheManager = provider.getCacheManager(new URI(
					cacheManagerURI), null);
			Cache<String, String> cache = cacheManager.getCache(cacheName);

			if (cache.containsKey(objectKey)) {
				json = cache.get(objectKey);
			} else {
				json = dataSource.get(objectKey);
				cache.put(objectKey, json);
			}
			cachePojo = (K) (json != null ? new Gson().fromJson(json,
					cachePojoClass) : null);
		} catch (Exception e) {
			logger.catching(Level.WARN, e);
		}
		return cachePojo;
	}

}
