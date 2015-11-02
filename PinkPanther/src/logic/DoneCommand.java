package logic;


import java.time.LocalDate;
import java.util.ArrayList;

import common.Display;
import common.Pair;
import common.Task;

public class DoneCommand implements Command{
	private TaskManager manager;
	private Task taskRef;
	
	public DoneCommand(TaskManager manager){
		this.manager=manager;
	}
	
	public boolean execute(Pair<LocalDate,ArrayList<Integer>>pair){
		ArrayList<Task>taskList=manager.searchTasks(pair);
		if(taskList==null){
			return false;
		}
		
		taskRef=taskList.get(0);
		if(taskRef!=null){
			//move the task to doneList
			manager.deleteTask(taskRef);
			taskRef.setDoneStatus(true);
			manager.addTask(taskRef);
			Display.setFeedBack("\"" + taskRef.getName()+"\""+" has been marked as 'done'");
			return true;
		}
		return false;
		
	}
	
	public void undo(){
		manager.deleteTask(taskRef);
		taskRef.setDoneStatus(false);
		manager.addTask(taskRef);
	}
	
	public void redo(){
		manager.deleteTask(taskRef);
		taskRef.setDoneStatus(true);
		manager.addTask(taskRef);
	}
}
