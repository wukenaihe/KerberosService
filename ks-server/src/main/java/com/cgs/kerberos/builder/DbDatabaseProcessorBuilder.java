package com.cgs.kerberos.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.exception.DatabaseException;
import com.cgs.kerberos.handle.DatabaseProcessor;
import com.cgs.kerberos.handle.DbDatabaseProcessor;

public class DbDatabaseProcessorBuilder implements DatabaseProcessorBuilder{
	
	private static Logger logger=LoggerFactory.getLogger(DbDatabaseProcessorBuilder.class);
	
	public static final String DRIVER="driver_class";
	public static final String USER_NAME="username";
	public static final String PASSWORD="password";
	public static final String URL="url";
	
	private String configPropertiesPath;
	private BasicDataSource basicDataSource=new BasicDataSource();
	
	public DbDatabaseProcessorBuilder(String path){
		configPropertiesPath=path;
		
		Properties properties=new Properties();
		try {
			properties.load(new FileInputStream(new File(configPropertiesPath)));
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
			throw new DatabaseException("can not find "+path,e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw new DatabaseException(e.getMessage(),e);
		}
		
		basicDataSource.setDriverClassName(properties.getProperty(DRIVER));
		basicDataSource.setUrl(properties.getProperty(URL));
		basicDataSource.setUsername(properties.getProperty(USER_NAME));
		basicDataSource.setPassword(properties.getProperty(PASSWORD));
	}

	public DatabaseProcessor getDatabaseProcessor() {
		
		return new DbDatabaseProcessor(basicDataSource);
	}

}
