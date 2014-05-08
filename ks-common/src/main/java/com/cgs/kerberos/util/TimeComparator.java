package com.cgs.kerberos.util;

import java.util.Calendar;
import java.util.Date;

public class TimeComparator{

	
	/**
	 * 比较是否过期
	 * 
	 * @param date
	 * @param lifeTime
	 * @return 如果过期返回true
	 */
	public static boolean isTimeOut(Date date,long lifeTime){
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, (int) lifeTime);
		date=c.getTime();
		Date current=new Date();
		
		return current.after(date);
	}
	
	/**
	 * 检查是否 currentTime-expired<=date<=currentTime+expired
	 * 
	 * @param date
	 * @param expired
	 * @return
	 */
	public static boolean isInDifference(Date date,long expired){
		boolean less=isTimeOut(date, expired);
		boolean more=isTimeOut(date, -expired);
		return less&&more;
	}
}
