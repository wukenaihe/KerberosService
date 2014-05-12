package com.cgs.kerberos.handle;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.event.EndEvent;

import com.cgs.kerberos.bean.SecondRequest;
import com.cgs.kerberos.bean.SecondRequest.RequestInformation;
import com.cgs.kerberos.bean.SecondRequest.Verification;
import com.cgs.kerberos.bean.SecondResponse;
import com.cgs.kerberos.bean.SecondResponse.SecondResponseBody;
import com.cgs.kerberos.bean.ServiceTicket;
import com.cgs.kerberos.bean.TicketGrantingTicket;
import com.cgs.kerberos.exception.InvalidTgsRequest;
import com.cgs.kerberos.exception.KerberosException;
import com.cgs.kerberos.exception.NoSuchUser;
import com.cgs.kerberos.util.KryoSerializer;
import com.cgs.kerberos.util.SecurityUtil;
import com.cgs.kerberos.util.Serializer;
import com.cgs.kerberos.util.TimeComparator;

public class BaseTgsProcessor implements TgsProcessor{
	private static Logger logger=LoggerFactory.getLogger(BaseTgsProcessor.class);
	
	private DatabaseProcessor dbp;
	private String name;
	private Serializer serializer;
	private int expiredTime;//客户端请求与服务器接收到请求之间的时间差不能超过的范围，如果<0，则不检查
	
	public int getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(int expiredTime) {
		this.expiredTime = expiredTime;
	}

	//修改
	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
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
	
	public BaseTgsProcessor(){
		this.dbp=new FileDatabaseProcessor();
		this.serializer=new KryoSerializer();
	}
	
	public BaseTgsProcessor(DatabaseProcessor dbp,Serializer serializer){
		this.dbp=dbp;
		this.serializer=serializer;
	}
	
	public SecondResponse check(SecondRequest secondRequest) throws KerberosException {
		RequestInformation requestInformation=secondRequest.getRequestInformation();
		
		//检查服务名是否在KDC数据库中存在
		String server=requestInformation.getRequestServerName();
		boolean containServer=dbp.contain(server);
		if(!containServer){
			throw new NoSuchUser("Can not find "+server+" in KDC's database");
		}
		
		//解密TGT
		byte[] encryptedTgt=secondRequest.getTgt();
		String password=dbp.getSelfPassword();
		byte[] decryptedTgt=SecurityUtil.decryptAes(encryptedTgt, password);
		TicketGrantingTicket tgt=(TicketGrantingTicket) serializer.byte2Object(decryptedTgt);
		
		//用TGS Session key解密
		byte[] sessionKey=tgt.getTgsSessionKey();
		byte[] encryptedVerification=secondRequest.getVerification();
		byte[] decryptedVerification=SecurityUtil.decryptAes(encryptedVerification, sessionKey);
		Verification v=(Verification) serializer.byte2Object(decryptedVerification);
		
		//进行验证过程
		
		//检查客户端名字是否一致
		if(!v.getName().equals(tgt.getClientName())){
			throw new InvalidTgsRequest("Invalid Tgt. The TGT's client name is different from verification's name.");
		}
		
		//检查请求时间与当前时间是否超过5分钟
		if(expiredTime>0){
			if(TimeComparator.isInDifference(v.getTimeStamp(), expiredTime)){
				throw new InvalidTgsRequest("Invalid request.The request time is "+v.getTimeStamp()+". The sever's system time is "+(new Date())+". Is difference "+expiredTime+" Minutea");
			}
		}
		
		//检查生命周期是否有效
		long lifeTime=tgt.getLifeTime();
		Date date=tgt.getTimeStamp();
		if(lifeTime>0){
			if(TimeComparator.isTimeOut(date, lifeTime)){
				throw new InvalidTgsRequest("Invalid Tgt. The TGT is expired");
			}
		}
		
		//检查客户端请求tgt时的IP，与这次请求的Ip是否一致
		//UNDO 暂时取消，极容易出错，可能回事浮动IP等
		String tgtIp=tgt.getIp();
//		String clientIp=requestInformation.getIp();
//		if(!tgtIp.equals(clientIp)){
//			throw new InvalidTgsRequest("Invalid Tgt. The Tgt's Ip is "+tgtIp+" and your ip is "+clientIp);
//		}
		
		//TODO TGS缓存检查
		
		//生成Second Response
		SecondResponse sr=new SecondResponse();
		
		//产生一个随机的service session key
		byte[] serviceSessionKey=generateTgsSessionKey();
		ServiceTicket st=new ServiceTicket();
		st.setClientIp(tgtIp);
		st.setClientName(v.getName());
		st.setLifeTime(requestInformation.getLifeTime());
		st.setServerName(requestInformation.getRequestServerName());
		st.setHttpSessionKey(serviceSessionKey);
		st.setTimeStamp(new Date());
		
		//序列化，加密Service Ticket
		String serverPassword=dbp.getPassword(server);
		byte[] decryptedSt=serializer.object2Byte(st);
		byte[] encryptedSt=SecurityUtil.encryptAes(decryptedSt, serverPassword);
		sr.setSt(encryptedSt);
		
		
		//产生Second Response Body
		//使用TGS Session key加密
		byte[] tgsSessionKey=tgt.getTgsSessionKey();
		SecondResponseBody secondResponseBody=new SecondResponseBody();
		secondResponseBody.setLifeTime(requestInformation.getLifeTime());
		secondResponseBody.setServiceSessionKey(serviceSessionKey);
		secondResponseBody.setTimeStamp(new Date());
		secondResponseBody.setSeverName(requestInformation.getRequestServerName());
		byte[] decryptedSecondResponseBody=serializer.object2Byte(secondResponseBody);
		byte[] encryptedSecondeResponseBody=SecurityUtil.encryptAes(decryptedSecondResponseBody, tgsSessionKey);
		sr.setResponseBody(encryptedSecondeResponseBody);
		
		return sr;
	}
	
	
	/**
	 * 产生128位的,16字节的TGT Session key
	 * @return
	 */
	protected byte[] generateTgsSessionKey(){
		byte[] keys=new byte[16];
		Random random=new Random();
		random.nextBytes(keys);
		return keys;
	}

}
