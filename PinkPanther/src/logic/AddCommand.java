package logic;

import java.time.LocalDate;

import common.Display;
import common.Task;
/*
 * Add class description
 */
public class AddCommand implements Command{
	private TaskHandler handler;
	private Task taskRef;
	
	public AddCommand(TaskHandler handler){
		this.handler=handler;
	}
	
	public boolean execute(Task task){	
		if(task==null){
			Display.setFeedBack("You did not enter a valid task name!");
			return false;
		}
		
	    if(handler.addTask(task,false)){
	    	taskRef=task;
	    	LocalDate date = taskRef.getDate();
	    	String dateString = (date == null) ? "floating tasks" : date.toString();
	    	Display.setFeedBack("\"" + task.getName()+ "\"" + " has been added to " + dateString + ".");
	    	return true;
	    }
	    else{
	    	Display.setFeedBack("You have another event during this period, which might cause clashes.");
	    	return false;
	    }
	}
	
	
	public void undo(){
		handler.deleteTask(taskRef,false);
	}
	
	public void redo(){
		handler.addTask(taskRef,false);
	}
	
}
