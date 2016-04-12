/**
 * 
 */
package com.anz.common.cache.jcache;

import java.net.URI;
import java.util.Properties;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.OptionalFeature;


/**
 * @author sanketsw
 *
 */
public abstract class JCacheManager implements CacheManager {

	/**
	 * @param jCacheProvider
	 * @param classLoader
	 * @param uri
	 * @param properties
	 */
	public abstract void initiate(JCacheCachingProvider jCacheProvider, ClassLoader classLoader, URI uri,
			Properties properties);


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getCache(java.lang.String, java.lang.Class, java.lang.Class)
	 */
	public <K, V> Cache<K, V> getCache(String arg0, Class<K> arg1, Class<V> arg2) {
		throw new UnsupportedOperationException("Not applicable");
	}

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getCacheNames()
	 */
	public Iterable<String> getCacheNames() {
		throw new UnsupportedOperationException("Not applicable");
	}
	

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#isClosed()
	 */
	public boolean isClosed() {
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#unwrap(java.lang.Class)
	 */
	public <T> T unwrap(Class<T> arg0) {
		throw new UnsupportedOperationException("Not applicable");
	}
	
	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#createCache(java.lang.String, C)
	 */
	public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(
			String cacheName, C configuration) throws IllegalArgumentException {
		throw new UnsupportedOperationException("Not applicable");		
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getCache(java.lang.String)
	 */
	public <K, V> Cache<K, V> getCache(String cacheName) {
		throw new UnsupportedOperationException("Not applicable");		
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#destroyCache(java.lang.String)
	 */
	public void destroyCache(String cacheName) {
		throw new UnsupportedOperationException("Not applicable");		
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#enableManagement(java.lang.String, boolean)
	 */
	public void enableManagement(String cacheName, boolean enabled) {
		throw new UnsupportedOperationException("Not applicable");		
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#enableStatistics(java.lang.String, boolean)
	 */
	public void enableStatistics(String cacheName, boolean enabled) {
		throw new UnsupportedOperationException("Not applicable");		
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#close()
	 */
	public void close() {
		throw new UnsupportedOperationException("Not applicable");		
	}

}
