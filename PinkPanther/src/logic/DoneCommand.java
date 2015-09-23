package logic;

import common.Task;

public class DoneCommand implements Command{
	
	private Task taskRef;
	private int previousTaskStatus;
	
	public DoneCommand(){

	}
	
	public boolean execute(Task task){
		taskRef=task;
		if(taskRef==null){
			Display.setFeedBack("task does not exist");
			return false;
		}
		previousTaskStatus=taskRef.getStatus();
		taskRef.setStatus(1);
		Display.setFeedBack(taskRef.getName()+" is marked as done");
		return true;
	}
	
	public void undo(){
		taskRef.setStatus(previousTaskStatus);
	}
	
	public void redo(){
		taskRef.setStatus(1);
	}
}
