package parser;

import java.time.LocalDate;
import common.Pair;

public class QueryParser {
	
	private static final int INDEX_DATE = 0;
	private static final int INDEX_INDEX = 1;
	
	public static void main(String[] args) {
		String text = "4 jul, 7";
		String text2 = "float, 8";
		Pair test1 = parse(text);
		Pair test2 = parse(text2);
		System.out.println(test1.getFirst());
		System.out.println(test1.getSecond());
		System.out.println(test2.getFirst());
		System.out.println(test2.getSecond());
	}
	
	public static Pair parse(String commandContent) {
		String[] userInfo = commandContent.split(",");
		userInfo = Auxiliary.trimStringArray(userInfo);
		SingleDateParser sdp = new SingleDateParser();
		
		Integer index = null;
		try {
			index = (Integer)Integer.parseInt(userInfo[INDEX_INDEX]);
		} catch (Exception e) {
			//Display some invalid input
		} finally {
			
		}
		
		
		if (userInfo[INDEX_DATE].equalsIgnoreCase("float")) {
			Pair<String, Integer> floatIndex = new Pair<String, Integer>(userInfo[INDEX_DATE],index);
			return floatIndex;
		}
		
		LocalDate date = sdp.parse(userInfo[INDEX_DATE]);
		Pair<LocalDate, Integer> dateIndex = new Pair<LocalDate, Integer>(date, index);
		return dateIndex;
	}
}
