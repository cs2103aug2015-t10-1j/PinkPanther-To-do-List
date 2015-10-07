package parser;

import common.Pair;
import common.Auxiliary;
import logic.Display;

import java.time.LocalDate;
import java.util.ArrayList;

public class QueryParser implements Parser {
	
	private static final int INDEX_KEYWORD = 0;
	private static final int INDEX_INDEX = 1;
	
	public Pair parse(String commandContent) {
		String[] userInfo = commandContent.split(",");
		userInfo = Auxiliary.trimStringArray(userInfo);
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		SingleDateParser sdp = new SingleDateParser();
		
		// retrieving multiple floating tasks
		if (userInfo[INDEX_KEYWORD].equalsIgnoreCase("float")) {
			for (int i = INDEX_INDEX; i < userInfo.length; i++) {
				if (userInfo[i].isEmpty()) {
					continue;
				}
				
				try {
					indexList.add(Integer.valueOf(userInfo[i]));
				} catch (Exception e) {
					Display.setFeedBack("Invalid index entered");
					return null;
				}
			}
			return new Pair<LocalDate, ArrayList<Integer>>(null, indexList);
			
		} 
		
		// retrieving multiple dated tasks
		LocalDate date = sdp.parse(userInfo[INDEX_KEYWORD]);
		if (date != null) {
			// retrieve all tasks
			if (userInfo.length == 2 && userInfo[INDEX_INDEX].equalsIgnoreCase("all")) {
				return new Pair<LocalDate, ArrayList<Integer>>(date, null);
			}
			// retrieve multiple tasks
			for (int i = INDEX_INDEX; i < userInfo.length; i++) {
				if (userInfo[i].isEmpty()) {
					continue;
				}
				try {
					indexList.add(Integer.valueOf(userInfo[i]));
				} catch (Exception e) {
					Display.setFeedBack("Invalid index entered");
					return null;
				}
			}
			return new Pair<LocalDate, ArrayList<Integer>>(date, indexList);
		}
		
		// no keywords found!
		Display.setFeedBack("No valid keyword or date entered");
		return null;
		
		
		
	}
}
	
	/*Integer index = null;
	try {
		index = (Integer)Integer.parseInt(userInfo[INDEX_INDEX]);
	} catch (Exception e) {
		Display.setFeedBack("invalid int");
		//Display some invalid input
		return null;
	} finally {
		
	}
	
	if (userInfo[INDEX_DATE].equalsIgnoreCase("float")) {
		Pair<LocalDate, Integer> floatIndex = new Pair<LocalDate, Integer>(null,index);
		return floatIndex;
	}
	
	LocalDate date = sdp.parse(userInfo[INDEX_DATE]);
	if (date == null) {
		Display.setFeedBack("invalid date or float indicator");
		return null;
	}
	Pair<LocalDate, Integer> dateIndex = new Pair<LocalDate, Integer>(date, index);
	return dateIndex;*/
