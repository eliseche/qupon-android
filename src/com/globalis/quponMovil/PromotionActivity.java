package com.globalis.quponMovil;

import java.lang.reflect.Type;
import java.util.List;
import com.globalis.entities.Promotion;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PromotionActivity extends Activity implements OnItemClickListener, OnClickListener{
	private ListView listViewPromotion;	
	private PromotionAdapter promotionAdapter; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion);
		
		initViews();
		
		HttpRequest req = new HttpRequest();
		req.set(HttpRequest.Url.promotions, null, HttpRequest.HttpMethod.GET);		
		HttpTask task = new HttpTask() {
			
			@Override
			public void doWork(Response response) {
				if(response != null) {
					Gson gson = new Gson();
					Type collectionType = new TypeToken<List<Promotion>>(){}.getType();
					List<Promotion> promotions = gson.fromJson(response.getBody(), collectionType);
					Promotion.setPromotions(promotions);
					
					promotionAdapter = new PromotionAdapter(PromotionActivity.this, R.layout.promotion_adapter, Promotion.getPromotions());
					listViewPromotion.setAdapter(promotionAdapter);
				}				
			}
		};
		task.set(this, req).execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_login:
			Intent intentLogin = new Intent(this, LoginActivity.class);
			startActivity(intentLogin);			
			break;
		default:
			break;
		}
		return true;
	}	
	
	private void initViews() {
		listViewPromotion = (ListView)findViewById(R.id.promotion_list);
		listViewPromotion.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Promotion promotion = Promotion.getPromotions().get(arg2);
		Intent intent = new Intent(this, PromotionDetailActivity.class);
		intent.putExtra("promotion", promotion);
		startActivity(intent);
	}
	
	public void generateCoupon(){
		Promotion promotion = null;
		
		HttpRequest req = new HttpRequest();
		req.set(HttpRequest.Url.promotions, null, HttpRequest.HttpMethod.GET);		
		HttpTask task = new HttpTask() {
			
			@Override
			public void doWork(Response response) {
				if(response != null) {
					Gson gson = new Gson();
					Type collectionType = new TypeToken<List<Promotion>>(){}.getType();
					List<Promotion> promotions = gson.fromJson(response.getBody(), collectionType);
					Promotion.setPromotions(promotions);
					
					promotionAdapter = new PromotionAdapter(PromotionActivity.this, R.layout.promotion_adapter, Promotion.getPromotions());
					listViewPromotion.setAdapter(promotionAdapter);
				}				
			}
		};
		task.set(this, req).execute();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}