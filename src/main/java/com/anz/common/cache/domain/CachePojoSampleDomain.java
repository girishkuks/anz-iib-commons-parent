/**
 * 
 */
package com.anz.common.cache.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.ICacheDomainObject;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.cache.pojo.CachePojoSample;
import com.anz.common.dataaccess.daos.ILookupDao;
import com.anz.common.dataaccess.models.iib.Lookup;
import com.anz.common.ioc.IIoCFactory;
import com.anz.common.ioc.spring.AnzSpringIoCFactory;
import com.anz.common.transform.TransformUtils;

/**
 * Domain class responsible for reading from cache or database as required.
 * Configure the data source from where the cache objects should be read from
 * 
 * Domain method flow: Lookup Cache get from database if not in cache Store to
 * cache Return
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
	 * 
	 * @param key
	 * @return
	 */
	public CachePojoSample getOperation(String key) {

		String json = null;
		CachePojoSample operation = null;

		json = cacheHandler.lookupCache(getDefaultCacheName(), key);

		if (json != null) {
			operation = TransformUtils.fromJSON(json, CachePojoSample.class);
		} else {
			operation = new CachePojoSample(key, "IIB REST API implementation");			

			// Read from JPA/Database and map to cacheable pojo
			IIoCFactory factory;
			try {
				factory = AnzSpringIoCFactory.getInstance();
				ILookupDao lookupDao = factory.getBean(ILookupDao.class);
				logger.info("lookupDao {}", lookupDao);
				Lookup l1 = lookupDao.findOne("Australia");
				logger.info("got value from lookupDao {}", l1.getValue());
				operation.setIsoCode("Country=" + l1.getName() + " ISO Code=" + l1.getValue());
			} catch (Exception e) {
				logger.error("Could not read from daat source");
				logger.throwing(e);
			}

			cacheHandler.updateCache(getDefaultCacheName(), key,
					operation.toJSON());

		}

		return operation;
	}

	public String getDefaultCacheName() {
		return "SamplePojoCache";
	}

}
