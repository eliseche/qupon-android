package com.globalis.quponMovil;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.globalis.entities.Coupon;
import com.globalis.entities.Promotion;
import com.globalis.extensions.IOnCustomClickListener;

public class CouponAdapter extends ArrayAdapter<Coupon>{

	private ImageManager imageManager;
	
	public CouponAdapter(Context context, int resource, List<Coupon> list) {		
		super(context, resource, list);	
		imageManager = new ImageManager(context.getApplicationContext());
	}
	
}
