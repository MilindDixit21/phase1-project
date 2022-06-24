package com.lockedme;

import java.io.Serializable;

public class Users implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Users(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public Users() {
		super();
	}
	@Override
	public String toString() {
		return "Username:" + username + ", Password:" + password ;
	}

	
	
	

}
