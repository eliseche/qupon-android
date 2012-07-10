package com.globalis.gps;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public abstract class GpsLocation {
	private Timer timer;
	private LocationManager locationManager;	
	private boolean gps_enabled = false;
	private boolean network_enabled = false;	
	
	public boolean getLocation(Context context) {
		if(locationManager == null) {
			locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		}
		
		try {
			gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		try {
			network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!gps_enabled && !network_enabled) {
			return false;
		}
		
		if(gps_enabled) {			
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);			
		}
		if(network_enabled) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
		}
		
		timer = new Timer();
		timer.schedule(new LastLocation(), 40000);		
		
		return true;
	}

	// Handles GPS events
	LocationListener locationListenerGps = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {			
			timer.cancel();
			removeUpdates();
			gotLocation(location);			
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
		
		@Override
		public void onProviderEnabled(String provider) {}
		
		@Override
		public void onProviderDisabled(String provider) {}
	};
	
	// Handles Network events
	LocationListener locationListenerNetwork = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {			
			timer.cancel();
			removeUpdates();
			gotLocation(location);
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
		
		@Override
		public void onProviderEnabled(String provider) {}
		
		@Override
		public void onProviderDisabled(String provider) {}
	};
	
	private void removeUpdates() {
		locationManager.removeUpdates(locationListenerGps);
		locationManager.removeUpdates(locationListenerNetwork);
	}
	
	// If we get no information from the GPS or Network, timer ends and we get the last position recorded
	class LastLocation extends TimerTask {
		private Location locationGps = null;
		private Location locationNetwork = null;
		
		@Override
		public void run() {			
			removeUpdates();
			
			if(gps_enabled) {
				locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);				
			}
			if(network_enabled) {
				locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
			
			// If there are both values use the last one
			if(locationGps != null && locationNetwork != null) {
				if(locationGps.getTime() > locationNetwork.getTime()) {
					gotLocation(locationGps);
				}
				else {
					gotLocation(locationNetwork);
				}
				return;
			}
			
			if(locationGps != null) {
				gotLocation(locationGps);
				return;				
			}			
			else if(locationNetwork != null) {
				gotLocation(locationNetwork);
				return;
			}
			else {
				gotLocation(null);				
			}
		}
	}	
	
	public abstract void gotLocation(Location location);
}