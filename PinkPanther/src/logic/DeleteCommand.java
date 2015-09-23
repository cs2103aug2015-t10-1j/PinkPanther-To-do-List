package logic;


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
	
	public boolean execute(Task task){
		taskRef=task;
		return handler.deleteTask(taskRef);
	}
	
	public void undo(){
		handler.addTask(taskRef);
	}
	
	public void redo(){
		handler.deleteTask(taskRef);
	}
}
