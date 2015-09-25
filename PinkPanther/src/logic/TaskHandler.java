package logic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import storage.TaskStorage;
import common.*;
/*
 * Add class description
 */
public class TaskHandler {
	private TreeMap<LocalDate,ArrayList<Task>>todoList;
	private ArrayList<Task>floatingList;
	
	public TaskHandler(){
		todoList=TaskStorage.readFromTodoFile();
		floatingList=TaskStorage.readFromFloatingFile();
	}
	
	public TreeMap<LocalDate,ArrayList<Task>> getTodoList(){
		return todoList;
	}
	
	public ArrayList<Task> getFloatingList(){
		return floatingList;
	}
	
	
	private void sortTodoList(LocalDate date){
		ArrayList<Task>tasks=todoList.get(date);
		Collections.sort(tasks,(Task t1,Task t2)->t1.getTime().compareTo(t2.getTime()));
		Collections.sort(tasks,(Task t1,Task t2)->t1.getTaskType().compareTo(t2.getTaskType()));
	}
	
	private void sortFloatingList(){
		Collections.sort(floatingList,(Task t1,Task t2) -> t1.getName().compareTo(t2.getName()));
	}
	
	
	public boolean addTask(Task task){
		boolean canAdd=true;
		if(task.getTaskType()==TaskType.FLOATING){
			floatingList.add(task);
			sortFloatingList();
			TaskStorage.writeToFile(floatingList);
		}
		else{
			LocalDate date=task.getDate();
			if(!todoList.containsKey(date)){
				todoList.put(date, new ArrayList<Task>());
			}
			
			if(task.getTaskType()==TaskType.EVENT && checkForTimeConflict(task)){
				canAdd=false;
			}
			else{
				todoList.get(date).add(task);
			}
			
			sortTodoList(date);
			TaskStorage.writeToFile(todoList);
		}
		return canAdd;
	}
	
	public boolean deleteTask(Task task){
		boolean canDelete=true;
		if(task==null){
			canDelete=false;
		}
		else if(task.getTaskType()==TaskType.FLOATING){
			floatingList.remove(task);
			TaskStorage.writeToFile(floatingList);
		}
		else{
			LocalDate date=task.getDate();
			todoList.get(date).remove(task);
			TaskStorage.writeToFile(todoList);
		}
		return canDelete;
	}
	
	
	public Task searchTaskByIndex(LocalDate date,int displayIndex){
		int actualIndex=displayIndex-1;
		if(date==null){
			if(actualIndex>=floatingList.size()){
				return null;
			}
			return floatingList.get(actualIndex);
		}
		else{
			if(actualIndex>=todoList.get(date).size()){
				return null;
			}
			return todoList.get(date).get(actualIndex-1);
		}
				
	}
	
	public boolean checkForTimeConflict(Task event){
		LocalDate date=event.getDate();
		for(Task task:todoList.get(date)){
			if(task.getTaskType()==TaskType.EVENT){
				if((event.getStartTime().isAfter(task.getStartTime()) && event.getStartTime().isBefore(task.getEndTime()))||
						(event.getEndTime().isAfter(task.getStartTime()) && event.getEndTime().isBefore(task.getEndTime()))){
					return true;
				}
			}
		}
		return false;
	}
	
	
}
