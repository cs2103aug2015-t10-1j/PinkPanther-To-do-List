/* @@author A0126473E */
package parser;

import common.Pair;
import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DateRangeParser implements Parser {
	
	private AddStringParser asp;
	
	private static final int SINGLE_DATE_TIME_FOUND = 1;
	private static final int DATE_TIME_RANGE_FOUND = 2;
	
	private static final String MESSAGE_ASSERTION_NULL = 
			 "Logic error. Null input is passed in as parameter!";
	
	private static final Logger log = Logger.getLogger("DateRangeParser");
	
	public DateRangeParser(AddStringParser asp) {
		this.asp = asp;
	}
	
	/**
	 * Return a Pair of a dates that specify a range.
	 * null is returned when no valid date is found.
	 * 
	 * @param commandContent	What the user enters.
	 * @return	A range of dates.
	 */
	public Pair<LocalDate, LocalDate> parse(String commandContent) {
		asp.clearStores();
		assert commandContent != null : MESSAGE_ASSERTION_NULL;
		
		int validDates = asp.countValidDates(commandContent);
		if (validDates == SINGLE_DATE_TIME_FOUND) {
			if (asp.getStartDate() != null) {
				log.log(Level.INFO, "A single date detected. Returning Pair to logic.");
				return new Pair<LocalDate, LocalDate>(asp.getStartDate(), asp.getStartDate());
			}
			log.log(Level.INFO, "A deadline detected. Returning Pair to logic.");
			return new Pair<LocalDate, LocalDate>(asp.getEndDate(), asp.getEndDate()); 
		} else if (validDates == DATE_TIME_RANGE_FOUND) {
			log.log(Level.INFO, "Range of dates detected. Returning Pair to logic.");
			return new Pair<LocalDate, LocalDate>(asp.getStartDate(), asp.getEndDate());
		}
		log.log(Level.INFO, "No dates detected. Returning null to logic.");
		return null;
	}
}
