package com.cgs.kerberos.builder;

import com.cgs.kerberos.util.KryoSerializer;
import com.cgs.kerberos.util.Serializer;

public class KryoSerializerBuilder implements SerializerBuilder{

	public Serializer getSerializer() {
		return new KryoSerializer();
	}

}
