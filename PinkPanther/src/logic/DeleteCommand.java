package logic;


import common.Pair;
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
	
	public boolean execute(Pair<?,Integer> pair){
		
		taskRef=TaskFinder.find(handler, pair);
		if(taskRef!=null){
			Display.setFeedBack(taskRef.getName()+" is deleted");
			handler.deleteTask(taskRef);
			return true;
		}
		return false;
	}
	
	public void undo(){
		handler.addTask(taskRef);
	}
	
	public void redo(){
		handler.deleteTask(taskRef);
	}
}
