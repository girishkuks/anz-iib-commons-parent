/**
 * 
 */
package com.anz.common.cache.impl;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.anz.common.cache.pojo.CachePojoSample;

/**
 * @author root
 *
 */
public class CacheableObjectTest {	
	
	private CachePojoSample op;
	private CachePojoSample opForSetters;
	private String implementation = "IIB";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		op = new CachePojoSample(CachePojoSample.ADD, implementation);
		opForSetters = new CachePojoSample(CachePojoSample.ADD, implementation);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.anz.common.cache.AbstractCachePojo#getKey()}.
	 */
	@Test
	public void testGetKey() {
		assertEquals(CachePojoSample.ADD, op.getKey());
	}

	/**
	 * Test method for {@link com.anz.common.cache.AbstractCachePojo#setKey(java.lang.String)}.
	 */
	@Test
	public void testSetKey() {
		opForSetters.setKey("Subtract");
		assertEquals("Subtract", opForSetters.getKey());
	}
	
	/**
	 * Test method for {@link com.anz.common.cache.AbstractCachePojo#toJSON()}.
	 * @throws JSONException 
	 */
	@Test
	public void testToJSON() throws JSONException {
		System.out.println(op.toJSON());
		String expected="{\"imeplementation\":\"IIB\",\"operation\":\"Add\",\"key\":\"Add\"}";
		JSONObject json = new JSONObject(op.toJSON());
		JSONAssert.assertEquals(expected, json, false);
	}
	
	

}
