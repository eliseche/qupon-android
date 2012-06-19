package com.globalis.quponMovil;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceClickListener {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		initView();
	}	

	public boolean onPreferenceClick(Preference preference) {
		if(preference.getKey().equals("settings_btn_modify_info_user")) {
			Intent intentSignup = new Intent(this, SignupActivity.class);
			startActivity(intentSignup);			
		}
		
		return true;
	}
	
	private void initView() {
		Preference btnModifyInfoUser = findPreference("settings_btn_modify_info_user");
		btnModifyInfoUser.setOnPreferenceClickListener(this);
	}
}
