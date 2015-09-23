package logic;


import common.Task;
/*
 * Add class description
 */
public class DeleteCommand implements Command{
	private TaskHandler handler;
	private Task taskRef;
	
	public DeleteCommand(TaskHandler handler,Task task){
		this.handler=handler;
		execute(task);
	}
	
	public void execute(Task task){
		taskRef=task;
		handler.deleteTask(taskRef);
		Display.showFeedBack(taskRef.getName()+" is deleted");
	}
	
	public void undo(){
		handler.addTask(taskRef);
	}
	
	public void redo(){
		handler.deleteTask(taskRef);
	}
}
