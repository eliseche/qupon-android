package com.globalis.quponMovil;

import java.io.Serializable;
import java.util.Hashtable;

import com.globalis.entities.JsonResponse;
import com.globalis.entities.User;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.globalis.quponMovil.CouponDetailActivity.CouponJson;
import com.globalis.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignupActivity extends Activity implements OnClickListener {
	private String email, password, rePassword, firstName, lastName,
			phoneNumber, zipCode, gender;
	private int userID;
	private EditText txtEmail, txtPassword, txtRePassword, txtFirstName,
			txtLastName, txtPhoneNumber, txtZipCode;
	private Spinner spnGender;
	private Button btnSignUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		initViews();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup_btn_sign_up:
			collectUserData();
			if (validateFields()) {
				if (!LoginActivity.isLogged(this)) {
					User newUser = createUser();
					saveUser(newUser);
				} else {
					User user = createUser();
					editUser(user);
				}
			}
			break;
		default:
			break;
		}
	}

	public void saveUser(final User user) {
		//set the gender in Masculino of Femenino
		if (user.getGender().equals(getResources().getStringArray(R.array.gender)[1])) {
			user.setGender("Masculino");
		} else if (user.getGender().equals(getResources().getStringArray(R.array.gender)[2])) {
			user.setGender("Femenino");
		}
		
		UserJson userJson = new UserJson();
		userJson.setUser(user);
		Gson gson = new Gson();
		String userString = gson.toJson(userJson).toString();
		
		HttpRequest req = new HttpRequest();
		req.set(HttpRequest.Url.getSignup(), userString,
				HttpRequest.HttpMethod.POSTJSON);
		HttpTask task = new HttpTask() {
			@Override
			public void doWork(Response response) {
				if (response != null) {
					Gson gson = new Gson();
					SignUpResponse signUpResponse = gson.fromJson(
							response.getBody(), SignUpResponse.class);
					if (signUpResponse.equals(getResources().getString(
							R.string.success))) {
						Toast.makeText(SignupActivity.this,
								signUpResponse.getMessage(), Toast.LENGTH_LONG)
								.show();
						Intent intent = new Intent();
						intent.putExtra("username", user.getEmail());
						intent.putExtra("password", user.getPassword());
						setResult(Activity.RESULT_OK, intent);
						finish();
					} else {
						Toast.makeText(SignupActivity.this,
								signUpResponse.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
				}
			}
		};
		task.set(SignupActivity.this, req).execute();
	}

	private void editUser(User user) {
		//set the gender in Masculino of Femenino
		if (user.getGender().equals(getResources().getStringArray(R.array.gender)[1])) {
			user.setGender("Masculino");
		} else if (user.getGender().equals(getResources().getStringArray(R.array.gender)[2])) {
			user.setGender("Femenino");
		}
		
		
		UserEdit userEdit = new UserEdit();
		userEdit.setAuthToken(GlobalPreference.getToken());
		userEdit.setUser(user);
		Gson gson = new Gson();
		String userJson = gson.toJson(userEdit).toString();
		HttpRequest req = new HttpRequest();
		req.set(HttpRequest.Url.getSignup(), userJson,
				HttpRequest.HttpMethod.PUT);
		HttpTask task = new HttpTask() {
			@Override
			public void doWork(Response response) {
				if (response != null) {
					if (response.isValidStatusCode()) {
						Gson gson = new Gson();
						UserJson signUpResponse = gson.fromJson(
								response.getBody(), UserJson.class);
						if (signUpResponse.getState().equals(
								getResources().getString(R.string.success))) {
							Toast.makeText(getApplicationContext(),
									getApplicationContext().getResources()
											.getString(R.string.success_edit),
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(),
									signUpResponse.getMessage(),
									Toast.LENGTH_SHORT).show();
						}

					} else {
						Toast.makeText(getApplicationContext(),
								response.getBody(), Toast.LENGTH_SHORT).show();
					}

				}
			}
		};
		task.set(SignupActivity.this, req).execute();
	}

	private void collectUserData() {
		email = String.valueOf(txtEmail.getText());
		password = String.valueOf(txtPassword.getText());
		rePassword = String.valueOf(txtRePassword.getText());
		firstName = String.valueOf(txtFirstName.getText());
		lastName = String.valueOf(txtLastName.getText());
		phoneNumber = String.valueOf(txtPhoneNumber.getText());
		zipCode = String.valueOf(txtZipCode.getText());
		gender = String.valueOf(spnGender.getSelectedItem());
	}

	private boolean validateFields() {
		if (Utils.isNullOrEmpty(email)) {
			Toast.makeText(
					this,
					getResources().getString(
							R.string.signup_error_msg_complete_user),
					Toast.LENGTH_LONG).show();
			return false;
		}
		if (Utils.isNullOrEmpty(password) || Utils.isNullOrEmpty(rePassword)
				|| !password.equals(rePassword)) {
			Toast.makeText(
					this,
					getResources().getString(
							R.string.signup_error_msg_unmatching_pass),
					Toast.LENGTH_LONG).show();
			return false;
		}
		if (Utils.isNullOrEmpty(firstName)) {
			Toast.makeText(
					this,
					getResources().getString(
							R.string.signup_error_msg_complete_first_name),
					Toast.LENGTH_LONG).show();
			return false;
		}
		if (Utils.isNullOrEmpty(lastName)) {
			Toast.makeText(
					this,
					getResources().getString(
							R.string.signup_error_msg_complete_last_name),
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	private User createUser() {
		User user = new User(email, firstName, lastName, zipCode, phoneNumber,
				gender, password, rePassword);
		return user;
	}

	private void initViews() {
		txtEmail = (EditText) findViewById(R.id.signup_txt_username);
		txtPassword = (EditText) findViewById(R.id.signup_txt_password);
		txtRePassword = (EditText) findViewById(R.id.signup_txt_repassword);
		txtFirstName = (EditText) findViewById(R.id.signup_txt_first_name);
		txtLastName = (EditText) findViewById(R.id.signup_txt_last_name);
		txtPhoneNumber = (EditText) findViewById(R.id.signup_txt_phone_number);
		txtZipCode = (EditText) findViewById(R.id.signup_txt_zip_code);
		btnSignUp = (Button) findViewById(R.id.signup_btn_sign_up);
		btnSignUp.setOnClickListener(this);
		spnGender = (Spinner) findViewById(R.id.signup_spn_gender);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.gender, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnGender.setAdapter(adapter);

		if (LoginActivity.isLogged(this)) {
			HttpRequest req = new HttpRequest();
			Hashtable<String, String> params = new Hashtable<String, String>();
			params.put("auth_token", GlobalPreference.getToken());
			req.set(HttpRequest.Url.getUserInfo(), params,
					HttpRequest.HttpMethod.GET);
			HttpTask task = new HttpTask() {
				@Override
				public void doWork(Response response) {
					if (response != null) {
						Gson gson = new Gson();
						UserJson user = gson.fromJson(response.getBody(),
								UserJson.class);
						if (user.getState().equals(
								getResources().getString(R.string.success))) {
							completeWithUserData(user.getUser());
						} else {
							Toast.makeText(SignupActivity.this,
									user.getMessage(), Toast.LENGTH_LONG)
									.show();
						}
					}
				}

			};
			task.set(SignupActivity.this, req).execute();
		}
	}

	private void completeWithUserData(User user) {
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				GlobalPreference.getLogin(), MODE_PRIVATE);
		String password = pref.getString(GlobalPreference.getLoginPassword(),
				null);

		txtEmail.setText(user.getEmail());
		txtPassword.setText(password);
		txtRePassword.setText(password);
		txtFirstName.setText(user.getFirstName());
		txtLastName.setText(user.getLastName());
		txtPhoneNumber.setText(user.getPhoneNumber());
		txtZipCode.setText(user.getZipCode());
		String gender = user.getGender();
		if (gender.equals("Masculino") || gender.equals("Male")) {
			spnGender.setSelection(1);
		} else if (gender.equals("Femenino") || gender.equals("Female")) {
			spnGender.setSelection(2);
		} else {
			spnGender.setSelection(0);
		}
	}

	private class UserJson extends JsonResponse {
		private User user;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

	}

	private class UserEdit implements Serializable {
		@SerializedName("auth_token")
		private String authToken;
		private User user;

		public String getAuthToken() {
			return authToken;
		}

		public void setAuthToken(String authToken) {
			this.authToken = authToken;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

	}

	private class SignUpResponse extends JsonResponse {

	}
}