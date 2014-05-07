package com.cgs.kerberos.bean;

import java.util.Date;

public class SecondRequest {
	
	/**
	 * 
	 * 明文请求信息
	 * @author xumh
	 *
	 */
	public static class RequestInformation{
		private String requestServerName;
		private String ip;
		private long lifeTime;
		public String getRequestServerName() {
			return requestServerName;
		}
		public void setRequestServerName(String requestServerName) {
			this.requestServerName = requestServerName;
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
	}
	
	/**
	 * 
	 * 验证信息
	 * @author xumh
	 *
	 */
	public static class Verification{
		private String name;
		private Date timeStamp;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Date getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(Date timeStamp) {
			this.timeStamp = timeStamp;
		}
	
	}
	
	
	/**
	 * 	通过TGS Session Key加密的认证器部分
	 */
	private byte[] verification;
	
	/**
	 * 明文请求部分
	 */
	private RequestInformation requestInformation;
	
	/**
	 * 从服务器获取的tgt
	 */
	private byte[] tgt;

	public byte[] getVerification() {
		return verification;
	}

	public void setVerification(byte[] verification) {
		this.verification = verification;
	}

	public byte[] getTgt() {
		return tgt;
	}

	public void setTgt(byte[] tgt) {
		this.tgt = tgt;
	}

	public RequestInformation getRequestInformation() {
		return requestInformation;
	}

	public void setRequestInformation(RequestInformation requestInformation) {
		this.requestInformation = requestInformation;
	}
	
	
	
	 
}
