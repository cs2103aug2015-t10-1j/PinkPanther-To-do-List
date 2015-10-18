package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import common.Display;
import common.Pair;
import common.Task;
import common.TaskType;
import storage.StorageControl;

public class TaskManager {
	private static LocalDate THE_MYTH_DAY=LocalDate.of(1979, 7, 11);
	private SortedMap<LocalDate,ArrayList<Task>>doneList;
	private SortedMap<LocalDate,ArrayList<Task>>todoList;
	
	public TaskManager(StorageControl storage){
		doneList=new TreeMap<LocalDate,ArrayList<Task>>();
		doneList.put(THE_MYTH_DAY, new ArrayList<Task>());
		todoList=new TreeMap<LocalDate,ArrayList<Task>>();
		todoList.put(THE_MYTH_DAY, new ArrayList<Task>());
		storage.setStorageEnvironmentNormal();
		addMultipleTasks(storage.loadTaskList(true));
		addMultipleTasks(storage.loadTaskList(false));
	}
	
// --------------------------------------Getters------------------------------------------
	
	public ArrayList<Task> getTaskArray(boolean isDone){
		SortedMap<LocalDate,ArrayList<Task>>taskList=isDone?doneList:todoList;
		Set<Task>taskSet=new HashSet<Task>();
		for(LocalDate date:taskList.keySet()){
			for(Task task:taskList.get(date)){
				taskSet.add(task);
			}
		}
		return new ArrayList<Task>(taskSet);
	} 
	
	public ArrayList<Task> getFloating(boolean isDone){
		return isDone?doneList.get(THE_MYTH_DAY):todoList.get(THE_MYTH_DAY);
	}
	
	public SortedMap<LocalDate, ArrayList<Task>> getDated(boolean isDone){
		LocalDate date=LocalDate.of(1980, 1, 1);
		return checkForClash(isDone?doneList.tailMap(date):todoList.tailMap(date));
	}
	
	public SortedMap<LocalDate, ArrayList<Task>> getTwoWeek(){
		return checkForClash(todoList.subMap(LocalDate.now(), LocalDate.now().plusWeeks(2)));
	}
	
	public SortedMap<LocalDate,ArrayList<Task>> getDateRange(LocalDate date1,LocalDate date2){
		return checkForClash(todoList.subMap(date1, date2.plusDays(1)));
	}
	
	public SortedMap<LocalDate,ArrayList<Task>> getMatchedDated(String keyword){
		SortedMap<LocalDate,ArrayList<Task>>matchedList=new TreeMap<LocalDate,ArrayList<Task>>();
		for(LocalDate date:todoList.keySet()){
			if(date.isEqual(THE_MYTH_DAY)){
				continue;
			}
			
			for(Task task:todoList.get(date)){
				if(task.getName().contains(keyword)){
					addTaskAtDate(matchedList,task.getDate(),task);
				}
			}
		}
		return checkForClash(matchedList);
	}
	
	public ArrayList<Task>getMatchedFloating(String keyword){
		ArrayList<Task>matchedFloating=new ArrayList<Task>();
		for(Task task:todoList.get(THE_MYTH_DAY)){
			if(task.getName().contains(keyword)){
				matchedFloating.add(task);
			}
		}
		return matchedFloating;
	}
	
	
//-------------------------------Task List Modifiers ---------------------------------------
	
	public void addTask(Task task){
		boolean isDone=task.getDoneStatus();
		SortedMap<LocalDate,ArrayList<Task>>taskList=isDone?doneList:todoList;
		
		if(task.getTaskType()==TaskType.FLOATING){
			addTaskAtDate(taskList,THE_MYTH_DAY,task);
			sortTaskAtDate(taskList,THE_MYTH_DAY);
		}
		else{
			if(task.getTaskType()==TaskType.EVENT && 
					task.getStartDate().isBefore(task.getEndDate())){
				
				LocalDate currentDate=task.getStartDate();
				while(!currentDate.isAfter(task.getEndDate())){
					addTaskAtDate(taskList,currentDate,task);
					sortTaskAtDate(taskList,currentDate);
					currentDate=currentDate.plusDays(1);
				}
			}
			else{
				addTaskAtDate(taskList,task.getDate(),task);
				sortTaskAtDate(taskList,task.getDate());
			}
		}
	}
	
	public void addMultipleTasks(ArrayList<Task>taskList){
		for(Task task:taskList){
			addTask(task);
		}
	}
	
	public void deleteTask(Task task){
		boolean isDone=task.getDoneStatus();
		SortedMap<LocalDate,ArrayList<Task>>taskList=isDone?doneList:todoList;
		
		if(task.getTaskType()==TaskType.FLOATING){
			removeTaskAtDate(taskList,THE_MYTH_DAY,task);
		}
		
		else{
			if(task.getTaskType()==TaskType.EVENT && 
					task.getStartDate().isBefore(task.getEndDate())){
				
				LocalDate currentDate=task.getStartDate();
				while(!currentDate.isAfter(task.getEndDate())){
					removeTaskAtDate(taskList,currentDate,task);
					currentDate=currentDate.plusDays(1);
				}
			}
			else{
				removeTaskAtDate(taskList,task.getDate(),task);
			}
		}
		
	}
	
	public void deleteMultipleTasks(ArrayList<Task>taskList){
		for(Task task:taskList){
			deleteTask(task);
		}
	}
	
	public void clearAllTasks(){
		todoList.clear();
		doneList.clear();		
	}
	
	
// ------------------------------------- Task Query -----------------------------------------
	
	public ArrayList<Task> searchTasks(Pair<LocalDate,ArrayList<Integer>>pair){
		if(pair==null){
			return null;
		}
		
		ArrayList<Task>output;
		LocalDate date=pair.getFirst();
		ArrayList<Integer>indexList=pair.getSecond();
		if(indexList!=null){
			output=searchMultipleTasksAtDate(date,indexList);
		}
		else{
			output=searchAllTasksAtDate(date);
		}
		return output;
	}
	
	private ArrayList<Task>searchMultipleTasksAtDate(LocalDate date,ArrayList<Integer>indexList){
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
	
	public Task searchSingleTask(LocalDate date,int displayIndex){
		int actualIndex=displayIndex-1;
		if(date==null){
			if(actualIndex>=todoList.get(THE_MYTH_DAY).size()){
				return null;
			}
			return todoList.get(THE_MYTH_DAY).get(actualIndex);
		}
		else{
			if(!todoList.containsKey(date)||actualIndex>=todoList.get(date).size()){
				return null;
			}
			return todoList.get(date).get(actualIndex);
		}
				
	}
	
	private ArrayList<Task>searchAllTasksAtDate(LocalDate date){
		if(date==null){
			return new ArrayList<Task>(todoList.get(THE_MYTH_DAY));
		}
		else if(!todoList.containsKey(date)){
			Display.setFeedBack("you do not have any task on this date");
			return null;
		}
		else{
			return new ArrayList<Task>(todoList.get(date));
		}
	}
	
//------------------------------------Auxiliary Methods---------------------------------------
	
	private static void sortTaskAtDate(SortedMap<LocalDate,ArrayList<Task>>taskList,
			LocalDate date){
		
		ArrayList<Task>tasks=taskList.get(date);
		
		//sort by time or name
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
		
		//sort by task type
		Collections.sort(tasks,(Task t1,Task t2)->t1.getTaskType().compareTo(t2.getTaskType()));
	}
	
	
	private static void addTaskAtDate(SortedMap<LocalDate,ArrayList<Task>>map,
			LocalDate date,Task task){
		
		if(!map.containsKey(date)){
			map.put(date, new ArrayList<Task>());
		}
		map.get(date).add(task);
	}
	
	private static void removeTaskAtDate(SortedMap<LocalDate,ArrayList<Task>>map,
			LocalDate date,Task task){
		
		map.get(date).remove(task);
		if(map.get(date).isEmpty() && !date.isEqual(THE_MYTH_DAY)){
			map.remove(date);
		}
	}
	
	private static void markClashingEvents(ArrayList<Task>eventList){
		for(Task task:eventList){
			task.setClash(false);
		}
		
		Task first,second;
		for(int i=0;i<eventList.size()-1;i++){
			first=eventList.get(i);
			second=eventList.get(i+1);
			if(first.getStartTime().isBefore(second.getEndTime())&&
					first.getEndTime().isAfter(second.getStartTime())){
				System.out.println("test");
				first.setClash(true);
				second.setClash(true);
				continue;
			}
			
			
		}
	}
	
	
	private static SortedMap<LocalDate,ArrayList<Task>> checkForClash(SortedMap<LocalDate,ArrayList<Task>>taskMap){
		for(LocalDate date:taskMap.keySet()){
			if(date.isEqual(THE_MYTH_DAY)){
				continue;
			}
			ArrayList<Task>eventList=new ArrayList<Task>();
			for(Task task:taskMap.get(date)){
				if(task.getTaskType()==TaskType.EVENT && task.getStartTime()!=null){
					eventList.add(task);
				}
			}
			markClashingEvents(eventList);	
		}
		return taskMap;
	}
	
}
