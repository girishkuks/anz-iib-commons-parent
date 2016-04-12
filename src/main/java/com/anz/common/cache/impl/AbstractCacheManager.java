/**
 * 
 */
package com.anz.common.cache.impl;

import com.anz.common.cache.ICacheDataSource;
import com.anz.common.cache.ICacheManager;
import com.anz.common.cache.ICachePojo;
import com.google.gson.Gson;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;
import com.ibm.broker.plugin.MbGlobalMapSessionPolicy;

/**
 * @author sanketsw
 * 
 */
public abstract class AbstractCacheManager implements ICacheManager {
	
	/**
	 * Get the cached object from the cache
	 * 
	 * @param map
	 * @param key
	 * @param cacheablePojoClass
	 * @param fromDatSourceIfNotCached
	 *            set to false if you want to query only from the cache
	 * @return CacheableObject (Cast it to cacheablePojoClass)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ICachePojo lookupCache(String map, String key,
			Class cacheablePojoClass, boolean fromDatSourceIfNotCached) {
		ICachePojo obj = null;
		MbGlobalMap globalMap = null;
		try {
			globalMap = getCacheMap(map);
			String json = (String) globalMap.get(key);
			obj = (ICachePojo) (json != null ? new Gson().fromJson(json,
					cacheablePojoClass) : null);
		} catch (Exception e) {
			// Log here
			;
			;
		}
		if (obj == null && globalMap != null && fromDatSourceIfNotCached) {
			try {
				ICacheDataSource datasource = getDataSource();
				if (datasource == null) {
					return obj;
				}
				obj = datasource.getObjectFromSource(key, cacheablePojoClass);
				globalMap.put(key, obj.toJSON());
			} catch (MbException e) {
				;
				;
			}
		}
		return obj;
	}

	/**
	 * Get the JSON String stored in the cache for the given key
	 * 
	 * @param map
	 * @param key
	 * @return JSON string
	 */
	public String retrieveFromCache(String map, String key) {
		try {
			MbGlobalMap globalMap = getCacheMap(map);
			String json = (String) globalMap.get(key);
			return json;
		} catch (Exception e) {
			// Log here
			;
			;
		}
		return null;
	}

	/**
	 * Add the JSON of the Pojo to the Cache
	 * 
	 * @param map
	 * @param pojo
	 */
	public void insertIntoCache(String map, ICachePojo pojo) {
		try {
			MbGlobalMap globalMap = getCacheMap(map);
			if (!globalMap.containsKey(pojo.getKey())) {
				globalMap.put(pojo.getKey(), pojo.toJSON());
			}
		} catch (Exception e) {
			// Log here
			;
			;
		}
	}

	/**
	 * Save the JSON String to the Cache
	 * 
	 * @param map
	 * @param key
	 * @param json
	 */
	public void insertIntoCache(String map, String key, String json) {
		try {
			MbGlobalMap globalMap = getCacheMap(map);
			if (!globalMap.containsKey(key)) {
				globalMap.put(key, json);
			}
		} catch (Exception e) {
			// Log here
			;
			;
		}
	}

	/**
	 * Get the Global cache map object
	 * 
	 * @param map
	 * @return
	 * @throws MbException
	 */
	public MbGlobalMap getCacheMap(String map) throws MbException {
		return MbGlobalMap.getGlobalMap(map, getCachePolicy());
	}

	/**
	 * Define or override cache timeout policy
	 * 
	 * @return cache session policy
	 */
	public MbGlobalMapSessionPolicy getCachePolicy() {
		return new MbGlobalMapSessionPolicy(getCacheTimeToLive());
	}
}