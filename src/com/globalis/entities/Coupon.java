package com.globalis.entities;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Coupon implements Serializable {
	private CouponData coupon;
	private String state;
	private static List<Coupon> Coupons;
	
	public CouponData getCoupon() {
		return coupon;
	}
	
	public void setCoupon(CouponData coupon) {
		this.coupon = coupon;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public static List<Coupon> getCoupons() {
		return Coupons;		
	}
	
	public static void setCoupons(List<Coupon> coupons) {
		Coupon.Coupons = coupons;		
	}
	
	public static class CouponData implements Serializable {
		private int id;
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
	}
}