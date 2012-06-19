package com.globalis.entities;

import java.util.Hashtable;

public class Person {
	private String firstName;
	private String lastName;
	private String zipCode;
	private String phoneNumber;
	private String gender;
	private String email;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
		
	public Person(String firstName, String lastName, String zipCode, String phoneNumber, String gender, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.email = email;
	}
	
	public Hashtable<String, String> getHashTable() {
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		hashTable.put("firstName", firstName);
		hashTable.put("lastName", lastName);
		hashTable.put("zipCode", zipCode);
		hashTable.put("phoneNumber", phoneNumber);
		hashTable.put("gender", gender);
		hashTable.put("email", email);
		return hashTable;
	}
}