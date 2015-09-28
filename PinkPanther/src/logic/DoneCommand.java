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
		LocalDate date=pair.getFirst();
		if(date==null){
			Display.setFeedBack("Invalid date input");
			return false;
		}
		
		int index=pair.getSecond();
		taskRef=handler.searchTaskByIndex(date, index);
		if(taskRef==null){
			Display.setFeedBack("task does not exist");
			return false;
		}
		previousTaskStatus=taskRef.getStatus();
		taskRef.setStatus(1);
		Display.setFeedBack(taskRef.getName()+" is done");
		return true;
	}
	
	public void undo(){
		taskRef.setStatus(previousTaskStatus);
	}
	
	public void redo(){
		taskRef.setStatus(1);
	}
}
