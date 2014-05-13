package com.cgs.kerberos.client.handle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.exception.DatabaseException;
import com.cgs.kerberos.util.SecurityUtil;

public class FileClientDatabaseProcessor implements ClientDatabaseProcessor {
	private static Logger logger = LoggerFactory.getLogger(FileClientDatabaseProcessor.class);

	public static final String DEFAULT_CLIENT_DATABASE_PATH = "ks-client-database.dat";

	private String path = DEFAULT_CLIENT_DATABASE_PATH;
	private static final String KEY = "5tgbNHY^";

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public FileClientDatabaseProcessor() {

	}

	public FileClientDatabaseProcessor(String path) {
		this.path = path;
	}

	public String getPassord() {
		ObjectInputStream obj = null;
		try {
			obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(path))));
			obj.readObject();
			int num=obj.readInt();
			byte[] bytes=new byte[num];
			obj.read(bytes);
			String password=new String(SecurityUtil.decryptAes(bytes, KEY));
			return password;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseException("Can not find " + path + ", please check the database which save the client name and password file");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseException(e.getMessage());
		} finally{
			try {
				obj.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public String getName() throws DatabaseException {
		ObjectInputStream obj = null;
		try {
			obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(path))));
			String name=(String) obj.readObject();
			return name;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseException("Can not find " + path + ", please check the database which save the client name and password file");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseException(e.getMessage());
		} finally{
			try {
				obj.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
