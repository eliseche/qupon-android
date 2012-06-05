package com.globalis.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

public class Utils {
	public static String parseDate(String startDate, Context ctx) {
		String parsedDate = null;
		try {
			SimpleDateFormat initialDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'hh:mm:ss'Z'");
			Date date = initialDateFormat.parse(startDate);

			DateFormat dateFormat = android.text.format.DateFormat
					.getDateFormat(ctx);
			parsedDate = dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}
}