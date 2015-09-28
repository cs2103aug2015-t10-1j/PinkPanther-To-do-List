package logic;

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
		taskRef=task;
		if(taskRef==null){
			Display.setFeedBack("you have entered an invalid date or time");
		}
		
	    if(handler.addTask(task)){
	    	Display.setFeedBack(task.getName()+" is added");
	    	return true;
	    }
	    else{
	    	Display.setFeedBack("you have another event during this period");
	    	return false;
	    }
	}
	
	
	public void undo(){
		handler.deleteTask(taskRef);
	}
	
	public void redo(){
		handler.addTask(taskRef);
	}
}
