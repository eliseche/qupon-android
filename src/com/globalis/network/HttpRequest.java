package com.globalis.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

public class HttpRequest {	
	private URL url;
	private String params;	
	
	public Response get(String url) {
		setUrl(url);
		
		HttpURLConnection conn = null;
		Response response = null;
		
		try {
			Authenticator.setDefault(new ProxyAuthenticator(USER, PASSWORD));
			System.setProperty("http.proxyHost", HOST);
			System.setProperty("http.proxyPort", PORT);
			
			conn = (HttpURLConnection)this.url.openConnection();
			InputStream in = conn.getInputStream();
			byte[] body = readStream(in);
			response = new Response(conn.getResponseCode(), new String(body));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		return response;
	}
	
	private static final String USER = "aeliseche",
			  PASSWORD = "Tafirol1204",
			  HOST = "fwglobalis.omnitronic.com.ar",
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
	
	public Response post(String url, Hashtable<String, String> params) {
		setUrl(url);
		setParams(params);
		
		HttpURLConnection conn = null;
		Response response = null;
		
		try {
			Authenticator.setDefault(new ProxyAuthenticator(USER, PASSWORD));
			System.setProperty("http.proxyHost", HOST);
			System.setProperty("http.proxyPort", PORT);
			
			conn = (HttpURLConnection)this.url.openConnection();
			// Sets the output to true, indicating that it's going to send POST data
			conn.setDoOutput(true);
			// Once it sets the output, it's not necessary to do that. Doing anyway
			conn.setRequestMethod("POST");
			// Documentation suggest to set the length of the data we are sending to the server
			conn.setFixedLengthStreamingMode(this.params.getBytes().length);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// Sends POST out
			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(this.params);
			out.close();
			InputStream in = conn.getInputStream();
			byte[] body = readStream(in);			
			response = new Response(conn.getResponseCode(), new String(body));
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		return response;		
	}
	
	private void setUrl(String url) {		
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void setParams(Hashtable<String, String> params) {
		if(params.size() == 0) {
			this.params = "";
		}
		
		StringBuffer buf = new StringBuffer();
		Enumeration<String> keys = params.keys();
		while(keys.hasMoreElements()) {
			buf.append(buf.length() == 0 ? "" : "&");
			String key = keys.nextElement();
			buf.append(key).append("=").append(params.get(key));
		}
		this.params = buf.toString();
	}
	
	private byte[] readStream(InputStream in) throws IOException {
		byte[] buf = new byte[1024];
		int count = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		while((count = in.read(buf)) != -1) {
			out.write(buf, 0, count);
		}		
		return out.toByteArray();
	}
	
	public static class Url {
		public static String login = "https://eventioz.com/session.json";
		public static String promotions = "http://192.168.0.196:3000/promotions.json"; 
	}
}