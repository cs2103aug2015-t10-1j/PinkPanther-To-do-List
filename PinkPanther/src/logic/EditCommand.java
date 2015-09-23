package logic;

import java.util.ArrayList;

import common.Task;

public class EditCommand implements Command{
	private TaskHandler handler;
	private Task unmodified;
	private Task modified;
	
	public EditCommand(TaskHandler handler){
		this.handler=handler;
	}
	
	public boolean execute(ArrayList<Task>inputTasks){
		unmodified=inputTasks.get(0);
		if(unmodified==null){
			return false;
		}
		modified=inputTasks.get(1);
		modified=applyModification(unmodified,modified);
		handler.deleteTask(unmodified);
		return handler.addTask(modified);
		
	}
	
	private Task applyModification(Task unmodfied,Task modified){
		modified.setName(unmodified.getName());
		if(modified.getStartDate()==null){
			modified.setStartDate(unmodified.getStartDate());
		}
		if(modified.getStartTime()==null){
			modified.setStartTime(unmodified.getStartTime());
		}
		if(modified.getEndDate()==null){
			modified.setEndDate(unmodified.getEndDate());
		}
		if(modified.getEndTime()==null){
			modified.setEndTime(unmodified.getEndTime());
		}
		
		return modified;
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
