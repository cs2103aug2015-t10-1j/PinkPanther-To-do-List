package parser;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SingleTimeParser {
	
	private static final List<String> TIME_FORMATS= 
			Collections.unmodifiableList(Arrays.asList("h:mma", "hh:mma", 
					"h.mma", "hh.mma", "hmma", "hhmma", "H:mm", "HH:mm", 
					"H.mm", "HH.mm", "Hmm", "Hhmm", "ha", "hha"));
	
	private static ArrayList<String> validTimeFormats;
	
	public SingleTimeParser() {
		validTimeFormats = new ArrayList<String>();
		validTimeFormats.addAll(TIME_FORMATS);
	}

	public LocalTime parse(String time) {
		
		time = time.toUpperCase();
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
			
		} catch (Exception e) {
			// some exception
		}
		return null;
	}
}
