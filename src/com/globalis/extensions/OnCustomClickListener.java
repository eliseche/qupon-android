package com.globalis.extensions;

import android.view.View;
import android.view.View.OnClickListener;

public class OnCustomClickListener implements OnClickListener  {
	private int position;
	private IOnCustomClickListener callback;
	
	// Pass in the callback (this will be the Activity) and the row position
	public OnCustomClickListener(IOnCustomClickListener callback, int position) {
		this.callback = callback;
		this.position = position;
	}
	
	// The onClick method wich has "NO" position information
	public void onClick(View view) {
		callback.OnCustomClick(view, position);		
	}
}
