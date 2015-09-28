package logic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		Collections.sort(tasks,new Comparator<Task>(){
			public int compare(Task t1,Task t2){
				if(t1.getTime()!=null && t2.getTime()!=null){
					return t1.getTime().compareTo(t2.getTime());
				}
				else if(t1.getTime()==null && t2.getTime()!=null){
					return 1;
				}
				else if(t1.getTime()!=null && t2.getTime()==null){
					return -1;
				}
				else{
					return t1.getName().compareTo(t2.getName());
				}
			}
		});
		
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
	
	public void deleteTask(Task task){
		if(task.getTaskType()==TaskType.FLOATING){
			floatingList.remove(task);
			TaskStorage.writeToFile(floatingList);
		}
		else{
			LocalDate date=task.getDate();
			ArrayList<Task>taskList=todoList.get(date);
			taskList.remove(task);
			if(taskList.isEmpty()){
				todoList.remove(date);
			}
			TaskStorage.writeToFile(todoList);
		}
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
				if(event.getEndTime().isAfter(task.getStartTime())&& 
						event.getStartTime().isBefore(task.getEndTime())){
					return true;
				}
			}
		}
		return false;
	}
	
	public void clearAllTasks(){
		todoList.clear();
		floatingList.clear();
	}
	
	//for debugging purpose
	public void printTodoAtDate(LocalDate date){
		ArrayList<Task>tasks=todoList.get(date);
		for(Task task:tasks){
			System.out.println(task.getName());
		}
	}
	
	
}
