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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CouponActivity extends Activity implements OnItemClickListener {
	private ListView listViewCoupons;
	private CouponAdapter couponAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_coupon);

		initViews();
		getCoupons();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Coupon coupon = Coupon.getCoupons().get(arg2);
		Intent intentCouponDetail = new Intent(this, CouponDetailActivity.class);
		intentCouponDetail.putExtra("coupon", coupon);
		startActivity(intentCouponDetail);
	}

	private void initViews() {
		listViewCoupons = (ListView) findViewById(R.id.coupon_list);
		listViewCoupons.setOnItemClickListener(this);

		// agregar task para buscar cupones

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

					couponAdapter = new CouponAdapter(CouponActivity.this,
							R.layout.coupon_adapter, Coupon.getCoupons());

					listViewCoupons.setAdapter(couponAdapter);
				}
			}
		};
		task.set(CouponActivity.this, req).execute();
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