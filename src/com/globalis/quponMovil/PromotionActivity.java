package com.globalis.quponMovil;

import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.List;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;
import com.globalis.network.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class PromotionActivity extends Activity {
	private ListView listViewPromotion;	
	private PromotionAdapter promotionAdapter; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotions);
		
		initViews();		
		
		/*Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("login", "Demo");
		params.put("password", "Demo");
		params.put("locale", "es");*/
		HttpRequest req = new HttpRequest();
		Response response = req.get(HttpRequest.Url.promotions);
		
		Gson gson = new Gson();
		Type collectionType = new TypeToken<List<Promotion>>(){}.getType();
		List<Promotion> promotions = gson.fromJson(response.getBody(), collectionType);
		Promotion.setPromotions(promotions);
		
		promotionAdapter = new PromotionAdapter(getApplicationContext(), R.layout.promotions_adapter, Promotion.getPromotions());
		listViewPromotion.setAdapter(promotionAdapter);		
	}
	
	private void initViews() {
		listViewPromotion = (ListView)findViewById(R.id.promotions_lst);
	}
}
