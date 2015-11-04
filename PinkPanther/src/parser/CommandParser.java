/* @@author CS */
package parser;

import common.Task;
import common.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

public class CommandParser {
	
	private AddStringParser asp = new AddStringParser();
	private QueryParser qp = new QueryParser();
	private DateRangeParser drp = new DateRangeParser(asp);
	
	public Task createTask(String userInput){
		Task task = asp.parse(userInput);
		return task;
	}
	
	@SuppressWarnings("unchecked")
	public Pair<LocalDate,ArrayList<Integer>> query(String userInput){
		return qp.parse(userInput);
	}
	
	public Pair<LocalDate,LocalDate> queryDateRange(String userInput){
		return drp.parse(userInput);
	}

}
