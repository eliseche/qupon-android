package com.globalis.network;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class ProxyAuthenticator extends Authenticator {
	public static String USER = "aeliseche";
	public static String PASSWORD = "Tafirol1204";
	public static String HOST = "fwglobalis.omnitronic.com.ar";
	public static String PORT = "3128";
	private String user, password;
	
	public ProxyAuthenticator(String user, String password) {
		this.user = user;
		this.password = password;
	}
	
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password.toCharArray());
	}	
}

