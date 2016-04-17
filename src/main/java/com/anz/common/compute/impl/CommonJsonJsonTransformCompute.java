/**
 * 
 */
package com.anz.common.compute.impl;

import com.anz.common.compute.TransformType;
import com.anz.common.ioc.spring.AnzSpringIoCFactory;
import com.anz.common.ioc.spring.IIBJdbc4DataSource;
import com.anz.common.ioc.spring.MbNodefactory;
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
		
		/* 
		 * Set the compute node in the node factory so that 
		 * Transform classes can use the jdbc type4 connection datasource later
		 * @see #IIBJdbc4DataSource
		 * @see #AnzSpringIoCFactory
		 */
		MbNodefactory.getInstance().setMbNode(this);
		
		
		String inputJson = ComputeUtils.getJsonDataFromBlob(inMessage);
		String outputJson = executeJsonToJsonTranform(inputJson);
		if (outputJson != null) {
			// Write this outputJson to outMessage
			ComputeUtils.replaceJsonDataToBlob(outMessage, outputJson);
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
