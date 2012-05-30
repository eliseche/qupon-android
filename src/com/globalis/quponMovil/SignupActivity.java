package com.globalis.quponMovil;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SignupActivity extends Activity implements OnClickListener {
	private Button btnSignIn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
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
		btnSignIn = (Button)findViewById(R.id.signup_btn_sign_up);		
		btnSignIn.setOnClickListener(this);
		
		Spinner s1 = (Spinner) findViewById(R.id.signup_spn_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
	}	
}