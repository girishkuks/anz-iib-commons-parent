/**
 * 
 */
package com.anz.common.cache.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.anz.common.cache.domain.CachePojoSampleDomain;
import com.anz.common.cache.pojo.CachePojoSample;
import com.anz.common.transform.TransformUtils;

/**
 * @author root
 * 
 */
public class DataSourceSampleTest {

	CachePojoSampleDomain ds;

	CachePojoSample obj;

	/**
	 * @throws java.lang.Exception
	 */
	//@Before
	public void setUp() throws Exception {
		ds = CachePojoSampleDomain.getInstance();
		obj = ds.getOperation("SampleKey");
	}

	/**
	 * @throws java.lang.Exception
	 */
	//@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.anz.common.cache.domain.CachePojoSampleDomain#getObjectFromSource(java.lang.String, java.lang.String, java.lang.Class)}
	 * .
	 */
	//@Test
	public void testGetObjectFromSource() {
		assertNotNull(obj);
		assertEquals("SampleKey", obj.getKey());
	}

}
