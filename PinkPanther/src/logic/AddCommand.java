package logic;

import common.Task;
/*
 * Add class description
 */
public class AddCommand implements Command{
	TaskHandler handler;
	Task TaskRef;
	
	public AddCommand(TaskHandler handler){
		this.handler=handler;
	}
	
	public String execute(Task task){	
		handler.addTask(task);
		TaskRef=task;
		return task.getName()+" is added";
	}
	
	
	public void undo(){
		handler.deleteTask(TaskRef);
	}
	
	public void redo(){
		handler.addTask(TaskRef);
	}
}
