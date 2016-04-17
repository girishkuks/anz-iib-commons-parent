/**
 * 
 */
package com.anz.common.cache;



/**
 * Domain class responsible for reading from cache or database as required.
 * @author sanketsw
 *
 */
public interface ICacheDomainObject {
	
		
	/**
	 * Define the linked cache name e.g. ErrorCodeCache or ISOCodeCache
	 * @return cache manager URI
	 */
	public String getDefaultCacheName();

}
