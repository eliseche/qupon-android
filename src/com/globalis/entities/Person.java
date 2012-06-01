package com.globalis.entities;

import java.util.Hashtable;

public class Person {
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLast_name() {
		return lastName;
	}
	public void setLast_name(String last_name) {
		this.lastName = last_name;
	}
	public String getZip_code() {
		return zipCode;
	}
	public void setZip_code(String zip_code) {
		this.zipCode = zip_code;
	}
	public String getPhone_number() {
		return phoneNumber;
	}
	public void setPhone_number(String phone_number) {
		this.phoneNumber = phone_number;
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
	
	private String name;
	private String lastName;
	private String zipCode;
	private String phoneNumber;
	private String gender;
	private String email;
	
	public Person(){}
	
	public Person(String name, String lastName, String zipCode, String phoneNumber,
			String gender, String email){
		this.name = name;
		this.lastName = lastName;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.email = email;
	}
	
	public Hashtable<String, String> getHashtable(){
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("name",name);
		hashtable.put("last_name",lastName);
		hashtable.put("zip_code",zipCode);
		hashtable.put("phone_number",phoneNumber);
		hashtable.put("gender",gender);
		hashtable.put("email",email);
		return hashtable;
	}
	
}
