package logic;

import java.time.LocalDate;

import common.Pair;
import common.Task;
//
public class TaskFinder {
	
	public static Task find(TaskHandler handler,Pair<LocalDate,Integer> pair){
		if(pair==null){
			return null;
		}
		
		LocalDate date=pair.getFirst();
		Integer index=pair.getSecond();
		Task task=handler.searchTaskByIndex(date, index);
			
		if(task==null){
			Display.setFeedBack("task does not exist");
		}
		return task;
		
	}
}
