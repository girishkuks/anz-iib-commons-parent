/**
 * 
 */
package com.anz.common.cache.pojo;

import java.io.Serializable;

import com.anz.common.cache.AbstractCachePojo;

/**
 * @author sanketsw
 *
 */
public class CachePojoSample extends AbstractCachePojo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3608447270509765318L;


	public static String ADD = "Add";
	
	
	String imeplementation;
	String operation;	
	
	/**
	 * @param key
	 * @param operation
	 */
	public CachePojoSample(String operation, String implementation) {
		super(CachePojoSample.generateKey(operation)); // This will act as a key to save into the cacheMap
		this.operation = operation;
		this.imeplementation = implementation;
	}
	
	private static String generateKey(String operation) {
		return operation;
	}

	/**
	 * @return the implementation
	 */
	public String getImeplementation() { 
		return imeplementation;
	}
	/**
	 * @param imeplementation the implementation to set
	 */
	public void setImeplementation(String imeplementation) {
		this.imeplementation = imeplementation;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
	
}
