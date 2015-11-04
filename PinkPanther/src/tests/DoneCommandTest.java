package tests;

import static org.junit.Assert.*;

import logic.AddCommand;
import logic.DeleteCommand;
import logic.DoneCommand;
import logic.EditCommand;
import logic.TaskManager;
import storage.StorageControl;
import common.Task;
import common.TaskType;
import common.Pair;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.SortedMap;

import org.junit.Test;

public class DoneCommandTest {

	@Test
	public void execute_NonBoundary_SingleFloatingTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddcommand = new AddCommand(testManager);
		DoneCommand testDoneCommand = new DoneCommand(testManager);
		Task testTask = new Task("testing");
		ArrayList<Integer> testIndexArrayList = new ArrayList<Integer>();
		testIndexArrayList.add(1);
		Pair<LocalDate, ArrayList<Integer>> testPair = new Pair<LocalDate, ArrayList<Integer>>(null, testIndexArrayList);
		
		testAddcommand.execute(testTask);
		assertEquals(testDoneCommand.execute(testPair), true);
		
		ArrayList<Task> testFloatingList;
		testFloatingList = testManager.getFloating(true);
		assertEquals(testFloatingList.get(0), testTask);
	}
	
	@Test
	public void execute_NonBoundary_SingleDatedTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddcommand = new AddCommand(testManager);
		DoneCommand testDoneCommand = new DoneCommand(testManager);
		Task testTask = new Task("testing", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), LocalDate.of(2015, 12, 25), LocalTime.of(18, 30));
		ArrayList<Integer> testIndexArrayList = new ArrayList<Integer>();
		testIndexArrayList.add(1);
		Pair<LocalDate, ArrayList<Integer>> testPair = new Pair<LocalDate, ArrayList<Integer>>(LocalDate.of(2015, 12, 20), testIndexArrayList);
		
		testAddcommand.execute(testTask);
		assertEquals(testDoneCommand.execute(testPair), true);
		
		SortedMap<LocalDate, ArrayList<Task>> testDatedList;
		testDatedList = testManager.getDated(true);
		assertEquals(testDatedList.get(LocalDate.of(2015,  12,  20)).toString(), "[" + testTask.toString() + "]");
	}
	
	@Test
	public void execute_NonBoundary_MultiFloatingTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		DoneCommand testDoneCommand = new DoneCommand(testManager);
		Task testTask1 = new Task("testing1");
		Task testTask2 = new Task("testing2");
		Task testTask3 = new Task("testing3");
		ArrayList<Integer> testIndexArrayList = new ArrayList<Integer>();
		testIndexArrayList.add(1);
		testIndexArrayList.add(2);
		testIndexArrayList.add(3);
		Pair<LocalDate, ArrayList<Integer>> testPair = new Pair<LocalDate, ArrayList<Integer>>(null, testIndexArrayList);
		
		testAddCommand.execute(testTask1);
		testAddCommand.execute(testTask2);
		testAddCommand.execute(testTask3);
		assertEquals(testDoneCommand.execute(testPair), true);
		
		ArrayList<Task> testFloatingList;
		testFloatingList = testManager.getFloating(true);
		assertEquals(testFloatingList.get(0), testTask1);
		assertEquals(testFloatingList.get(1), testTask2);
		assertEquals(testFloatingList.get(2), testTask3);
	}
	
	@Test
	public void execute_NonBoundary_MultiDatedTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddcommand = new AddCommand(testManager);
		DoneCommand testDoneCommand = new DoneCommand(testManager);
		Task testTask1 = new Task("testing1", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), LocalDate.of(2015, 12, 25), LocalTime.of(18, 30));
		Task testTask2 = new Task("testing2", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), LocalDate.of(2015, 12, 26), LocalTime.of(19, 30));
		Task testTask3 = new Task("testing3", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), LocalDate.of(2015, 12, 27), LocalTime.of(20, 30));
		ArrayList<Integer> testIndexArrayList = new ArrayList<Integer>();
		testIndexArrayList.add(1);
		testIndexArrayList.add(2);
		testIndexArrayList.add(3);
		Pair<LocalDate, ArrayList<Integer>> testPair = new Pair<LocalDate, ArrayList<Integer>>(LocalDate.of(2015, 12, 20), testIndexArrayList);
		
		testAddcommand.execute(testTask1);
		testAddcommand.execute(testTask2);
		testAddcommand.execute(testTask3);
		assertEquals(testDoneCommand.execute(testPair), true);
		
		SortedMap<LocalDate, ArrayList<Task>> testDatedList;
		testDatedList = testManager.getDated(true);
		assertEquals(testDatedList.isEmpty(), false);
	}
	
	@Test
	public void execute_Boundary_Null() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		DoneCommand testDoneCommand = new DoneCommand(testManager);
		Pair<LocalDate, ArrayList<Integer>> testPair = null;
		
		assertEquals(testDoneCommand.execute(testPair), false);
	}
}