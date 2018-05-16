package de.hska.iiwi.fittslaw.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hska.iiwi.fittslaw.Constants;

public class FileNameCreator {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String getFileName(Date date) {
		return Constants.TITLE + DATE_FORMAT.format(date) + ".csv";
	}
	
}
