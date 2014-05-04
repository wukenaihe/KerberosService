package com.cgs.kerberos.handle;

import com.cgs.kerberos.exception.DatabaseException;

/**
 * 服务端数据库服务
 * 
 * 数据库可以是文件、内存或者数据库等，保存的方式应该加密
 * 
 * @author xumh
 *
 */
public interface DatabaseProcessor {
	/**
	 * 服务器数据库中是否包含该请求者
	 * 
	 * @param name
	 * @return boolean
	 * @throws DatabaseException 如果无则抛出异常
	 */
	boolean contain(String name) throws DatabaseException;
	
	/**
	 * 获取服务器自己的密码
	 * 
	 * @return String password
	 * @throws DatabaseException 无法密码则抛出异常
	 */
	String getSelfPassword() throws DatabaseException;
	
	/**
	 * 获取指定服务器的密码
	 * 
	 * @param name
	 * @throws DatabaseException 无法获取密码则抛出异常
	 */
	String getPassword(String name) throws DatabaseException;
}
