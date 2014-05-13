package com.cgs.kerberos.client;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.bean.ServiceTicket;
import com.cgs.kerberos.bean.ThirdRequest;
import com.cgs.kerberos.bean.ThirdRequest.ThirdRequestInformation;
import com.cgs.kerberos.bean.ThirdResponse;
import com.cgs.kerberos.client.bean.SecondResponseWrapper;
import com.cgs.kerberos.client.handle.ClientDatabaseProcessor;
import com.cgs.kerberos.exception.InvalidStException;
import com.cgs.kerberos.exception.InvalidTgsRequest;
import com.cgs.kerberos.exception.KerberosException;
import com.cgs.kerberos.util.SecurityUtil;
import com.cgs.kerberos.util.Serializer;
import com.cgs.kerberos.util.TimeComparator;

public class KerberosClientServerImpl implements KerberosClientServer {
	private static Logger logger = LoggerFactory.getLogger(KerberosClientServerImpl.class);

	private int expiredTime = 5;

	private Serializer serializer;
	private ClientDatabaseProcessor cdp;

	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

	public void setCdp(ClientDatabaseProcessor cdp) {
		this.cdp = cdp;
	}

	public ThirdRequest getThirdRequest(SecondResponseWrapper secondResponseWrapper) throws KerberosException {
		
		ThirdRequestInformation thirdRequestInformation = new ThirdRequestInformation();
		String serverName = secondResponseWrapper.getSecondResponseBody().getSeverName();
		thirdRequestInformation.setClientName(serverName);
		thirdRequestInformation.setTimeStamp(new Date());
		logger.debug("Create thirdRequestInformation request for "+serverName);

		byte[] serviceSessionKey = secondResponseWrapper.getSecondResponseBody().getServiceSessionKey();

		// 序列化，加密
		logger.debug("Encrypt thirdRequestInformation by service session key");
		ThirdRequest thirdRequest = new ThirdRequest();
		byte[] decryptedThirdRequestInformation = serializer.object2Byte(thirdRequestInformation);
		byte[] encryptedThirdRequestInformation = SecurityUtil.encryptAes(decryptedThirdRequestInformation, serviceSessionKey);
		thirdRequest.setThirdRequestInformation(encryptedThirdRequestInformation);
		thirdRequest.setSt(secondResponseWrapper.getSt());

		return thirdRequest;
	}

	public byte[] getThirdRequestByte(SecondResponseWrapper secondResponseWrapper) throws KerberosException {
		ThirdRequest thirdRequest = getThirdRequest(secondResponseWrapper);
		
		logger.debug("Serialize thirdRequest");
		byte[] bytes = serializer.object2Byte(thirdRequest);
		return bytes;
	}

	public byte[] checkServiceTicket(byte[] thirdRequestByte) throws KerberosException {
		ThirdRequest thirdRequest = (ThirdRequest) serializer.byte2Object(thirdRequestByte);

		// 解密ST
		logger.debug("Decrypt thirdRequest's Service Ticket");
		byte[] encryptedSt = thirdRequest.getSt();
		String passWord = cdp.getPassord();
		byte[] decryptedSt = SecurityUtil.decryptAes(encryptedSt, passWord);
		ServiceTicket st = (ServiceTicket) serializer.byte2Object(decryptedSt);

		// 解密请求信息
		logger.debug("Decrypt thirdRequestInformation");
		byte[] serviceSessionKey = st.getHttpSessionKey();
		byte[] encryptedThirdRequestInfo = thirdRequest.getThirdRequestInformation();
		byte[] decryptedThirdRequestInfo = SecurityUtil.decryptAes(encryptedThirdRequestInfo, serviceSessionKey);
		ThirdRequestInformation thirdRequestInformation = (ThirdRequestInformation) serializer.byte2Object(decryptedThirdRequestInfo);

		// 对比ST中客户端的名字与请求信息中客户端名字
		logger.debug("Compare ST's client name with requestInformation's client name");
		String clientName1 = st.getClientName();
		String clientName2 = thirdRequestInformation.getClientName();
		if (clientName1 == null || clientName2 == null || !clientName1.equals(clientName2)) {
			throw new InvalidStException("Invalid Service Ticket. Request client name is different from service ticket's client name");
		}

		// 检查请求时间与当前时间是否超过5分钟
		logger.debug("Compare request's time with current time");
		if (expiredTime > 0) {
			if (TimeComparator.isInDifference(thirdRequestInformation.getTimeStamp(), expiredTime)) {
				throw new InvalidStException("Invalid Service Ticket.The request time is " + thirdRequestInformation.getTimeStamp() + ". The sever's system time is "
						+ (new Date()) + ". Is difference " + expiredTime + " Minutea");
			}
		}

		// 检查生命周期是否有效
		logger.debug("Check service ticket is expired?");
		long lifeTime = st.getLifeTime();
		Date date = st.getTimeStamp();
		if (lifeTime > 0) {
			if (TimeComparator.isTimeOut(date, lifeTime)) {
				throw new InvalidTgsRequest("Invalid Service Ticket. The Service Ticket is expired");
			}
		}
		
		ThirdResponse thirdResponse=new ThirdResponse();
		thirdResponse.setServerName(cdp.getName());
		thirdResponse.setTimeStamp(new Date());
		byte[] decryptedThirdResponse=serializer.object2Byte(thirdResponse);
		byte[] encryptedThirdResponse=SecurityUtil.encryptAes(decryptedThirdResponse, serviceSessionKey);

		return encryptedThirdResponse;
	}

	public boolean checkServiceResponse(byte[] thirdResponse,SecondResponseWrapper secondResponseWrapper) throws KerberosException {
		byte[] serviceSessionKey=secondResponseWrapper.getSecondResponseBody().getServiceSessionKey();
		byte[] decryptedThirdResponse=SecurityUtil.decryptAes(thirdResponse, serviceSessionKey);
		ThirdResponse t=(ThirdResponse) serializer.byte2Object(decryptedThirdResponse);
		String serverName1=t.getServerName();
		String serverName2=secondResponseWrapper.getSecondResponseBody().getSeverName();
		
		if(serverName1==null||serverName2==null||!serverName1.equals(serverName2)){
			return false;
		}
		return true;
	}
}
