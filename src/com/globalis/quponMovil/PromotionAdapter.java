package com.globalis.quponMovil;

import java.net.URL;
import java.util.List;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PromotionAdapter extends ArrayAdapter<Promotion> {
	private Activity context;
	public ImageManager imageManager;
	
	public PromotionAdapter(Activity context, int resource, List<Promotion> list) {		
		super(context, resource, list);
		this.context = context;
		imageManager = new ImageManager(context.getApplicationContext());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {			
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = li.inflate(R.layout.promotions_adapter, parent, false);
		}
		
		// Get views from promotions_adapter.xml
		TextView lblDescription = (TextView)convertView.findViewById(R.id.promotions_lbl_description);
		TextView lblTermsAndCondition = (TextView)convertView.findViewById(R.id.promotions_lbl_terms_and_condition);
		ImageView imgPromotion = (ImageView)convertView.findViewById(R.id.promotions_img_promotion);		
		TextView lblSpecialPrice = (TextView)convertView.findViewById(R.id.promotions_lbl_special_price);
		TextView lblDiscount= (TextView)convertView.findViewById(R.id.promotions_lbl_discount);
		
		Promotion promotion = getItem(position);
		
		// Assign the appropriate data from out promotions object
		lblDescription.setText(promotion.getDescription());
		lblTermsAndCondition.setText(promotion.getTermsAndCondition());
		imageManager.displayImage(HttpRequest.Url.base + promotion.getImagePath(), imgPromotion);		
		lblSpecialPrice.setText("$" + String.valueOf(promotion.getSpecialPrice()));		
		lblDiscount.setText(String.valueOf(promotion.getDiscount()) + "%");
		
		return convertView;
	} 
}
