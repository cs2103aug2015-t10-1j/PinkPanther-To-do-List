package logic;

import common.Task;

public class DoneCommand implements Command{
	
	private Task taskRef;
	private int previousTaskStatus;
	
	public DoneCommand(Task task){
		execute(task);
	}
	
	public void execute(Task task){
		taskRef=task;
		previousTaskStatus=taskRef.getStatus();
		taskRef.setStatus(1);
		Display.showFeedBack(taskRef.getName()+" is marked as done");
	}
	
	public void undo(){
		taskRef.setStatus(previousTaskStatus);
	}
	
	public void redo(){
		taskRef.setStatus(1);
	}
}
