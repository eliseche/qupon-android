package com.globalis.entities;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class User {

	private static User loggedUser;
	private String email;
	private String firstName;
	private String lastName;
	private String zipCode;
	private String phoneNumber;
	private String gender;
	@SerializedName("password_digest")
	private String passwordDigest;
	private UserState userState;
	private UserType userType;
	private List<Permission> permissions;

	public static User getLoggedUser() {
		return loggedUser;
	}

	public static void setLoggedUser(User loggedUser) {
		User.loggedUser = loggedUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPasswordDigest() {
		return passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
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

	public User() {
	}
	
	public User(String email, String passwordDigest,String firstName, String lastName,
			String zipCode, String phoneNumber, String gender,
			UserState userState, UserType userType, List<Permission> permissions) {
		this.email = email;
		this.passwordDigest = passwordDigest;
		this.firstName = firstName;
		this.lastName = lastName;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.userState = userState;
		this.userType = userType;
		this.permissions = permissions;
	}

	public Hashtable<String, String> getHashtable() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("user[email]", email);
		hashtable.put("user[name]", firstName);
		hashtable.put("user[last_name]", lastName);
		hashtable.put("user[zip_code]", zipCode);
		hashtable.put("user[phone_number]", phoneNumber);
		hashtable.put("user[sex]", gender);
		hashtable.put("user[password]", passwordDigest);
		hashtable.put("user[password_confirmation]", passwordDigest);
		return hashtable;
	}

}
