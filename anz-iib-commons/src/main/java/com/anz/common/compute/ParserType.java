/**
 * 
 */
package com.anz.common.compute;

import org.skyscreamer.jsonassert.JSONParser;

import com.anz.common.compute.impl.DFDLParser;

/**
 * @author root
 *
 */
@SuppressWarnings("rawtypes")
public enum ParserType {
	
	JSON(JSONParser.class),
	DFDL(DFDLParser.class);
	
	private final Class className;
	
	ParserType(Class value) {
		this.className = value;
	}
	
	public Class getClassName() {
		return className;
	}

}
