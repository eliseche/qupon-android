package com.globalis.quponMovil;

import java.io.Serializable;

import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;
import com.globalis.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PromotionDetailActivity extends Activity {

	public TextView lblDescription;
	public TextView lblSavPrice;
	public TextView lblPercDisc;
	public TextView lblTagNames;
	public TextView lblQuantityRemaining;
	public TextView lblTermsCond;
	public ImageView imgPromotion;
	public TextView lblFromDate;
	public TextView lblToDate;
	public TextView lblState;

	private Promotion promotion;
	private Bitmap imageBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion_detail);

		Bundle extras = getIntent().getExtras();
		Serializable serializable = extras.getSerializable("promotion");
		Serializable serializable1 = extras.getSerializable("imageBitmap");
		if (serializable == null || serializable1 == null) {
			Toast.makeText(PromotionDetailActivity.this, "Error",
					Toast.LENGTH_LONG).show();
			finish();
		} else {
			promotion = (Promotion) serializable;
			//imageBitmap = (Bitmap) serializable1;
			initViews();
		}
	}

	private void initViews() {

		lblDescription = (TextView) this
				.findViewById(R.id.promotion_detail_lbl_description1);
		lblSavPrice = (TextView) this
				.findViewById(R.id.promotion_detail_lbl_sav_price1);
		lblPercDisc = (TextView) this
				.findViewById(R.id.promotion_detail_lbl_perc_disc1);
		lblTagNames = (TextView) this
				.findViewById(R.id.promotion_detail_lbl_tag_names1);
		lblQuantityRemaining = (TextView) this
				.findViewById(R.id.promotion_detail_lbl_quantity_remaining1);
		lblTermsCond = (TextView) this
				.findViewById(R.id.promotion_detail_lbl_terms_cond1);
		lblFromDate = (TextView) this
				.findViewById(R.id.promotion_detail_lbl_from_date1);
		lblToDate = (TextView) this
				.findViewById(R.id.promotion_detail_lbl_to_date1);
		lblState = (TextView) this
				.findViewById(R.id.promotion_detail_lbl_state1);

		lblDescription.setText(promotion.getDescription());
		lblSavPrice.setText(String.valueOf(promotion.getNormalPrice()));
		lblPercDisc.setText(String.valueOf(promotion.getDiscount()));
		lblTagNames.setText(lblTagNames.getText());
		lblQuantityRemaining.setText(lblQuantityRemaining.getText());
		lblTermsCond
				.setText("texto super largo texto super largo texto super largo texto super largo texto super largo ");
		// new ImageManager(this).displayImage(HttpRequest.Url.base +
		// promotion.getImagePath(), imgPromotion);
		lblFromDate.setText(Utils.parseDate(promotion.getSinceDate(), this));
		lblToDate.setText(Utils.parseDate(promotion.getDueDate(), this));
		lblState.setText(lblState.getText());

	}

}
