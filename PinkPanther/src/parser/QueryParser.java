/* @@author A0126473E */
package parser;

import common.Pair;
import common.Auxiliary;
import common.Display;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class QueryParser implements Parser {
	
	// attributes
	private SingleDateParser sdp;
	
	// indices and indicators
	private static final int INDEX_KEYWORD = 0;
	private static final int INDEX_INDEX = 1;
	
	// messages
	private static final String MESSAGE_ASSERTION_NULL = 
			"Logic error. Null input is passed in as parameter!";
	private static final String MESSAGE_INVALID_COMMA= 
			"Invalid input format.";
	private static final String MESSAGE_INVALID_KEYWORD = 
			"Invalid keyword or date entered.";
	private static final String MESSAGE_INVALID_INDEX =
			"Invalid index entered.";
	private static final String MESSAGE_ASSERTION_COMMA = 
			"Unaccounted case where #commas detected is not 1.";
	private static final String MESSAGE_LOG_INVALID_FORMAT = 
			"Invalid input format. Returning null to logic.";
	private static final String MESSAGE_LOG_INVALID_KEYWORD = 
			"No valid keyword detected. Returning null to logic.";
	private static final String MESSAGE_LOG_INVALID_INDEX = 
			"Invalid index detected. Returning null to logic.";
	private static final String MESSAGE_LOG_PARSE_SUCCESS = 
			"Returning Task indices of %1$s on %2$s to logic.";
	private static final String MESSAGE_LOG_NON_NUMERICAL_INDEX = 
			"Non numerical index detected in list of indices.";

	// other constants
	private static final Logger log = Logger.getLogger("QueryParser");
	
	/**
	 * Constructor
	 */
	public QueryParser() {
		sdp = new SingleDateParser();
	}
	
	/**
	 * Return a Pair of a date and an ArrayList of indices.
	 * null is returned when no valid date or index is found.
	 * 
	 * @param commandContent	What the user enters.
	 * @return	A certain date and the list of indices for that date.
	 */
	public Pair<LocalDate,ArrayList<Integer>> parse(String commandContent) {
		assert commandContent != null : MESSAGE_ASSERTION_NULL;
		String[] userInfo = commandContent.split(",");
		userInfo = Auxiliary.trimStringArray(userInfo);
		
		// there should only be 1 comma
		if (userInfo.length != 2) {
			Display.setFeedBack(MESSAGE_INVALID_COMMA);
			log.log(Level.INFO, MESSAGE_LOG_INVALID_FORMAT);
			return null;
		}
		assert userInfo.length == 2 : MESSAGE_ASSERTION_COMMA;
		
		LocalDate keyword = generateKeyword(userInfo[INDEX_KEYWORD]);
		ArrayList<Integer> indexList = generateIndexList(userInfo[INDEX_INDEX]);
		
		if (isValidKeyword(userInfo[INDEX_KEYWORD]) 
				&& isValidIndex(userInfo[INDEX_INDEX], indexList)) {
			log.log(Level.INFO, String.format(MESSAGE_LOG_PARSE_SUCCESS, 
					indexList, keyword));
			return new Pair<LocalDate, ArrayList<Integer>>(keyword, indexList);
		} else if (!isValidKeyword(userInfo[INDEX_KEYWORD])) {
			Display.setFeedBack(MESSAGE_INVALID_KEYWORD);
			log.log(Level.INFO, MESSAGE_LOG_INVALID_KEYWORD);
			return null;
		} else if (!isValidIndex(userInfo[INDEX_INDEX], indexList)) {
			Display.setFeedBack(MESSAGE_INVALID_INDEX);
			log.log(Level.INFO, MESSAGE_LOG_INVALID_INDEX);
			return null;
		}
		return null;
	}
	
	private LocalDate generateKeyword (String keyword) {
		return sdp.parse(keyword);
	}
	
	private ArrayList<Integer> generateIndexList(String userInfo) {
		assert userInfo != null : MESSAGE_ASSERTION_NULL;
		String[] listOfIndices = userInfo.split(" ");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = INDEX_KEYWORD; i < listOfIndices.length; i++) {
			try {
				list.add(Integer.valueOf(listOfIndices[i]));
			} catch (NumberFormatException e) {
				log.log(Level.FINE, MESSAGE_LOG_NON_NUMERICAL_INDEX);
				return null;
			}
		}	
		return list;
	}
	
	private boolean isValidKeyword (String keyword) {
		return keyword.equalsIgnoreCase("undated") || sdp.parse(keyword) != null;
	}
	
	private boolean isValidIndex (String indices, ArrayList<Integer> indexList) {
		return indices.equalsIgnoreCase("all") || indexList != null;
	}
}

