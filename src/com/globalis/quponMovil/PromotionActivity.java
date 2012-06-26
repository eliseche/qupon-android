package com.globalis.quponMovil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.globalis.entities.Branch;
import com.globalis.entities.Coupon;
import com.globalis.entities.Promotion;
import com.globalis.extensions.IOnCustomClickListener;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.globalis.network.HttpRequest.HttpMethod;
import com.globalis.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class PromotionActivity extends Activity implements OnItemClickListener,
		IOnCustomClickListener {
	private ListView listViewPromotion;
	private PromotionAdapter promotionAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion);

		initViews();

		HttpRequest req = new HttpRequest();
		req.set(HttpRequest.Url.getPromotion(), null,
				HttpRequest.HttpMethod.GET);
		HttpTask task = new HttpTask() {
			@Override
			public void doWork(Response response) {
				if (response != null) {
					Gson gson = new Gson();
					Type collectionType = new TypeToken<List<PromotionJson>>() {
					}.getType();
					List<PromotionJson> promotionsJson = gson.fromJson(
							response.getBody(), collectionType);
					List<Promotion> promotions = new ArrayList<Promotion>();
					for (PromotionJson promotionJson : promotionsJson) {
						promotionJson.getPromotion().setImagePath(promotionJson.getImage_url());
						promotionJson.getPromotion().setTags(promotionJson.getTagList());
						promotions.add(promotionJson.getPromotion());
					}
					Promotion.setPromotions(promotions);

					promotionAdapter = new PromotionAdapter(
							PromotionActivity.this, R.layout.promotion_adapter,
							Promotion.getPromotions());
					listViewPromotion.setAdapter(promotionAdapter);
				}
			}
		};
		task.set(PromotionActivity.this, req).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		SharedPreferences pref = getSharedPreferences(
				GlobalPreference.getLogin(), MODE_PRIVATE);
		String email = pref.getString(GlobalPreference.getLoginEmail(), null);
		String password = pref.getString(GlobalPreference.getLoginPassword(),
				null);

		if (Utils.isNullOrEmpty(email) && Utils.isNullOrEmpty(password)) {
			menu.findItem(R.id.menu_settings).setVisible(false);
		} else {
			menu.findItem(R.id.menu_login).setVisible(false);
		}

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_login:
			Intent intentLogin = new Intent(this, LoginActivity.class);
			startActivity(intentLogin);
			break;
		case R.id.menu_settings:
			Intent intentSettings = new Intent(this, SettingsActivity.class);
			startActivity(intentSettings);
			break;
		default:
			break;
		}
		return true;
	}

	private void initViews() {
		listViewPromotion = (ListView) findViewById(R.id.promotion_list);
		listViewPromotion.setOnItemClickListener(this);
		SharedPreferences pref = getSharedPreferences(
				GlobalPreference.getLogin(), MODE_PRIVATE);
		String email = pref.getString(GlobalPreference.getLoginEmail(), null);
		String password = pref.getString(GlobalPreference.getLoginPassword(),
				null);
		if (email != null && !email.equals("") && password != null) {
			new LoginActivity().log(this, email, password);
		}
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Promotion promotion = Promotion.getPromotions().get(arg2);
		Intent intent = new Intent(this, PromotionDetailActivity.class);
		intent.putExtra("promotion", promotion);
		startActivity(intent);
	}

	public void OnCustomClick(View view, int position) {
		if (LoginActivity.isLogged(this)) {
			final Promotion promotion = Promotion.getPromotions().get(position);
			int promID = promotion.getId();

			HttpRequest req = new HttpRequest();
			Hashtable<String, String> params = new Hashtable<String, String>();
			params.put("auth_token", GlobalPreference.getToken());
			req.set(HttpRequest.Url.getCoupons(promID), params,
					HttpRequest.HttpMethod.POST);
			HttpTask task = new HttpTask() {
				@Override
				public void doWork(Response response) {
					if (response != null) {
						// Log.i("exito", response.getBody());
						Gson gson = new Gson();
						CouponJson couponJson = gson.fromJson(response.getBody(),
								CouponJson.class);
						couponJson.getCoupon().setPromotion(promotion);
						Intent intent = new Intent(getApplicationContext(),
								CouponDetailActivity.class);
						intent.putExtra("coupon", couponJson.getCoupon());
						startActivity(intent);
					} else
						Log.i("falla", "ninguna respuesta del sitio");
				}
			};
			task.set(PromotionActivity.this, req).execute();
		} else {
			Toast.makeText(this, R.string.must_be_logged, Toast.LENGTH_SHORT);
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(getResources().getString(R.string.exit));
			builder.setCancelable(false);
			builder.setPositiveButton(getResources().getString(R.string.yes),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			builder.setNegativeButton(getResources().getString(R.string.no),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		}

		return super.onKeyDown(keyCode, event);
	}

	private class PromotionJson implements Serializable{
		
		private Promotion promotion;
		private String image_url;
		@SerializedName("tag_list")
		private List<String> tagList;
		private List<Branch> branch;
		
		public Promotion getPromotion() {
			return promotion;
		}
		public void setPromotion(Promotion promotion) {
			this.promotion = promotion;
		}
		public String getImage_url() {
			return image_url;
		}
		public void setImage_url(String image_url) {
			this.image_url = image_url;
		}
		public List<String> getTagList() {
			return tagList;
		}
		public void setTagList(List<String> tagList) {
			this.tagList = tagList;
		}
		public List<Branch> getBranch() {
			return branch;
		}
		public void setBranch(List<Branch> branch) {
			this.branch = branch;
		}
		
	}
	
	private class CouponJson implements Serializable{
		
		private Coupon coupon;
		private String state;
		
		public Coupon getCoupon() {
			return coupon;
		}
		public void setCoupon(Coupon coupon) {
			this.coupon = coupon;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		
	}

}