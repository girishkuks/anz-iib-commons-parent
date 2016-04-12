package com.anz.common.cache.impl;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.cache.Cache;
import javax.cache.spi.CachingProvider;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.jcache.JCacheCachingProvider;
import com.anz.common.cache.jcache.JCacheManager;
import com.ibm.broker.plugin.MbException;

public class EXtremeScaleCacheManager extends JCacheManager {
	
	private static final Logger logger = LogManager.getLogger();

	public static final String URI = EXtremeScaleCacheManager.class.getName();

	private JCacheCachingProvider jCacheProvider;
	private ClassLoader classLoader;
	private URI uri;
	private Properties properties;
	@SuppressWarnings("rawtypes")
	private final ConcurrentHashMap<String, Cache> allCaches = new ConcurrentHashMap<String, Cache>();

	
	

	/**
	 * @return the allCaches
	 */
	public ConcurrentHashMap<String, Cache> getAllCaches() {
		return allCaches;
	}


	/* (non-Javadoc)
	 * @see com.anz.common.cache.impl.JCacheManager#getCache(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Cache<String, String> getCache(String cacheName) {
		Cache<String, String> cache = allCaches.get(cacheName);
        if(cache == null) {
            try {
				cache = new EXtremeScaleCache<String, String>(cacheName, this);
				allCaches.put(cacheName, cache);
			} catch (MbException e) {
				logger.catching(Level.WARN, e);
			}
        }
        return cache;
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getCachingProvider()
	 */
	public CachingProvider getCachingProvider() {
		return jCacheProvider;
	}

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getClassLoader()
	 */
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getProperties()
	 */
	public Properties getProperties() {
		return properties;
	}

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getURI()
	 */
	public URI getURI() {
		return uri;
	}


	/* (non-Javadoc)
	 * @see com.anz.common.cache.impl.JCacheManager#initiate(com.anz.common.cache.impl.JCacheProvider, java.lang.ClassLoader, java.net.URI, java.util.Properties)
	 */
	public void initiate(JCacheCachingProvider jCacheProvider, ClassLoader classLoader, URI uri,
			Properties properties) {
		this.jCacheProvider = jCacheProvider;
		this.classLoader = classLoader;
		this.uri = uri;
		this.properties = properties;
		
	}




}
