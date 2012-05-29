package com.globalis.quponMovil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SignupActivity extends Activity implements OnClickListener {
	private Button btnSignIn;
	private TextView btnSignUp;	
	
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
			break;
		default:
			break;
		}		
	}
	
	private void initViews() {
		btnSignIn = (Button)findViewById(R.id.login_btn_sign_in);		
		btnSignIn.setOnClickListener(this);
		btnSignUp = (TextView)findViewById(R.id.login_btn_sign_up);		
		btnSignUp.setOnClickListener(this);		
	}	
}