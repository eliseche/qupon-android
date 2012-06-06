package com.globalis.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utils {
	public enum Resolution {
		LOW,
		MEDIUM,
		HIGH,
		XHIGH
	}
	
	public static String parseDate(String startDate, Context context) {
		String parsedDate = null;
		try {
			SimpleDateFormat initialDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
			Date date = initialDateFormat.parse(startDate);
			DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);			
			parsedDate = dateFormat.format(date);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parsedDate;
	}
	
	public static Resolution getResolution(Context context) {
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();		
		wm.getDefaultDisplay().getMetrics(metrics);
		switch (metrics.densityDpi) {
		case DisplayMetrics.DENSITY_LOW:			
			return Resolution.LOW;
		case DisplayMetrics.DENSITY_MEDIUM:
			return Resolution.MEDIUM;
		case DisplayMetrics.DENSITY_HIGH:
			return Resolution.HIGH;
		case DisplayMetrics.DENSITY_XHIGH:
			return Resolution.XHIGH;
		default:
			return null;			
		}
	}
	
	public static boolean isNetworkAvailable(Context context) {
		boolean haveWifiConnection = false;
		boolean haveMobileConnection = false;
		
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : networkInfo) {
			if(ni.getType() == ConnectivityManager.TYPE_WIFI) {
				if(ni.isConnected()) {
					haveWifiConnection = true;					
				}				
			}
			if(ni.getType() == ConnectivityManager.TYPE_MOBILE) {
				if(ni.isConnected()) {
					haveMobileConnection = true;					
				}				
			}
		}
		
		return haveWifiConnection || haveMobileConnection;
	}
}