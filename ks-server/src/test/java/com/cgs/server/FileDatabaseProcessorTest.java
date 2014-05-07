package com.cgs.server;

import org.junit.Test;

import com.cgs.kerberos.handle.DatabaseProcessor;
import com.cgs.kerberos.handle.FileDatabaseProcessor;


public class FileDatabaseProcessorTest {
	public DatabaseProcessor dp=new FileDatabaseProcessor();
	
	@Test
	public void getName(){
		String password=dp.getSelfPassword();
		System.out.println(password);
	}
}
