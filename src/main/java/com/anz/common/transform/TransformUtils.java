/**
 * 
 */
package com.anz.common.transform;

import com.google.gson.Gson;

/**
 * @author sanketsw
 *
 */
public class TransformUtils {
	

	
	/**
	 * Convert the JSON string to a Java Pojo Object
	 * @param <T>
	 * @param jsonString
	 * @param pojoClassType
	 * @return Java Pojo Object
	 */
	public static <T> T fromJSON(String jsonString, Class<T> pojoClassType) {
		T obj = new Gson().fromJson(jsonString, pojoClassType);
		return obj;
	}
	
	/**
	 * Convert Java Pojo into a JSON string
	 * @param pojo
	 * @return JSON string
	 */
	public static String toJSON(ITransformPojo pojo) {
		String out = new Gson().toJson(pojo);
		return out;
	}

}
