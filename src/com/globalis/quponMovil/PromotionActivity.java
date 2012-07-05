package com.globalis.quponMovil;

import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.List;
import com.globalis.entities.Coupon;
import com.globalis.entities.Promotion;
import com.globalis.extensions.IOnCustomClickListener;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.globalis.quponMovil.CouponDetailActivity.CouponJson;
import com.globalis.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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

	public static final int LOGIN_SUCCESS = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion);

		initViews();
		getPromotions();
	}

	@Override
	/**
	 * It creates a menu for this activity
	 * */
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	/**
	 * It prepares the items on the menu. If the user is logged in it makes
	 * visible the settings and logout options, if not it shows the login option
	 * */
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if (LoginActivity.isLogged(this)) {
			menu.removeItem(R.id.menu_login);
			menu.add(0, R.id.menu_logout, Menu.NONE, R.string.menu_logout)
					.setIcon(R.drawable.unlock);
			menu.add(0, R.id.menu_settings, Menu.NONE, R.string.menu_settings)
					.setIcon(R.drawable.wheel);
			menu.add(0, R.id.menu_my_coupons, Menu.NONE, R.string.menu_coupones)
					.setIcon(R.drawable.coupones);
		} else {
			menu.add(0, R.id.menu_login, Menu.NONE, R.string.menu_login)
					.setIcon(R.drawable.lock);
			menu.removeItem(R.id.menu_logout);
			menu.removeItem(R.id.menu_settings);
			menu.removeItem(R.id.menu_my_coupons);
		}

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	/**
	 * It handles every menu item event
	 * */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_login:
			Intent intentLogin = new Intent(this, LoginActivity.class);
			startActivity(intentLogin);
			break;
		case R.id.menu_my_coupons:
			Intent intentMyCoupons = new Intent(this, CouponActivity.class);
			startActivity(intentMyCoupons);
			break;
		case R.id.menu_settings:
			Intent intentSettings = new Intent(this, SettingsActivity.class);
			startActivity(intentSettings);
			break;
		case R.id.menu_logout:
			LoginActivity loginActivity = new LoginActivity();
			loginActivity.logout(this);
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * It's the event that triggers when the user clicks on a promotion. It show
	 * that promotion detail
	 * */
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Promotion promotion = Promotion.getPromotions().get(arg2);
		Intent intent = new Intent(this, PromotionDetailActivity.class);
		intent.putExtra("promotion", promotion);
		startActivity(intent);
	}

	/**
	 * It's the event that triggers when the user clicks on the generate button
	 * on any of the displayed promotions. It tries to generate a coupon, if
	 * successful then it shows the coupon detail
	 * */
	public void OnCustomClick(View view, int position) {
		if (LoginActivity.isLogged(this)) {
			final Promotion promotion = Promotion.getPromotions().get(position);
			int promID = promotion.getPromotion().getId();

			HttpRequest req = new HttpRequest();
			Hashtable<String, String> params = new Hashtable<String, String>();
			params.put("auth_token", GlobalPreference.getToken());
			req.set(HttpRequest.Url.getCoupons(promID), params,
					HttpRequest.HttpMethod.POST);
			HttpTask task = new HttpTask() {
				@Override
				public void doWork(Response response) {
					if (response != null) {
						Gson gson = new Gson();
						CouponJson coupon = gson.fromJson(response.getBody(),
								CouponJson.class);
						if (coupon.getState().equals(
								getResources().getString(R.string.success))) {
							Intent intentCouponDetail = new Intent(
									PromotionActivity.this,
									CouponDetailActivity.class);
							intentCouponDetail.putExtra("coupon", coupon.getCoupon());
							startActivity(intentCouponDetail);
						} else {
							Toast.makeText(PromotionActivity.this,
									coupon.getMessage(), Toast.LENGTH_LONG)
									.show();
						}
					}

				}
			};
			task.set(PromotionActivity.this, req).execute();
		} else {
			Toast.makeText(this, R.string.must_be_logged, Toast.LENGTH_LONG);
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}

	@Override
	/**
	 * It handles the going-back-event on this activity which is the first on the stack
	 * so in other words it triggers when the user closes the application.
	 * It creates a dialog asking the user if he's really sure about
	 * closing the application.
	 * */
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

	/**
	 * Initializes the view filling the listViewPromotion field, creating an
	 * event for itemclicking on it and finally logging the user if there's an
	 * email and a password stored in the SharedPreferences
	 * 
	 * @param None
	 * 
	 * @return Nothing
	 * */
	private void initViews() {
		listViewPromotion = (ListView) findViewById(R.id.promotion_list);
		listViewPromotion.setOnItemClickListener(this);
		SharedPreferences pref = getSharedPreferences(
				GlobalPreference.getLogin(), MODE_PRIVATE);
		String email = pref.getString(GlobalPreference.getLoginEmail(), null);
		String password = pref.getString(GlobalPreference.getLoginPassword(),
				null);
		if (!Utils.isNullOrEmpty(email) && !Utils.isNullOrEmpty(password)) {
			new LoginActivity().log(this, email, password);
		}
	}

	/**
	 * Searches for all active promotions
	 * 
	 * @param None
	 * 
	 * @return Nothing
	 * */
	private void getPromotions() {
		HttpRequest req = new HttpRequest();
		req.set(HttpRequest.Url.getPromotion(), null,
				HttpRequest.HttpMethod.GET);
		HttpTask task = new HttpTask() {
			@Override
			public void doWork(Response response) {
				if (response != null) {
					Gson gson = new Gson();
					Type collectionType = new TypeToken<List<Promotion>>() {
					}.getType();
					List<Promotion> promotions = gson.fromJson(
							response.getBody(), collectionType);
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
}