package com.cgs.kerberos.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 服务ticket,传说中的ST
 * 
 * @author xumh
 *
 */
public class ServiceTicket implements Serializable{
	private String clientName;
	private String clientIp;
	private String serverName;
	private Date timeStamp;
	private long lifeTime;
	private byte[] httpSessionKey;
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public long getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}
	public byte[] getHttpSessionKey() {
		return httpSessionKey;
	}
	public void setHttpSessionKey(byte[] httpSessionKey) {
		this.httpSessionKey = httpSessionKey;
	}
	@Override
	public String toString() {
		return "ServiceTicket [clientName=" + clientName + ", clientIp=" + clientIp + ", serverName=" + serverName + ", timeStamp=" + timeStamp + ", lifeTime="
				+ lifeTime + ", httpSessionKey=" + Arrays.toString(httpSessionKey) + "]";
	}
	
}
