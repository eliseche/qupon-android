package com.globalis.quponMovil;

import java.io.Serializable;

import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;
import com.globalis.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PromotionDetailActivity extends Activity {
	
	private Promotion promotion;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion_detail);
		
		Bundle extras = getIntent().getExtras();
		Serializable serializable = extras.getSerializable("promotion");
		if(serializable==null){
			Toast.makeText(PromotionDetailActivity.this, "Error" , Toast.LENGTH_LONG).show();						
			finish();
		}
		else{
			promotion = (Promotion) extras.getSerializable("promotion");
			
			initViews();
		}
	}

	private void initViews() {
		ViewHolder holder = new ViewHolder();
		holder.lblDescription = (TextView)this.findViewById(R.id.promotion_detail_lbl_description1);
		holder.lblSavPrice = (TextView)this.findViewById(R.id.promotion_detail_lbl_sav_price1);
		holder.lblPercDisc = (TextView)this.findViewById(R.id.promotion_detail_lbl_perc_disc1);
		holder.lblTagNames = (TextView)this.findViewById(R.id.promotion_detail_lbl_tag_names1);
		holder.lblQuantityRemaining = (TextView)this.findViewById(R.id.promotion_detail_lbl_quantity_remaining1);
		holder.lblTermsCond = (TextView)this.findViewById(R.id.promotion_detail_lbl_terms_cond1);
		holder.lblFromDate = (TextView)this.findViewById(R.id.promotion_detail_lbl_from_date1);
		holder.lblToDate = (TextView)this.findViewById(R.id.promotion_detail_lbl_to_date1);
		holder.lblState = (TextView)this.findViewById(R.id.promotion_detail_lbl_state1);

		holder.lblDescription.setText(holder.lblDescription.getText()+promotion.getDescription());
		holder.lblSavPrice.setText(holder.lblSavPrice.getText()+String.valueOf(promotion.getNormalPrice()));
		holder.lblPercDisc.setText(holder.lblPercDisc.getText()+String.valueOf(promotion.getDiscount()));
		holder.lblTagNames.setText(holder.lblTagNames.getText());
		holder.lblQuantityRemaining.setText(holder.lblQuantityRemaining.getText());
		holder.lblTermsCond.setText(holder.lblTermsCond.getText()+promotion.getTermsAndCondition());
		//new ImageManager(this).displayImage(HttpRequest.Url.base + promotion.getImagePath(), holder.imgPromotion);		
		holder.lblFromDate.setText(holder.lblFromDate.getText()+Utils.parseDate(promotion.getSinceDate(), this));
		holder.lblToDate.setText(holder.lblToDate.getText()+Utils.parseDate(promotion.getDueDate(), this));
		holder.lblState.setText(holder.lblState.getText());
		
	}
	
	private static class ViewHolder {
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
	}
	
}
