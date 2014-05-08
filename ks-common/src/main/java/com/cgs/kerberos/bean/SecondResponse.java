package com.cgs.kerberos.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class SecondResponse implements Serializable{
	
	private byte[] st;//通过需要访问的服务端密码加密
	private byte[] responseBody;//通过TGS Session key加密

	public byte[] getSt() {
		return st;
	}

	public void setSt(byte[] st) {
		this.st = st;
	}
	
	public byte[] getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(byte[] responseBody) {
		this.responseBody = responseBody;
	}


	public static class SecondResponseBody implements Serializable{
		private String severName;
		private Date timeStamp;
		private long lifeTime;
		private byte[] serviceSessionKey;
		public String getSeverName() {
			return severName;
		}
		public void setSeverName(String severName) {
			this.severName = severName;
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
		public byte[] getServiceSessionKey() {
			return serviceSessionKey;
		}
		public void setServiceSessionKey(byte[] serviceSessionKey) {
			this.serviceSessionKey = serviceSessionKey;
		}
		@Override
		public String toString() {
			return "SecondResponseBody [severName=" + severName + ", timeStamp=" + timeStamp + ", lifeTime=" + lifeTime + ", serviceSessionKey="
					+ Arrays.toString(serviceSessionKey) + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (lifeTime ^ (lifeTime >>> 32));
			result = prime * result + Arrays.hashCode(serviceSessionKey);
			result = prime * result + ((severName == null) ? 0 : severName.hashCode());
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
			SecondResponseBody other = (SecondResponseBody) obj;
			if (lifeTime != other.lifeTime)
				return false;
			if (!Arrays.equals(serviceSessionKey, other.serviceSessionKey))
				return false;
			if (severName == null) {
				if (other.severName != null)
					return false;
			} else if (!severName.equals(other.severName))
				return false;
			if (timeStamp == null) {
				if (other.timeStamp != null)
					return false;
			} else if (!timeStamp.equals(other.timeStamp))
				return false;
			return true;
		}
		
		
	}
	
}
