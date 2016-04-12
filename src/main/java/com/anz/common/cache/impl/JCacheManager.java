package com.anz.common.cache.impl;

import java.net.URI;
import java.util.Properties;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;

public class JCacheManager implements CacheManager {

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(
			String arg0, C arg1) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	public void destroyCache(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void enableManagement(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	public void enableStatistics(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	public <K, V> Cache<K, V> getCache(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public <K, V> Cache<K, V> getCache(String arg0, Class<K> arg1, Class<V> arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getCacheNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public CachingProvider getCachingProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public URI getURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
