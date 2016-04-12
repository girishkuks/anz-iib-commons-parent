/**
 * 
 */
package com.anz.common.cache.data;

import java.util.ArrayList;
import java.util.List;

import javax.cache.CacheManager;

import com.anz.common.cache.ICacheDataSource;
import com.anz.common.cache.ICachePojo;
import com.anz.common.cache.impl.InMemoryCacheManager;
import com.anz.common.cache.pojo.CachePojoSample;

/**
 * Configure the data source from where the cache objects should be read from
 * 
 * @author sanketsw
 * 
 */
public class StaticInMemoryDataSource implements ICacheDataSource {

	private static StaticInMemoryDataSource _inst = null;

	private StaticInMemoryDataSource() {
	}

	public static StaticInMemoryDataSource getInstance() {
		if (_inst == null) {
			_inst = new StaticInMemoryDataSource();
		}
		return _inst;
	}

	public String get(String key) {
		return new CachePojoSample(key, "IIB REST API implementation").toJSON();
	}

	public String getCacheManagerURI() {
		return InMemoryCacheManager.URI;
	}

}
