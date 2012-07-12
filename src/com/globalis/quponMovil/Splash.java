package com.globalis.quponMovil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class Splash extends Activity {
	private boolean active = true;
	private int splashTime = 2500;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread splashThread = new Thread() {
			public void run() {
				try {
					int waited  = 0;
					while(active && (waited < splashTime)) {
						sleep(100);
						if(active) {
							waited += 100;
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					finish();
					startActivity(new Intent(Splash.this, MainActivity.class));					
				}				
			};			
		};
		splashThread.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			active = false;
		}
		return true;
	}
}