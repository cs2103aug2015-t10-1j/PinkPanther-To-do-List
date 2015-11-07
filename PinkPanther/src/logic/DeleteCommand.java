/* @@author GB */
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
		
		LocalDate date=taskList.get(0).getDate();
		String dateString = (date == null) ? "floating tasks" : date.toString();
		
		if(taskList.size()==1){
			taskRef=taskList.get(0);
			manager.deleteTask(taskRef);
			Display.setFeedBack("Task deleted: ["+dateString+"] "+"\"" + taskRef.getName() + "\"");
			return true;
		} else{
			taskListRef=taskList;
			manager.deleteMultipleTasks(taskListRef);
			Display.setFeedBack("Tasks deleted: ["+dateString+"] "+"\"" + taskListRef.get(0).getName() + "\""+"...");
			return true;
		}
		
	}
	
	public void undo(){
		if(taskRef!=null){
			manager.addTask(taskRef);
		} else{
			manager.addMultipleTasks(taskListRef);
		}
		
	}
	
	public void redo(){
		if(taskRef!=null){
			manager.deleteTask(taskRef);
		} else{
			manager.deleteMultipleTasks(taskListRef);
		}
	}
}
