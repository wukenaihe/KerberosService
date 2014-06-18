package com.cgs.kerveros.server;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;

import com.cgs.kerberos.builder.DbDatabaseProcessorBuilder;
import com.cgs.kerberos.handle.DatabaseProcessor;

public class DbDatabaseProcessorTest {
	 public static DbDatabaseProcessorBuilder bpb=new DbDatabaseProcessorBuilder("target/test-classes/db.properties");
	 
	 public static DatabaseProcessor dbDatabaseProcessor;
	 
	 @BeforeClass
	 public static void init(){
		 dbDatabaseProcessor=bpb.getDatabaseProcessor();
	 }
	 
	 
	 @Test
	 public void getSelfName(){
		 String name=dbDatabaseProcessor.getSelfName();
		 System.out.println(name);
		 Assert.notNull(name);
	 }
	 
	 @Test
	 public void containName(){
		 boolean contain=dbDatabaseProcessor.contain("thisFep");
		 
		 Assert.isTrue(contain);
	 }
	 
	 @AfterClass
	 public static void destory(){
		 dbDatabaseProcessor.close();
	 }
}
