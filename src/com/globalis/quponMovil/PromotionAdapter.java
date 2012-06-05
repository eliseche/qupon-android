package com.globalis.quponMovil;

import java.util.List;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PromotionAdapter extends ArrayAdapter<Promotion> {	
	public ImageManager imageManager;
	
	public PromotionAdapter(Context context, int resource, List<Promotion> list) {		
		super(context, resource, list);		
		imageManager = new ImageManager(context.getApplicationContext());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		
		if(convertView ==  null) {			
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = li.inflate(R.layout.promotions_adapter, null);
			
			holder = new ViewHolder();
			// Get views from promotions_adapter.xml
			holder.lblDescription = (TextView)convertView.findViewById(R.id.promotions_lbl_description);
			holder.lblTermsAndCondition = (TextView)convertView.findViewById(R.id.promotions_lbl_terms_and_condition);
			holder.imgPromotion = (ImageView)convertView.findViewById(R.id.promotions_img_promotion);		
			holder.lblSpecialPrice = (TextView)convertView.findViewById(R.id.promotions_lbl_special_price);
			holder.lblDiscount = (TextView)convertView.findViewById(R.id.promotions_lbl_discount);
			holder.pbProgress = (ProgressBar)convertView.findViewById(R.id.promotions_progress);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		final Promotion promotion = getItem(position);
		if(promotion != null) {
			// Assign the appropriate data from out promotions object
			holder.lblDescription.setText(promotion.getDescription());
			holder.	lblTermsAndCondition.setText(promotion.getTermsAndCondition());
			imageManager.displayImage(HttpRequest.Url.base + promotion.getImagePath(), holder.imgPromotion, holder.pbProgress);		
			holder.lblSpecialPrice.setText("$" + String.valueOf(promotion.getSpecialPrice()));		
			holder.lblDiscount.setText(String.valueOf(promotion.getDiscount()) + "%");			
		}
		
		return convertView;
	} 
	
	private static class ViewHolder {
		public TextView lblDescription;
		public TextView lblTermsAndCondition;
		public ImageView imgPromotion;
		public TextView lblSpecialPrice;
		public TextView lblDiscount;
		public ProgressBar pbProgress;
	}	
}