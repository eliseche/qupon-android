package com.globalis.quponMovil;

import java.util.Hashtable;
import com.globalis.entities.User;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.globalis.utils.Utils;
import com.google.gson.Gson;
import android.app.Activity;
import android.content.Intent;
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
	private EditText txtEmail, txtPassword, txtRePassword, txtFirstName, txtLastName,
		txtPhoneNumber, txtZipCode;
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
			if(validateFields()) {
				User newUser = createUser();
				saveUser(newUser);
			}
			break;
		default:
			break;
		}		
	}
	
	public void saveUser(final User user) {
		HttpRequest req = new HttpRequest();
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.putAll(user.getHashtable());
		req.set(HttpRequest.Url.getSignup(), params, HttpRequest.HttpMethod.POST);		
		HttpTask task = new HttpTask() {			
			@Override
			public void doWork(Response response) {
				if(response != null) {
					Gson gson = new Gson();
					RegisterResponse registerResponse = gson.fromJson(response.getBody(), RegisterResponse.class);
					if(response.isValidStatusCode()) {
						Toast.makeText(SignupActivity.this, registerResponse.getMessage(), Toast.LENGTH_LONG).show();						
						Intent intent = new Intent();                   
                        intent.putExtra("username", user.getEmail());
                        intent.putExtra("password", user.getPassword());
                        setResult(Activity.RESULT_OK, intent);
                        finish();
					}
					else{
						Toast.makeText(SignupActivity.this, registerResponse.getMessage(), Toast.LENGTH_LONG).show();
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
		if(Utils.isNullOrEmpty(email)) {
			Toast.makeText(this, getResources().getString(R.string.signup_error_msg_complete_user), Toast.LENGTH_LONG).show();
			return false;
		}
		if(Utils.isNullOrEmpty(password) || Utils.isNullOrEmpty(rePassword) || !password.equals(rePassword)) {
			Toast.makeText(this, getResources().getString(R.string.signup_error_msg_unmatching_pass), Toast.LENGTH_LONG).show();
			return false;
		}
		if(Utils.isNullOrEmpty(firstName)) {
			Toast.makeText(this,getResources().getString(R.string.signup_error_msg_complete_first_name), Toast.LENGTH_LONG).show();
			return false;
		}
		if(Utils.isNullOrEmpty(lastName)) {
			Toast.makeText(this, getResources().getString(R.string.signup_error_msg_complete_last_name), Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	
	private User createUser() {
		User user = new User(email, firstName, lastName, zipCode, phoneNumber, gender, password, rePassword);		 
		return user;
	}	

	private void initViews() {
		txtEmail = (EditText)findViewById(R.id.signup_txt_username);
		txtPassword = (EditText)findViewById(R.id.signup_txt_password);
		txtRePassword = (EditText)findViewById(R.id.signup_txt_repassword);
		txtFirstName = (EditText)findViewById(R.id.signup_txt_first_name);
		txtLastName = (EditText)findViewById(R.id.signup_txt_last_name);
		txtPhoneNumber = (EditText)findViewById(R.id.signup_txt_phone_number);
		txtZipCode = (EditText)findViewById(R.id.signup_txt_zip_code);
		btnSignUp = (Button)findViewById(R.id.signup_btn_sign_up);		
		btnSignUp.setOnClickListener(this);		
		spnGender = (Spinner)findViewById(R.id.signup_spn_gender);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnGender.setAdapter(adapter);
	}
	
	private class RegisterResponse {
		private String message;
		
		public String getMessage() {
			return message;
		}
	}
}