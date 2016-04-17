/**
 * 
 */
package com.anz.common.cache;

import java.io.Serializable;

/**
 * @author sanketsw
 *
 */
public interface ICachePojo extends Serializable {
	
	/**
	 * @return key of this cacheable object
	 */
	public String getKey();
	
	/**
	 * Convert to JSON string
	 * @return JSON String
	 */
	public String toJSON();

}
