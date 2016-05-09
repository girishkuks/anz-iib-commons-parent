/**
 * 
 */
package com.anz.common.compute;

import org.skyscreamer.jsonassert.JSONParser;

import com.anz.common.compute.impl.JaxbDFDLParser;

/**
 * @author root
 *
 */
@SuppressWarnings("rawtypes")
public enum ParserType {
	
	JSON(JSONParser.class),
	DFDL(JaxbDFDLParser.class);
	
	private final Class className;
	
	ParserType(Class value) {
		this.className = value;
	}
	
	public Class getClassName() {
		return className;
	}

}
