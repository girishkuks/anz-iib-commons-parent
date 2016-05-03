/**
 * 
 */
package com.anz.common.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.ICacheDomainObject;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.dataaccess.daos.IIFXCodeDao;
import com.anz.common.dataaccess.models.iib.IFXCode;
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
public class IFXCodeDomain implements ICacheDomainObject {

	private static final Logger logger = LogManager.getLogger();

	private static IFXCodeDomain _inst = null;

	private CacheHandlerFactory cacheHandler = CacheHandlerFactory
			.getInstance();

	private IFXCodeDomain() {
	}

	public static IFXCodeDomain getInstance() {
		if (_inst == null) {
			_inst = new IFXCodeDomain();
		}
		return _inst;
	}

	/**
	 * Get the error code details from the cache or static database
	 * 
	 * @param key
	 * @return
	 */
	public IFXCode getErrorCode(String key) {

		String json = null;
		IFXCode errorCode = null;

		json = cacheHandler.lookupCache(getDefaultCacheName(), key);

		if (json != null) {
			errorCode = TransformUtils.fromJSON(json, IFXCode.class);
		} else {	

			// Read from JPA/Database and map to cacheable pojo
			IIoCFactory factory;
			try {
				factory = AnzSpringIoCFactory.getInstance();
				IIFXCodeDao dao = factory.getBean(IIFXCodeDao.class);
				logger.info("operationDao: {}", dao);
				errorCode = dao.findOne(key);
				if(errorCode == null) {
					// Create a new one 
					// database is empty
					IFXCode operation2 = new IFXCode();
					operation2.setCode(key);
					operation2.setDescr("Error conencting to the system. Please try again later.");
					operation2.setSeverity(IFXCode.SEV_CRITICAL);
					operation2.setStatus(IFXCode.STATUS_FAILURE);
					errorCode = dao.saveAndFlush(operation2);
					logger.info("created new operation: {}", errorCode.getKey());
				}
				logger.info("got value in operationDao from data source: {}", errorCode.getKey());
				
			} catch (Exception e) {
				logger.warn("Could not read from data source");
				logger.throwing(e);
			}

			if(errorCode == null) {
				cacheHandler.updateCache(getDefaultCacheName(), key,
						TransformUtils.toJSON(errorCode));
			}

		}

		return errorCode;
	}

	public String getDefaultCacheName() {
		return "LookupCache";
	}

	
}
