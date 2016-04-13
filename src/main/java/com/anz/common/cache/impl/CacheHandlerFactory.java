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
	 * Get from the cache configured here this mehtod is for In memeory Cache
	 * Manager implemented using JCache
	 * 
	 * @param cacheName
	 * @param objectKey
	 * @param cachePojoClass
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public <K> K lookupIIBCache(String cacheName, String objectKey,
			Class<K> cachePojoClass) throws Exception {

		String cacheManagerURI = EXtremeScaleCacheManager.URI;
		ICacheDataSource dataSource = StaticInMemoryDataSource.getInstance();

		return lookupCache(cacheName, objectKey, cachePojoClass,
				cacheManagerURI, dataSource);
	}

	/**
	 * Get from the cache configured here this mehtod is for In memeory Cache
	 * Manager implemented using JCache
	 * 
	 * @param cacheName
	 * @param objectKey
	 * @param cachePojoClass
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public <K> K lookupInMemoryCache(String cacheName, String objectKey,
			Class<K> cachePojoClass) throws Exception {

		String cacheManagerURI = InMemoryCacheManager.URI;
		ICacheDataSource dataSource = StaticInMemoryDataSource.getInstance();

		logger.info("Using cacheManager={} and dataSource={}", cacheManagerURI,
				dataSource);

		return lookupCache(cacheName, objectKey, cachePojoClass,
				cacheManagerURI, dataSource);
	}

	/**
	 * Generic method to handle cachemanager and datasource
	 * 
	 * @param cacheName
	 * @param objectKey
	 * @param cachePojoClass
	 * @param cacheManagerURI
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	public <K> K lookupCache(String cacheName, String objectKey,
			Class<K> cachePojoClass, String cacheManagerURI,
			ICacheDataSource dataSource) throws Exception {
		String json = null;
		Cache<String, String> cache = null;
		try {

			/*if (System.getProperty("javax.cache.spi.CachingProvider") == null) {
				logger.warn("System property javax.cache.spi.CachingProvider is not set. Setting it to com.anz.common.cache.jcache.JCacheCachingProvider...");
				System.setProperty("javax.cache.spi.CachingProvider",
						"com.anz.common.cache.jcache.JCacheCachingProvider");
			}*/

			CachingProvider provider = Caching.getCachingProvider();
			if (provider == null) {
				// If the default caching provider is not set in a system property then use the default one
				// To change default caching provider, set System property javax.cache.spi.CachingProvider
				provider = Caching.getCachingProvider(JCacheCachingProvider.class.getName());
			}
			if (provider == null) {
				logger.warn("No CachingProvider has been configured");
			} else {

				CacheManager cacheManager = provider.getCacheManager(new URI(
						cacheManagerURI), null);
				if (cacheManager == null) {
					logger.warn("CacheManagr {} has not been configured",
							cacheManagerURI);
				} else {

					cache = cacheManager.getCache(cacheName);
					json = cache.get(objectKey);
					logger.info("Data from Cache= {}", json);
				}
			}
		} catch (Exception e) {
			logger.catching(Level.WARN, e);
			// Exception in Cache Manager are not fatal.
		}

		if (json == null) {
			json = dataSource.get(objectKey);
			if (cache != null) {
				cache.put(objectKey, json);
			}
		}
		K cachePojo = (json != null ? new Gson().fromJson(json, cachePojoClass)
				: null);

		return cachePojo;
	}

}
