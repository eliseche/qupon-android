package com.globalis.quponMovil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import com.globalis.entities.JsonResponse;
import com.globalis.entities.User;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.globalis.quponMovil.CouponDetailActivity.CouponJson;
import com.globalis.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SignupActivity extends Activity implements OnClickListener {

	static final int DATE_DIALOG_ID = 1;

	private String email, password, rePassword, firstName, lastName,
			phoneNumber, zipCode, gender, birthday;
	private int userID;
	private EditText txtEmail, txtPassword, txtRePassword, txtFirstName,
			txtLastName, txtPhoneNumber, txtZipCode;
	private Spinner spnGender;
	// birthday data
	private int mYear, mMonth, mDay;
	private Button btnSignUp, btnBirthdaySel;

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
		// set the gender in Masculino of Femenino
		if (user.getGender().equals(
				getResources().getStringArray(R.array.gender)[1])) {
			user.setGender("Masculino");
		} else if (user.getGender().equals(
				getResources().getStringArray(R.array.gender)[2])) {
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
								getResources().getString(R.string.success), Toast.LENGTH_LONG)
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
		// set the gender in Masculino of Femenino
		if (user.getGender().equals(
				getResources().getStringArray(R.array.gender)[1])) {
			user.setGender("Masculino");
		} else if (user.getGender().equals(
				getResources().getStringArray(R.array.gender)[2])) {
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
							Toast.makeText(
									getApplicationContext(),
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
		// this one value is automatic birthday = mYear+"-"+mMonth+"-"+mDay;
		if (birthday == null)
			birthday = Utils.parseDate(new Date(mYear - 1900, mMonth, mDay));
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
				gender, password, rePassword, birthday);
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
		btnBirthdaySel = (Button) findViewById(R.id.signup_btn_sel_birthday);
		final Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -13);
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DATE);
		btnBirthdaySel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
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
		Date birthdayDate = Utils.toDate(user.getBirthday());
		mYear = birthdayDate.getYear()+1900;
		mMonth = birthdayDate.getMonth();
		mDay = birthdayDate.getDate();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			birthday = Utils.parseDate(new Date(year - 1900, monthOfYear,
					dayOfMonth));
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
		}
	};

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
		private User user;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
	}
}