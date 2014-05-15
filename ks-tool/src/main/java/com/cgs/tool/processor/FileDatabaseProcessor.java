package com.cgs.tool.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.util.SecurityUtil;

public class FileDatabaseProcessor {
	private static Logger logger = LoggerFactory.getLogger(FileDatabaseProcessor.class);

	private static final String DEFAULT_PATH = "ks-database.dat";
	private final String key = "zhejiangchenggong";

	private LinkedHashMap<String, String> loadDatabase(String path) throws Exception {
		File file;
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		if (path == null) {
			file = new File(DEFAULT_PATH);
		} else {
			file = new File(path);
		}

		ObjectInputStream obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
		String name;
		try {
			while (true) {
				name = (String) obj.readObject();
				int num = obj.readInt();
				byte[] bytes = new byte[num];
				obj.read(bytes);
				String value = new String(SecurityUtil.decryptAes(bytes, key));
				map.put(name, value);

			}
		} catch (EOFException e) {
			// e.printStackTrace();
		} finally {
			obj.close();
		}
		return map;
	}

	private void save(Map<String, String> map,String path) {
		File file;
		if (path == null) {
			file = new File(DEFAULT_PATH);
		} else {
			file = new File(path);
		}
		ObjectOutputStream objOutputStream=null;
		try {
			objOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			Set<String> name = map.keySet();
			for (String string : name) {
				System.out.println(string);
				objOutputStream.writeObject(string);
				byte[] bytes = map.get(string).getBytes();
				bytes = SecurityUtil.encryptAes(bytes, key);
				objOutputStream.writeInt(bytes.length);
				objOutputStream.write(bytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				objOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateKdc(String name,String password,String path){
		try {
			LinkedHashMap<String, String> map = loadDatabase(path);
			Set<String> keys = map.keySet();
			for (String string : keys) {
				map.remove(string);
				break;
			}
			LinkedHashMap<String, String> newMap=new LinkedHashMap<String, String>();
			newMap.put(name, password);
			keys=map.keySet();
			for (String string : keys) {
				newMap.put(string, map.get(string));
			}
			
			save(map,path);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void changeSelfPassword(String password,String path) {
		try {
			LinkedHashMap<String, String> map = loadDatabase(path);
			Set<String> keys = map.keySet();
			for (String string : keys) {
				map.put(string, password);
				break;
			}
			save(map,path);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void changePassword(String clientName, String passWord,String path) {
		try {
			LinkedHashMap<String, String> map = loadDatabase(path);
			if (!map.containsKey(clientName)) {
				System.out.println("KDC数据库中不存在" + clientName);
				return;
			}
			map.put(clientName, passWord);
			save(map,path);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void delete(String clientName,String path) {
		try {
			LinkedHashMap<String, String> map = loadDatabase(path);
			if (!map.containsKey(clientName)) {
				System.out.println("KDC数据库中不存在" + clientName);
				return;
			}
			map.remove(clientName);
			save(map,path);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Set<String> getClientNames(String path) {
		try {
			LinkedHashMap<String, String> map = loadDatabase(path);
			Set<String> keys = map.keySet();
			String name = null;
			for (String string : keys) {
				name = string;
				break;
			}
			keys.remove(name);
			for (String string : keys) {
				System.out.println(string);
			}
			return keys;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public void append(String clientName, String passWord,String path) {
		try {
			LinkedHashMap<String, String> map = loadDatabase(path);
			if (map.containsKey(clientName)) {
				System.out.println(clientName + "：名称不允许重复！");
				return;
			}
			map.put(clientName, passWord);
			save(map,path);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void create(String path, String name, String password) {
		try {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put(name, password);
			save(map,path);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}

	public static void main(String[] args) throws Exception {
		FileDatabaseProcessor f = new FileDatabaseProcessor();

//		Map<String, String> map = new LinkedHashMap<String, String>();
//		map.put("firstName", "nihao");
//		map.put("second", "nihao");
//		map.put("server", "123456");
//		map.put("sourceFep", "123456");
//		try {
//			f.save(map,null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		map = f.loadDatabase(null);
//		System.out.println(map);
		
		f.create("D://database.dat", "kdc", "123456");

		f.getClientNames("D://database.dat");
	}
}
