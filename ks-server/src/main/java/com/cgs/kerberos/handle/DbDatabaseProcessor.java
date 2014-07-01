package com.cgs.kerberos.handle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.cgs.kerberos.exception.DatabaseException;
import com.cgs.kerberos.util.SecurityUtil;

public class DbDatabaseProcessor implements DatabaseProcessor,DatabaseWriter{
	
	private static Logger logger=LoggerFactory.getLogger(DbDatabaseProcessor.class);

	protected NamedParameterJdbcTemplate jdbcTemplate;
	protected BasicDataSource dataSource;
	
	protected static final String key="zhejiangchenggong";
	
	public static final String HAS_NAME="select count(*) from kerberos_client_database k where k.name=:name";
	public static final String GET_SERVER_PASSWORD="select encryptPassword from kerberos_server_database k";
	public static final String GET_CLIENT_PASSWORD="select encryptPassword from kerberos_client_database k where k.name=:name";
	public static final String GET_SERVER_NAME="select name from kerberos_server_database k";
	
	public static final String INSERT_CLIENT="insert into kerberos_client_database(name,encryptPassword) values(:name,:password)";
	public static final String UPDATE_CLIENT="update kerberos_client_database set encryptPassword = :password where name=:name";
	public static final String REMOVE_CLIENT="delete from kerberos_client_database where name=:name";
	public static final String UPDATE_SERVER="update kerberos_server_database set encryptPassword = :password,name=:name where keyName='Name'";
	
	
	public DbDatabaseProcessor(BasicDataSource dataSource){
		this.dataSource=dataSource;
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DbDatabaseProcessor(){
		
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(readOnly=true)
	public boolean contain(String name) throws DatabaseException {
		Map<String,String> param = new HashMap<String, String>();
		param.put("name", name);
		int num=jdbcTemplate.queryForInt(HAS_NAME, param);
		if(num<=0){
			return false;
		}
		return true;
	}

	@Transactional(readOnly=true)
	public String getSelfPassword() throws DatabaseException {
		List<Map<String, Object>> result=jdbcTemplate.queryForList(GET_SERVER_PASSWORD, new HashMap());
		Map<String, Object> map=result.get(0);
		byte[] bytes=(byte[]) map.get("encryptPassword");
		byte[] decryptBytes=SecurityUtil.decryptAes(bytes, key);
		String s=new String(decryptBytes);
		return s;
	}

	@Transactional(readOnly=true)
	public String getPassword(String name) throws DatabaseException {
		Map<String,String> param = new HashMap<String, String>();
		param.put("name", name);
		List<Map<String, Object>> result=jdbcTemplate.queryForList(GET_CLIENT_PASSWORD, param);
		Map<String, Object> map=result.get(0);
		byte[] bytes=(byte[]) map.get("encryptPassword");
		byte[] decryptBytes=SecurityUtil.decryptAes(bytes, key);
		String s=new String(decryptBytes);
		return s;
	}

	@Transactional(readOnly=true)
	public String getSelfName() throws DatabaseException {
		List<String> result=jdbcTemplate.queryForList(GET_SERVER_NAME, new HashMap(), String.class);
		String name=result.get(0);
		return name;
	}

	public void close() throws DatabaseException {
		try {
			dataSource.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Transactional(readOnly=false)
	public boolean addClient(String name, String password) {
		if(contain(name)){
			return false;
		}
		byte[] bytes=SecurityUtil.encryptAes(password.getBytes(), key);
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("name", name);
		param.put("password", bytes);
		
		jdbcTemplate.update(INSERT_CLIENT, param);
		
		return true;
	}

	@Transactional(readOnly=false)
	public boolean updateClient(String name, String password) {
		byte[] bytes=SecurityUtil.encryptAes(password.getBytes(), key);
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("name", name);
		param.put("password", bytes);
		jdbcTemplate.update(UPDATE_CLIENT, param);
		return true;
	}

	@Transactional(readOnly=false)
	public boolean removeClient(String name) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("name", name);
		
		jdbcTemplate.update(REMOVE_CLIENT, param);
		
		return true;
	}

	@Transactional(readOnly=false)
	public boolean addServer(String name, String password) {
		byte[] bytes=SecurityUtil.encryptAes(password.getBytes(), key);
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("name", name);
		param.put("password", bytes);
		
		jdbcTemplate.update(UPDATE_SERVER, param);
		return true;
	}

	@Transactional(readOnly=false)
	public boolean updateServer(String name, String password) {
		return addServer(name, password);
	}

}
