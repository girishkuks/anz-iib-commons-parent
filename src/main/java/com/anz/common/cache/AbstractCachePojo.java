/**
 * 
 */
package com.anz.common.cache;

import com.google.gson.Gson;

/**
 * @author sanketsw
 *
 */
public abstract class AbstractCachePojo implements ICachePojo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3155287861356193294L;
	
	String key;

	
	/* (non-Javadoc)
	 * @see com.anz.common.cache.ICacheableObject#getKey()
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @param key
	 */
	public AbstractCachePojo(String key) {
		super();
		this.key = key;
	}


	
	/* (non-Javadoc)
	 * @see com.anz.common.cache.ICacheableObject#toJSON()
	 */
	public String toJSON() {
		//NumbersInput json = new Gson().fromJson(jsonInput, NumbersInput.class);
		return new Gson().toJson(this);
	}

}
