package com.globalis.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;

import com.globalis.entities.Promotion;
import com.globalis.entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonConnector {
	
	private static final String USER = "aeliseche",
				   		 PASSWORD = "Tafirol1204",
				   		 HOST = "fwglobalis",
				   		 PORT = "3128";
	
	public static class ProxyAuthenticator extends Authenticator {
		 
	    private String user, password; 
	 
	    public ProxyAuthenticator(String user, String password) {
	        this.user = user;
	        this.password = password;
	    }
	 
	    protected PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication(user, password.toCharArray());
	    }
	 
	}
	
	public static String consultar(URL url) throws IOException{
		Authenticator.setDefault(new ProxyAuthenticator(USER, PASSWORD));
		System.setProperty("http.proxyHost", HOST);
		System.setProperty("http.proxyPort", PORT);
        URLConnection con = url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String jsonText = "", linea;
        while ((linea = in.readLine()) != null) {
            jsonText = jsonText + linea;
        }
        return jsonText;
    }
	
	public static String obtainJsonString(String urlString) throws IOException{
		try {
			URL url = new URL(urlString);
			String jsonString = consultar(url);
			return jsonString;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static User mapUser(String urlString) throws IOException{
		String jsonString = obtainJsonString(urlString);
		Gson gson = new Gson();
		User user = gson.fromJson(jsonString, User.class);
		return user;
	}
	
	public static List<Promotion> mapPromotions(String urlString) throws IOException{
		String jsonString = obtainJsonString(urlString);
		Gson gson = new Gson();
		Type collectionType = new TypeToken<List<Promotion>>(){}.getType();
		List<Promotion> promotions = gson.fromJson(jsonString, collectionType);
		return promotions;
	}

}
