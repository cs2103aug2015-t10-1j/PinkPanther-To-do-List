package parser;

import java.time.LocalDate;
import common.Pair;

public class QueryParser {
	
	private static final int INDEX_DATE = 0;
	private static final int INDEX_INDEX = 1;
	
	public static Pair<LocalDate, Integer> parse(String commandContent) {
		String[] userInfo = commandContent.split(",");
		userInfo = AddStringParser.trimStringArray(userInfo);
		
		SingleDateParser sdp = new SingleDateParser();
		LocalDate date = sdp.parse(userInfo[INDEX_DATE]);
		
		Integer index = null;
		try {
			index = (Integer)Integer.parseInt(userInfo[INDEX_INDEX]);
		} catch (Exception e) {
			//Display some invalid input
		} 
		
		Pair<LocalDate, Integer> dateIndex = new Pair<LocalDate, Integer>(date, index);
		return dateIndex;
	}
}
