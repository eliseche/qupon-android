package com.globalis.quponMovil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
			break;
		case R.id.login_btn_sign_up:
			Intent intentSignup = new Intent(this, SignupActivity.class);
			startActivityForResult(intentSignup, REGISTRATION_SUCCESS);			
			break;
		default:
			break;
		}		
	}
	
	private void initViews() {
		txtUser = (EditText)findViewById(R.id.login_txt_user);
		txtPassword = (EditText)findViewById(R.id.login_txt_password);
		btnSignIn = (Button)findViewById(R.id.login_btn_sign_in);		
		btnSignIn.setOnClickListener(this);
		btnSignUp = (TextView)findViewById(R.id.login_btn_sign_up);		
		btnSignUp.setOnClickListener(this);		
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
            }
            else if(resultCode == RESULT_CANCELED) {
            	// nothing to do for the moment
            }
		}
	}
}