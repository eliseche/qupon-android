package com.globalis.entities;

public class UserState {

	private String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public UserState(){}
	
	public UserState(String name){
		this.name = name;
	}
	
}
