/* @@author Brayton */

package tests;

import static org.junit.Assert.*;

import logic.AddCommand;
import logic.EditCommand;
import logic.TaskManager;
import storage.StorageControl;
import common.Task;
import common.Pair;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.SortedMap;

import org.junit.Test;

public class EditCommandTest {

	@Test
	public void execute_NonBoundary_FloatingPair() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		EditCommand testEditCommand = new EditCommand(testManager);
		Task testUnmodifiedTask = new Task("testing unmodified");
		Task testModifiedTask = new Task("testing modified");
		Pair<Task, Task> testPair = new Pair<Task, Task>(testUnmodifiedTask, testModifiedTask);
		
		assertEquals(testEditCommand.execute(testPair), true);
		
		ArrayList<Task> testFloatingList;
		testFloatingList = testManager.getFloating(false);
		assertEquals(testFloatingList.get(0), testModifiedTask);
	}
	
	@Test
	public void execute_NonBoundary_EventPair() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		EditCommand testEditCommand = new EditCommand(testManager);
		Task testUnmodifiedTask = new Task("testing unmodified", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), LocalDate.of(2015, 12, 25), LocalTime.of(18, 30));
		Task testModifiedTask = new Task("testing modified", LocalDate.of(2014, 11, 19), LocalTime.of(9, 14), LocalDate.of(2014, 11, 24), LocalTime.of(17, 29));
		Pair<Task, Task> testPair = new Pair<Task, Task>(testUnmodifiedTask, testModifiedTask);
		
		testAddCommand.execute(testUnmodifiedTask);
		assertEquals(testEditCommand.execute(testPair), true);
		
		SortedMap<LocalDate, ArrayList<Task>> testDatedList;
		testDatedList = testManager.getDated(false);
		assertEquals(testDatedList.get(LocalDate.of(2014, 11, 19)).toString(), "[" + testModifiedTask.toString() + "]");
	}
	
	@Test
	public void execute_Boundary_NullPair() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		EditCommand testEditCommand = new EditCommand(testManager);
		Pair<Task, Task> testPair = null;
		
		assertEquals(testEditCommand.execute(testPair), false);
	}
}