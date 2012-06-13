package com.globalis.entities;

import java.util.Hashtable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class User {
	
	private static User loggedUser;
	
	public static User getLoggedUser(){
		return loggedUser;
	}
	
	public static void setLoggedUser(User loggedUser){
		User.loggedUser = loggedUser;
	}
	
	private String username;
	@SerializedName("password_digest")
	private	String passwordDigest;
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
	
	public String getPasswordDigest() {
		return passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
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
	
	public User(String username, String passwordDigest, Person person,
			UserState userState, UserType userType, List<Permission> permissions){
		this.username = username;
		this.passwordDigest = passwordDigest;
		this.person = person;
		this.userState = userState;
		this.userType = userType;
		this.permissions = permissions;
	}

	public Hashtable<String, String> getHashtable(){
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("username", username);
		hashtable.put("password_digest", passwordDigest);
		return hashtable;
	}

	
}
