package com.anz.common.cache.impl;

import java.net.URI;
import java.util.Properties;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;

public class JCacheProvider implements CachingProvider  {

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public void close(ClassLoader arg0) {
		// TODO Auto-generated method stub
		
	}

	public void close(URI arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub
		
	}

	public CacheManager getCacheManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public CacheManager getCacheManager(URI arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public CacheManager getCacheManager(URI arg0, ClassLoader arg1,
			Properties arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassLoader getDefaultClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getDefaultProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public URI getDefaultURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSupported(OptionalFeature arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
