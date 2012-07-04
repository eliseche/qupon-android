package com.globalis.quponMovil;

import java.util.Hashtable;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.globalis.utils.Utils;
import com.google.gson.Gson;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private Button btnSignIn;
	private TextView btnSignUp;
	private EditText txtUser, txtPassword;

	public static final int REGISTRATION_SUCCESS = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		initViews();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_sign_in:
			String user = txtUser.getText().toString();
			String password = txtPassword.getText().toString();
			log(this, user, password);
			break;
		case R.id.login_btn_sign_up:
			Intent intentSignup = new Intent(this, SignupActivity.class);
			startActivityForResult(intentSignup, REGISTRATION_SUCCESS);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REGISTRATION_SUCCESS) {
			if (resultCode == RESULT_OK) {
				String username = data.getStringExtra("username");
				String password = data.getStringExtra("password");
				txtUser.setText(username);
				txtPassword.setText(password);
			} else if (resultCode == RESULT_CANCELED) {
				// nothing to do for the moment
			}
		}
	}	
	
	public static boolean isLogged(final Context context){
		SharedPreferences pref = context.getSharedPreferences(GlobalPreference.getLogin(), MODE_PRIVATE);
		String email = pref.getString(GlobalPreference.getLoginEmail(), null);
		String password = pref.getString(GlobalPreference.getLoginPassword(), null);
		if (!Utils.isNullOrEmpty(email) && !Utils.isNullOrEmpty(password) &&
				!Utils.isNullOrEmpty(GlobalPreference.getToken())) {
			return true;
		}
		return false;
	}

	public void log(final Context context,final String user, final String password) {
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("email", user);
		params.put("password", password);
		HttpRequest req = new HttpRequest();
		req.set(HttpRequest.Url.getLogin(), params, HttpRequest.HttpMethod.POST);
		HttpTask task = new HttpTask() {
			@Override
			public void doWork(Response response) {
				if (response != null) {
					Gson gson = new Gson();
					LoginResponse loginResponse = gson.fromJson(response.getBody(), LoginResponse.class);
					if (response.isValidStatusCode()) {
						if (!isLogged(context)) {							
							context.getSharedPreferences(GlobalPreference.getLogin(), MODE_PRIVATE)
									.edit()
									.putString(GlobalPreference.getLoginEmail(), user)
									.putString(GlobalPreference.getLoginPassword(), password)
									.commit();
							GlobalPreference.setToken(loginResponse.getToken());
							Toast.makeText(context, getResources().getString(R.string.login_successfully), Toast.LENGTH_LONG).show();
							Intent intent = new Intent();
	                        setResult(Activity.RESULT_OK, intent);
	                        finish();
						}else{
							Intent intent = new Intent();
							setResult(Activity.RESULT_CANCELED, intent);
						}
					} else {
						Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}
		};
		task.set(context, req).execute();
	}
	
	public void logout(final Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(context.getResources().getString(R.string.logout));
		builder.setCancelable(false);
		builder.setPositiveButton(context.getResources().getString(R.string.yes),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						GlobalPreference.setToken(null);
						context.getSharedPreferences(GlobalPreference.getLogin(), MODE_PRIVATE)
						.edit()
						.putString(GlobalPreference.getLoginEmail(), null)
						.putString(GlobalPreference.getLoginPassword(), null)
						.commit();
					}
				});
		builder.setNegativeButton(context.getResources().getString(R.string.no),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void initViews() {
		txtUser = (EditText) findViewById(R.id.login_txt_user);
		txtPassword = (EditText) findViewById(R.id.login_txt_password);
		btnSignIn = (Button) findViewById(R.id.login_btn_sign_in);
		btnSignIn.setOnClickListener(this);
		btnSignUp = (TextView) findViewById(R.id.login_btn_sign_up);
		btnSignUp.setOnClickListener(this);
	}

	private class LoginResponse {
		private String token;
		private String message;

		public String getToken() {
			return token;
		}

		public String getMessage() {
			return message;
		}
	}
}