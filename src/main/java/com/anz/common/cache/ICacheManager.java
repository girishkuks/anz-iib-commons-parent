/**
 * 
 */
package com.anz.common.cache;

/**
 * @author sanketsw
 *
 */
public interface ICacheManager {
	
	/**
	 * Define the Backend data source for this cacheManager
	 * Could be a database connection or reading from filessystem 
	 * @return
	 */
	public ICacheDataSource getDataSource();
	
	/**
	 * Define cached object's time to live
	 * @return time in seconds
	 */
	public int getCacheTimeToLive();
	

}
