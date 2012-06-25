package com.globalis.quponMovil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CouponActivity extends Activity{

	private TextView lblDiscount, lblTitle, lblDescription, lblTermsCond;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon);

		initViews();
	}

	private void initViews() {
		findViewById(R.id.coupon_rl_code).getBackground().setAlpha(50);
		lblDiscount = (TextView) findViewById(R.id.coupon_lbl_discount);
		lblTitle = (TextView) findViewById(R.id.coupon_lbl_title);
		lblDescription = (TextView) findViewById(R.id.coupon_lbl_description);
		lblTermsCond = (TextView) findViewById(R.id.coupon_lbl_terms_and_cond);
	}
	
	public void setDiscount(String discount){
		lblDiscount.setText(discount+R.id.coupon_lbl_discount);
	}
	
}
