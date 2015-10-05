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
		if(task==null){
			Display.setFeedBack("retard,enter a name lah");
			return false;
		}
		
	    if(handler.addTask(task)){
	    	taskRef=task;
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
