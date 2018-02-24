package data;

import database.MySQLDriver;

public class ClientThread extends Thread {
	
	private String username;
	private MySQLDriver mysql;
	public ClientThread(String username, MySQLDriver mysql){
		this.username = username;
		this.mysql = mysql;
	}
	
	public MySQLDriver getDriver(){
		return this.mysql;
	}
	
	public String getUsername(){
		return this.username;
	}
}
