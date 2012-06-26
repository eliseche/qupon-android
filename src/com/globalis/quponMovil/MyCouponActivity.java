package com.globalis.quponMovil;

import com.globalis.entities.Coupon;
import com.globalis.entities.Promotion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyCouponActivity extends Activity implements OnItemClickListener{
	
	private ListView listViewCoupons;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_coupon);
		
		initViews();
	}

	private void initViews() {
		listViewCoupons = (ListView) findViewById(R.id.coupon_list);
		listViewCoupons.setOnItemClickListener(this);
		
		//agregar task para buscar cupones
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Coupon coupon = Coupon.getCoupons().get(arg2);
		Intent intent = new Intent(this, CouponDetailActivity.class);
		intent.putExtra("promotion", coupon);
		startActivity(intent);
	}
}
