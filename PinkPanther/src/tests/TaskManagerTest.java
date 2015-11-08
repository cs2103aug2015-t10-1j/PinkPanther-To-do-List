/* @@author A0121585H */

package tests;
import logic.TaskManager;
import storage.StorageControl;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;

import org.junit.Before;
import org.junit.Test;

import common.Display;
import common.Pair;
import common.Task;
import common.TaskType;

public class TaskManagerTest {
	private StorageControl storage = new StorageControl();
	private TaskManager manager = new TaskManager(storage);
	LocalDate date = LocalDate.of(2015, 11, 13);
	
	@Before
	public void setUp() {
		manager.clearAllTasks();
		
		//initializing all tasks
		
		Task dated_1 = new Task("write Junit test cases", date,null,TaskType.TODO);
		Task dated_2 = new Task("submit report", date,LocalTime.of(19, 30),TaskType.DEADLINE);
		Task dated_3 = new Task("software demo", date,LocalTime.of(17, 0),date,LocalTime.of(18, 0));
		manager.addTask(dated_1);
		manager.addTask(dated_2);
		manager.addTask(dated_3);
	}
	
	@Test
	public void addTaskIsSorted(){
		Task task1 = new Task("submit developer guide",date,LocalTime.of(18, 30),TaskType.DEADLINE);
		Task task2 = new Task("apply for scholarship",date,null,TaskType.TODO);
		manager.addTask(task1);
		manager.addTask(task2);
		SortedMap<LocalDate,ArrayList<Task>>todoList = manager.getDated(false);
		
		assertEquals(todoList.get(date).size(),5);
		
		assertEquals(todoList.get(date).get(3).getName(),"submit developer guide");
		assertEquals(todoList.get(date).get(4).getName(),"submit report");
		
		assertEquals(todoList.get(date).get(1).getName(),"apply for scholarship");
		assertEquals(todoList.get(date).get(2).getName(),"write Junit test cases");
		
		assertEquals(todoList.get(date).get(0).getName(),"software demo");
		assertEquals(todoList.get(date).get(1).getName(),"apply for scholarship");
		
		assertEquals(todoList.get(date).get(2).getName(),"write Junit test cases");
		assertEquals(todoList.get(date).get(3).getName(),"submit developer guide");
	}
	
	@Test
	public void addTaskCheckDuplicateNegative(){
		Task task = new Task("watch star war movie",LocalDate.of(2015, 12, 15),null,TaskType.TODO);
		assertEquals(manager.addTask(task),true);
	}
	
	@Test
	public void addTaskCheckDuplicatePositive(){
		Task task = new Task("submit report", date,LocalTime.of(19, 30),TaskType.DEADLINE);
		assertEquals(manager.addTask(task),false);
	}
	
	@Test
	public void addTaskCheckClashPositive(){
		Task task = new Task("another event", date,LocalTime.of(16, 0),date,LocalTime.of(17, 20));
		manager.addTask(task);
		assertEquals(task.getClash(),true);
		assertEquals(manager.getDated(false).get(date).get(0).getClash(),true);
	}
	
	@Test
	public void addTaskCheckClashNegative(){
		Task task = new Task("another event", date,LocalTime.of(16, 0),date,LocalTime.of(17, 0));
		manager.addTask(task);
		assertEquals(task.getClash(),false);
		assertEquals(manager.getDated(false).get(date).get(0).getClash(),false);
	}
	
	@Test
	public void searchTasksPositive(){
		ArrayList<Integer> list1 = new ArrayList<Integer>(Arrays.asList(1));
		ArrayList<Integer> list2 = new ArrayList<Integer>(Arrays.asList(1,2,3));
		ArrayList<Integer> list3 = new ArrayList<Integer>(Arrays.asList(1,2,5));
		Pair<LocalDate,ArrayList<Integer>> pair1 = new Pair<LocalDate,ArrayList<Integer>>(date,list1);
		Pair<LocalDate,ArrayList<Integer>> pair2 = new Pair<LocalDate,ArrayList<Integer>>(date,list2);
		Pair<LocalDate,ArrayList<Integer>> pair3 = new Pair<LocalDate,ArrayList<Integer>>(date,list3);
		
		
		ArrayList<Task> output1 = manager.searchTasks(pair1);
		assertEquals(output1.get(0).getName(),"software demo");
		
		ArrayList<Task> output2 = manager.searchTasks(pair2);
		assertEquals(output2.get(1).getName(),"write Junit test cases");
		assertEquals(output2.get(2).getName(),"submit report");
		
		ArrayList<Task> output3 = manager.searchTasks(pair3);
		assertEquals(output3.size(),2);

	}
	
	@Test
	public void searchTasksNegative(){
		ArrayList<Integer> list1 = new ArrayList<Integer>(Arrays.asList(5));
		Pair<LocalDate,ArrayList<Integer>> pair1 = new Pair<LocalDate,ArrayList<Integer>>(date,list1);
		
		ArrayList<Task> output1 = manager.searchTasks(pair1);
		assertEquals(output1,null);
		assertEquals(Display.showFeedBack(),"Task or tasks do not exist");
	}
	
	@Test
	public void deleteTaskTaskDeleted(){
		Task task = manager.searchTasks(new Pair<LocalDate,ArrayList<Integer>>(date,
				new ArrayList<Integer>(Arrays.asList(2)))).get(0);
		manager.deleteTask(task);
		assertEquals(manager.getDated(false).get(date).get(1).getName(),"submit report");
		assertEquals(manager.getDated(false).get(date).size(),2);
	}
	
	@Test
	public void deleteTaskKeyDeleted(){
		LocalDate date1 = LocalDate.of(2015, 11, 10);
		Task task = new Task("random event",date1,null,date1,null);
		manager.addTask(task);
		assertEquals(manager.getDated(false).containsKey(date1),true);
		manager.deleteTask(task);
		assertEquals(manager.getDated(false).containsKey(date1),false);
	}

}
