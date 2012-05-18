package com.globalis.entities;

import java.util.List;

public class User {
	
	private String username;
	private	String password_digest;
	private Person person;
	private UserState userState;
	private UserType userType;
	private List<Permission> permissions;
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword_digest(){
		return password_digest;
	}
	
	public void setPassword_digest(String password_digest){
		this.password_digest = password_digest;
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public UserState getUserState() {
		return userState;
	}

	public void setUserState(UserState userState) {
		this.userState = userState;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public User(){}
	
	public User(String username, String password_digest, Person person,
			UserState userState, UserType userType, List<Permission> permissions){
		this.username = username;
		this.password_digest = password_digest;
		this.person = person;
		this.userState = userState;
		this.userType = userType;
		this.permissions = permissions;
	}




	
}
