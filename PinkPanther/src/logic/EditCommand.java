package logic;


import common.Pair;
import common.Task;

public class EditCommand implements Command{
	private TaskHandler handler;
	private Task unmodified;
	private Task modified;
	
	public EditCommand(TaskHandler handler){
		this.handler=handler;
	}
	
	public boolean execute(Pair<Task,Task>pair){
		unmodified=pair.getFirst();
		modified=pair.getSecond();
		handler.deleteTask(unmodified);
		if(handler.addTask(modified)){
			Display.setFeedBack("the task has been modified");
			return true;
		}
		else{
			Display.setFeedBack("you have another event during this period");
			handler.addTask(unmodified);
			return false;
		}
	}
	
	
	public void undo(){
		handler.deleteTask(modified);
		handler.addTask(unmodified);
	}
	
	public void redo(){
		handler.deleteTask(unmodified);
		handler.addTask(modified);
	}
}
