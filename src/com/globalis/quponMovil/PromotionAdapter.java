package com.globalis.quponMovil;

import java.lang.reflect.Type;
import java.util.List;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PromotionAdapter extends ArrayAdapter<Promotion>{	
	public ImageManager imageManager;
	
	public PromotionAdapter(Context context, int resource, List<Promotion> list) {		
		super(context, resource, list);		
		imageManager = new ImageManager(context.getApplicationContext());
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView ==  null) {			
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = li.inflate(R.layout.promotion_adapter, null);
			
			holder = new ViewHolder();
			// Get views from promotions_adapter.xml
			holder.lblDescription = (TextView)convertView.findViewById(R.id.promotion_lbl_description);
			holder.lblTermsAndCondition = (TextView)convertView.findViewById(R.id.promotion_lbl_terms_and_condition);
			holder.imgPromotion = (ImageView)convertView.findViewById(R.id.promotion_img_promotion);		
			holder.lblSpecialPrice = (TextView)convertView.findViewById(R.id.promotion_lbl_special_price);
			holder.lblDiscount = (TextView)convertView.findViewById(R.id.promotion_lbl_discount);
			holder.pbProgress = (ProgressBar)convertView.findViewById(R.id.promotion_pb_loading);
			convertView.setTag(holder);
			holder.btnGenCoupon = (Button)convertView.findViewById(R.id.promotion_btn_generate_coupon);
			holder.btnGenCoupon.setFocusable(false);
			holder.btnGenCoupon.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					Promotion promotion = getItem(position);
					int promID = promotion.getId();
					
					HttpRequest req = new HttpRequest();
					req.set(HttpRequest.Url.getURLGenQPon(promID), null, HttpRequest.HttpMethod.POST);		
					Log.i("debug", HttpRequest.Url.getURLGenQPon(promID));
					HttpTask task = new HttpTask() {
						
						@Override
						public void doWork(Response response) {
							if(response != null) {
								Log.i("exito", response.getBody());
							}
							Log.i("falla", "ninguna respuesta del sitio");
						}
					};
					task.set(getContext(), req).execute();
				}
			});
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		Promotion promotion = getItem(position);
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
		public Button btnGenCoupon;
	}

}