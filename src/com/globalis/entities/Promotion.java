package com.globalis.entities;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Promotion implements Serializable {
	private int id;
	private String title;
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
	@SerializedName("image_path")
	private String imagePath;
	@SerializedName("normal_price")
	private float normalPrice;
	@SerializedName("special_price")
	private float specialPrice;
	private float discount;
	private String state;
	private List<Tag> tags;
	private static List<Promotion> promotions;

	public static class Tag implements Serializable{
		private int id;
		private String name;

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

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	public Promotion(String description, String dueDate, String image,
			int maxQuantityOfGeneratedCoupon, int maxQuantityOfValidatedCoupon,
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
		return description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}