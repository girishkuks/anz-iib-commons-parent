/**
 * 
 */
package com.anz.common.cache.data;

import com.anz.common.cache.ICacheDataSource;
import com.anz.common.cache.bean.CachePojoSample;
import com.anz.common.cache.impl.CacheDataSourceManager;
import com.anz.common.cache.impl.CacheableObject;

/**
 * Configure the data source from where the cache objects should be read from
 * @author sanketsw
 *
 */
public class DataSourceSample implements ICacheDataSource {
	
	/* (non-Javadoc)
	 * @see com.anz.common.cache.ICacheDataSourceManager#getObjectFromSource(java.lang.String, java.lang.String, java.lang.Class)
	 */
	public CacheableObject getObjectFromSource(String map, String key,
			Class cacheablePojoClass) {
		return new CachePojoSample(key, "IIB REST API implementation");
	}

}
