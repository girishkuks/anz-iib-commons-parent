/**
 * 
 */
package com.anz.common.cache.domain;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.ICacheDomainObject;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.cache.pojo.CachePojoSample;
import com.anz.common.transform.TransformUtils;

/**
 * Domain class responsible for reading from cache or database as required.
 * Configure the data source from where the cache objects should be read from
 * 
 * Domain method flow:
 * Lookup Cache
 * get from database if not in cache
 * Store to cache
 * Return
 * 
 * @author sanketsw
 * 
 */
public class CachePojoSampleDomain implements ICacheDomainObject {

	private static final Logger logger = LogManager.getLogger();

	private static CachePojoSampleDomain _inst = null;

	private CacheHandlerFactory cacheHandler = CacheHandlerFactory
			.getInstance();

	private CachePojoSampleDomain() {
	}

	public static CachePojoSampleDomain getInstance() {
		if (_inst == null) {
			_inst = new CachePojoSampleDomain();
		}
		return _inst;
	}

	/**
	 * Get the operation details from the cache or static database
	 * @param key
	 * @return
	 */
	public CachePojoSample getOperation(String key) {

		String json = null;
		CachePojoSample operation = null;
		try {
			json = cacheHandler.lookupCache(getDefaultCacheName(), key);
		} catch (Exception e) {
			logger.error(Level.WARN, e);
		} catch (NoClassDefFoundError e) {
			logger.error(Level.WARN, e);
		}

		if (json != null) {
			operation = TransformUtils.fromJSON(json, CachePojoSample.class);
		} else {
			// TODO Read from JPA/Database
			operation = new CachePojoSample(key, "IIB REST API implementation");
			try {
				cacheHandler.updateCache(getDefaultCacheName(), key,
						operation.toJSON());
			} catch (Exception e) {
				logger.error(Level.WARN, e);
			} catch (NoClassDefFoundError e) {
				logger.error(Level.WARN, e);
			}
		}

		return operation;
	}

	public String getDefaultCacheName() {
		return "SamplePojoCache";
	}

}
