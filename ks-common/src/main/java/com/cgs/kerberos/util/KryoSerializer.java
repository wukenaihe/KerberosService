package com.cgs.kerberos.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;


public class KryoSerializer implements Serializer{
	private static Logger logger=LoggerFactory.getLogger(KryoSerializer.class);
		
	/**
	 * Kryo是非线程安全的
	 */
	private Kryo kryo;
	
	public KryoSerializer(){
		kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
	}
	
	public KryoSerializer(Kryo kryo){
		this.kryo=kryo;
	}
	
	public byte[] object2Byte(Object obj){
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		Output output = new Output(outStream, 1024);
		kryo.writeClassAndObject(output, obj);
		output.flush();
		byte[] reBytes=outStream.toByteArray();
		try {
			outStream.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return reBytes;
	}
	
	public <T> T byte2Object(byte[] bytes){
		Input input  = new Input(new ByteArrayInputStream(bytes), 1024);
		return (T) kryo.readClassAndObject(input);
	}

}
