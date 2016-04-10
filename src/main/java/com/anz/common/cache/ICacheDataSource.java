/**
 * 
 */
package com.anz.common.cache;

import com.anz.common.cache.impl.CacheableObject;

/**
 * @author sanketsw
 *
 */
public interface ICacheDataSource {
	
	/**
	 * Get the cacheable object from the data source such as static files or database
	 * @param map
	 * @param key
	 * @param cacheablePojoClass
	 * @return cacheable object pojo instance
	 */
	public CacheableObject getObjectFromSource(String map, String key,
			Class<CacheableObject> cacheablePojoClass);

}
