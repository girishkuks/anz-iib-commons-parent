/**
 * 
 */
package com.anz.common.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.ICacheDomainObject;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.dataaccess.daos.IErrorStatusCodeDao;
import com.anz.common.dataaccess.models.iib.ErrorStatusCode;
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
public class ErrorStatusCodeDomain implements ICacheDomainObject {

	private static final Logger logger = LogManager.getLogger();

	private static ErrorStatusCodeDomain _inst = null;
	
	private IErrorStatusCodeDao dao;

	private CacheHandlerFactory cacheHandler = CacheHandlerFactory
			.getInstance();

	private ErrorStatusCodeDomain() {
		try {
			IIoCFactory factory = AnzSpringIoCFactory.getInstance();
			dao = factory.getBean(IErrorStatusCodeDao.class);
			if(dao == null) {
				throw new Exception("Could not instantiate DAO class");
			}
			logger.info("operationDao: {}", dao);
			
			// Create Some error objects in the database
			ErrorStatusCode errorCode = dao.findOne("TimeoutException");
			if(errorCode == null) {
				// Create a new one 
				ErrorStatusCode operation2 = new ErrorStatusCode();
				operation2.setException("TimeoutException");
				operation2.setCode("300");
				operation2.setDescr("Timeout Exception occured while trying to connect");
				operation2.setSeverity(ErrorStatusCode.SEV_CRITICAL);
				errorCode = dao.saveAndFlush(operation2);
				logger.info("created new error status code: {}", errorCode.getKey());
			}
			
			errorCode = dao.findOne("InternalException");
			if(errorCode == null) {
				// Create a new one 
				ErrorStatusCode operation2 = new ErrorStatusCode();
				operation2.setException("InternalException");
				operation2.setCode("100");
				operation2.setDescr("Internal Server Error");
				operation2.setSeverity(ErrorStatusCode.SEV_CRITICAL);
				errorCode = dao.saveAndFlush(operation2);
				logger.info("created new error status code: {}", errorCode.getKey());
			}
		} catch(Exception e) {
			logger.warn(e);
		}
		
		
		
		
	}

	public static ErrorStatusCodeDomain getInstance() throws Exception {
		if (_inst == null) {
			_inst = new ErrorStatusCodeDomain();
		}
		return _inst;
	}

	/**
	 * Get the error code details from the cache or static database
	 * 
	 * @param key
	 * @return
	 */
	public ErrorStatusCode getErrorCode(String key) {

		String json = null;
		ErrorStatusCode errorCode = null;

		json = cacheHandler.lookupCache(getDefaultCacheName(), key);

		if (json != null) {
			errorCode = TransformUtils.fromJSON(json, ErrorStatusCode.class);
		} else {	

			// Read from JPA/Database and map to cacheable pojo
			IIoCFactory factory;
			try {
				factory = AnzSpringIoCFactory.getInstance();
				IErrorStatusCodeDao dao = factory.getBean(IErrorStatusCodeDao.class);
				logger.info("operationDao: {}", dao);
				errorCode = dao.findOne(key);
				if(errorCode == null && ! key.equalsIgnoreCase("InternalException")) {
					//Return the default error code
					getErrorCode("InternalException");
				}
				logger.info("got value in operationDao from data source: {}", errorCode.getKey());
				
			} catch (Exception e) {
				logger.warn("Could not read from data source");
				logger.throwing(e);
			}

			if(errorCode != null) {
				cacheHandler.updateCache(getDefaultCacheName(), key, TransformUtils.toJSON(errorCode));
			}
					

		}

		return errorCode;
	}

	public String getDefaultCacheName() {
		return "LookupCache";
	}

	
}
