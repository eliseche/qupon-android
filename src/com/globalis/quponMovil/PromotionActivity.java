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
import android.widget.ListView;

public class PromotionActivity extends Activity {
	private ListView listViewPromotion;	
	private PromotionAdapter promotionAdapter; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotions);
		
		initViews();
		
		HttpTask task = new HttpTask() {
			
			@Override
			public void doWork(Response response) {
				if(response != null) {
					Gson gson = new Gson();
					Type collectionType = new TypeToken<List<Promotion>>(){}.getType();
					List<Promotion> promotions = gson.fromJson(response.getBody(), collectionType);
					Promotion.setPromotions(promotions);
					
					promotionAdapter = new PromotionAdapter(getApplicationContext(), R.layout.promotions_adapter, Promotion.getPromotions());
					listViewPromotion.setAdapter(promotionAdapter);
				}				
			}
		};
		task.setContext(this).execute();
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
		listViewPromotion = (ListView)findViewById(R.id.promotions_lst);
	}
}