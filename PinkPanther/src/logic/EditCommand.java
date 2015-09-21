package logic;

import java.time.LocalDate;

import common.Task;
import common.TaskType;

public class EditCommand implements Command{
	TaskHandler handler;
	Task unmodified;
	Task modified;
	
	public EditCommand(TaskHandler handler){
		this.handler=handler;
	}
	
	public String execute(LocalDate date,int TaskIndex, Task modified){
		unmodified=handler.searchTaskByIndexAndDate(date, TaskIndex);
		modified=applyModification(unmodified,modified);
		handler.deleteTask(unmodified);
		handler.addTask(modified);
		return unmodified.getName()+" is modified";
		
	}
	
	private Task applyModification(Task unmodfied,Task modified){
		modified.setName(unmodified.getName());
		TaskType type=modified.getTaskType();
		
		if(type==TaskType.TODO){
			if(modified.getStartDate()==null){
				modified.setStartDate(unmodified.getStartDate());
			}
			if(modified.getStartTime()==null){
				modified.setStartTime(unmodified.getStartTime());
			}
		}
		
		else if(type==TaskType.DEADLINE){
			if(modified.getEndDate()==null){
				modified.setEndDate(unmodified.getEndDate());
			}
			if(modified.getEndTime()==null){
				modified.setEndTime(unmodified.getEndTime());
			}
			
		}
		
		else if(type==TaskType.EVENT){
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
