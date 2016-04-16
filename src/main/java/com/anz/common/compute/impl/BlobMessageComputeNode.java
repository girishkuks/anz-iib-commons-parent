package com.anz.common.compute.impl;

import java.io.OutputStreamWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.anz.common.transform.ITransformer;
import com.anz.common.utils.TerminalAssemblyPair;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

public class BlobMessageComputeNode extends CommonMbJavaComputeNode {

	private static Logger logger = LogManager.getLogger();

	@Override
	public TerminalAssemblyPair execute(MbMessageAssembly inAssembly) throws Exception {
				
		TerminalAssemblyPair terminalAssemblyPair = new TerminalAssemblyPair();
		
		// create new message as a copy of the input
		MbMessage inMessage = inAssembly.getMessage();
		MbMessage outMessage = new MbMessage(inAssembly.getMessage());
		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMessage);		
		
		byte[] inputBlob = ComputeUtils.getBlobData(inMessage);

		// use the name of the node to identify the transform class	
		logger.error("Message Tree");
		//logger.error(inMessage.getDOMDocument());
		/*
		Document doc = inMessage.getDOMDocument();
		doc.getDocumentElement().normalize();

	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    transformer.transform(new DOMSource(doc), 
	         new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
	    */
	    
		ITransformer<byte[], byte[]> blobTransformer = (ITransformer<byte[], byte[]>)Class.forName(getName()).newInstance();
		byte[] outputBlob = blobTransformer.execute(inputBlob);
		
		ComputeUtils.replaceBlobData(outMessage, outputBlob);
		
		terminalAssemblyPair.setMessageAssembly(outAssembly);
		terminalAssemblyPair.setOutputTerminal(getOutputTerminal("out"));

		return terminalAssemblyPair;
	}
}
