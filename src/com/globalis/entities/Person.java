package com.globalis.entities;

public class Person {
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	private String name;
	private String last_name;
	private String zip_code;
	private String phone_number;
	private char sex;
	private String email;
	
	public Person(){}
	
	public Person(String name, String last_name, String zip_code, String phone_number,
			char sex, String email){
		this.name = name;
		this.last_name = last_name;
		this.zip_code = zip_code;
		this.phone_number = phone_number;
		this.sex = sex;
		this.email = email;
	}
	
}
