package com.globalis.quponMovil;

import java.util.List;
import android.content.Context;
import android.widget.ArrayAdapter;
import com.globalis.entities.Coupon;

public class CouponAdapter extends ArrayAdapter<Coupon> {
	private ImageManager imageManager;
	
	public CouponAdapter(Context context, int resource, List<Coupon> list) {		
		super(context, resource, list);	
		imageManager = new ImageManager(context.getApplicationContext());
	}	
}
