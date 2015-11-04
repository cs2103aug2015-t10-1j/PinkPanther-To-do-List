/* @@author CS */
package parser;

import common.Pair;
import common.Auxiliary;
import common.Display;

import java.time.LocalDate;
import java.util.ArrayList;

public class QueryParser implements Parser {
	
	private static final int INDEX_KEYWORD = 0;
	private static final int INDEX_INDEX = 1;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pair parse(String commandContent) {
		String[] userInfo = commandContent.split(",");
		userInfo = Auxiliary.trimStringArray(userInfo);
		
		// there should only be 1 comma
		if (userInfo.length != 2) {
			Display.setFeedBack("Invalid Input Format");
			return null;
		}
		
		String[] indexInfo = userInfo[INDEX_INDEX].split(" ");
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		SingleDateParser sdp = new SingleDateParser();
		
		indexList = generateIndexList(indexInfo);
		
		// retrieve multiple floating tasks
		if (userInfo[INDEX_KEYWORD].equalsIgnoreCase("undated")) {
			
			if (indexInfo.length == 1 && indexInfo[INDEX_KEYWORD].equalsIgnoreCase("all")) {
				return new Pair<LocalDate, ArrayList<Integer>>(null, indexList);
			}
			
			if (indexList == null) {
				Display.setFeedBack("Invalid index entered");
				return null;
			}
			
			return new Pair<LocalDate, ArrayList<Integer>>(null, indexList);
		} 
		
		// retrieve multiple dated tasks
		LocalDate date = sdp.parse(userInfo[INDEX_KEYWORD]);
		if (date != null) {
			
			// retrieve all tasks
			if (indexInfo.length == 1 && indexInfo[INDEX_KEYWORD].equalsIgnoreCase("all")) {
				return new Pair<LocalDate, ArrayList<Integer>>(date, indexList);
			}
			// retrieve multiple tasks
			if (indexList == null) {
				Display.setFeedBack("Invalid index entered");
				return null;
			}
			
			return new Pair<LocalDate, ArrayList<Integer>>(date, indexList);
		}
		
		// no keywords found
		Display.setFeedBack("No valid keyword or date entered");
		return null;
	}
	
	private ArrayList<Integer> generateIndexList(String[] listOfIndices) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = INDEX_KEYWORD; i < listOfIndices.length; i++) {
			try {
				list.add(Integer.valueOf(listOfIndices[i]));
			} catch (Exception e) {
				return null;
			}
		}	
		return list;
	}
}

