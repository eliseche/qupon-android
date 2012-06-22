package com.globalis.quponMovil;

import java.lang.reflect.Type;
import java.util.List;
import com.globalis.entities.Promotion;
import com.globalis.extensions.IOnCustomClickListener;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.globalis.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.app.Activity;
import android.app.AlertDialog;
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
		String email = pref
				.getString(GlobalPreference.getLoginEmail(), null);
		String password = pref.getString(
				GlobalPreference.getLoginPassword(), null);
		if (email != null && !email.equals("") && password != null) {
			new LoginActivity().log(this,email, password);
		}
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Promotion promotion = Promotion.getPromotions().get(arg2);
		Intent intent = new Intent(this, PromotionDetailActivity.class);
		intent.putExtra("promotion", promotion);
		startActivity(intent);
	}

	public void OnCustomClick(View view, int position) {
		Promotion promotion = Promotion.getPromotions().get(position);
		int promID = promotion.getId();

		HttpRequest req = new HttpRequest();
		req.set(HttpRequest.Url.getCoupon(promID), null,
				HttpRequest.HttpMethod.POST);
		HttpTask task = new HttpTask() {
			@Override
			public void doWork(Response response) {
				if (response != null) {
					Log.i("exito", response.getBody());
				}
				Log.i("falla", "ninguna respuesta del sitio");
			}
		};
		task.set(PromotionActivity.this, req).execute();
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
}