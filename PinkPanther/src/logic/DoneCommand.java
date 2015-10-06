package logic;


import java.time.LocalDate;

import common.Pair;
import common.Task;

public class DoneCommand implements Command{
	private TaskHandler handler;
	private Task taskRef;
	
	public DoneCommand(TaskHandler handler){
		this.handler=handler;
	}
	
	public boolean execute(Pair<LocalDate,Integer>pair){
		
		taskRef=TaskFinder.find(handler, pair);
		if(taskRef!=null){
			taskRef.setDoneStatus(true);
			Display.setFeedBack(taskRef.getName()+" is done");
			return true;
		}
		return false;
		
	}
	
	public void undo(){
		taskRef.setDoneStatus(false);
	}
	
	public void redo(){
		taskRef.setDoneStatus(true);
	}
}
