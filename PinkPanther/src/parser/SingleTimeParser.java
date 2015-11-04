/* @@author CS */
package parser;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SingleTimeParser implements Parser{
	
	private static final List<String> TIME_FORMATS= 
			Collections.unmodifiableList(Arrays.asList("h:mma", "hh:mma", 
					"h.mma", "hh.mma", "hmma", "hhmma", "H:mm", "HH:mm", 
					"H.mm", "HH.mm", "Hmm", "HHmm", "ha", "hha"));
	private static final String[] TIME_INDICATORS = {"TONIGHT", "NOW", "MORNING",
			"AFTERNOON", "NIGHT"};
	
	private static ArrayList<String> validTimeFormats;
	
	public SingleTimeParser() {
		validTimeFormats = new ArrayList<String>();
		validTimeFormats.addAll(TIME_FORMATS);
	}

	@SuppressWarnings("unchecked")
	public LocalTime parse(String time) {
		
		time = time.toUpperCase();
		
		// if time contains a certain keyword, refer to a list and parse separately
		for (int i = 0; i < TIME_INDICATORS.length; i++) {
			if (time.equals(TIME_INDICATORS[i])) {
				return parseTimeWord(time);
			}
		}
		
		// otherwise parse it as a numerical time
		for (String timeFormat : validTimeFormats) {
			LocalTime parsedTime = compareTimeFormat(time, timeFormat);
			if (parsedTime != null) {
				return parsedTime;
			}
		}
		
		return null;
	}
	
	private LocalTime compareTimeFormat(String timeString, String pattern) {
		
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			LocalTime time = LocalTime.parse(timeString, formatter);
			return time;
			
		//If DateTimeException is caught
		} catch (DateTimeException e) {
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
