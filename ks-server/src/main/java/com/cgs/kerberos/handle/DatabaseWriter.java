package com.cgs.kerberos.handle;

public interface DatabaseWriter {
	boolean addClient(String name,String password);
	boolean updateClient(String name,String password);
	boolean removeClient(String name);
	boolean addServer(String name,String password);
	boolean updateServer(String name,String password);
}
