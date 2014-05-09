package com.cgs.kerberos.builder;

import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.handle.BaseTgsProcessor;
import com.cgs.kerberos.handle.DatabaseProcessor;
import com.cgs.kerberos.server.TGSHandler;
import com.cgs.kerberos.util.Serializer;

public class BaseTGSHandlerBuilder implements TGSHandlerBuilder {
	private static Logger logger = LoggerFactory.getLogger(BaseTGSHandlerBuilder.class);

	private int expired = -1;
	private DatabaseProcessorBuilder databaseProcessorBuilder;
	private SerializerBuilder serializerBuilder;

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public void setDatabaseProcessorBuilder(DatabaseProcessorBuilder databaseProcessorBuilder) {
		this.databaseProcessorBuilder = databaseProcessorBuilder;
	}

	public void setSerializerBuilder(SerializerBuilder serializerBuilder) {
		this.serializerBuilder = serializerBuilder;
	}

	public BaseTGSHandlerBuilder() {
		databaseProcessorBuilder = new FileDatabaseProcessorBuilder();
		serializerBuilder = new KryoSerializerBuilder();
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	public TGSHandler getTGSHandler(Socket socket) {
		TGSHandler instance = new TGSHandler(socket);

		Serializer s = serializerBuilder.getSerializer();
		DatabaseProcessor d = databaseProcessorBuilder.getDatabaseProcessor();
		instance.setSerializer(s);

		BaseTgsProcessor tgsProcessor = new BaseTgsProcessor();
		tgsProcessor.setDbp(d);
		tgsProcessor.setSerializer(s);
		tgsProcessor.setName(name);
		tgsProcessor.setExpiredTime(expired);

		instance.setTgsProcessor(tgsProcessor);
		return instance;
	}

}
