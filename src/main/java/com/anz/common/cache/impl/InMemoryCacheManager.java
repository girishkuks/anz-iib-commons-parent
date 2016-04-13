/**
 * 
 */
package com.anz.common.cache.impl;

import javax.cache.Cache;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.jcache.JCache;
import com.anz.common.cache.jcache.JCacheManager;
import com.ibm.broker.plugin.MbException;

/**
 * @author sanketsw
 *
 */
public class InMemoryCacheManager extends JCacheManager {
	
	public static final String URI = InMemoryCacheManager.class.getName();
	
	private static final Logger logger = LogManager.getLogger();


	@SuppressWarnings("unchecked")
	public Cache<String, String> getCache(String cacheName) {
		Cache<String, String> cache = getAllCaches().get(cacheName);
        if(cache == null) {
            try {
				cache = new JCache<String, String>(cacheName, this);
				getAllCaches().put(cacheName, cache);
			} catch (MbException e) {
				logger.catching(Level.WARN, e);
			}
        }
        return cache;
	}

}
