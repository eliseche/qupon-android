package com.globalis.quponMovil;

import java.io.Serializable;
import com.globalis.entities.Coupon;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CouponDetailActivity extends Activity {
	private Coupon coupon;
	private TextView lblDiscount, lblTitle, lblDescription, lblTermsCond, lblCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_detail);

		//initViews((Coupon)savedInstanceState.get("coupon"));
		initViews();
		
		Bundle extras = getIntent().getExtras();
		Serializable serializable = extras.getSerializable("coupon");		
		if (serializable == null) {
			Toast.makeText(CouponDetailActivity.this, "Error", Toast.LENGTH_LONG).show();
			finish();
		} else {		
			coupon = (Coupon)serializable;
			collectData();
		}
	}
	
	private void collectData() {
		lblDiscount.setText(String.valueOf(50));
		lblTitle.setText("Titulo hardcoded");
		lblDescription.setText("Descripcion hardcoded");
		lblTermsCond.setText("Termsand conditions hardocoded");
		lblCode.setText(coupon.getCoupon().getCouponCode());
	}

	private void initViews() {
		findViewById(R.id.coupon_detail_rl_code).getBackground().setAlpha(50);
		lblDiscount = (TextView) findViewById(R.id.coupon_detail_lbl_discount);
		lblTitle = (TextView) findViewById(R.id.coupon_detail_lbl_title);
		lblDescription = (TextView) findViewById(R.id.coupon_detail_lbl_description);
		lblTermsCond = (TextView) findViewById(R.id.coupon_detail_lbl_terms_and_cond);
		lblCode = (TextView) findViewById(R.id.coupon_detail_lbl_code);
	}
	
	public void setDiscount(String discount){
		lblDiscount.setText(discount+R.id.coupon_detail_lbl_discount);
	}
}
