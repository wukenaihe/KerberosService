package com.cgs.kerveros.server;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.Assert;

import com.cgs.kerberos.builder.DbDatabaseProcessorBuilder;
import com.cgs.kerberos.handle.DatabaseProcessor;
import com.cgs.kerberos.handle.DatabaseWriter;

public class DbDatabaseProcessorTest {
	 public static DbDatabaseProcessorBuilder bpb=new DbDatabaseProcessorBuilder("target/test-classes/db.properties");
	 
	 public static DatabaseProcessor dbDatabaseProcessor;
	 public static DatabaseWriter databaseWriter;
	 
	 @BeforeClass
	 public static void init(){
		 dbDatabaseProcessor=bpb.getDatabaseProcessor();
		 databaseWriter=(DatabaseWriter) dbDatabaseProcessor;
	 }
	 
	 
	 @Test
	 @Ignore
	 public void getSelfName(){
		 String name=dbDatabaseProcessor.getSelfName();
		 System.out.println(name);
		 Assert.notNull(name);
	 }
	 
	 @Test
	 @Ignore
	 public void containName(){
		 boolean contain=dbDatabaseProcessor.contain("sourceFep");
		 
		 Assert.isTrue(contain);
	 }
	 
	 @Test
	 @Ignore
	 public void addClient(){
		 boolean r=databaseWriter.addClient("thisFep", "123456");
		 Assert.isTrue(r);
	 }
	 
	 @Test
	 @Ignore
	 public void showPassword(){
		String s= dbDatabaseProcessor.getPassword("thisFep");
		System.out.println(s);
	 }
	 
	 @Test
	 @Ignore
	 public void updateClient(){
		 databaseWriter.updateClient("thisFep", "mysql");
	 }
	 
	 @Test
	 @Ignore
	 public void removeClient(){
		 databaseWriter.removeClient("thisFep");
	 }
	 
	 @Test
	 @Ignore
	 public void addServer(){
		 databaseWriter.addServer("sourceFep", "123456");
	 }
	 
	 @Test
//	 @Ignore
	 public void showSelfPassword(){
		 String p=dbDatabaseProcessor.getSelfPassword();
		 String n=dbDatabaseProcessor.getSelfName();
		 
		 System.out.println(p+"   "+n);
		 
	 }
	 
	 @AfterClass
	 public static void destory(){
		 dbDatabaseProcessor.close();
	 }
}
