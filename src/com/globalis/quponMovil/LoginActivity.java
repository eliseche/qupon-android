package com.globalis.quponMovil;

import java.util.Hashtable;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.google.gson.Gson;
import android.app.Activity;
import android.content.Context;
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

	private void initViews() {
		txtUser = (EditText) findViewById(R.id.login_txt_user);
		txtPassword = (EditText) findViewById(R.id.login_txt_password);
		btnSignIn = (Button) findViewById(R.id.login_btn_sign_in);
		btnSignIn.setOnClickListener(this);
		btnSignUp = (TextView) findViewById(R.id.login_btn_sign_up);
		btnSignUp.setOnClickListener(this);
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
					LoginResponse loginResponse = gson.fromJson(
							response.getBody(), LoginResponse.class);
					if (response.isValidStatusCode()) {
						GlobalPreference.setToken(loginResponse.getToken());

						SharedPreferences pref = context.getSharedPreferences(
								GlobalPreference.getLogin(), MODE_PRIVATE);
						String emailOld = pref.getString(
								GlobalPreference.getLoginEmail(), null);
						String passwordOld = pref.getString(
								GlobalPreference.getLoginPassword(), null);
						if (emailOld == null || emailOld.equals("")
								|| passwordOld == null) {
							
							context.getSharedPreferences(GlobalPreference.getLogin(),
									MODE_PRIVATE)
									.edit()
									.putString(
											GlobalPreference.getLoginEmail(),
											user)
									.putString(
											GlobalPreference.getLoginPassword(),
											password).commit();
							Toast.makeText(LoginActivity.this,
									loginResponse.getMessage(),
									Toast.LENGTH_LONG).show();
							finish();
						}
					} else {
						Toast.makeText(LoginActivity.this,
								loginResponse.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
				}
			}
		};
		task.set(context, req).execute();
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