package com.globalis.entities;

import java.util.Calendar;

public class Promotion {
	
	private String description;
	private Calendar since_date;
	private Calendar due_date;
	private int max_quantity_of_generated_coupon;
	private int max_quantity_of_validated_coupon;
	private int max_quantity_per_client;
	private String image;
	private String terms_and_condition;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Calendar getSince_date() {
		return since_date;
	}
	public void setSince_date(Calendar since_date) {
		this.since_date = since_date;
	}
	public Calendar getDue_date() {
		return due_date;
	}
	public void setDue_date(Calendar due_date) {
		this.due_date = due_date;
	}
	public int getMax_quantity_of_generated_coupon() {
		return max_quantity_of_generated_coupon;
	}
	public void setMax_quantity_of_generated_coupon(
			int max_quantity_of_generated_coupon) {
		this.max_quantity_of_generated_coupon = max_quantity_of_generated_coupon;
	}
	public int getMax_quantity_of_validated_coupon() {
		return max_quantity_of_validated_coupon;
	}
	public void setMax_quantity_of_validated_coupon(
			int max_quantity_of_validated_coupon) {
		this.max_quantity_of_validated_coupon = max_quantity_of_validated_coupon;
	}
	public int getMax_quantity_per_client() {
		return max_quantity_per_client;
	}
	public void setMax_quantity_per_client(int max_quantity_per_client) {
		this.max_quantity_per_client = max_quantity_per_client;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTerms_and_condition() {
		return terms_and_condition;
	}
	public void setTerms_and_condition(String terms_and_condition) {
		this.terms_and_condition = terms_and_condition;
	}

	public Promotion(){}
	
	public Promotion(String description, Calendar since_date, Calendar due_date,
			int max_quantity_of_generated_coupon, int max_quantity_of_validated_coupon,
			int max_quantity_per_client, String image, String terms_and_condition){
		this.description = description;
		this.since_date = since_date;
		this.due_date = due_date;
		this.max_quantity_of_generated_coupon = max_quantity_of_generated_coupon;
		this.max_quantity_of_validated_coupon = max_quantity_of_validated_coupon;
		this.max_quantity_per_client = max_quantity_per_client;
		this.image = image;
		this.terms_and_condition = terms_and_condition;
	}
}
