package com.cgs.kerberos.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 回应客户端密码加密部分
 * 
 * @author xumh
 *
 */
public class TgtResponse implements Serializable{

	private static final long serialVersionUID = 108651713906189188L;
	
	private String tgsName;
	private Date timeStamp;
	private long lifeTime;
	private byte[] tgsSessionKey;
	
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
		return "TgtResponse [TgsName=" + tgsName + ", timeStamp=" + timeStamp + ", lifeTime=" + lifeTime + ", tgsSessionKey=" + Arrays.toString(tgsSessionKey)
				+ "]";
	}

}
