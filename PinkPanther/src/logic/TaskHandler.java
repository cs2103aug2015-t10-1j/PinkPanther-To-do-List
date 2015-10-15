package logic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

import storage.StorageControl;
import common.*;
/*
 * Add class description
 */
public class TaskHandler {
	private SortedMap<LocalDate,ArrayList<Task>>todoList;
	private SortedMap<LocalDate,ArrayList<Task>>doneTodo;
	private ArrayList<Task>floatingList;
	private ArrayList<Task>doneFloating;

	public TaskHandler(StorageControl storage){
		storage.setStorageEnvironmentNormal();
		todoList=storage.loadDated(false);
		doneTodo=storage.loadDated(true);
		floatingList=storage.loadFloating(false);
		doneFloating=storage.loadFloating(true);
	}
		
	public SortedMap<LocalDate,ArrayList<Task>>getTodo(boolean isDone){
		return isDone?doneTodo:todoList;
	}
	
	public SortedMap<LocalDate, ArrayList<Task>> getTwoWeekTodo(){
		return todoList.subMap(LocalDate.now(), LocalDate.now().plusWeeks(2));
	}
	
	public SortedMap<LocalDate,ArrayList<Task>> getDateRangeTodo(LocalDate date1,LocalDate date2){
		return todoList.subMap(date1, date2);
	}
	
	public SortedMap<LocalDate,ArrayList<Task>>getMatchedTodo(String keyword){
		SortedMap<LocalDate,ArrayList<Task>>matchedTodo=new TreeMap<LocalDate,ArrayList<Task>>();
		for(LocalDate date:todoList.keySet()){
			for(Task task:todoList.get(date)){
				if(task.getName().contains(keyword)){
					addTaskToMap(matchedTodo,task);
				}
			}
		}
		return matchedTodo;
	}
	
	private static void addTaskToMap(SortedMap<LocalDate,ArrayList<Task>>map,Task task){
		LocalDate date=task.getDate();
		if(!map.containsKey(date)){
			map.put(date, new ArrayList<Task>());
		}
		map.get(date).add(task);
	}
	
	public ArrayList<Task> getFloating(boolean isDone){
		return isDone?doneFloating:floatingList;
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
	
	
	private void sortTodo(LocalDate date,boolean isDone){
		ArrayList<Task>tasks=isDone?doneTodo.get(date):todoList.get(date);
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
	
	private void sortFloating(boolean isDone){
		Collections.sort(isDone?doneFloating:floatingList,(Task t1,Task t2) -> 
					t1.getName().compareTo(t2.getName()));
	}
	
	
	public boolean addTask(Task task,boolean isDone){
		if(task.getTaskType()==TaskType.FLOATING){
			(isDone?doneFloating:floatingList).add(task);
			sortFloating(isDone);
		}
		else{
			if(!isDone && task.getTaskType()==TaskType.EVENT && 
					checkForTimeConflict(task)){
				return false;
			}
			
			addTaskToMap(isDone?doneTodo:todoList,task);
			sortTodo(task.getDate(),isDone);
		}
		return true;
	}
	
	public void addMultipleTasks(ArrayList<Task>taskList,boolean isDone){
		for(Task task:taskList){
			addTask(task,isDone);
		}
	}
	
	public void deleteTask(Task task,boolean isDone){
		if(task.getTaskType()==TaskType.FLOATING){
			(isDone?doneFloating:floatingList).remove(task);
		}
		else{
			LocalDate date=task.getDate();
			SortedMap<LocalDate,ArrayList<Task>>taskList=isDone?doneTodo:todoList;
			taskList.get(date).remove(task);
			if(taskList.get(date).isEmpty()){
				taskList.remove(date);
			}
		}
	}
	
	public void deleteMultipleTasks(ArrayList<Task>taskList,boolean isDone){
		for(Task task:taskList){
			deleteTask(task,isDone);
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
		if(!todoList.containsKey(date)){
			return false;
		}
		
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
		doneTodo.clear();
		floatingList.clear();
		doneFloating.clear();
		
	}
	
	
	//for debugging purpose
	public void printTasks(ArrayList<Task>taskList){
		for(Task task:taskList){
			System.out.println(task.getName());
		}
	}
	
	
}
