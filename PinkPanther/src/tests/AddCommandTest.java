package tests;

import static org.junit.Assert.*;

import logic.AddCommand;
import logic.TaskManager;
import storage.StorageControl;
import common.Task;
import common.TaskType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.SortedMap;

import org.junit.Test;

public class AddCommandTest {

	@Test
	public void execute_NonBoundary_FloatingTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		Task testTask = new Task("testing");
		
		assertEquals(testAddCommand.execute(testTask), true);
		
		ArrayList<Task> testFloatingList;
		testFloatingList = testManager.getFloating(false);
		assertEquals(testFloatingList.get(0), testTask);
	}
	
	@Test
	public void execute_NonBoundary_EventTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		Task testTask = new Task("testing", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), LocalDate.of(2015, 12, 25), LocalTime.of(18, 30));
		
		assertEquals(testAddCommand.execute(testTask), true);
		
		SortedMap<LocalDate, ArrayList<Task>> testDatedList;
		testDatedList = testManager.getDated(false);
		assertEquals(testDatedList.get(LocalDate.of(2015, 12, 20)).toString(), "[" + testTask.toString() + "]");
	}
	
	@Test
	public void execute_NonBoundary_TodoTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		Task testTask = new Task("testing", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), TaskType.TODO);
		
		assertEquals(testAddCommand.execute(testTask), true);
		
		SortedMap<LocalDate, ArrayList<Task>> testDatedList;
		testDatedList = testManager.getDated(false);
		assertEquals(testDatedList.get(LocalDate.of(2015, 12, 20)).toString(), "[" + testTask.toString() + "]");
	}
	
	@Test
	public void execute_NonBoundary_DeadlineTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		Task testTask = new Task("testing", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), TaskType.DEADLINE);
		
		assertEquals(testAddCommand.execute(testTask), true);
		
		SortedMap<LocalDate, ArrayList<Task>> testDatedList;
		testDatedList = testManager.getDated(false);
		assertEquals(testDatedList.get(LocalDate.of(2015, 12, 20)).toString(), "[" + testTask.toString() + "]");
	}
	
	@Test
	public void execute_Boundary_Null() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		Task testTask = null;
		assertEquals(testAddCommand.execute(testTask), false);
	}
	
	@Test
	public void execute_Boundary_RepeatedTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		Task testTask = new Task("testing", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), LocalDate.of(2015, 12, 25), LocalTime.of(18, 30));
		
		assertEquals(testAddCommand.execute(testTask), true);
		
		SortedMap<LocalDate, ArrayList<Task>> testDatedList;
		testDatedList = testManager.getDated(false);
		assertEquals(testDatedList.get(LocalDate.of(2015, 12, 20)).toString(), "[" + testTask.toString() + "]");
		
		assertEquals(testAddCommand.execute(testTask), false);
	}

}
