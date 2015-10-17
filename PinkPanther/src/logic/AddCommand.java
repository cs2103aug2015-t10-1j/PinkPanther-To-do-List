package logic;

import java.time.LocalDate;

import common.Display;
import common.Task;
/*
 * Add class description
 */
public class AddCommand implements Command{
	private TaskManager manager;
	private Task taskRef;
	
	public AddCommand(TaskManager manager){
		this.manager=manager;
	}
	
	public boolean execute(Task task){	
		if(task==null){
			Display.setFeedBack("You did not enter a valid task name!");
			return false;
		}
		
	    taskRef=task;
	    manager.addTask(task);
	    LocalDate date = taskRef.getDate();
	    String dateString = (date == null) ? "floating tasks" : date.toString();
	    Display.setFeedBack("\"" + task.getName()+ "\"" + " has been added to " + dateString + ".");
	    return true;
	}
	
	
	public void undo(){
		manager.deleteTask(taskRef);
	}
	
	public void redo(){
		manager.addTask(taskRef);
	}
	
}
