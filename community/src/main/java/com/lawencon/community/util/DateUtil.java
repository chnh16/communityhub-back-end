package com.lawencon.community.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	private static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	
	public static LocalDateTime strToDate(String date) {
		final LocalDateTime dateFormat = LocalDateTime.parse(date, formatter);
		return dateFormat;
	}
	
	public static LocalDate strToLocalDate(String date) {
		final LocalDate dateFormat = LocalDate.parse(date, formatterDate);
		return dateFormat;
	}

	public static String dateToStr(LocalDateTime date) {
		final String dateFormat = date.format(formatter);
		return dateFormat;
	}
	
	public static String localDateToStr(LocalDate date) {
		final String dateFormat = date.format(formatterDate);
		return dateFormat;
	}
	
	public static LocalDateTime dateNow(LocalDateTime date) {
		final LocalDateTime dateFormat = LocalDateTime.now(); 
		return dateFormat;
	}
}
