package com.lockedme;

import java.io.Serializable;

public class UserCredentials implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String sitename;
	String siteusername;
	String sitepassword;
	
	public UserCredentials(String sitename, String siteusername, String sitepassword) {
		super();
		this.sitename = sitename;
		this.siteusername = siteusername;
		this.sitepassword = sitepassword;
	}
	public UserCredentials() {
		super();
	}
	@Override
	public String toString() {
		return "Site name :" + sitename + "     Username :" + siteusername + "     Password :" + sitepassword;
	}
	
	
	}
