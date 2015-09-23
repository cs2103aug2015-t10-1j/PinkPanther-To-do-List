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
		if(handler.deleteTask(taskRef)){
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
