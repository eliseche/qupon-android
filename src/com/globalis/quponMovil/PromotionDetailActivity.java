package com.globalis.quponMovil;

import java.io.Serializable;

import com.globalis.entities.Promotion;

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
			Promotion promotion = (Promotion) extras.getSerializable("promotion");
			
			initViews();
		}
	}

	private void initViews() {
		ViewHolder holder = new ViewHolder();
		holder.lblDescription = (TextView)this.findViewById(R.id.promotion_detail_lbl_description);
		holder.lblSavPrice = (TextView)this.findViewById(R.id.promotion_detail_lbl_sav_price);
		holder.lblPercDisc = (TextView)this.findViewById(R.id.promotion_detail_lbl_perc_disc);
		holder.lblTagNames = (TextView)this.findViewById(R.id.promotion_detail_lbl_tag_names);
		holder.lblQuantityRemaining = (TextView)this.findViewById(R.id.promotion_detail_lbl_quantity_remaining);
		holder.lblTermsCond = (TextView)this.findViewById(R.id.promotion_detail_lbl_terms_cond);
		holder.lblFromDate = (TextView)this.findViewById(R.id.promotion_detail_lbl_from_date);
		holder.lblToDate = (TextView)this.findViewById(R.id.promotion_detail_lbl_to_date);
		holder.lblState = (TextView)this.findViewById(R.id.promotion_detail_lbl_state);

		holder.lblDescription.setText(holder.lblDescription.getText()+promotion.getDescription());
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
