package com.globalis.quponMovil;


import java.util.Hashtable;

import com.globalis.entities.Person;
import com.globalis.entities.User;
import com.globalis.network.HttpRequest;
import com.globalis.network.HttpTask;
import com.globalis.network.Response;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends Activity implements OnClickListener {
	private Button btnSignIn;
	private String uUser,uPassword,uRePassword,uName,uLastName,
	uPhoneNumber,uZipCode,uGender,uEmail;
	
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
			if(validateFields()){
				User newUser = createUser();
				saveUser(newUser);
				//to return the data of the user to the previous activity
			}
			break;
		default:
			break;
		}		
	}

	private void collectUserData() {
		uUser = ((TextView)findViewById(R.id.signup_txt_user)).getText().toString();
		uPassword = ((TextView)findViewById(R.id.signup_txt_password)).getText().toString();
		uRePassword = ((TextView)findViewById(R.id.signup_txt_repassword)).getText().toString();
		uName = ((TextView)findViewById(R.id.signup_txt_name)).getText().toString();
		uLastName = ((TextView)findViewById(R.id.signup_txt_last_name)).getText().toString();
		uPhoneNumber = ((TextView)findViewById(R.id.signup_txt_phone_number)).getText().toString();
		uZipCode = ((TextView)findViewById(R.id.signup_txt_zip_code)).getText().toString();
		uGender = ((Spinner)findViewById(R.id.signup_spn_gender)).getSelectedItem().toString();
		uEmail = ((Spinner)findViewById(R.id.signup_txt_email)).getSelectedItem().toString();
	}

	private boolean validateFields() {
		
		if(uUser.equals("")){
			Toast.makeText(this,getResources().getString(R.string.signup_error_msg_complete_user), Toast.LENGTH_LONG).show();
			return false;
		}
		if(!uPassword.equals(uRePassword)){
			Toast.makeText(this,getResources().getString(R.string.signup_error_msg_unmatching_pass), Toast.LENGTH_LONG).show();
			return false;
		}
		if(uName.equals("")){
			Toast.makeText(this,getResources().getString(R.string.signup_error_msg_complete_name), Toast.LENGTH_LONG).show();
			return false;
		}
		if(uLastName.equals("")){
			Toast.makeText(this,getResources().getString(R.string.signup_error_msg_complete_last_name), Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	
	private User createUser() {
		Person p = new Person(uName, uLastName, uZipCode, uPhoneNumber, uGender, uEmail);
		User u = new User(uName, uPassword, p , null, null, null);
		return u;
	}
	
	public void saveUser(User user) {
		HttpRequest req = new HttpRequest();
		Hashtable<String, String> initialParams = new Hashtable<String, String>();
		initialParams.putAll(user.getHashtable());
		initialParams.putAll(user.getPerson().getHashtable());
		req.set(HttpRequest.Url.signup, initialParams, HttpRequest.HttpMethod.POST);		
		HttpTask task = new HttpTask() {
			
			@Override
			public void doWork(Response response) {
				if(response != null) {
					Gson gson = new Gson();
					SignupActivity signupActivity = (SignupActivity)getApplicationContext();
					LoginResponse loginResponse = gson.fromJson(response.getBody(),LoginResponse.class);
					if(response.isValidStatusCode()){
						Toast.makeText(signupActivity, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
						Intent intent = new Intent();                   
                        intent.putExtra("USERNAME", signupActivity.uName);
                        intent.putExtra("PASSWORD", signupActivity.uPassword);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
					}else{
						Toast.makeText(signupActivity, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
					}
				}				
			}
		};
		task.set(this, req).execute();
	}

	private void initViews() {
		btnSignIn = (Button)findViewById(R.id.signup_btn_sign_up);		
		btnSignIn.setOnClickListener(this);
		
		Spinner s1 = (Spinner) findViewById(R.id.signup_spn_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
	}
	
	private class LoginResponse{
		private String message;
		
		public String getMessage() {
			return message;
		}
	}
	
}