/* @@author A0126473E */
package parser;

import common.Pair;
import common.Auxiliary;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;

public class SingleDateParser implements Parser {
	
	private static final int INDEX_DAY = 0;
	private static final int INDEX_MONTH = 1;
	
	private static final String[] DATE_DELIMITERS = {"/", "-", " "};
	private static final List<String> DATE_FORMAT_SLASH = 
			Collections.unmodifiableList(Arrays.asList("d/M/yy", "dd/M/yy",
		    		"d/MM/yy", "dd/MM/yy", "d/MMM/yy", "dd/MMM/yy",  "d/MMMM/yy",
		    		"dd/MMMM/yy", "d/M/yyyy", "dd/M/yyyy", "d/MM/yyyy", "dd/MM/yyyy",
		    		"d/MMM/yyyy", "dd/MMM/yyyy", "d/MMMM/yyyy", "dd/MMMM/yyyy"));
	private static final List<String> DATE_FORMAT_DASH = 
			Collections.unmodifiableList(Arrays.asList("d-M-yy", "dd-M-yy",
		    		"d-MM-yy", "dd-MM-yy", "d-MMM-yy", "dd-MMM-yy", "d-MMMM-yy",
		    		"dd-MMMM-yy", "d-M-yyyy", "dd-M-yyyy", "d-MM-yyyy", "dd-MM-yyyy",
		    		"d-MMM-yyyy", "dd-MMM-yyyy", "d-MMMM-yyyy", "dd-MMMM-yyyy"));
	private static final List<String> DATE_FORMAT_SPACE = 
			Collections.unmodifiableList(Arrays.asList("d M yy", "dd M yy",
		    		"d MM yy", "dd MM yy", "d MMM yy", "dd MMM yy", "d MMMM yy", 
		    		"dd MMMM yy", "d M yyyy", "d MM yyyy", "d MM yyyy", "dd MM yyyy", 
		    		"d MMM yyyy", "dd MMM yyyy", "d MMMM yyyy", "dd MMMM yyyy"));
	private static final List<String> DAY_FORMAT = 
			Collections.unmodifiableList(Arrays.asList("EEE", "EEEE"));
	
	private static final String[] DATE_INDICATORS_ONE = {"TONIGHT", "NOW", "TODAY",
			"TOMORROW",};
	
	private static ArrayList<String> validDateFormats;
	private static ArrayList<String> validDayFormats;
	
	public SingleDateParser() {
		validDateFormats = new ArrayList<String>();
		validDayFormats = new ArrayList<String>();
		validDateFormats.addAll(DATE_FORMAT_SLASH);
		validDateFormats.addAll(DATE_FORMAT_DASH);
		validDateFormats.addAll(DATE_FORMAT_SPACE);
		validDayFormats.addAll(DAY_FORMAT);
	}
	
	public static void main (String[] args) {
		SingleDateParser sdp = new SingleDateParser();
		System.out.println(sdp.parse("next week"));
	}
	
	
	@SuppressWarnings("unchecked")
	/**
	 * Return a date based on user input.
	 * null is returned when user input is not a date.
	 * 
	 * @param date	What the user enters.
	 * @return	LocalDate object of the date that was entered.
	 */
	public LocalDate parse(String date) {
		
		// case: dates that contain a certain keyword
		if (isDateIndicator(date)) {
			return oneWordIndicatorParser(date);
		}

		if (hasDateIndicator(date)) {
			return twoWordIndicatorParser(date);
		}
		
		// case: dates without keywords
		Pair<String, Boolean>fixDateDetails = fixDate(date);
		String fixedDate = fixDateDetails.getFirst();
		for (String dateFormat : validDateFormats) {
			LocalDate parsedDate = compareDateFormat(fixedDate, dateFormat);
			if (parsedDate != null) {
				return parsedDate;
			}
		}
		
		// case: not a date
		return null;
	}
	
	private LocalDate compareDateFormat(String dateString, String pattern) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			LocalDate date = LocalDate.parse(dateString, formatter);
			return date;
		} catch (DateTimeException e) {
			// do logging
		}
		return null;
		
	}
	
	/**
	 * Return a Pair of a date ready for parsing and
	 * a boolean value of whether its year was appended.
	 * 
	 * @param date	A date String that the user enters.
	 * @return	A Pair with the first object as a fixed date String formatted
	 * 			in a way that the DateTimeFormatter accepts, and a boolean
	 * 			reflecting whether the year was appended.
	 */
	public Pair<String, Boolean> fixDate(String date) {
		String fixedDate;
		boolean hasAppendedYear = false;
		
		for (int i = 0; i < DATE_DELIMITERS.length; i++){	
			String[] dateDetails = date.split(DATE_DELIMITERS[i]);
			if (dateDetails.length > 1) {
				dateDetails = Auxiliary.trimStringArray(dateDetails);
				fixedDate = dateDetails[INDEX_DAY];
				
				// fix month
				dateDetails[INDEX_MONTH] = 
						dateDetails[INDEX_MONTH].substring(0,1).toUpperCase() 
							+ dateDetails[INDEX_MONTH].substring(1).toLowerCase();
				
				// fix year
				for (int j = 1; j < dateDetails.length; j++) {
					String appendedContent = DATE_DELIMITERS[i] + dateDetails[j];
					fixedDate += appendedContent;
				}
				
				if (dateDetails.length < 3 ) {
					String appendedYear = DATE_DELIMITERS[i] 
							+ String.valueOf(LocalDate.now().getYear());
					fixedDate += appendedYear;
					hasAppendedYear = true;
				}

				return new Pair<String, Boolean>(fixedDate, hasAppendedYear);
				
			}
		}
		
		return new Pair<String, Boolean>(date, hasAppendedYear);
	}
	
	private boolean isDateIndicator(String date) {
		for (int i = 0; i < DATE_INDICATORS_ONE.length; i++) {
			if (date.equalsIgnoreCase(DATE_INDICATORS_ONE[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	private LocalDate oneWordIndicatorParser(String date) {
		date = date.toUpperCase();
		switch (date) {
			case "TODAY":
			case "TONIGHT":
			case "NOW":
				return LocalDate.now();
			case "TOMORROW":
				return LocalDate.now().plusDays(1);
			default:
		}
		return null;
	}
	
	private boolean hasDateIndicator(String date) {
		return date.toUpperCase().contains("THIS") || date.toUpperCase().contains("NEXT");
	}

	private LocalDate twoWordIndicatorParser (String date) {
		String precursor = Auxiliary.getFirstWord(date).toUpperCase();
		String content = Auxiliary.removeFirstWord(date).toUpperCase().trim();
		if (content.length() > 1) {
			content = content.substring(0,1) + content.substring(1).toLowerCase();
		}	
		LocalDate parsedDay = parseDayOfWeek(content);
		if (precursor.equals("THIS")) {
			if (parsedDay != null && parsedDay.isBefore(LocalDate.now())) {
				return parsedDay.plusWeeks(1);
			}
			return parseDayOfWeek(content);
		} else if (precursor.equals("NEXT")) {
			if (content.equals("Week")) {	
				return LocalDate.now().plusWeeks(1);
			} else if (content.equals("Month")) {
				return LocalDate.now().plusMonths(1);
			} else if (content.equals("Year")) {
				return LocalDate.now().plusYears(1);
			} else if (parsedDay != null) {
				return parsedDay.plusWeeks(1);
			}
		}
		return null;
	}
	
	private LocalDate parseDayOfWeek(String dayOfWeek) {
		DayOfWeek day = null;
		int dayIndicator = 0;
		for (String dayFormat : DAY_FORMAT) {
			day = compareDayFormat(dayOfWeek, dayFormat);
			if (day != null) {
				dayIndicator = day.getValue();
			}
		}
		if (dayIndicator == 0) {
			return null;
		}
		
		int dayToday = LocalDate.now().getDayOfWeek().getValue();
		int differenceInDays = dayIndicator - dayToday;
		if (differenceInDays >= 0) {
			return LocalDate.now().plusDays(differenceInDays);
		} else if (differenceInDays < 0) {
			return LocalDate.now().minusDays(Math.abs(differenceInDays));
		}
		return null;
	}
	
	private DayOfWeek compareDayFormat(String dateString, String pattern) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			DayOfWeek day = DayOfWeek.from(formatter.parse(dateString));
			return day;
		} catch (DateTimeException e) {
			// do logging
			return null;
		}
	}
}
