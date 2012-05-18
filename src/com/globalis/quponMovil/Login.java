package com.globalis.quponMovil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {
	private Button btnLogin,btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		initViews();		
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_log:
				login();
				//Toast toast = Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT);
				//toast.show();
			break;
		case R.id.login_btn_register:
				register();
			break;
		default:
			break;
		}		
	}
	
	private void initViews() {
		btnLogin = (Button)findViewById(R.id.login_btn_log);
		btnLogin.setOnClickListener(this);	
		btnRegister = (Button)findViewById(R.id.login_btn_register);
		btnRegister.setOnClickListener(this);
	}

	private void login(){
		
	}
	
	private void register(){
		
	}
}