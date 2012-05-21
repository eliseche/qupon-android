package com.globalis.quponMovil;

import java.util.List;
import com.globalis.entities.Promotion;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		
		// Assign the appropriate data from out promotions object
		lblDescription.setText(promotion.getDescription());
		lblTermsAndCondition.setText(promotion.getTermsAndCondition());
		
		return convertView;
	}		
}
