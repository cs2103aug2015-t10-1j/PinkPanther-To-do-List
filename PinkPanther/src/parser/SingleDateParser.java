package parser;

import common.Pair;
import common.Auxiliary;

import java.time.ZoneId;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;

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
	
	public LocalDate parse(String date) {
		
		// if date contains a certain keyword, refer to list(s) and parse separately
		if (isDateIndicator(date)) {
			return oneWordIndicatorParser(date);
		}

		if (hasDateIndicator(date)) {
			return twoWordIndicatorParser(date);
		}
		
		// for dates
		Pair<String, Boolean>fixDateDetails = fixDate(date);
		String fixedDate = fixDateDetails.getFirst();
		for (String dateFormat : validDateFormats) {
			LocalDate parsedDate = compareDateFormat(fixedDate, dateFormat);
			if (parsedDate != null) {
				if (parsedDate.isBefore(LocalDate.now()) && fixDateDetails.getSecond() ) {
					parsedDate = parsedDate.plusYears(1);
				}
				return parsedDate;
			}
		}
		
		return null;
	}
	
	private LocalDate compareDateFormat(String dateString, String pattern) {
		
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			LocalDate date = LocalDate.parse(dateString, formatter);
			return date;
			
		} catch (Exception e) {
			// some exception
		}
		return null;
		
	}
	
	private Pair<String, Boolean> fixDate(String date) {
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
		if (precursor.equals("THIS")) {
			if (parseDayOfWeek(content).isBefore(LocalDate.now())) {
				return parseDayOfWeek(content).plusWeeks(1);
			}
			return parseDayOfWeek(content);
		} else if (precursor.equals("NEXT")) {
			if (content.equals("WEEK")) {
				return LocalDate.now().plusWeeks(1);
			} else if (content.equals("MONTH")) {
				return LocalDate.now().plusMonths(1);
			} else if (content.equals("YEAR")) {
				return LocalDate.now().plusYears(1);
			} else {
				return parseDayOfWeek(content).plusWeeks(1);
			}
		}
		return null;
	}
	
	private LocalDate parseDayOfWeek(String dayOfWeek) {
		LocalDate date = null;
		int dayIndicator = 0;
		for (String dayFormat : DAY_FORMAT) {
			date = compareDayFormat(dayOfWeek, dayFormat);
			if (date != null) {
				dayIndicator = date.getDayOfWeek().getValue();
			} else {
				return null;
			}
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
	
	private LocalDate compareDayFormat(String dateString, String pattern) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		try {
			Date date = sdf.parse(dateString);
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		} catch (Exception e) {
			// nothing
		}

		return null;
	}
}
