/* @@author A0126473E */
package parser;

import common.Pair;
import common.Auxiliary;
import common.Display;

import java.time.LocalDate;
import java.util.ArrayList;

public class QueryParser implements Parser {
	
	private static final int INDEX_KEYWORD = 0;
	private static final int INDEX_INDEX = 1;
	
	private static final SingleDateParser sdp = new SingleDateParser();
	
	/**
	 * Return a Pair of a date and an ArrayList of indices.
	 * null is returned when no valid date or index is found.
	 * 
	 * @param commandContent	What the user enters.
	 * @return	A certain date and the list of indices for that date.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pair parse(String commandContent) {
		String[] userInfo = commandContent.split(",");
		userInfo = Auxiliary.trimStringArray(userInfo);
		
		// there should only be 1 comma
		if (userInfo.length != 2) {
			Display.setFeedBack("Invalid input format. Please enter only 1 comma.");
			return null;
		}
		
		LocalDate keyword = generateKeyword(userInfo[INDEX_KEYWORD]);
		ArrayList<Integer> indexList = generateIndexList(userInfo[INDEX_INDEX]);
		
		if (isValidKeyword(userInfo[INDEX_KEYWORD]) 
				&& isValidIndex(userInfo[INDEX_INDEX], indexList)) {
			return new Pair<LocalDate, ArrayList<Integer>>(keyword, indexList);
		} else if (!isValidKeyword(userInfo[INDEX_KEYWORD])) {
			Display.setFeedBack("Invalid keyword or date entered.");
			return null;
		} else if (!isValidIndex(userInfo[INDEX_INDEX], indexList)) {
			Display.setFeedBack("Invalid index entered.");
			return null;
		}
		return null;
	}
	
	private LocalDate generateKeyword (String keyword) {
		return sdp.parse(keyword);
	}
	
	private ArrayList<Integer> generateIndexList(String userInfo) {
		String[] listOfIndices = userInfo.split(" ");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = INDEX_KEYWORD; i < listOfIndices.length; i++) {
			try {
				list.add(Integer.valueOf(listOfIndices[i]));
			} catch (NumberFormatException e) {
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

