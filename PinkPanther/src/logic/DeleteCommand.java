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
	private TaskHandler handler;
	private Task taskRef;
	private ArrayList<Task> taskListRef;
	
	public DeleteCommand(TaskHandler handler){
		this.handler=handler;
	}
	
	public boolean execute(Pair<LocalDate,ArrayList<Integer>>pair){
		
		ArrayList<Task>taskList=handler.searchTasks(pair);
		if(taskList==null){
			return false;
		}
		
		if(taskList.size()==1){
			taskRef=taskList.get(0);
			Display.setFeedBack("the task has been deleted");
			handler.deleteTask(taskRef);
			return true;
		}
		else{
			taskListRef=taskList;
			Display.setFeedBack("the tasks have been deleted");
			handler.deleteMultipleTasks(taskListRef);
			return true;
		}
		
	}
	
	public void undo(){
		if(taskRef!=null){
			handler.addTask(taskRef);
		}
		else{
			handler.addMultipleTasks(taskListRef);
		}
		
	}
	
	public void redo(){
		if(taskRef!=null){
			handler.deleteTask(taskRef);
		}
		else{
			handler.deleteMultipleTasks(taskListRef);
		}
	}
}
