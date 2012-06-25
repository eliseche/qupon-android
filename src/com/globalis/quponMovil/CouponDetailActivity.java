package com.globalis.quponMovil;

import java.util.Calendar;
import java.util.Date;

import com.globalis.entities.Coupon;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CouponDetailActivity extends Activity{

	//coupon hardcodeado para prueba
	private static Coupon hcCoupon = new Coupon();
	static{
		hcCoupon.setID(5);
		hcCoupon.setCouponCode("1Jueg2Brot4");
		Promotion promotion = new Promotion("Juegos gratis","Juegos casi gratis. Te llevas 10x1",
				"2012-09-01T17:21:00Z" , null, 0, 0, 0, null,
				"Solo disponible para juegos en stock de commodoro 64",
				0,0,90);
		hcCoupon.setPromotion(promotion);
	}
	
	private TextView lblDiscount, lblTitle, lblDescription, lblTermsCond, lblCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_detail);

		//initViews((Coupon)savedInstanceState.get("coupon"));
		initViews(hcCoupon);
	}

	private void initViews(Coupon coupon) {
		findViewById(R.id.coupon_detail_rl_code).getBackground().setAlpha(50);
		lblDiscount = (TextView) findViewById(R.id.coupon_detail_lbl_discount);
		lblTitle = (TextView) findViewById(R.id.coupon_detail_lbl_title);
		lblDescription = (TextView) findViewById(R.id.coupon_detail_lbl_description);
		lblTermsCond = (TextView) findViewById(R.id.coupon_detail_lbl_terms_and_cond);
		lblCode = (TextView) findViewById(R.id.coupon_detail_lbl_code);
		
		Promotion promotion = coupon.getPromotion();
		lblDiscount.setText(String.valueOf(promotion.getDiscount()));
		lblTitle.setText(promotion.getTitle());
		lblDescription.setText(promotion.getDescription());
		lblTermsCond.setText(promotion.getTermsAndCondition());
		lblCode.setText(coupon.getCouponCode());
	}
	
	
	
	public void setDiscount(String discount){
		lblDiscount.setText(discount+R.id.coupon_detail_lbl_discount);
	}
	
}
