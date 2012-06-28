package com.globalis.entities;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Coupon implements Serializable {

	private static List<Coupon> coupons;

	private int id;
	@SerializedName("promotion_id")
	private int promotionId;
	@SerializedName("coupon_code")
	private String couponCode;
	@SerializedName("coupon_state")
	private String couponState;
	@SerializedName("generation_date")
	private String generationDate;
	@SerializedName("validation_date")
	private String validationDate;

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponState() {
		return couponState;
	}

	public void setCouponState(String couponState) {
		this.couponState = couponState;
	}

	public String getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(String generationDate) {
		this.generationDate = generationDate;
	}

	public String getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(String validationDate) {
		this.validationDate = validationDate;
	}

	public int getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}

	public static List<Coupon> getCoupons() {
		return coupons;
	}

	public static void setCoupons(List<Coupon> coupons) {
		Coupon.coupons = coupons;
	}

}