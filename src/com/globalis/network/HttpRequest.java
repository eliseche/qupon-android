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
	private URL url;
	private String params;
	
	public static class Url {
		public static String login = "https://eventioz.com/session.json";
		public static String promotions = "http://192.168.0.196:3000/promotions.json"; 
	}
	
	public Response get(String url, Hashtable<String, String> params) {
		setProxy();
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
		} catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		return response;
	}
	
	public Response post(String url, Hashtable<String, String> params) {
		setProxy();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		return response;		
	}
	
	private void setProxy() {
		Authenticator.setDefault(new ProxyAuthenticator("aeliseche", "Tafirol1204"));		
		System.setProperty("http.proxyHost", "fwglobalis.omnitronic.com.ar");
		System.setProperty("http.proxyPort", "3128");		
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
}