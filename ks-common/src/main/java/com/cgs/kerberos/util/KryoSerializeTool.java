package com.cgs.kerberos.util;

public class KryoSerializeTool implements Serializer{
	
	private KryoUtil kryoUtil=KryoUtil.getInstance();

	public byte[] object2Byte(Object obj) {
		return kryoUtil.object2Byte(obj);
	}

	public <T> T byte2Object(byte[] bytes) {
		return kryoUtil.byte2Object(bytes);
	}
	
}
