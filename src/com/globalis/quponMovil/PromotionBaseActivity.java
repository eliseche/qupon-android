package com.globalis.quponMovil;

import java.util.Hashtable;
import com.globalis.entities.Promotion;
import com.globalis.extensions.IOnCustomClickListener;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.globalis.quponMovil.CouponDetailActivity.CouponJson;
import com.globalis.utils.Utils;
import com.google.gson.Gson;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PromotionBaseActivity extends MenuEventActivity implements OnItemClickListener, IOnCustomClickListener {
	protected ListView listViewPromotion;
	protected PromotionAdapter promotionAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}
	
	protected void initViews() {
		listViewPromotion = (ListView) findViewById(R.id.promotion_list);
		listViewPromotion.setOnItemClickListener(this);
		SharedPreferences pref = getSharedPreferences(GlobalPreference.getLogin(), MODE_PRIVATE);
		String email = pref.getString(GlobalPreference.getLoginEmail(), null);
		String password = pref.getString(GlobalPreference.getLoginPassword(), null);
		if (!Utils.isNullOrEmpty(email) && !Utils.isNullOrEmpty(password)) {
			new LoginActivity().log(this, email, password);
		}		
	}
	
	/*
	 * This event fires when the user clicks on a promotion. It shows
	 * the promotion detail   
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Promotion promotion = Promotion.getPromotions().get(arg2);
		Intent intent = new Intent(this, PromotionDetailActivity.class);
		intent.putExtra("promotion", promotion);
		startActivity(intent);		
	}	
	
	/*
	 * This event fires when the user clicks on generate button
	 * on any of the displayed promotions. It tries to generate a coupon, if
	 * successful, then it shows the coupon detail
	 */	
	@Override
	public void OnCustomClick(View view, int position) {
		if (LoginActivity.isLogged(this)) {
			final Promotion promotion = Promotion.getPromotions().get(position);
			int promID = promotion.getPromotion().getId();

			HttpRequest req = new HttpRequest();
			Hashtable<String, String> params = new Hashtable<String, String>();
			params.put("auth_token", GlobalPreference.getToken());
			req.set(HttpRequest.Url.getCoupons(promID), params, HttpRequest.HttpMethod.POST);
			HttpTask task = new HttpTask() {
				@Override
				public void doWork(Response response) {
					if (response != null) {
						Gson gson = new Gson();
						CouponJson coupon = gson.fromJson(response.getBody(), CouponJson.class);
						if (coupon.getState().equals(getResources().getString(R.string.success))) {
							Intent intentCouponDetail = new Intent(PromotionBaseActivity.this, CouponDetailActivity.class);
							intentCouponDetail.putExtra("coupon", coupon.getCoupon());
							startActivity(intentCouponDetail);
						} else {
							Toast.makeText(PromotionBaseActivity.this, coupon.getMessage(), Toast.LENGTH_LONG).show();
						}
					}
				}
			};
			task.set(PromotionBaseActivity.this, req).execute();
		} else {
			Toast.makeText(this, R.string.must_be_logged, Toast.LENGTH_LONG);
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}		
	}
}