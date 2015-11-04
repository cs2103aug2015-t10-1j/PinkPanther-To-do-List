/* @@author GB */
package logic;


import common.Display;
import common.Pair;
import common.Task;

public class EditCommand implements Command{
	private TaskManager manager;
	private Task unmodified;
	private Task modified;
	
	public EditCommand(TaskManager manager){
		this.manager=manager;
	}
	
	public boolean execute(Pair<Task,Task>pair){
		unmodified=pair.getFirst();
		modified=pair.getSecond();
		manager.deleteTask(unmodified);
		manager.addTask(modified);
		Display.setFeedBack("\""+unmodified.getName()+"\""+" has been modified");
		return true;
	}
	
	
	public void undo(){
		manager.deleteTask(modified);
		manager.addTask(unmodified);
	}
	
	public void redo(){
		manager.deleteTask(unmodified);
		manager.addTask(modified);
	}
}
