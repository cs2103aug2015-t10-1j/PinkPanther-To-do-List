/* @@author GB */
package logic;


import java.time.LocalDate;
import java.util.ArrayList;

import common.Display;
import common.Pair;
import common.Task;

public class DoneCommand implements Command{
	private TaskManager manager;
	private Task taskRef;
	private ArrayList<Task> taskListRef;
	
	public DoneCommand(TaskManager manager){
		this.manager=manager;
	}
	
	public boolean execute(Pair<LocalDate,ArrayList<Integer>>pair){
		ArrayList<Task>taskList=manager.searchTasks(pair);
		if(taskList==null){
			return false;
		}
		
		LocalDate date=taskList.get(0).getDate();
		String dateString = (date == null) ? "floating tasks" : date.toString();
		
		if(taskList.size()==1){
			taskRef=taskList.get(0);
			manager.deleteTask(taskRef);
			taskRef.setDoneStatus(true);
			manager.addTask(taskRef);
			Display.setFeedBack("\"" + taskRef.getName()+"\" from "+dateString+" has been marked as 'done'");
			return true;
		}
		else{
			taskListRef=taskList;
			manager.deleteMultipleTasks(taskListRef);
			for(Task task:taskListRef){
				task.setDoneStatus(true);
			}
			manager.addMultipleTasks(taskListRef);
			Display.setFeedBack("Tasks from "+dateString+" have been marked as 'done'");
			return true;
		}
		
	}
	
	public void undo(){
		if(taskRef!=null){
			manager.deleteTask(taskRef);
			taskRef.setDoneStatus(false);
			manager.addTask(taskRef);
		} else{
			manager.deleteMultipleTasks(taskListRef);
			for(Task task:taskListRef){
				task.setDoneStatus(false);
			}
			manager.addMultipleTasks(taskListRef);
			
		}
		
	}
	
	public void redo(){
		if(taskRef!=null){
			manager.deleteTask(taskRef);
			taskRef.setDoneStatus(true);
			manager.addTask(taskRef);
		} else{
			manager.deleteMultipleTasks(taskListRef);
			for(Task task:taskListRef){
				task.setDoneStatus(true);
			}
			manager.addMultipleTasks(taskListRef);
		}
		
	}
}
