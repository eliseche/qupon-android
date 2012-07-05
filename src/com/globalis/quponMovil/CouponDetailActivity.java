package com.globalis.quponMovil;

import java.io.Serializable;

import com.globalis.entities.Coupon;
import com.globalis.entities.JsonResponse;
import com.globalis.entities.Promotion;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CouponDetailActivity extends Activity {
	private Coupon coupon;
	private TextView lblDiscount, lblTitle, lblDescription, lblTermsCond,
			lblCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_detail);

		// initViews((Coupon)savedInstanceState.get("coupon"));
		initViews();

		Bundle extras = getIntent().getExtras();
		Serializable serializable = extras.getSerializable("coupon");
		if (serializable == null) {
			Toast.makeText(CouponDetailActivity.this, "Error",
					Toast.LENGTH_LONG).show();
			finish();
		} else {
			coupon = (Coupon) serializable;
			collectData();
		}
	}

	private void collectData() {
		Promotion promotion = Promotion.getPromotion(coupon.getPromotionId());
		if (promotion != null) {
			lblDiscount.setText(String.valueOf(promotion.getPromotion().getDiscount()));
			lblTitle.setText(promotion.getPromotion().getTitle());
			lblDescription.setText(promotion.getPromotion().getDescription());
			lblTermsCond.setText(promotion.getPromotion().getTermsAndCondition());
		}
		lblCode.setText(coupon.getCouponCode());
	}

	private void initViews() {
		findViewById(R.id.coupon_detail_rl_code).getBackground().setAlpha(50);
		lblDiscount = (TextView) findViewById(R.id.coupon_detail_lbl_discount);
		lblTitle = (TextView) findViewById(R.id.coupon_detail_lbl_title);
		lblDescription = (TextView) findViewById(R.id.coupon_detail_lbl_description);
		lblTermsCond = (TextView) findViewById(R.id.coupon_detail_lbl_terms_and_cond);
		lblCode = (TextView) findViewById(R.id.coupon_detail_lbl_code);
	}

	public void setDiscount(String discount) {
		lblDiscount.setText(discount + R.id.coupon_detail_lbl_discount);
	}
	
	public class CouponJson extends JsonResponse implements Serializable {
		private Coupon coupon;
		
		public Coupon getCoupon() {
			return coupon;
		}
		
		public void setCoupon(Coupon coupon) {
			this.coupon = coupon;
		}
		
	}
}
