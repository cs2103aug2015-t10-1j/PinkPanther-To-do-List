package parser;

import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SingleDateParser {
	
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
	
	private static ArrayList<String> validDateFormats;
	
	public SingleDateParser() {
		validDateFormats = new ArrayList<String>();
		validDateFormats.addAll(DATE_FORMAT_SLASH);
		validDateFormats.addAll(DATE_FORMAT_DASH);
		validDateFormats.addAll(DATE_FORMAT_SPACE);
	}
	
	public LocalDate parse(String date) {
		
		// if date contains a certain keyword, refer to list(s) and parse separately
		
		// for dates
		date = fixDate(date);
		for (String dateFormat : validDateFormats) {
			LocalDate parsedDate = compareDateFormat(date, dateFormat);
			if (parsedDate != null) {
				if (parsedDate.isBefore(LocalDate.now())) {
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
	
	private String fixDate(String date) {
		String fixedDate;
		
		for (int i = 0; i < DATE_DELIMITERS.length; i++){	
			String[] dateDetails = date.split(DATE_DELIMITERS[i]);
			if (dateDetails.length > 1) {
				dateDetails = AddStringParser.trimStringArray(dateDetails);
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
				}

				return fixedDate;
				
			}
		}
		
		return null;
	}
}

