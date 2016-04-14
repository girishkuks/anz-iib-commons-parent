/**
 * 
 */
package com.anz.common.cache.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.jcache.JCacheCachingProvider;

/**
 * eXtreme Scale Cache Handler in JCashe JSR107 standard API Cache Handler
 * Factory -> Cache Handler -> Caching Provider -> Cache Manager -> Cache
 * 
 * @author sanketsw
 * 
 */
public class GlobalCacheHandler extends AbstractCacheHandler {

	private static final Logger logger = LogManager.getLogger();

	private static GlobalCacheHandler _inst = null;

	private GlobalCacheHandler() throws Exception {
		super();
	}

	public static GlobalCacheHandler getInstance() throws Exception {
		if (_inst == null) {
			_inst = new GlobalCacheHandler();

		}
		return _inst;
	}

	@Override
	public String getDefaultCacheName() {
		return "DefaultMap";
	}

	@Override
	public String getCachingProviderName() {
		return JCacheCachingProvider.class.getName();
	}

	@Override
	public String getCacheManagerURI() {
		return null;
	}

}
