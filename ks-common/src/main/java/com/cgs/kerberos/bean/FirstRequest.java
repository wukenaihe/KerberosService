package com.cgs.kerberos.bean;

import java.io.Serializable;
import java.net.Inet4Address;
import java.util.Date;

/**
 * 第一次通信，获取tgt的请求信息；通过明文传输
 * 
 * @author xumh
 *
 */
public class FirstRequest implements Serializable{

	private static final long serialVersionUID = -7178637171344936684L;
	
	private String name;
	private String ip;
	private Date timestamp;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	public String toString() {
		return "FirstRequest [name=" + name + ", ip=" + ip + ", timestamp=" + timestamp + "]";
	}
	
	
	
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FirstRequest other = (FirstRequest) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
	public static FirstRequest getInstance(){
		FirstRequest f=new FirstRequest();
		f.setIp("172.18.110.3");
		f.setName("xumh");
		f.setTimestamp(new Date());
		return f;
	}
	
}
