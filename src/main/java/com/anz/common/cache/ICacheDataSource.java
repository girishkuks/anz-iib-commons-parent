/**
 * 
 */
package com.anz.common.cache;


/**
 * @author sanketsw
 *
 */
public interface ICacheDataSource {
	
	/**
	 * Get the cacheable object from the data source such as static files or database
	 * @param key
	 * @param cacheablePojoClass
	 * @return cacheable object pojo instance
	 */
	@SuppressWarnings("rawtypes")
	public ICachePojo getObjectFromSource(String key, Class cacheablePojoClass);

}
