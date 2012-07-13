package com.globalis.quponMovil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuEventActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if (LoginActivity.isLogged(this)) {
			menu.removeItem(R.id.menu_login);
			menu.add(0, R.id.menu_logout, Menu.NONE, R.string.menu_logout).setIcon(R.drawable.unlock);
			menu.add(0, R.id.menu_settings, Menu.NONE, R.string.menu_settings).setIcon(R.drawable.wheel);
		} else {
			menu.add(0, R.id.menu_login, Menu.NONE, R.string.menu_login).setIcon(R.drawable.lock);
			menu.removeItem(R.id.menu_logout);
			menu.removeItem(R.id.menu_settings);
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
		case R.id.menu_logout:
			LoginActivity loginActivity = new LoginActivity();
			loginActivity.logout(this);
			break;
		default:
			break;
		}
		return true;
	}
	
	/*
	 * Captures the back event to prevent exiting the application
	 * (because it's the first activity of the stack).
	 * Creates a dialog asking the user if he's sure about
	 * closing the application
	 */
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