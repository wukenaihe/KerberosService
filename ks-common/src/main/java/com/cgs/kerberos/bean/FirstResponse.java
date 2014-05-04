package com.cgs.kerberos.bean;

import java.util.Date;

public class FirstResponse {
	private byte[] tgt;
	private String tgsName;
	private Date timeStamp;
	private long lifeTime;
	private String tgsSessionKey;
	
	public byte[] getTgt() {
		return tgt;
	}
	public void setTgt(byte[] tgt) {
		this.tgt = tgt;
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
	public long getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}
	public String getTgsSessionKey() {
		return tgsSessionKey;
	}
	public void setTgsSessionKey(String tgsSessionKey) {
		this.tgsSessionKey = tgsSessionKey;
	}
	
	
}
