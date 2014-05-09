package com.cgs.kerberos.builder;

import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.handle.BaseTgsProcessor;
import com.cgs.kerberos.handle.BaseTgtProcessor;
import com.cgs.kerberos.handle.DatabaseProcessor;
import com.cgs.kerberos.handle.TgtProcessor;
import com.cgs.kerberos.server.TGTHandler;
import com.cgs.kerberos.util.Serializer;

public class BaseTGTHandlerBuilder implements TGTHandlerBuilder{
	private static Logger logger=LoggerFactory.getLogger(BaseTGTHandlerBuilder.class);
	
	private String serializerClassPath="com.cgs.kerberos.util.KryoSerializer";
	private String databaseProcessorClassPath="com.cgs.kerberos.handle.FileDatabaseProcessor";
	private Class serializerClass;
	private Class databaseProcessorClass;
	
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}

	public BaseTGTHandlerBuilder(){
		Class c=BaseTGTHandlerBuilder.class;
		try {
			serializerClass=c.forName(serializerClassPath);
			databaseProcessorClass=c.forName(databaseProcessorClassPath);
		} catch (ClassNotFoundException e) {
			logger.debug(e.getMessage(),e);
		}
	}

	public synchronized TGTHandler getTGTHandler(Socket socket) {
		TGTHandler instance=new TGTHandler(socket);
		try {
			Serializer s=(Serializer) serializerClass.newInstance();
			DatabaseProcessor d=(DatabaseProcessor) databaseProcessorClass.newInstance();
			instance.setSerializer(s);
			
			BaseTgtProcessor tgtProcessor=new BaseTgtProcessor();
			tgtProcessor.setDbp(d);
			tgtProcessor.setSerializer(s);
			tgtProcessor.setName(name);
			
			instance.setTgtProcessor(tgtProcessor);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
	
		return instance;
	}
	
}
