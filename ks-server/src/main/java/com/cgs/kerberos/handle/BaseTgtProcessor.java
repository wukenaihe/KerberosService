package com.cgs.kerberos.handle;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import com.cgs.kerberos.bean.FirstRequest;
import com.cgs.kerberos.bean.FirstResponse;
import com.cgs.kerberos.bean.TgtResponse;
import com.cgs.kerberos.bean.TicketGrantingTicket;
import com.cgs.kerberos.exception.NoSuchUser;
import com.cgs.kerberos.util.KryoSerializer;
import com.cgs.kerberos.util.SecurityUtil;
import com.cgs.kerberos.util.Serializer;

public class BaseTgtProcessor implements TgtProcessor{
	private DatabaseProcessor dbp;
	private String name;
	private String password;
	private Serializer serializer=new KryoSerializer();
	
	//修改
	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public DatabaseProcessor getDbp() {
		return dbp;
	}

	public void setDbp(DatabaseProcessor dbp) {
		this.dbp = dbp;
	}
	
	
	public BaseTgtProcessor(String databasePath){
		this.dbp=new FileDatabaseProcessor();
	}
	
	public BaseTgtProcessor(){
		this.dbp=new FileDatabaseProcessor();
	}

	public FirstResponse check(FirstRequest firstRequest) throws NoSuchUser {
		//1、KDC数据库中是否有该客户端，如果无则抛出异常
		boolean containClient=dbp.contain(firstRequest.getName());
//		if(!containClient){
//			throw new NoSuchUser("Can not find "+firstRequest.getName()+" in KDC database, please contact the administractor");
//		}
		
		//产生一个随机的TGT Sesssion key
		byte[] tgtSessionKey=generateTgtSessionKey();
		
		//生成应答第一部分，通过客户端的密码进行加密
		TgtResponse tgtResponse=generateTgtResponse(firstRequest, tgtSessionKey);
		byte[] tgtResponseBytes=serialAndEncryptTgtResponse(tgtResponse, firstRequest.getName());
		
		//生成TGT，通过KDC的密码进行加密
		TicketGrantingTicket tgt=generateTGT(firstRequest, tgtSessionKey);
		byte[] tgtBytes=serialAndEncryptTGT(tgt);
		
		FirstResponse firstResponse=new FirstResponse();
		firstResponse.setTgt(tgtBytes);
		firstResponse.setTgtReponse(tgtResponseBytes);
		
		return firstResponse;
	}
	
	/**
	 * 产生128位的,16字节的TGT Session key
	 * @return
	 */
	protected byte[] generateTgtSessionKey(){
		byte[] keys=new byte[16];
		Random random=new Random();
		random.nextBytes(keys);
		return keys;
	}
	
	/**
	 * 获取KDC密码，对KDC密码应该进行一定的加密
	 * 
	 * @return
	 */
	protected String getPassword(){
		return dbp.getSelfPassword();
	}
	
	/**
	 * 生成Ticket granting Ticket(TGT)
	 * 
	 * @param FirstRequest TGT请求信息
	 * @param sessionKey 随机产生的TGT Session key
	 * @return
	 */
	protected TicketGrantingTicket generateTGT(FirstRequest f,byte[] sessionKey){
		TicketGrantingTicket t=new TicketGrantingTicket();
		t.setClientName(f.getName());
		t.setIp(f.getIp());
		t.setLifeTime(f.getLifeTime());
		t.setTgsName(getName());
		t.setTgtSessionKey(sessionKey);
		t.setTimeStamp(new Date());
		return t;
	}
	
	/**
	 * 将TGT序列化，并对序列化结果通过Aes加密。加密的密钥为KDC的密码
	 * 使用Kryo进行序列化
	 * 
	 * 允许override
	 * override后，客户端和服务端的序列化方式和加密解密方式必须一致
	 * 
	 * @param tgt TicketGrantingTicket
	 * @return byte[] 序列加密结果
	 */
	protected byte[] serialAndEncryptTGT(TicketGrantingTicket tgt){
		byte[] bytes=serializer.object2Byte(tgt);
		byte[] encryptedBytes=SecurityUtil.encryptAes(bytes, getPassword());
		return encryptedBytes;
	}
	
	
	/**
	 * 生成TgtResponse
	 * 
	 * @param f
	 * @param sessionKey
	 * @return
	 */
	protected TgtResponse generateTgtResponse(FirstRequest f,byte[] sessionKey){
		TgtResponse t=new TgtResponse();
		t.setLifeTime(f.getLifeTime());
		t.setTgsName(getName());
		t.setTgtSessionKey(sessionKey);
		t.setTimeStamp(new Date());
		return t;
	}
	
	/**
	 * 
	 * 将TgtResponse序列化，并对序列化结果通过Aes加密。加密的密钥为KDC的密码
	 * 使用Kryo进行序列化
	 * 
	 * 允许override
	 * override后，客户端和服务端的序列化方式和加密解密方式必须一致
	 * 
	 * @param tgtResponse
	 * @param clientName
	 * @return
	 */
	protected byte[] serialAndEncryptTgtResponse(TgtResponse tgtResponse,String clientName){
		byte[] bytes=serializer.object2Byte(tgtResponse);
		String key=dbp.getPassword(clientName);
		byte[] encrytedBytes=SecurityUtil.encryptAes(bytes, key);
		return encrytedBytes;
	}

}
