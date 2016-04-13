package com.anz.common.cache.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.net.URISyntaxException;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import junit.framework.TestCase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * @author sanketsw
 * 
 */
public class JCacheCachingProviderTest extends TestCase {

	CachingProvider provider;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		if(System.getProperty("javax.cache.spi.CachingProvider") == null) {
			logger.warn("System property javax.cache.spi.CachingProvider is not set. Setting it to com.anz.common.cache.jcache.JCacheCachingProvider...");
			System.setProperty("javax.cache.spi.CachingProvider","com.anz.common.cache.jcache.JCacheCachingProvider");
		}
		provider = Caching.getCachingProvider();
	}

	private static final Logger logger = LogManager.getLogger();

	@Test
	public void testGetCacheManager() throws URISyntaxException {
		CacheManager cacheManager = provider.getCacheManager(new URI(
				InMemoryCacheManager.URI), null);
		logger.info(cacheManager);
		assertNotNull(cacheManager);
	}

	@Test
	public void testGetCachingProvider() {
		logger.info(provider);
		assertNotNull(provider);
	}

	@Test
	public void testCache() throws URISyntaxException {
		CacheManager cacheManager = provider.getCacheManager(new URI(
				InMemoryCacheManager.URI), null);
		Cache<String, String> cache = cacheManager.getCache("testCache");
		logger.info(cache);
		assertNotNull(cache);
	}

	@Test
	public void testElementsInCache() throws URISyntaxException {
		CacheManager cacheManager = provider.getCacheManager(new URI(
				InMemoryCacheManager.URI), null);
		Cache<String, String> cache = cacheManager.getCache("testCache");

		assertNull(cache.get("testElem"));

		cache.put("testElem", "Testing elements in cache");
		assertEquals("Testing elements in cache", cache.get("testElem"));

		cache.remove("testElem");
		assertNull(cache.get("testElem"));

	}

}
