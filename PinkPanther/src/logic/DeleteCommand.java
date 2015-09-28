package logic;


import java.time.LocalDate;

import common.Pair;
import common.Task;
/*
 * Add class description
 */
public class DeleteCommand implements Command{
	private TaskHandler handler;
	private Task taskRef;
	
	public DeleteCommand(TaskHandler handler){
		this.handler=handler;
	}
	
	public boolean execute(Pair<LocalDate,Integer> pair){
		LocalDate date=pair.getFirst();
		int index=pair.getSecond();
		if(date==null){
			Display.setFeedBack("you have entered an invalid date");
			return false;
		}
		
		taskRef=handler.searchTaskByIndex(date,index);
		if(taskRef!=null){
			Display.setFeedBack(taskRef.getName()+" is deleted");
			return true;
		}
		else{
			Display.setFeedBack("task does not exist");
			return false;
		}
	}
	
	public void undo(){
		handler.addTask(taskRef);
	}
	
	public void redo(){
		handler.deleteTask(taskRef);
	}
}
