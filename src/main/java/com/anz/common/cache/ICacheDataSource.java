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
	 * Get the JSON output of the query from the data source e.g. from static files or database
	 * @param <T>
	 * @param key
	 * @param cacheablePojoClass
	 * @return cacheable object pojo instance
	 */
	public String get(String key) throws Exception;
	
	/**
	 * Define the linked cache manager
	 * @return cache manager URI
	 */
	public String getCacheManagerURI();

}
