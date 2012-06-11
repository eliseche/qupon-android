package com.globalis.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

public class HttpRequest {
	private String initialUrl;
	private Hashtable<String, String> initialParams;
	private HttpMethod initialHttpMethod;
	private URL url;
	private String params;
	
	public enum HttpMethod {
		GET, POST;
	}
	
	public static class Url {
		public static String base = "http://50.116.21.186";
		public static String login = base + "/session.json";
		public static String promotions = base + "/promotions.json";
		public static String signup = base + "/signup";
		
		public static String getURLGenQPon(int promID){
			return base + "/promotions/" + promID + "/coupons";
		}
	}
	
	public void set(String initialUrl, Hashtable<String, String> initialParams, HttpMethod initialHttpMethod) {
		this.initialUrl = initialUrl;
		this.initialParams = initialParams;
		this.initialHttpMethod = initialHttpMethod;
	}
	
	public Response execute() throws Exception {
		if(this.initialHttpMethod == HttpMethod.GET) {
			return get(this.initialUrl, this.initialParams);			
		}
		else if(this.initialHttpMethod == HttpMethod.POST) {
			return post(this.initialUrl, this.initialParams);			
		}
		
		return null;
	}	
	
	public Response get(String url, Hashtable<String, String> params) throws Exception {
		//setProxy();
		setParams(params);
		if(this.params.length() != 0) {
			url = url + "?" + this.params;			
		}
		setUrl(url);		
		
		HttpURLConnection conn = null;
		Response response = null;
		
		try {
			conn = (HttpURLConnection)this.url.openConnection();
			InputStream in = conn.getInputStream();
			byte[] body = readStream(in);			
			response = new Response(conn.getResponseCode(), new String(body));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConnectionErrorException();
		} catch (Exception e){
			e.printStackTrace();
			throw new ConnectionErrorException();
		}
		finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		return response;
	}
	
	public Response post(String url, Hashtable<String, String> params) throws Exception {
		//setProxy();
		setParams(params);
		setUrl(url);				
		
		HttpURLConnection conn = null;
		Response response = null;
		
		try {			
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
			throw new ConnectionErrorException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectionErrorException();
		}
		finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		return response;		
	}
	
	private void setProxy() {
		Authenticator.setDefault(new ProxyAuthenticator(ProxyAuthenticator.USER, ProxyAuthenticator.PASSWORD));		
		System.setProperty("http.proxyHost", ProxyAuthenticator.HOST);
		System.setProperty("http.proxyPort", ProxyAuthenticator.PORT);		
	}
	
	private void setUrl(String url) {		
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void setParams(Hashtable<String, String> params) {
		if(params == null) {
			this.params = "";
			return;
		}
		else {
			if(params.size() == 0) {
				this.params = "";
				return;
			}
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
	
	private class ConnectionErrorException extends Exception
	{
		String errorMessage;
		
		ConnectionErrorException() {
			super();
			errorMessage = new String("Error when trying to bring data from network");
		}
		
		@Override
		public String getMessage() {
			return errorMessage;
		}
	}
}