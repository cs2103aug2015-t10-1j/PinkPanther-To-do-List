/* @@author A0126473E */
package parser;

import common.Pair;
import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DateRangeParser implements Parser {
	
	// attributes
	private AddStringParser asp;
	
	// indices and indicators
	private static final int SINGLE_DATE_TIME_FOUND = 1;
	private static final int DATE_TIME_RANGE_FOUND = 2;
	
	// messages
	private static final String MESSAGE_ASSERTION_NULL = 
			 "Logic error. Null input is passed in as parameter!";
	private static final String MESSAGE_LOG_LEGIT_DATE = 
			"A single date detected. Returning Pair of range %1$s to %1$s to logic.";
	private static final String MESSAGE_LOG_DATE = 
			"A deadline detected. Returning Pair of range %1$s to %1$s to logic anyways.";
	private static final String MESSAGE_LOG_DATE_RANGE = 
			"Range of dates detected. Returning Pair of range %1$s to %2$s to logic.";
	private static final String MESSAGE_LOG_PARSE_FAIL = 
			"No dates detected. Returning null to logic.";
	
	// other constants
	private static final Logger log = Logger.getLogger("DateRangeParser");
	
	/**
	 * Constructor
	 */
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
				log.log(Level.INFO, String.format(MESSAGE_LOG_LEGIT_DATE,
						asp.getStartDate()));
				return new Pair<LocalDate, LocalDate>(asp.getStartDate(), asp.getStartDate());
			}
			log.log(Level.INFO, String.format(MESSAGE_LOG_DATE, asp.getEndDate()));
			return new Pair<LocalDate, LocalDate>(asp.getEndDate(), asp.getEndDate()); 
		} else if (validDates == DATE_TIME_RANGE_FOUND) {
			log.log(Level.INFO, String.format(MESSAGE_LOG_DATE_RANGE, asp.getStartDate(),
					asp.getEndDate()));
			return new Pair<LocalDate, LocalDate>(asp.getStartDate(), asp.getEndDate());
		}
		log.log(Level.INFO, MESSAGE_LOG_PARSE_FAIL);
		return null;
	}
}
