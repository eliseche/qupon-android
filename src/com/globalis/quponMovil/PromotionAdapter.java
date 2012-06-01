package com.globalis.quponMovil;

import java.net.URL;
import java.util.List;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PromotionAdapter extends ArrayAdapter<Promotion> {
	public PromotionAdapter(Context context, int resource, List<Promotion> list) {		
		super(context, resource, list);		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Promotion promotion = getItem(position);
		
		if(convertView == null) {			
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = li.inflate(R.layout.promotions_adapter, parent, false);			
		}
		
		// Get views from promotions_adapter.xml
		TextView lblDescription = (TextView)convertView.findViewById(R.id.promotions_lbl_description);
		TextView lblTermsAndCondition = (TextView)convertView.findViewById(R.id.promotions_lbl_terms_and_condition);
		ImageView imgPromotion = (ImageView)convertView.findViewById(R.id.promotions_img_promotion);
		
		// Assign the appropriate data from out promotions object
		lblDescription.setText(promotion.getDescription());
		lblTermsAndCondition.setText(promotion.getTermsAndCondition());
		imgPromotion.setImageBitmap(getBitmap("http://50.116.21.186/" + promotion.getImagePath()));
		
		return convertView;
	}		
	
	public Bitmap getBitmap(String bitmapUrl) {
		  try {
		    URL url = new URL(bitmapUrl);
		    return BitmapFactory.decodeStream(url.openConnection().getInputStream()); 
		  }
		  catch(Exception ex) {return null;}
		}
}
