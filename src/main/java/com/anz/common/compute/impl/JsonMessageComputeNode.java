package com.anz.common.compute.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.transform.ITransformer;
import com.anz.common.utils.TerminalAssemblyPair;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

public class JsonMessageComputeNode extends CommonMbJavaComputeNode {

	private static Logger logger = LogManager.getLogger();

	@Override
	public TerminalAssemblyPair execute(MbMessageAssembly inAssembly) throws Exception {
		
		TerminalAssemblyPair terminalAssemblyPair = new TerminalAssemblyPair();
		
		// create new message as a copy of the input
		MbMessage inMessage = inAssembly.getMessage();
		MbMessage outMessage = new MbMessage(inAssembly.getMessage());
		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMessage);
		
		
		String inputJson = ComputeUtils.getJsonData(inMessage);
				
		// use the name of the node to identify the transform class		
		ITransformer<String, String> stringTransformer = (ITransformer<String, String>)Class.forName(getName()).newInstance();
		String outputJson = stringTransformer.execute(inputJson);
		
		ComputeUtils.replaceJsonData(outMessage, outputJson);
		
		terminalAssemblyPair.setMessageAssembly(outAssembly);
		terminalAssemblyPair.setOutputTerminal(getOutputTerminal("out"));

		return terminalAssemblyPair;
	}
}
