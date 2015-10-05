package logic;


import java.time.LocalDate;

import common.Pair;
import common.Task;

public class DoneCommand implements Command{
	private TaskHandler handler;
	private Task taskRef;
	private int previousTaskStatus;
	
	public DoneCommand(TaskHandler handler){
		this.handler=handler;
	}
	
	public boolean execute(Pair<LocalDate,Integer>pair){
		
		taskRef=TaskFinder.find(handler, pair);
		if(taskRef!=null){
			previousTaskStatus=taskRef.getStatus();
			taskRef.setStatus(1);
			Display.setFeedBack(taskRef.getName()+" is done");
			return true;
		}
		return false;
		
	}
	
	public void undo(){
		taskRef.setStatus(previousTaskStatus);
	}
	
	public void redo(){
		taskRef.setStatus(1);
	}
}
