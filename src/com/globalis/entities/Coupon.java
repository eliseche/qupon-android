package com.globalis.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import com.globalis.network.HttpRequest;
import com.globalis.network.Response;
import com.globalis.network.HttpRequest.HttpMethod;
import com.globalis.network.HttpTask;
import com.globalis.quponMovil.GlobalPreference;
import com.google.gson.annotations.SerializedName;

public class Coupon implements Serializable{

	private int id;
	@SerializedName("coupon_code")
	private String couponCode;
	@SerializedName("generation_date")
	private Calendar generationDate;
	@SerializedName("validation_date")
	private Calendar validationDate;
	private Promotion promotion;
	
	private static List<Coupon> coupons;
	
	public int getID(){
		return id;
	}
	public void setID(int id){
		this.id = id;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public Calendar getGenerationDate() {
		return generationDate;
	}
	public void setGenerationDate(Calendar generationDate) {
		this.generationDate = generationDate;
	}
	public Calendar getValidationDate() {
		return validationDate;
	}
	public void setValidationDate(Calendar validationDate) {
		this.validationDate = validationDate;
	}
	public Promotion getPromotion() {
		return promotion;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	public static List<Coupon> getCoupons() {
		return coupons;
	}
	public static void setCoupons(List<Coupon> coupons) {
		Coupon.coupons = coupons;
	}

	
}
