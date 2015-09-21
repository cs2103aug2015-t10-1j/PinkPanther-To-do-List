package logic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import storage.TaskStorage;
import common.*;
/*
 * Add class description
 */
public class TaskHandler {
	private static int TODO_LIST=0;
	private static int FLOATING_LIST=1;
	private ArrayList<ArrayList<Task>>taskList;
	
	public TaskHandler(){
		taskList=new ArrayList<ArrayList<Task>>();
		taskList.add(TaskStorage.readFromFile(TODO_LIST));
		taskList.add(TaskStorage.readFromFile(FLOATING_LIST));
	}
	
	public ArrayList<Task> getTODO_LIST(){
		return taskList.get(TODO_LIST);
	}
	
	public ArrayList<Task> getFLOATING_LIST(){
		return taskList.get(FLOATING_LIST);
	}
	
	
	private void sortTodoList(){
		
	}
	
	private void sortFloatingList(){
		
	}
	
	
	public void addTask(Task task){
		if(task.getTaskType()==TaskType.FLOATING){
			taskList.get(FLOATING_LIST).add(task);
			sortFloatingList();
			TaskStorage.writeToFile(taskList.get(FLOATING_LIST), FLOATING_LIST);
		}
		else{
			taskList.get(TODO_LIST).add(task);
			sortTodoList();
			TaskStorage.writeToFile(taskList.get(TODO_LIST), TODO_LIST);
		}
	}
	
	public void deleteTask(Task task){
		if(task.getTaskType()==TaskType.FLOATING){
			taskList.get(FLOATING_LIST).remove(task);
			TaskStorage.writeToFile(taskList.get(FLOATING_LIST), FLOATING_LIST);
		}
		else{
			taskList.get(TODO_LIST).remove(task);
			TaskStorage.writeToFile(taskList.get(TODO_LIST), TODO_LIST);
		}
	}
	
	
	public Task searchTodoByIndexAndDate(LocalDate date,int todoIndex){
		int startDateIndex=0;
		for(Task task:taskList.get(TODO_LIST)){
			if(task.getDate().equals(date)){
				break;
			}
			startDateIndex++;
		}
		
		return taskList.get(TODO_LIST).get(startDateIndex+todoIndex-1);	
	}
	
	
}
