package com.cgs.kerberos.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * TGT部分
 * 
 * @author xumh
 *
 */
public class TicketGrantingTicket implements Serializable{

	private static final long serialVersionUID = -4669864940958256658L;
	
	private String clientName;
	private String tgsName;
	private Date timeStamp;
	private String ip;
	private long lifeTime;
	private byte[] tgsSessionKey;
	
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getTgsName() {
		return tgsName;
	}
	public void setTgsName(String tgsName) {
		this.tgsName = tgsName;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public long getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}
	public byte[] getTgsSessionKey() {
		return tgsSessionKey;
	}
	public void setTgsSessionKey(byte[] tgsSessionKey) {
		this.tgsSessionKey = tgsSessionKey;
	}
	@Override
	public String toString() {
		return "TicketGrantingTicket [clientName=" + clientName + ", tgsName=" + tgsName + ", timeStamp=" + timeStamp + ", ip=" + ip + ", lifeTime=" + lifeTime
				+ ", tgsSessionKey=" + Arrays.toString(tgsSessionKey) + "]";
	}
	
}
