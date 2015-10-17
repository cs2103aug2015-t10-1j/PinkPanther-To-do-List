package logic;


import java.time.LocalDate;
import java.util.ArrayList;

import common.Display;
import common.Pair;
import common.Task;
/*
 * Add class description
 */
public class DeleteCommand implements Command{
	private TaskManager manager;
	private Task taskRef;
	private ArrayList<Task> taskListRef;
	
	public DeleteCommand(TaskManager manager){
		this.manager=manager;
	}
	
	public boolean execute(Pair<LocalDate,ArrayList<Integer>>pair){
		
		ArrayList<Task>taskList=manager.searchTasks(pair);
		if(taskList==null){
			return false;
		}
		
		if(taskList.size()==1){
			taskRef=taskList.get(0);
			Display.setFeedBack(taskRef + " has been deleted");
			manager.deleteTask(taskRef);
			return true;
		}
		else{
			taskListRef=taskList;
			Display.setFeedBack(taskListRef + " have been deleted");
			manager.deleteMultipleTasks(taskListRef);
			return true;
		}
		
	}
	
	public void undo(){
		if(taskRef!=null){
			manager.addTask(taskRef);
		}
		else{
			manager.addMultipleTasks(taskListRef);
		}
		
	}
	
	public void redo(){
		if(taskRef!=null){
			manager.deleteTask(taskRef);
		}
		else{
			manager.deleteMultipleTasks(taskListRef);
		}
	}
}
