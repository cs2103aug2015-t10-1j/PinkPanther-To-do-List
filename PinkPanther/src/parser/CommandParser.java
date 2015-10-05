package parser;

import common.Task;
import common.Pair;

import java.time.LocalDate;



//cs please create all your parser objects here. I am only going to use this object
//in controller class

public class CommandParser {
	
	private AddStringParser asp = new AddStringParser();
	private QueryParser qp = new QueryParser();
	
	public Task createTask(String userInput){
		Task task = asp.parse(userInput);
		return task;
	}
	
	public Pair<LocalDate,Integer> query(String userInput){
		return qp.parse(userInput);
		//return null;
	}

}
