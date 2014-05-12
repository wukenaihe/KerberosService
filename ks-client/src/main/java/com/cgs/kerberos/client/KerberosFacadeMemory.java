package com.cgs.kerberos.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.client.bean.SecondResponseWrapper;
import com.cgs.kerberos.exception.KerberosException;

public class KerberosFacadeMemory implements KerberosFacade {
	
	private static Logger logger=LoggerFactory.getLogger(KerberosFacadeMemory.class);

	private KerberosClient kerberosClient;
	private KerberosClientServer kerberosClientServer;
	private long lifeTime=-1;
	private String clientName;

	public long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}



	/**
	 * 保存TGS返回的内容
	 * 
	 */
	private static Map<String, SecondResponseWrapper> tgsPool = new ConcurrentHashMap<String, SecondResponseWrapper>();
	private static FirstResponseWrapper tgt;

	public void setKerberosClient(KerberosClient kerberosClient) {
		this.kerberosClient = kerberosClient;
	}

	public void setKerberosClientServer(KerberosClientServer kerberosClientServer) {
		this.kerberosClientServer = kerberosClientServer;
	}

	public byte[] getThirdRequestByte(String serverName) throws KerberosException {
		SecondResponseWrapper secondResponseWrapper = tgsPool.get(serverName);
		if (secondResponseWrapper == null) {
			secondResponseWrapper=getSecondResponseWrapper(serverName);
		}
		byte[] bytes= kerberosClientServer.getThirdRequestByte(secondResponseWrapper);
		
		return bytes;
	}

	public byte[] checkServiceTicket(byte[] thirdRequestByte) throws KerberosException {
		return kerberosClientServer.checkServiceTicket(thirdRequestByte);
	}

	public boolean checkServiceResponse(byte[] thirdResponse, String serverName) throws KerberosException {
		SecondResponseWrapper secondResponseWrapper = tgsPool.get(serverName);
		if (secondResponseWrapper == null) {
			secondResponseWrapper=getSecondResponseWrapper(serverName);
		}
		return kerberosClientServer.checkServiceResponse(thirdResponse, secondResponseWrapper);
	}

	private SecondResponseWrapper getSecondResponseWrapper(String serverName) {
		if (tgt == null) {
			getFirstResponseWrapper();
		}
		int reAsk=2;
		SecondResponseWrapper secondResponseWrapper;
		//获取ST，两次。
		//第一次，可能存在lifetime expired的情况，可以重新获取TGT
		//如果第二次，还是失败，仅仅是因为lifetime expired的情况讲不存在。一定是别原因出现了异常，需要检查重新获取
		while(reAsk>0){
			try{
				if(clientName==null){
					secondResponseWrapper=kerberosClient.getSt(tgt, serverName, null,lifeTime);
				}else{
					secondResponseWrapper=kerberosClient.getSt(tgt, clientName,serverName, null,lifeTime);
				}
				
				tgsPool.put(serverName, secondResponseWrapper);
				return secondResponseWrapper;
			}catch(KerberosException e){
				logger.debug(e.getMessage());
				reAsk--;
				if(reAsk==0){
					throw e;
				}
			}
		}
		
		return null;
	}

	private FirstResponseWrapper getFirstResponseWrapper() {
		try {
			tgt = kerberosClient.getTgt();
		} catch (KerberosException e) {
			logger.debug(e.getMessage(),e);
			throw e;
		}
		return tgt;
	}

}
