/**
 * 
 */
package com.anz.common.compute.impl;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbJSON;
import com.ibm.broker.plugin.MbMessage;

/**
 * @author sanketsw
 *
 */
public class ComputeUtils {
	
	/** 
	 * Converts JSON string to a message tree and assign to outMessage
	 * @param outMessage outMessage
	 * @param outputJson JSON string
	 * @throws Exception
	 */
	public static void replaceJsonData(MbMessage outMessage, String outputJson) throws Exception {
		MbElement outMsgRootEl = outMessage.getRootElement();			    
	    String parserName = MbJSON.PARSER_NAME;
	    String messageType = "";
	    String messageSet = "";
	    String messageFormat = "";
	    int encoding = 0;
	    int ccsid = 0;
	    int options = 0; 
	    MbElement JSONElement = outMsgRootEl.getFirstElementByPath("JSON");
	    
	    if(JSONElement != null) {
	    	JSONElement.detach();
		    outMsgRootEl.createElementAsLastChildFromBitstream(outputJson.getBytes("UTF-8"),
		    		   parserName, messageType, messageSet, messageFormat, encoding, ccsid,
		    		   options); 
	    }
		
	}

	/**
	 * Get the JSON Data from the message
	 * @param inMessage Message
	 * @return JSON String
	 * @throws Exception
	 */
	public static String getJsonData(MbMessage inMessage) throws Exception {
		MbElement jsonElem = inMessage.getRootElement().getFirstElementByPath("JSON/Data");
		if(jsonElem == null) return "";
		byte[] bs = jsonElem.toBitstream(null, null, null, 0, 1208, 0);
		if(bs == null) return "";
	    String inputJson = new String(bs, "UTF-8");
	    return inputJson;
	}

}
