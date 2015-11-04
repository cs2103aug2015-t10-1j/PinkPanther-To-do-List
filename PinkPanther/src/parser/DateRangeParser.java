/* @@author CS */
package parser;

import common.Pair;
import java.time.LocalDate;

public class DateRangeParser implements Parser {
	
	private AddStringParser asp;
	
	public DateRangeParser(AddStringParser asp) {
		this.asp = asp;
	}
	
	@SuppressWarnings("unchecked")
	public Pair<LocalDate, LocalDate> parse(String commandContent) {
		asp.clearStores();
		int validDates = asp.countValidDates(commandContent);
		if (validDates == 1) {
			return new Pair<LocalDate, LocalDate>(asp.getStartDate(), asp.getStartDate()); 
		} else if (validDates == 2) {
			return new Pair<LocalDate, LocalDate>(asp.getStartDate(), asp.getEndDate());
		} 
		return null;
	}

}
