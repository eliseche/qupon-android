package com.globalis.quponMovil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.globalis.entities.Coupon;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.globalis.quponMovil.CouponDetailActivity.CouponJson;
import com.globalis.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.text.GetChars;
import android.util.Config;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TableLayout;
import android.widget.TextView;

public class CouponActivity extends Activity implements OnClickListener {

	private TableLayout tableLayout;
	private ImageManager imageManager;
	public static final int REGISTRATION_SUCCESS = 0;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_coupon);
		if (!LoginActivity.isLogged(this)) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			MainActivity.selectPreviousTab();
		} else {
			initViews();
			getCoupons();
		}
	}

	private void initViews() {
		tableLayout = (TableLayout) findViewById(R.id.my_coupon_tblll_coupons);
		imageManager = new ImageManager(this.getApplicationContext());
	}

	private void getCoupons() {
		HttpRequest req = new HttpRequest();
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("auth_token", GlobalPreference.getToken());
		req.set(HttpRequest.Url.getMyCoupons(), params,
				HttpRequest.HttpMethod.GET);
		HttpTask task = new HttpTask() {
			@Override
			public void doWork(Response response) {
				if (response != null) {
					Gson gson = new Gson();
					Type collectionType = new TypeToken<List<CouponJson>>() {
					}.getType();
					List<CouponJson> couponsJson = gson.fromJson(
							response.getBody(), collectionType);
					List<Coupon> coupons = new ArrayList<Coupon>();
					for (CouponJson couponJson : couponsJson) {
						Coupon coupon = couponJson.getCoupon();
						coupons.add(coupon);
					}
					Coupon.setCoupons(coupons);

					chargeCoupons(couponsJson);
				}
			}
		};
		task.set(CouponActivity.this, req).execute();
	}

	private void chargeCoupons(List<CouponJson> couponsJsons) {
		TableRow currentTableRow = null;
		View child;
		TextView lblTitle, lblGenerationDate;
		ImageView imgPromotion;
		ProgressBar progressBar;
		int noCoupon = 0;
		for (CouponJson couponJson : couponsJsons) {
			Coupon coupon = couponJson.getCoupon();
			noCoupon++;
			LayoutInflater layoutInflater = LayoutInflater.from(this);
			switch (getResources().getConfiguration().orientation) {
			case Configuration.ORIENTATION_PORTRAIT:
				if (noCoupon % 2 == 1) {
					currentTableRow = new TableRow(this);
					currentTableRow.setGravity(Gravity.CENTER_HORIZONTAL);
					tableLayout.addView(currentTableRow);
				}
				break;
			case Configuration.ORIENTATION_LANDSCAPE:
				if (noCoupon % 3 == 1) {
					currentTableRow = new TableRow(this);
					currentTableRow.setGravity(Gravity.CENTER_HORIZONTAL);
					tableLayout.addView(currentTableRow);
				}
				break;
			default:
				if (noCoupon % 2 == 1) {
					currentTableRow = new TableRow(this);
					currentTableRow.setGravity(Gravity.CENTER_HORIZONTAL);
					tableLayout.addView(currentTableRow);
				}
			}
			// create view from xml
			child = (RelativeLayout) layoutInflater.inflate(
					R.layout.coupon_adapter, currentTableRow, false);
			// get views from childs
			imgPromotion = (ImageView) child
					.findViewById(R.id.coupon_adapter_img_promotion);
			progressBar = (ProgressBar) child
					.findViewById(R.id.coupon_adapter_pb_loading);
			lblTitle = (TextView) child
					.findViewById(R.id.coupon_adapter_lbl_title);
			lblGenerationDate = (TextView) child
					.findViewById(R.id.coupon_adapter_lbl_generation_date);
			// set values to views
			imageManager.displayImage(
					HttpRequest.Url.getBase() + couponJson.getImageUrl(),
					imgPromotion, progressBar);
			lblTitle.setText(couponJson.getPromotionTitle());
			lblGenerationDate.setText(coupon.getGenerationDate().split("T")[0]);
			// add click event
			child.setOnClickListener(this);
			// add coupon to actual row
			currentTableRow.addView(child);
		}
	}

	private class CouponJson implements Serializable {

		private Coupon coupon;
		@SerializedName("image_url")
		private String imageUrl;
		@SerializedName("promotion_title")
		private String promotionTitle;

		public Coupon getCoupon() {
			return coupon;
		}

		public void setCoupon(Coupon coupon) {
			this.coupon = coupon;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getPromotionTitle() {
			return promotionTitle;
		}

		public void setPromotionTitle(String promotionTitle) {
			this.promotionTitle = promotionTitle;
		}

	}

	/**
	 * It makes you travel to coupon detail activity
	 * */
	public void onClick(View v) {
		Coupon coupon = Coupon.getCoupons().get(getChildLocation(v));
		Intent intentCouponDetail = new Intent(CouponActivity.this,
				CouponDetailActivity.class);
		intentCouponDetail.putExtra("coupon", coupon);
		startActivity(intentCouponDetail);
	}

	private int getChildLocation(View v) {
		int childLocation = -1;
		for (int i = 0; i < tableLayout.getChildCount(); i++) {
			TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
			for (int j = 0; j < tableRow.getChildCount(); j++) {
				childLocation++;
				View child = tableRow.getChildAt(j);
				if (v.equals(child))
					return childLocation;
			}
		}
		return -1;
	}
}