package com.globalis.quponMovil;

public class GlobalPreference {
	private static String token = null;
	private static final String PREF_LOGIN = "Login";
	private static final String PREF_LOGIN_EMAIL = "email";
	private static final String PREF_LOGIN_PASSWORD = "password";
	
	public static String getLogin() {
		return PREF_LOGIN;
	}
	
	public static String getLoginEmail() {
		return PREF_LOGIN_EMAIL;		
	}
	
	public static String getLoginPassword() {
		return PREF_LOGIN_PASSWORD;		
	}
	
	public static String getToken() {
		return token;
	}
	
	public static void setToken(String token) {
		GlobalPreference.token = token;
	}
}
