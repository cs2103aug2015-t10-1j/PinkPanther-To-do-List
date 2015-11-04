/* @@author Brayton */

package tests;

import static org.junit.Assert.*;

import logic.AddCommand;
import logic.DeleteCommand;
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

public class DeleteCommandTest {

	@Test
	public void execute_NonBoundary_SingleFloatingTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddcommand = new AddCommand(testManager);
		DeleteCommand testDeleteCommand = new DeleteCommand(testManager);
		Task testTask = new Task("testing");
		ArrayList<Integer> testIndexArrayList = new ArrayList<Integer>();
		testIndexArrayList.add(1);
		Pair<LocalDate, ArrayList<Integer>> testPair = new Pair<LocalDate, ArrayList<Integer>>(null, testIndexArrayList);
		
		testAddcommand.execute(testTask);
		assertEquals(testDeleteCommand.execute(testPair), true);
		
		ArrayList<Task> testFloatingList;
		testFloatingList = testManager.getFloating(false);
		assertEquals(testFloatingList.isEmpty(), true);
	}
	
	@Test
	public void execute_NonBoundary_SingleDatedTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddcommand = new AddCommand(testManager);
		DeleteCommand testDeleteCommand = new DeleteCommand(testManager);
		Task testTask = new Task("testing", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), LocalDate.of(2015, 12, 25), LocalTime.of(18, 30));
		ArrayList<Integer> testIndexArrayList = new ArrayList<Integer>();
		testIndexArrayList.add(1);
		Pair<LocalDate, ArrayList<Integer>> testPair = new Pair<LocalDate, ArrayList<Integer>>(LocalDate.of(2015, 12, 20), testIndexArrayList);
		
		testAddcommand.execute(testTask);
		assertEquals(testDeleteCommand.execute(testPair), true);
		
		SortedMap<LocalDate, ArrayList<Task>> testDatedList;
		testDatedList = testManager.getDated(false);
		assertEquals(testDatedList.isEmpty(), true);
	}
	
	@Test
	public void execute_NonBoundary_MultiFloatingTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddcommand = new AddCommand(testManager);
		DeleteCommand testDeleteCommand = new DeleteCommand(testManager);
		Task testTask1 = new Task("testing1");
		Task testTask2 = new Task("testing2");
		Task testTask3 = new Task("testing3");
		ArrayList<Integer> testIndexArrayList = new ArrayList<Integer>();
		testIndexArrayList.add(1);
		testIndexArrayList.add(2);
		testIndexArrayList.add(3);
		Pair<LocalDate, ArrayList<Integer>> testPair = new Pair<LocalDate, ArrayList<Integer>>(null, testIndexArrayList);
		
		testAddcommand.execute(testTask1);
		testAddcommand.execute(testTask2);
		testAddcommand.execute(testTask3);
		assertEquals(testDeleteCommand.execute(testPair), true);
		
		ArrayList<Task> testFloatingList;
		testFloatingList = testManager.getFloating(false);
		assertEquals(testFloatingList.isEmpty(), true);
	}
	
	@Test
	public void execute_NonBoundary_MultiDatedTask() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddcommand = new AddCommand(testManager);
		DeleteCommand testDeleteCommand = new DeleteCommand(testManager);
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
		assertEquals(testDeleteCommand.execute(testPair), true);
		
		SortedMap<LocalDate, ArrayList<Task>> testDatedList;
		testDatedList = testManager.getDated(false);
		assertEquals(testDatedList.isEmpty(), true);
	}
	
	@Test
	public void execute_Boundary_Null() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		DeleteCommand testDeleteCommand = new DeleteCommand(testManager);
		Pair<LocalDate, ArrayList<Integer>> testPair = null;
		
		assertEquals(testDeleteCommand.execute(testPair), false);
	}
}