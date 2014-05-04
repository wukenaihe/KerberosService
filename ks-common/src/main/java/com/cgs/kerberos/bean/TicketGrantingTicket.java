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
	private byte[] tgtSessionKey;
	
	
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
	public byte[] getTgtSessionKey() {
		return tgtSessionKey;
	}
	public void setTgtSessionKey(byte[] tgtSessionKey) {
		this.tgtSessionKey = tgtSessionKey;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientName == null) ? 0 : clientName.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + (int) (lifeTime ^ (lifeTime >>> 32));
		result = prime * result + ((tgsName == null) ? 0 : tgsName.hashCode());
		result = prime * result + Arrays.hashCode(tgtSessionKey);
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketGrantingTicket other = (TicketGrantingTicket) obj;
		if (clientName == null) {
			if (other.clientName != null)
				return false;
		} else if (!clientName.equals(other.clientName))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (lifeTime != other.lifeTime)
			return false;
		if (tgsName == null) {
			if (other.tgsName != null)
				return false;
		} else if (!tgsName.equals(other.tgsName))
			return false;
		if (!Arrays.equals(tgtSessionKey, other.tgtSessionKey))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}
	
	public String toString() {
		return "TicketGrantingTicket [clientName=" + clientName + ", tgsName=" + tgsName + ", timeStamp=" + timeStamp + ", ip=" + ip + ", lifeTime=" + lifeTime
				+ ", tgtSessionKey=" + Arrays.toString(tgtSessionKey) + "]";
	}
}
