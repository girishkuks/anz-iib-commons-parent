/**
 * 
 */
package com.anz.common.cache.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/**
 * @author root
 *
 */
public class CachePojoSampleTest {
	
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
	 * Test method for {@link com.anz.common.cache.bean.CachePojoSample#OperationDetails(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testOperationDetails() {
		assertNotNull(op);
	}

	/**
	 * Test method for {@link com.anz.common.cache.bean.CachePojoSample#getImeplementation()}.
	 */
	@Test
	public void testGetImeplementation() {
		assertEquals(implementation, op.getImeplementation());
	}

	/**
	 * Test method for {@link com.anz.common.cache.bean.CachePojoSample#setImeplementation(java.lang.String)}.
	 */
	@Test
	public void testSetImeplementation() {
		opForSetters.setImeplementation("IIB2");
		assertEquals("IIB2", opForSetters.getImeplementation());
	}

	/**
	 * Test method for {@link com.anz.common.cache.bean.CachePojoSample#getOperation()}.
	 */
	@Test
	public void testGetOperation() {
		assertEquals(CachePojoSample.ADD, op.getOperation());
	}

	/**
	 * Test method for {@link com.anz.common.cache.bean.CachePojoSample#setOperation(java.lang.String)}.
	 */
	@Test
	public void testSetOperation() {
		opForSetters.setOperation("Subtract");
		assertEquals("Subtract", opForSetters.getOperation());
	}
}
