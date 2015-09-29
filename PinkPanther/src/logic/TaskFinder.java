package logic;

import java.time.LocalDate;

import common.Pair;
import common.Task;

public class TaskFinder {
	
	public static Task find(TaskHandler handler,Pair<LocalDate,Integer>dateIndex){
		LocalDate date=dateIndex.getFirst();
		if(date==null){
			Display.setFeedBack("Invalid date input");
			return null;
		}
		
		int index=dateIndex.getSecond();
		Task task=handler.searchTaskByIndex(date, index);
		if(task==null){
			Display.setFeedBack("task does not exist");
			return null;
		}
		return task;
		
	}
}
