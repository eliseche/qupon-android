package com.globalis.quponMovil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import com.globalis.entities.Coupon;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.text.GetChars;
import android.util.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TableLayout;

public class CouponActivity extends Activity{
	
	private TableLayout tableLayout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_coupon);

		initViews();
		getCoupons();
	}

	private void initViews() {
		tableLayout = (TableLayout)findViewById(R.id.my_coupon_tblll_coupons);
	}

	private void getCoupons() {
		HttpRequest req = new HttpRequest();
		req.set(HttpRequest.Url.getMyCoupons(), null,
				HttpRequest.HttpMethod.GET);
		HttpTask task = new HttpTask() {
			@Override
			public void doWork(Response response) {
				if (response != null) {
					Gson gson = new Gson();
					CouponJson coupons = gson.fromJson(response.getBody(),
							CouponJson.class);
					Coupon.setCoupons(coupons.getCoupon());

					chargeCoupons();
				}
			}
		};
		task.set(CouponActivity.this, req).execute();
	}

	private void chargeCoupons(){
		List<Coupon> coupons = Coupon.getCoupons();
		Promotion promotion;
		TableRow currentTableRow = null;
		View child;
		int noCoupon = 0;
		for (Coupon coupon : coupons) {
			noCoupon++;
			promotion = Promotion.getPromotion(coupon.getPromotionId());
			LayoutInflater layoutInflater = LayoutInflater.from(this);
			//falta setear valores de promocion y coupon
			switch(getResources().getConfiguration().orientation){
			case Configuration.ORIENTATION_PORTRAIT:
				if(noCoupon%2==1){
					currentTableRow = new TableRow(this);
					child = layoutInflater.inflate(R.layout.coupon_adapter, currentTableRow);
					currentTableRow.addView(child);
					tableLayout.addView(currentTableRow);
				}else{
					child = layoutInflater.inflate(R.layout.coupon_adapter, currentTableRow);
					currentTableRow.addView(child);
				}
				break;
			case Configuration.ORIENTATION_LANDSCAPE:
				if(noCoupon%4==1){
					currentTableRow = new TableRow(this);
					child = layoutInflater.inflate(R.layout.coupon_adapter, currentTableRow);
					currentTableRow.addView(child);
					tableLayout.addView(currentTableRow);
				}else{
					child = layoutInflater.inflate(R.layout.coupon_adapter, currentTableRow);
					currentTableRow.addView(child);
				}
				break;
			}
		}
	}
	
	private class CouponJson implements Serializable {
		private List<Coupon> coupon;

		public List<Coupon> getCoupon() {
			return coupon;
		}

		public void setCoupon(List<Coupon> coupon) {
			this.coupon = coupon;
		}
	}
}