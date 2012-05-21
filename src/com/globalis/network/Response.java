package com.globalis.network;

public class Response {
	// HTTP status code
	private int statusCode;
	// Response body
	private String body;
	
	public Response(int statusCode, String body) {
		this.statusCode = statusCode;
		this.body = body;
	}
	
	public int getStatusCode() {
		return statusCode;		
	}
	
	public String getBody() {
		return body;
	}
	
	public boolean isValidStatusCode() {
		// Catches anything from 200 to 299, not just 200
		if(statusCode / 100 != 2) {
			return false;
		}
		return true;
	}
}
