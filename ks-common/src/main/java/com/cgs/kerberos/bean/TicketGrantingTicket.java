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
	
	private String clientName;//客户端名称
	private String tgsName;//KDC名称
	private Date timeStamp;//时间戳
	private String ip;//ip地址
	private long lifeTime;//生存时间
	private byte[] tgsSessionKey;//随机生成的tgsSessionKey
	
	
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
