/**
 * 
 */
package com.anz.common.cache.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.anz.common.cache.bean.CachePojoSample;

/**
 * @author root
 *
 */
public class CacheDataSourceManagerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.anz.common.cache.impl.CacheDataSourceManager#getCacheDataSource(java.lang.Class)}.
	 */
	@Test
	public void testGetCacheDataSourceManager() {
		assertNotNull(CacheDataSourceManager.getCacheDataSource(CachePojoSample.class));
		assertNull(CacheDataSourceManager.getCacheDataSource(AbstractCacheManager.class));
	}

}
