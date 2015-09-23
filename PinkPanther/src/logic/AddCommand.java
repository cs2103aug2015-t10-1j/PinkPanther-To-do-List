package logic;

import common.Task;
/*
 * Add class description
 */
public class AddCommand implements Command{
	private TaskHandler handler;
	private Task TaskRef;
	
	public AddCommand(TaskHandler handler){
		this.handler=handler;
	}
	
	public boolean execute(Task task){	
		TaskRef=task;
	    return handler.addTask(task);
	}
	
	
	public void undo(){
		handler.deleteTask(TaskRef);
	}
	
	public void redo(){
		handler.addTask(TaskRef);
	}
}
