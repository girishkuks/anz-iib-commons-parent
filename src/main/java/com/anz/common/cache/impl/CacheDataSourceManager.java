/**
 * 
 */
package com.anz.common.cache.impl;

import com.anz.common.cache.ICacheDataSource;
import com.anz.common.cache.data.StaticInMemoryDataSource;
import com.anz.common.cache.pojo.CachePojoSample;

/**
 * Factory of the all the datasources and decides which one to call based on the cacheable object class
 * @author sanketsw
 *
 */
public class CacheDataSourceManager {

	/**
	 * Factory method to instantiate right data source for cache
	 * @param cacheablePojoClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static ICacheDataSource getCacheDataSource(Class cacheablePojoClass) {
		if(cacheablePojoClass.equals(CachePojoSample.class)) {
			return new StaticInMemoryDataSource();
		}
		return null;
	}


}
