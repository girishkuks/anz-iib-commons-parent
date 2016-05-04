/**
 * 
 */
package com.anz.common.transform;

import java.math.BigInteger;

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
	public static String toJSON(Object pojo) {
		String out = new Gson().toJson(pojo);
		return out;
	}

	/**
	 * Converts a string message to a BLOB
	 * @param message
	 * @return BLOB
	 */
	public static String getBlob(String message) {
		byte[] bytes = message.getBytes(); 
	    BigInteger bigInt = new BigInteger(bytes);
	    String hexString = bigInt.toString(16); // 16 is the radix
	    return hexString;
	}

}
