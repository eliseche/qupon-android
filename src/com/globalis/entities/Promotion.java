package com.globalis.entities;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Promotion implements Serializable  {	
	private PromotionData promotion;
	@SerializedName("image_url")
	private String imageUrl;
	@SerializedName("tag_list")
	private List<String> tagList;
	private List<Branch> branch;
	private static List<Promotion> promotions;
	
	public PromotionData getPromotion() {
		return promotion;
	}
	
	public void setPromotion(PromotionData promotion) {
		this.promotion = promotion;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public List<String> getTagList() {
		return tagList;
	}
	
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}
	
	public List<Branch> getBranch() {
		return branch;
	}
	
	public void setBranch(List<Branch> branch) {
		this.branch = branch;
	}	
	
	public static List<Promotion> getPromotions() {
		return promotions;		
	}
	
	public static void setPromotions(List<Promotion> promotions) {
		Promotion.promotions = promotions;		
	}
	
	public static class PromotionData implements Serializable {
		private int id;
		private String title;
		private String description;
		@SerializedName("due_date")
		private String dueDate;		
		@SerializedName("max_quantity_of_generated_coupon")
		private int maxQuantityOfGeneratedCoupon;
		@SerializedName("generated_coupons")		
		private int generatedCoupons;
		@SerializedName("max_quantity_per_client")
		private int maxQuantityPerClient;
		@SerializedName("since_date")
		private String sinceDate;
		@SerializedName("terms_and_condition")
		private String termsAndCondition;		
		@SerializedName("normal_price")
		private float normalPrice;
		@SerializedName("special_price")
		private float specialPrice;
		private float discount;
		private String state;		
			
		public int getId() {
			return id;
		}
	
		public void setId(int id) {
			this.id = id;
		}
		
		public String getTitle() {
			return title;
		}
	
		public void setTitle(String title) {
			this.title = title;
		}
	
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
	
		public int getMaxQuantityOfGeneratedCoupon() {
			return maxQuantityOfGeneratedCoupon;
		}
	
		public void setMaxQuantityOfGeneratedCoupon(int maxQuantityOfGeneratedCoupon) {
			this.maxQuantityOfGeneratedCoupon = maxQuantityOfGeneratedCoupon;
		}
	
		public int getGeneratedCoupons() {
			return generatedCoupons;
		}
	
		public void setGeneratedCoupons(int generatedCoupons) {
			this.generatedCoupons = generatedCoupons;
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
	
		public float getNormalPrice() {
			return normalPrice;
		}
	
		public void setNormalPrice(float normalPrice) {
			this.normalPrice = normalPrice;
		}
	
		public float getSpecialPrice() {
			return specialPrice;
		}
	
		public void setSpecialPice(float specialPrice) {
			this.specialPrice = specialPrice;
		}
	
		public float getDiscount() {
			return discount;
		}
	
		public void setDiscount(float discount) {
			this.discount = discount;
		}
		
		public String getState() {
			return state;
		}
	
		public void setState(String state) {
			this.state = state;
		}
	}
	
	public static class Branch implements Serializable {
		private int id;
		private String name;
		private String address;
		private boolean gmaps;
		private double latitude;
		private double longitude;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public boolean isGmaps() {
			return gmaps;
		}
		public void setGmaps(boolean gmaps) {
			this.gmaps = gmaps;
		}
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
	}
}