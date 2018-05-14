package org.hashcode.minitabor.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("d.M.yyyy");

	public static Date parseDate(String date) throws Exception {
		return FORMAT_DATE.parse(date);
	}

	public static String formatDate(Date date) {
		if (date != null)
			return FORMAT_DATE.format(date);
		return null;
	}
}
