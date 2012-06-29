package com.globalis.entities;

import com.google.gson.annotations.SerializedName;

public class JsonResponse {

	private String state;
	
	@SerializedName("message")
	private String message;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
