package com.globalis.quponMovil;

import java.lang.reflect.Type;
import java.util.List;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.os.Bundle;

public class PromotionProximityActivity extends PromotionBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion);
		
		initViews();		
		getPromotions();
	}
	
	/**
	 * Search for all active promotions
	 * 
	 * @param None
	 * @return Nothing
	 */
	private void getPromotions() {
		HttpRequest req = new HttpRequest();
		req.set(HttpRequest.Url.getPromotion(), null, HttpRequest.HttpMethod.GET);
		HttpTask task = new HttpTask() {
			@Override
			public void doWork(Response response) {
				if (response != null) {
					Gson gson = new Gson();
					Type collectionType = new TypeToken<List<Promotion>>() {}.getType();
					List<Promotion> promotions = gson.fromJson(response.getBody(), collectionType);
					Promotion.setPromotions(promotions);

					promotionAdapter = new PromotionAdapter(PromotionProximityActivity.this, R.layout.promotion_adapter, Promotion.getPromotions());
					listViewPromotion.setAdapter(promotionAdapter);
				}
			}
		};
		task.set(PromotionProximityActivity.this, req).execute();
	}
}
