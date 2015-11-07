/* @@author A0126473E */
package parser;

import common.Pair;
import java.time.LocalDate;

public class DateRangeParser implements Parser {
	
	private AddStringParser asp;
	
	private static final int SINGLE_DATE_TIME_FOUND = 1;
	private static final int DATE_TIME_RANGE_FOUND = 2;
	
	public DateRangeParser(AddStringParser asp) {
		this.asp = asp;
	}
	
	@SuppressWarnings("unchecked")
	public Pair<LocalDate, LocalDate> parse(String commandContent) {
		asp.clearStores();
		int validDates = asp.countValidDates(commandContent);
		if (validDates == SINGLE_DATE_TIME_FOUND) {
			return new Pair<LocalDate, LocalDate>(asp.getStartDate(), asp.getStartDate()); 
		} else if (validDates == DATE_TIME_RANGE_FOUND) {
			return new Pair<LocalDate, LocalDate>(asp.getStartDate(), asp.getEndDate());
		} 
		return null;
	}
}
