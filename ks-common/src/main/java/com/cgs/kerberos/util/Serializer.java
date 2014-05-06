package com.cgs.kerberos.util;

/**
 * 一个系统，必然只会用一种序列化方式
 * 
 * @author xumh
 *
 */
public interface Serializer {
	/**
	 * 将对象序列化程byte[]数组
	 * 
	 * @param obj
	 * @return
	 */
	public byte[] object2Byte(Object obj);
	/**
	 * 反序列化为对象
	 * 
	 * @param bytes
	 * @return
	 */
	public <T> T byte2Object(byte[] bytes);
}
