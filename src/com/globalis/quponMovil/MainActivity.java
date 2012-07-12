package com.globalis.quponMovil;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		TabHost tabHost = getTabHost();
		// Tab for Promotions		
		TabSpec tabPromotions = tabHost.newTabSpec("Promotions");
		tabPromotions.setIndicator(getResources().getString(R.string.tab_promotions), getResources().getDrawable(R.drawable.lock));
		Intent intentPromotion = new Intent(this, PromotionActivity.class);
		tabPromotions.setContent(intentPromotion);
		// Tab for Promotions by proximity
		// Tab for my coupons
		TabSpec tabCoupons = tabHost.newTabSpec("Coupons");
		tabCoupons.setIndicator(getResources().getString(R.string.tab_coupons), getResources().getDrawable(R.drawable.coupones));
		Intent intentCoupons = new Intent(this, CouponActivity.class);
		tabCoupons.setContent(intentCoupons);
		
		tabHost.addTab(tabPromotions);
		tabHost.addTab(tabCoupons);
	}
}
