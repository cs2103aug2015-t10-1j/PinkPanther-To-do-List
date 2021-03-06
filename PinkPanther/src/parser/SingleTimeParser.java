/* @@author A0126473E */
package parser;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleTimeParser implements Parser{
	
	// Class attributes
	private static ArrayList<String> validTimeFormats;
	
	// messages
	private static final String MESSAGE_ASSERTION_NULL = 
			 "Logic error. Null input is passed in as parameter!";
	private static final String MESSAGE_LOG_PARSE_SUCCESS = 
			"Successful parsing. Returning a LocalTime.";
	private static final String MESSAGE_LOG_PARSE_FAIL = 
			"Not a time. Returning null.";
	private static final String MESSAGE_LOG_INVALID_FORMAT = 
			"Not an accepted format of time. Returning null.";
	
	// word/format lists
	private static final List<String> TIME_FORMATS= 
			Collections.unmodifiableList(Arrays.asList("h:mma", "hh:mma", 
					"h.mma", "hh.mma", "hmma", "hhmma", "H:mm", "HH:mm", 
					"H.mm", "HH.mm", "Hmm", "HHmm", "ha", "hha"));
	private static final String[] TIME_INDICATORS = {"TONIGHT", "NOW", "MORNING",
			"AFTERNOON", "NIGHT"};
	
	// other constants
	private static final Logger log = Logger.getLogger("SingleTimeParser");
	
	public SingleTimeParser() {
		validTimeFormats = new ArrayList<String>();
		validTimeFormats.addAll(TIME_FORMATS);
	}

	/**
	 * Return a time based on user input.
	 * null is returned when user input is not a time.
	 * 
	 * @param time	What the user enters.
	 * @return	LocalTime object of the time that was entered.
	 */
	public LocalTime parse(String time) {
		assert time != null : MESSAGE_ASSERTION_NULL;
		time = time.toUpperCase();
		
		// case: times that contain a certain keyword
		for (int i = 0; i < TIME_INDICATORS.length; i++) {
			if (time.equals(TIME_INDICATORS[i])) {
				log.log(Level.FINE, MESSAGE_LOG_PARSE_SUCCESS);
				return parseTimeWord(time);
			}
		}
		
		// case: times without keywords
		for (String timeFormat : validTimeFormats) {
			LocalTime parsedTime = compareTimeFormat(time, timeFormat);
			if (parsedTime != null) {
				log.log(Level.FINE, MESSAGE_LOG_PARSE_SUCCESS);
				return parsedTime;
			}
		}
		
		// case: not a time
		log.log(Level.FINE, MESSAGE_LOG_PARSE_FAIL);
		return null;
	}
	
	private LocalTime compareTimeFormat(String timeString, String pattern) {
		assert pattern != null : MESSAGE_ASSERTION_NULL;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			LocalTime time = LocalTime.parse(timeString, formatter);
			return time;
		} catch (DateTimeException e) {
			log.log(Level.FINER, MESSAGE_LOG_INVALID_FORMAT);
			return null;
		}

	}
	
	private LocalTime parseTimeWord (String time) {
		switch (time) {
			case "NOW":
				return LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
			case "TONIGHT":
			case "NIGHT":
				return LocalTime.of(19,0);
			case "MORNING":
				return LocalTime.of(9,0);
			case "AFTERNOON":
				return LocalTime.of(12,0);
			default:
		}
		return null;
	}
}
