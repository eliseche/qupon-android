package com.globalis.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map.Entry;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

public class HttpRequest {
	private String initialUrl;
	private Hashtable<String, String> initialParams;
	private HttpMethod initialHttpMethod;
	private URL url;
	private String params, jsonParams;

	public enum HttpMethod {
		GET, POST, POSTJSON, PUT;
	}

	public static class Url {
		public static String base = "http://www.321cupon.com";

		 //static {base = "http://192.168.0.196:3000";}

		public static String getBase() {
			return base;
		}

		public static String getLogin() {
			return base + "/api/v1/tokens.json";
		}

		public static String getPromotion() {
			return base + "/promotions.json";
		}

		public static String getPromotion(int promotionID) {
			return base + "/promotions/" + promotionID + ".json";
		}

		public static String getSignup() {
			return base + "/d/users.json";
		}

		public static String getUserInfo() {
			return base + "/d/users/edit.json";
		}

		public static String getCoupons(int promotionID) {
			return base + "/promotions/" + promotionID + "/coupons.json";
		}

		public static String getCoupon(int couponID) {
			return base + "coupons/" + couponID;
		}

		public static String getMyCoupons() {
			return base + "/coupons/my.json";
		}

	}

	public void set(String initialUrl, Object initialParams,
			HttpMethod initialHttpMethod) {
		this.initialUrl = initialUrl;
		if (initialParams instanceof Hashtable<?, ?>) {
			this.initialParams = (Hashtable<String, String>) initialParams;
			this.jsonParams = null;
		} else if (initialParams instanceof String) {
			this.jsonParams = (String) initialParams;
			this.initialParams = null;
		} else {
			this.initialParams = null;
			this.jsonParams = null;
		}
		this.initialHttpMethod = initialHttpMethod;
	}

	public Response execute() throws Exception {
		if (this.initialHttpMethod == HttpMethod.GET) {
			return get(this.initialUrl, this.initialParams);
		} else if (this.initialHttpMethod == HttpMethod.POST) {
			return post(this.initialUrl, this.initialParams);
		} else if(this.initialHttpMethod == HttpMethod.POSTJSON){
			return post(this.initialUrl, this.jsonParams);
		}else if (this.initialHttpMethod == HttpMethod.PUT) {
			return put(this.initialUrl, this.jsonParams);
		}

		return null;
	}


	public Response get(String url, Hashtable<String, String> params)
			throws Exception {
		setParams(params);
		if (this.params.length() != 0) {
			url = url + "?" + this.params;
		}
		setUrl(url);

		HttpURLConnection conn = null;
		Response response = null;

		try {
			conn = (HttpURLConnection) this.url.openConnection();
			InputStream in = conn.getInputStream();
			byte[] body = readStream(in);
			response = new Response(conn.getResponseCode(), new String(body));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConnectionErrorException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectionErrorException();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return response;
	}

	public Response post(String url, Hashtable<String, String> params)
			throws Exception {
		ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : params.entrySet()) {
			postParams.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}

		HttpClient conn = null;
		Response response = null;

		try {
			conn = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(postParams));
			HttpResponse res = conn.execute(post);
			InputStream in = res.getEntity().getContent();
			byte[] body = readStream(in);
			response = new Response(res.getStatusLine().getStatusCode(),
					new String(body));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectionErrorException();
		}

		return response;
	}
	
	private Response post(String url, String json) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response put(String url, Hashtable<String, String> params) {
		ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : params.entrySet()) {
			postParams.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}

		HttpClient conn = null;
		Response response = null;
		try {
			conn = new DefaultHttpClient();
			HttpPut put = new HttpPut(url);
			put.setEntity(new UrlEncodedFormEntity(postParams));
			HttpResponse httpResponse = conn.execute(put);
			InputStream in = httpResponse.getEntity().getContent();
			byte[] body = readStream(in);
			response = new Response(httpResponse.getStatusLine()
					.getStatusCode(), new String(body));
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public Response put(String url, String json) {
		Response response = null;
		HttpClient conn = null;
		try {
			conn = new DefaultHttpClient();
			HttpPut put = new HttpPut(url);
			put.setEntity(new StringEntity(json));
			put.setHeader("Accept", "application/json");
			put.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = conn.execute(put);
			InputStream in = httpResponse.getEntity().getContent();
			byte[] body = readStream(in);
			response = new Response(httpResponse.getStatusLine()
					.getStatusCode(), new String(body));
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}

	/*
	 * Better option, but not being used because of a bug when handling error
	 * code 401, throws IOException so we decided to use the Apache post.
	 */
	/*
	 * public Response post(String url, Hashtable<String, String> params) throws
	 * Exception { setParams(params); setUrl(url);
	 * 
	 * HttpURLConnection conn = null; Response response = null;
	 * 
	 * try { conn = (HttpURLConnection)this.url.openConnection(); // Sets the
	 * output to true, indicating that it's going to send POST data
	 * conn.setDoOutput(true); // Once it sets the output, it's not necessary to
	 * do that. Doing anyway conn.setRequestMethod("POST"); // Documentation
	 * suggest to set the length of the data we are sending to the server
	 * conn.setFixedLengthStreamingMode(this.params.getBytes().length);
	 * conn.setRequestProperty("Content-Type",
	 * "application/x-www-form-urlencoded"); // Sends POST out PrintWriter out =
	 * new PrintWriter(conn.getOutputStream()); out.print(this.params);
	 * out.close(); InputStream in = conn.getInputStream(); byte[] body =
	 * readStream(in); response = new Response(conn.getResponseCode(), new
	 * String(body)); } catch (IOException e) { e.printStackTrace(); throw new
	 * ConnectionErrorException(); } catch (Exception e) { e.printStackTrace();
	 * throw new ConnectionErrorException(); } finally { if(conn != null) {
	 * conn.disconnect(); } } return response; }
	 */

	private void setProxy() {
		Authenticator.setDefault(new ProxyAuthenticator(
				ProxyAuthenticator.USER, ProxyAuthenticator.PASSWORD));
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
		if (params == null) {
			this.params = "";
			return;
		} else {
			if (params.size() == 0) {
				this.params = "";
				return;
			}
		}

		StringBuffer buf = new StringBuffer();
		Enumeration<String> keys = params.keys();
		while (keys.hasMoreElements()) {
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
		while ((count = in.read(buf)) != -1) {
			out.write(buf, 0, count);
		}
		return out.toByteArray();
	}

	private class ConnectionErrorException extends Exception {
		String errorMessage;

		ConnectionErrorException() {
			super();
			errorMessage = new String(
					"Error when trying to bring data from network");
		}

		@Override
		public String getMessage() {
			return errorMessage;
		}
	}
}