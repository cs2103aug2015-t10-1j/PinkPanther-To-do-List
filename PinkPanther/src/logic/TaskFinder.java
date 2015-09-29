package logic;

import java.time.LocalDate;

import common.Pair;
import common.Task;
//
public class TaskFinder {
	
	public static Task find(TaskHandler handler,Pair<?,Integer> pair){
		Integer index=pair.getSecond();
		Task task;
		
		if(pair.getFirst() instanceof String){
			if(index!=null){
				task=handler.searchTaskByIndex(null, index);
			}
			else{
				Display.setFeedBack("invalid index");
				return null;
			}
		}
		
		else{
			LocalDate date=(LocalDate) pair.getFirst();
			if(date!=null && index!=null){
				task=handler.searchTaskByIndex(date, index);
			}
			else if(date==null && index==null){
				Display.setFeedBack("Invalid date and index");
				return null;
			}
			else if(date==null){
				Display.setFeedBack("invalid date");
				return null;
			}
			else{
				Display.setFeedBack("invalid index");
				return null;
			}			
		}
		
		if(task==null){
			Display.setFeedBack("task does not exist");
		}
		return task;
		
	}
}
