/* @@author A0126473E */
package parser;

import common.Task;
import common.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

/** 
 * Facade class for Logic*/
public class CommandParser {
	
	// attributes
	private AddStringParser asp;
	private QueryParser qp;
	private DateRangeParser drp;
	
	/**
	 * Constructor
	 */
	public CommandParser() {
		asp = new AddStringParser();
		qp = new QueryParser();
		drp = new DateRangeParser(asp);
	}
	
	public Task createTask(String userInput){
		Task task = asp.parse(userInput);
		return task;
	}
	
	public Pair<LocalDate,ArrayList<Integer>> query(String userInput){
		return qp.parse(userInput);
	}
	
	public Pair<LocalDate,LocalDate> queryDateRange(String userInput){
		return drp.parse(userInput);
	}

}
