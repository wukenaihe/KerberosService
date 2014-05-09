package com.cgs.kerberos.bean;

import java.io.Serializable;
import java.util.Date;

public class ThirdResponse implements Serializable{

	private static final long serialVersionUID = 5353263526224698395L;
	
	private String serverName;
	private Date timeStamp;
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
	@Override
	public String toString() {
		return "ThirdResponse [serverName=" + serverName + ", timeStamp=" + timeStamp + "]";
	}
	
	
}
