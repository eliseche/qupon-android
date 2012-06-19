package com.globalis.quponMovil;

import java.io.Serializable;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;
import com.globalis.utils.Utils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PromotionDetailActivity extends Activity {
	private Promotion promotion;
	private TextView lblSpecialPrice, lblDiscount, lblToDate, lblRemaining, lblTermsCond,
		lblDescription, lblTags, lblState, lblFromDate;
	private ImageView imgPromotion;
	private ProgressBar pbProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion_detail);
		
		initViews();

		Bundle extras = getIntent().getExtras();
		Serializable serializable = extras.getSerializable("promotion");		
		if (serializable == null) {
			Toast.makeText(PromotionDetailActivity.this, "Error", Toast.LENGTH_LONG).show();
			finish();
		} else {
			promotion = (Promotion)serializable;
			collectData();	
			ImageManager imageManager = new ImageManager(getApplicationContext());
			imageManager.displayImage(HttpRequest.Url.getBase() + promotion.getImagePath(), imgPromotion, pbProgress);
		}
	}
	
	private void collectData() {
		lblSpecialPrice.setText(String.valueOf(promotion.getSpecialPrice()));
		lblDiscount.setText(String.valueOf(promotion.getDiscount()));
		lblToDate.setText(Utils.parseDate(promotion.getDueDate(), PromotionDetailActivity.this));
		lblRemaining.setText(String.valueOf(promotion.getMaxQuantityOfGeneratedCoupon()));
		lblTermsCond.setText(String.valueOf(promotion.getTermsAndCondition()));
		lblDescription.setText(String.valueOf(promotion.getDescription()));
		lblFromDate.setText(Utils.parseDate(promotion.getSinceDate(), PromotionDetailActivity.this));
	}

	private void initViews() {
		lblSpecialPrice = (TextView)findViewById(R.id.promotion_detail_lbl_special_price);
		lblDiscount = (TextView)findViewById(R.id.promotion_detail_lbl_discount);
		lblToDate = (TextView)findViewById(R.id.promotion_detail_lbl_to_date);
		lblRemaining = (TextView)findViewById(R.id.promotion_detail_lbl_quantity_remaining);
		lblTermsCond = (TextView)findViewById(R.id.promotion_detail_lbl_terms_cond);
		lblDescription = (TextView)findViewById(R.id.promotion_detail_lbl_description);
		lblTags = (TextView)findViewById(R.id.promotion_detail_lbl_tag_names);
		lblState = (TextView)findViewById(R.id.promotion_detail_lbl_tag_names);
		lblFromDate = (TextView)findViewById(R.id.promotion_detail_lbl_from_date);
		imgPromotion = (ImageView)findViewById(R.id.promotion_detail_img_promotion);
		pbProgress = (ProgressBar)findViewById(R.id.promotion_detail_pb_loading);
	}
}