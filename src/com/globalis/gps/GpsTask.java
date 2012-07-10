package com.globalis.gps;

import com.globalis.quponMovil.R;
import com.globalis.utils.Utils;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

public abstract class GpsTask extends AsyncTask<String, Void, Location>  {	
	private Context context;
	private ProgressDialog dialog;		
	private boolean hasGpsData = false;
	private Location myLocation = null;
	
	public GpsTask set(Context context) {
		this.context = context;		
		return this;
	}
	
	@Override
	protected void onPreExecute() {	
		super.onPreExecute();
		GpsLocation gpsLocation = new GpsLocation() {			
			@Override
			public void gotLocation(Location location) {
				hasGpsData = true;
				myLocation = location;								
			}
		};
		gpsLocation.getLocation(context);		
		dialog = new ProgressDialog(context);
		dialog.setMessage("Retrieving GPS data...");
		dialog.show();			
	}
	
	@Override
	protected Location doInBackground(String... params) {		
		try {			
			if(Utils.isNetworkAvailable(context)) {
				Long time = System.currentTimeMillis();
				while(!hasGpsData && System.currentTimeMillis() - time < 60000) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return myLocation;								
			}
			else {				
				throw new Exception(context.getResources().getString(R.string.exception_no_network));				
			}
		} catch (Exception e) {			
			cancel(true);
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Location location) {	
		super.onPostExecute(location);
		dialog.dismiss();		
		doWork(location);
	}
	
	public abstract void doWork(Location location);
}
