package parser;

import common.Task;

import java.time.LocalDate;

import common.Pair;

//cs please create all your parser objects here. I am only going to use this object
//in controller class

public class CommandParser {
	
	public Task createTask(String userInput){
		AddStringParser asp = new AddStringParser();
		Task task = asp.parse(userInput);
		return task;
	}
	
	public Pair<LocalDate,Integer> query(String userInput){
		
		return null;
	}

}
