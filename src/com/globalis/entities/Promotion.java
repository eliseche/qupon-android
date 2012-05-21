package com.globalis.entities;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Promotion {	
	private String description;
	@SerializedName("due_date")
	private String dueDate;
	private String image;
	@SerializedName("max_quantity_of_generated_coupon") 
	private int maxQuantityOfGeneratedCoupon;
	@SerializedName("max_quantity_of_validated_coupon") 
	private int maxQuantityOfValidatedCoupon;
	@SerializedName("max_quantity_per_client") 
	private int maxQuantityPerClient;
	@SerializedName("since_date") 
	private String sinceDate;
	@SerializedName("terms_and_condition") 
	private String termsAndCondition;
	private static List<Promotion> promotions;
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}	
	
	public String getDueDate() {
		return dueDate;
	}	
	
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public int getMaxQuantityOfGeneratedCoupon() {
		return maxQuantityOfGeneratedCoupon;
	}
	
	public void setDescription(int maxQuantityOfGeneratedCoupon) {
		this.maxQuantityOfGeneratedCoupon = maxQuantityOfGeneratedCoupon;		
	}
	
	public int getMaxQuantityOfValidatedCoupon() {
		return maxQuantityOfValidatedCoupon;
	}
	
	public void setMaxQuantityOfValidatedCoupon(int maxQuantityOfValidatedCoupon) {
		this.maxQuantityOfValidatedCoupon = maxQuantityOfValidatedCoupon;
	}
	
	public int getMaxQuantityPerClient() {
		return maxQuantityPerClient;
	}
	
	public void setMaxQuantityPerClient(int maxQuantityPerClient) {
		this.maxQuantityPerClient = maxQuantityPerClient;
	}
	
	public String getSinceDate() {
		return sinceDate;
	}
	
	public void setsinceDate(String sinceDate) {
		this.sinceDate = sinceDate;
	}
	
	public String getTermsAndCondition() {
		return termsAndCondition;
	}
	
	public void setTermsAndCondition(String termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}
	
	public Promotion(String description, String dueDate, 
			String image, int maxQuantityOfGeneratedCoupon, int maxQuantityOfValidatedCoupon, 
			int maxQuantityPerClient, String sinceDate, String termsAndCondition) {
		this.description = description;
		this.dueDate = dueDate;
		this.image = image;
		this.maxQuantityOfGeneratedCoupon = maxQuantityOfGeneratedCoupon;
		this.maxQuantityOfValidatedCoupon = maxQuantityOfValidatedCoupon;
		this.maxQuantityPerClient = maxQuantityPerClient;
		this.sinceDate = sinceDate;
		this.termsAndCondition = termsAndCondition;
	}
	
	public static List<Promotion> getPromotions() {
		return promotions;
	}
	
	public static void setPromotions(List<Promotion> promotions) {
		Promotion.promotions = promotions;
	}
	
	@Override
	public String toString() {
		return "desciption: " + description + ", dueDate: " + dueDate + 
				", image: " + image + ", maxQuantityOfGeneratedCoupon: " + maxQuantityOfGeneratedCoupon + 
				", maxQuantityOfValidatedCoupon: " + maxQuantityOfValidatedCoupon + ", maxQuantityPerClient: " + maxQuantityPerClient +
				", sinceDate: " + sinceDate + ", termsAndCondition: " + termsAndCondition;  		
	}
}