package logic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import storage.StorageControl;
import common.*;
/*
 * Add class description
 */
public class TaskHandler {
	private TreeMap<LocalDate,ArrayList<Task>>todoList;
	private ArrayList<Task>floatingList;

	public TaskHandler(StorageControl storage){
		todoList=storage.loadToDo();
		floatingList=storage.loadFloating();
	}
	
	public TreeMap<LocalDate,ArrayList<Task>> getTodo(){
		return todoList;
	}
	
	
	public TreeMap<LocalDate,ArrayList<Task>> getDoneTodo(){
		TreeMap<LocalDate,ArrayList<Task>>doneTodo=new TreeMap<LocalDate,ArrayList<Task>>();
		for(LocalDate date:todoList.keySet()){
			for(Task task:todoList.get(date)){
				if(task.getDoneStatus()){
					addTaskToMap(doneTodo,task);
				}
			}
		}
		return doneTodo;
	}
	
	public TreeMap<LocalDate,ArrayList<Task>>getMatchedTodo(String keyword){
		TreeMap<LocalDate,ArrayList<Task>>matchedTodo=new TreeMap<LocalDate,ArrayList<Task>>();
		for(LocalDate date:todoList.keySet()){
			for(Task task:todoList.get(date)){
				if(task.getName().contains(keyword)){
					addTaskToMap(matchedTodo,task);
				}
			}
		}
		return matchedTodo;
	}
	
	private static TreeMap<LocalDate,ArrayList<Task>> addTaskToMap(TreeMap<LocalDate,ArrayList<Task>>map,Task task){
		LocalDate date=task.getDate();
		if(!map.containsKey(date)){
			map.put(date, new ArrayList<Task>());
		}
		map.get(date).add(task);
		return map;
	}
	
	public ArrayList<Task> getFloating(){
		return floatingList;
	}
	
	public ArrayList<Task> getDoneFloating(){
		ArrayList<Task>doneFloating=new ArrayList<Task>();
		for(Task task:floatingList){
			if(task.getDoneStatus()){
				doneFloating.add(task);
			}
		}
		return doneFloating;
	}
	
	public ArrayList<Task>getMatchedFloating(String keyword){
		ArrayList<Task>matchedFloating=new ArrayList<Task>();
		for(Task task:floatingList){
			if(task.getName().contains(keyword)){
				matchedFloating.add(task);
			}
		}
		return matchedFloating;
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
		if(task.getTaskType()==TaskType.FLOATING){
			floatingList.add(task);
			sortFloatingList();
		}
		else{
			LocalDate date=task.getDate();
			
			if(task.getTaskType()==TaskType.EVENT && checkForTimeConflict(task)){
				return false;
			}

			addTaskToMap(todoList,task);
			sortTodoList(date);
		}
		return true;
	}
	
	public void addMultipleTasks(ArrayList<Task>taskList){
		for(Task task:taskList){
			addTask(task);
		}
	}
	
	public void deleteTask(Task task){
		if(task.getTaskType()==TaskType.FLOATING){
			floatingList.remove(task);
		}
		else{
			LocalDate date=task.getDate();
			ArrayList<Task>taskList=todoList.get(date);
			taskList.remove(task);
			if(taskList.isEmpty()){
				todoList.remove(date);
			}
		}
	}
	
	public void deleteMultipleTasks(ArrayList<Task>taskList){
		for(Task task:taskList){
			deleteTask(task);
		}
	}
	
	public ArrayList<Task> searchTasks(Pair<LocalDate,ArrayList<Integer>>pair){
		if(pair==null){
			return null;
		}
		
		ArrayList<Task>output;
		LocalDate date=pair.getFirst();
		ArrayList<Integer>indexList=pair.getSecond();
		if(indexList!=null){
			output=searchMultipleTasks(date,indexList);
		}
		else{
			output=searchTasksAtDate(date);
		}
		return output;
	}
	
	public ArrayList<Task>searchTasksAtDate(LocalDate date){
		if(date==null){
			return new ArrayList<Task>(floatingList);
		}
		else if(!todoList.containsKey(date)){
			Display.setFeedBack("you do not have task on this date");
			return null;
		}
		else{
			return new ArrayList<Task>(todoList.get(date));
		}
	}
	
	
	public Task searchSingleTask(LocalDate date,int displayIndex){
		int actualIndex=displayIndex-1;
		if(date==null){
			if(actualIndex>=floatingList.size()){
				return null;
			}
			return floatingList.get(actualIndex);
		}
		else{
			if(!todoList.containsKey(date)||actualIndex>=todoList.get(date).size()){
				return null;
			}
			return todoList.get(date).get(actualIndex);
		}
				
	}
	
	public ArrayList<Task>searchMultipleTasks(LocalDate date,ArrayList<Integer>indexList){
		ArrayList<Task>taskList=new ArrayList<Task>();
		for(int index:indexList){
			Task task=searchSingleTask(date,index);
			if(task!=null){
				taskList.add(task);
			}
		}
		if(taskList.size()==0){
			Display.setFeedBack("task or tasks do not exist");
			return null;
		}
		return taskList;
	}
	
	private boolean checkForTimeConflict(Task event){
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
	public void printTasks(ArrayList<Task>taskList){
		for(Task task:taskList){
			System.out.println(task.getName());
		}
	}
	
	
}
