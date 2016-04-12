/**
 * 
 */
package com.anz.common.compute.impl;

import com.anz.common.compute.TransformType;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 *
 */
public class CommonJsonJsonTransformCompute extends CommonJavaCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.ICommonComputeNode#getTransformationType()
	 */
	public TransformType getTransformationType() {
		return TransformType.JSON_TO_JSON;
	}
	
	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJavaCompute#execute(com.ibm.broker.plugin.MbMessageAssembly, com.ibm.broker.plugin.MbMessageAssembly)
	 */
	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {
		
		MbMessage inMessage = inAssembly.getMessage();
		MbMessage outMessage = outAssembly.getMessage();
		
		String inputJson = ComputeUtils.getJsonData(inMessage);
		String outputJson = executeJsonToJsonTranform(inputJson);
		if (outputJson != null) {
			// Write this outputJson to outMessage
			ComputeUtils.replaceJsonData(outMessage, outputJson);
		}
		
	}
	
	/**
	 * Write business logic here if you merely need to transform the message
	 * from JSON to JSON
	 * 
	 * @param inputJson
	 *            input JSON Data
	 * @return output JSON Data to be placed in the message
	 */
	@SuppressWarnings("unchecked")
	public String executeJsonToJsonTranform(String inputJson) throws Exception {
		ITransformer<String, String> jsonTransformer = (ITransformer<String, String>)Class.forName(getName()).newInstance();
		String outJson = jsonTransformer.execute(inputJson);
		return outJson;
	}

	

}
