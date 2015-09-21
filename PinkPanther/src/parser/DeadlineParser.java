package parser;

import java.time.LocalDate;
import java.time.LocalTime;

public class DeadlineParser {

	public DeadlineParser() {
		
	}
	
	public LocalDate findDueDate(String[] taskDetails) {
		int day = 1;
		int month = 1;
		int year = 2005;
		
		// some logic
		
		LocalDate dueDate = LocalDate.of(year, month, day);
		return dueDate;
	}
	
	public LocalTime findDueTime(String[] taskDetails){
		int hour = 0;
		int min = 0;
		// some logic
		
		LocalTime dueTime = LocalTime.of(hour, min);
		return dueTime;
	}

}
