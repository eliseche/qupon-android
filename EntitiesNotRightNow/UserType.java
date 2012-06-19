package com.globalis.entities;

public class UserType {

	private String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public UserType(){}
	
	public UserType(String name){
		this.name = name;
	}
}
