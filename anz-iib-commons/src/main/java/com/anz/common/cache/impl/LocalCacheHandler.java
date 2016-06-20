/**
 * 
 */
package com.anz.common.cache.impl;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;

import javax.cache.Cache;
import javax.cache.configuration.MutableConfiguration;
import javax.management.MBeanServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ehcache.jcache.JCacheManager;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.management.ManagementService;

/**
 * ehCache Cache Handler in JCashe JSR107 standard API
 * Cache Handler Factory -> Cache Handler -> Caching Provider -> Cache Manager -> Cache
 * @author sanketsw
 * 
 */
public class LocalCacheHandler extends AbstractCacheHandler {

	private static final Logger logger = LogManager.getLogger();

	private static LocalCacheHandler _inst = null;

	

	private LocalCacheHandler() throws Exception {
		super();
	}

	public static LocalCacheHandler getInstance() throws Exception {
		if (_inst == null) {
			
				_inst = new LocalCacheHandler();
		}
		return _inst;
	}


	@Override
	public String getDefaultCacheName() {
		return "DefaultMap";
	}

	@Override
	public String getCachingProviderName() {
		return "org.ehcache.jcache.JCacheCachingProvider";
	}

	/* (non-Javadoc)
	 * @see com.anz.common.cache.impl.AbstractCacheHandler#getCache(java.lang.String)
	 */
	@Override
	public Cache<String, String> getCache(String cacheName) throws CacheException, Exception {
		Cache<String, String> cache = null;
		
		try {
			
			logger.debug("Retriving cache {}", cacheName);
			cache = cacheManager.getCache(cacheName);	
			
		} catch(Exception e) {
			logger.debug("Retriving cache using type classes {}", cacheName);
			try {
				cache = cacheManager.getCache(cacheName, String.class, String.class);
			}catch(Exception e2) {
				logger.throwing(e2);
			}
		}
		if (cache == null) {
			logger.debug("Starting cache {}", cacheName);
			MutableConfiguration<String, String> jcacheConfig = new MutableConfiguration<String, String>();
			jcacheConfig.setTypes(String.class, String.class);			
			cache = cacheManager.createCache(cacheName, jcacheConfig);
		}
		return cache;
	}
	
	/* (non-Javadoc)
	 * @see com.anz.common.cache.impl.AbstractCacheHandler#getCacheManager()
	 */
	@Override
	public javax.cache.CacheManager getCacheManager() throws Exception {
		javax.cache.CacheManager ret =  super.getCacheManager();

		try {
			// Register for JMX management
			JCacheManager ehCacheManager = (JCacheManager)ret;
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ehCacheManager.getEhCacheNativeCacheManager().setName("LocalCacheManager");
			logger.info("printing mBeanServer {}", mBeanServer);
			logger.info("printing EhCacheNativeCacheManager {}", ehCacheManager.getEhCacheNativeCacheManager());
			ManagementService.registerMBeans(ehCacheManager.getEhCacheNativeCacheManager(), mBeanServer, true, true, true, true);
		}catch(Exception e) {
			logger.info("net.sf.ehcache:type=CacheManager,name=LocalCacheManager is already registered for JMX management. Ignoring...");
			logger.info(e.getMessage());
		}
		
		return ret;
	}

	@Override
	public String getCacheManagerURI() {
		String path =  System.getenv("CACHE_CONFIG");
		logger.info("System property CACHE_CONFIG={}",path);
		File configFile = new File(path + "/" + "ehcache-localcache.xml");
		if(configFile.exists()) {
			try {
				return configFile.toURI().toURL().toString();
			} catch (MalformedURLException e) {
				logger.throwing(e);
			}
		} else {
			URL resource = LocalCacheHandler.class.getResource("ehcache-localcache.xml");
			if(resource != null) {
				logger.warn("Loading a backup config file={}",resource);
	            return resource.toString();
			}
		}
		logger.warn("Could not load the resource {}", "ehcache-localcache.xml");        
        return null;
	}
	
	
	
	
	
	

}
