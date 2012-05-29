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
		btnSignIn = (Button)findViewById(R.id.login_btn_sign_in);		
		btnSignIn.setOnClickListener(this);
		btnSignUp = (TextView)findViewById(R.id.login_btn_sign_up);		
		btnSignUp.setOnClickListener(this);	
		/*
		Spinner s1 = (Spinner) findViewById(R.id.);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        showToast("Spinner1: position=" + position + " id=" + id);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showToast("Spinner1: unselected");
                    }
                });*/
	}	
}