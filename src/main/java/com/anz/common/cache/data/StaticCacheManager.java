/**
 * 
 */
package com.anz.common.cache.data;

import com.anz.common.cache.ICacheDataSource;
import com.anz.common.cache.impl.AbstractCacheManager;

/**
 * @author root
 *
 */
public class StaticCacheManager extends AbstractCacheManager {

	/* (non-Javadoc)
	 * @see com.anz.common.cache.ICacheManager#getDataSource()
	 */
	public ICacheDataSource getDataSource() {
		return new StaticInMemoryDataSource();
	}

	/* (non-Javadoc)
	 * @see com.anz.common.cache.ICacheManager#getCacheTimeToLive()
	 */
	public int getCacheTimeToLive() {
		return 20; // 20 seconds
	}

}
