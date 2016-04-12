/**
 * 
 */
package com.anz.common.cache.data;

import com.anz.common.cache.ICacheDataSource;
import com.anz.common.cache.ICachePojo;
import com.anz.common.cache.bean.CachePojoSample;

/**
 * Configure the data source from where the cache objects should be read from
 * @author sanketsw
 *
 */
public class StaticInMemoryDataSource implements ICacheDataSource {


	@SuppressWarnings("rawtypes")
	public ICachePojo getObjectFromSource(String key,
			Class cacheablePojoClass) {
		return new CachePojoSample(key, "IIB REST API implementation");
	}

}
