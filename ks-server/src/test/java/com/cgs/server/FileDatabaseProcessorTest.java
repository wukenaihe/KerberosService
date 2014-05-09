package com.cgs.server;

import org.junit.Test;

import com.cgs.kerberos.handle.DatabaseProcessor;
import com.cgs.kerberos.handle.FileDatabaseProcessor;


public class FileDatabaseProcessorTest {
	public DatabaseProcessor dp=new FileDatabaseProcessor();
	public DatabaseProcessor dp1=new FileDatabaseProcessor();
	public DatabaseProcessor dp2=new FileDatabaseProcessor();
	
	@Test
	public void getName(){
		String password=dp.getSelfPassword();
		System.out.println(password);
		
		String password1=dp1.getSelfPassword();
		System.out.println(password1);
		
		String password2=dp2.getSelfPassword();
		System.out.println(password2);
		
		
	}
}
