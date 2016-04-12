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
	 * @param jsonString
	 * @param transformPojoClass
	 * @return Java Pojo Object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ITransformPojo fromJSON(String jsonString, Class transformPojoClass) {
		ITransformPojo obj = (ITransformPojo) new Gson().fromJson(jsonString, transformPojoClass);
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
