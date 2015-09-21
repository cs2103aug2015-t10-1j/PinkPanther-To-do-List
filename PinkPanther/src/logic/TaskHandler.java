package logic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import storage.TaskStorage;
import common.*;
/*
 * Add class description
 */
public class TaskHandler {
	private HashMap<LocalDate,ArrayList<Task>>todoList;
	private ArrayList<Task>floatingList;
	
	public TaskHandler(){
		todoList=TaskStorage.readFromTodoFile();
		floatingList=TaskStorage.readFromFloatingFile();
	}
	
	public HashMap<LocalDate,ArrayList<Task>> getTodoList(){
		return todoList;
	}
	
	public ArrayList<Task> getFloatingList(){
		return floatingList;
	}
	
	
	private void sortTodoList(LocalDate date){
		ArrayList<Task>tasks=todoList.get(date);
		
	}
	
	private void sortFloatingList(){
	}
	
	
	public void addTask(Task task){
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
			todoList.get(date).add(task);
			sortTodoList(date);
			TaskStorage.writeToFile(todoList);
		}
	}
	
	public void deleteTask(Task task){
		if(task.getTaskType()==TaskType.FLOATING){
			floatingList.remove(task);
			TaskStorage.writeToFile(floatingList);
		}
		else{
			LocalDate date=task.getDate();
			todoList.get(date).remove(task);
			TaskStorage.writeToFile(todoList);
		}
	}
	
	
	public Task searchTaskByIndexAndDate(LocalDate date,int displayIndex){
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
	
	
}
