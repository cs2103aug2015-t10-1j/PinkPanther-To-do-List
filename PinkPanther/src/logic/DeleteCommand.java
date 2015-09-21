package logic;

import java.time.LocalDate;
import java.util.ArrayList;

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
	
	public String execute(LocalDate date,int deleteIndex){
		taskRef=handler.searchTaskByIndexAndDate(date, deleteIndex);
		handler.deleteTask(taskRef);
		return taskRef.getName()+" is deleted";
	}
	
	public void undo(){
		handler.addTask(taskRef);
	}
	
	public void redo(){
		handler.deleteTask(taskRef);
	}
}
