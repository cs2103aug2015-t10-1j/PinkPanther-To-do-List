package parser;

import java.time.LocalDate;
import common.Pair;
import logic.Display;

public class QueryParser implements Parser {
	
	private static final int INDEX_DATE = 0;
	private static final int INDEX_INDEX = 1;
	
	public Pair parse(String commandContent) {
		String[] userInfo = commandContent.split(",");
		userInfo = Auxiliary.trimStringArray(userInfo);
		SingleDateParser sdp = new SingleDateParser();
		
		Integer index = null;
		try {
			index = (Integer)Integer.parseInt(userInfo[INDEX_INDEX]);
		} catch (NullPointerException e) {
			Display.setFeedBack("invalid int");
			//Display some invalid input
			return null;
		} finally {
			
		}
		
		if (userInfo[INDEX_DATE].equalsIgnoreCase("float")) {
			Pair<String, Integer> floatIndex = new Pair<String, Integer>(userInfo[INDEX_DATE],index);
			return floatIndex;
		}
		
		LocalDate date = sdp.parse(userInfo[INDEX_DATE]);
		if (date == null) {
			Display.setFeedBack("invalid date or float indicator");
			return null;
		}
		Pair<LocalDate, Integer> dateIndex = new Pair<LocalDate, Integer>(date, index);
		return dateIndex;
	}
}
