package com.globalis.quponMovil;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity implements OnTabChangeListener {
	
	public static TabHost tabHost;
	private static int previousTab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		tabHost = getTabHost();
		// Tab for Promotions		
		TabSpec tabPromotions = tabHost.newTabSpec("Promotions");
		tabPromotions.setIndicator(getResources().getString(R.string.tab_promotions), getResources().getDrawable(R.drawable.lock));
		Intent intentPromotion = new Intent(this, PromotionActivity.class);
		tabPromotions.setContent(intentPromotion);
		// Tab for Promotions by proximity
		TabSpec tabPromotionsProximity = tabHost.newTabSpec("PromotionsProximity");
		tabPromotionsProximity.setIndicator(getResources().getString(R.string.tab_promotions_proximity), getResources().getDrawable(R.drawable.wheel));
		Intent intentPromotionProximity = new Intent(this, PromotionProximityActivity.class);
		tabPromotionsProximity.setContent(intentPromotionProximity);
		// Tab for my coupons
		TabSpec tabCoupons = tabHost.newTabSpec("Coupons");
		tabCoupons.setIndicator(getResources().getString(R.string.tab_coupons), getResources().getDrawable(R.drawable.coupones));
		Intent intentCoupons = new Intent(this, CouponActivity.class);
		tabCoupons.setContent(intentCoupons.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		
		tabHost.addTab(tabPromotions);
		tabHost.addTab(tabPromotionsProximity);
		tabHost.addTab(tabCoupons);
		tabHost.setOnTabChangedListener(this);
	}

	public static void selectPreviousTab(){
		tabHost.setCurrentTab(previousTab);
	}

	@Override
	public void onTabChanged(String tabId) {
		previousTab = tabHost.getCurrentTab();
	}	
}
