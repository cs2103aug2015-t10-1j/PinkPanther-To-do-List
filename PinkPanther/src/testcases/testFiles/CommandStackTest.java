/* @@author A0125250W */

package testcases.testFiles;

import static org.junit.Assert.*;

import logic.AddCommand;
import logic.CommandStack;
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

public class CommandStackTest {

	 /**
	  * Has been commented out as a test method had to be put in
	  * CommandStack in order for effective testing. When not
	  *  testing, this test method is commented out. Thus, this 
	  *	Junit has to be commented out as well.
	  *
	  * Please uncomment the test methods in CommandStack if you would
	  * like to use this JUnit test.
	  */
/*	@Test
	public void addCommand_NonBoundary_SingleCommand() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		CommandStack testCommandStack = new CommandStack();
		
		testCommandStack.addCommand(testAddCommand);
		
		assertEquals(testCommandStack.getUndoStack().size(), 1);
		assertEquals(testCommandStack.getRedoStack().isEmpty(), true);
	}
	
	@Test
	public void addCommand_NonBoundary_MultipleCommand() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand1 = new AddCommand(testManager);
		AddCommand testAddCommand2 = new AddCommand(testManager);
		AddCommand testAddCommand3 = new AddCommand(testManager);
		
		CommandStack testCommandStack = new CommandStack();
		
		testCommandStack.addCommand(testAddCommand1);
		testCommandStack.addCommand(testAddCommand2);
		testCommandStack.addCommand(testAddCommand3);
		
		assertEquals(testCommandStack.getUndoStack().size(), 3);
		assertEquals(testCommandStack.getRedoStack().isEmpty(), true);
	}
	
	@Test
	public void undoOperation_NonBoundary_UndoSingleCommand() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		Task testTask = new Task("testing");
		CommandStack testCommandStack = new CommandStack();
		
		testAddCommand.execute(testTask);
		testCommandStack.addCommand(testAddCommand);
		
		assertEquals(testCommandStack.getUndoStack().size(), 1);
		assertEquals(testCommandStack.getRedoStack().isEmpty(), true);
		
		assertEquals(testCommandStack.undoOperation(), true);
		assertEquals(testCommandStack.getUndoStack().isEmpty(), true);
		assertEquals(testCommandStack.getRedoStack().size(), 1);
	}
	
	@Test
	public void undoOperation_NonBoundary_UndoMultipleCommand() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand1 = new AddCommand(testManager);
		AddCommand testAddCommand2 = new AddCommand(testManager);
		AddCommand testAddCommand3 = new AddCommand(testManager);
		Task testTask1 = new Task("testing1");
		Task testTask2 = new Task("testing2");
		Task testTask3 = new Task("testing3");
		CommandStack testCommandStack = new CommandStack();
		
		testAddCommand1.execute(testTask1);
		testCommandStack.addCommand(testAddCommand1);
		testAddCommand2.execute(testTask2);
		testCommandStack.addCommand(testAddCommand2);
		testAddCommand3.execute(testTask3);
		testCommandStack.addCommand(testAddCommand3);
		
		assertEquals(testCommandStack.getUndoStack().size(), 3);
		assertEquals(testCommandStack.getRedoStack().isEmpty(), true);
		
		assertEquals(testCommandStack.undoOperation(), true);
		assertEquals(testCommandStack.getUndoStack().size(), 2);
		assertEquals(testCommandStack.getRedoStack().size(), 1);
		
		assertEquals(testCommandStack.undoOperation(), true);
		assertEquals(testCommandStack.getUndoStack().size(), 1);
		assertEquals(testCommandStack.getRedoStack().size(), 2);
		
		assertEquals(testCommandStack.undoOperation(), true);
		assertEquals(testCommandStack.getUndoStack().isEmpty(), true);
		assertEquals(testCommandStack.getRedoStack().size(), 3);
	}
	
	@Test
	public void addCommand_NonBoundary_RedoSingleCommand() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand = new AddCommand(testManager);
		Task testTask = new Task("testing");
		CommandStack testCommandStack = new CommandStack();
		
		testAddCommand.execute(testTask);
		testCommandStack.addCommand(testAddCommand);
		testCommandStack.undoOperation();
		
		assertEquals(testCommandStack.redoOperation(), true);
		assertEquals(testCommandStack.getUndoStack().size(), 1);
		assertEquals(testCommandStack.getRedoStack().isEmpty(), true);
	}
	
	@Test
	public void redoOperation_NonBoundary_RedoMultipleCommand() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand1 = new AddCommand(testManager);
		AddCommand testAddCommand2 = new AddCommand(testManager);
		AddCommand testAddCommand3 = new AddCommand(testManager);
		Task testTask1 = new Task("testing1");
		Task testTask2 = new Task("testing2");
		Task testTask3 = new Task("testing3");
		CommandStack testCommandStack = new CommandStack();
		
		testAddCommand1.execute(testTask1);
		testCommandStack.addCommand(testAddCommand1);
		testAddCommand2.execute(testTask2);
		testCommandStack.addCommand(testAddCommand2);
		testAddCommand3.execute(testTask3);
		testCommandStack.addCommand(testAddCommand3);

		testCommandStack.undoOperation();
		testCommandStack.undoOperation();
		testCommandStack.undoOperation();
		
		assertEquals(testCommandStack.redoOperation(), true);
		assertEquals(testCommandStack.getUndoStack().size(), 1);
		assertEquals(testCommandStack.getRedoStack().size(), 2);
		
		assertEquals(testCommandStack.redoOperation(), true);
		assertEquals(testCommandStack.getUndoStack().size(), 2);
		assertEquals(testCommandStack.getRedoStack().size(), 1);
		
		assertEquals(testCommandStack.redoOperation(), true);
		assertEquals(testCommandStack.getUndoStack().size(), 3);
		assertEquals(testCommandStack.getRedoStack().isEmpty(), true);
	}
	
	@Test
	public void addCommand_Boundary_MultipleCommandExceed5() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand1 = new AddCommand(testManager);
		AddCommand testAddCommand2 = new AddCommand(testManager);
		AddCommand testAddCommand3 = new AddCommand(testManager);
		AddCommand testAddCommand4 = new AddCommand(testManager);
		AddCommand testAddCommand5 = new AddCommand(testManager);
		AddCommand testAddCommand6 = new AddCommand(testManager);
		
		CommandStack testCommandStack = new CommandStack();
		
		testCommandStack.addCommand(testAddCommand1);
		testCommandStack.addCommand(testAddCommand2);
		testCommandStack.addCommand(testAddCommand3);
		testCommandStack.addCommand(testAddCommand4);
		testCommandStack.addCommand(testAddCommand5);
		testCommandStack.addCommand(testAddCommand6);
		
		assertEquals(testCommandStack.getUndoStack().size(), 5);
		assertEquals(testCommandStack.getRedoStack().isEmpty(), true);
	}
	
	@Test
	public void undoOperation_Boundary_UndoMultipleCommandExceed5() {
		StorageControl testStorageControl = new StorageControl();
		TaskManager testManager = new TaskManager(testStorageControl);
		AddCommand testAddCommand1 = new AddCommand(testManager);
		AddCommand testAddCommand2 = new AddCommand(testManager);
		AddCommand testAddCommand3 = new AddCommand(testManager);
		AddCommand testAddCommand4 = new AddCommand(testManager);
		AddCommand testAddCommand5 = new AddCommand(testManager);
		AddCommand testAddCommand6 = new AddCommand(testManager);
		Task testTask1 = new Task("testing1");
		Task testTask2 = new Task("testing2");
		Task testTask3 = new Task("testing3");
		Task testTask4 = new Task("testing4");
		Task testTask5 = new Task("testing5");
		Task testTask6 = new Task("testing6");
		CommandStack testCommandStack = new CommandStack();
		
		testAddCommand1.execute(testTask1);
		testCommandStack.addCommand(testAddCommand1);
		testAddCommand2.execute(testTask2);
		testCommandStack.addCommand(testAddCommand2);
		testAddCommand3.execute(testTask3);
		testCommandStack.addCommand(testAddCommand3);
		testAddCommand4.execute(testTask4);
		testCommandStack.addCommand(testAddCommand4);
		testAddCommand5.execute(testTask5);
		testCommandStack.addCommand(testAddCommand5);
		testAddCommand6.execute(testTask6);
		testCommandStack.addCommand(testAddCommand6);
		
		testCommandStack.undoOperation();
		testCommandStack.undoOperation();
		testCommandStack.undoOperation();
		testCommandStack.undoOperation();
		testCommandStack.undoOperation();
		assertEquals(testCommandStack.undoOperation(), false);
		assertEquals(testCommandStack.getRedoStack().size(), 5);
	}
	
	@Test
	public void undoOperation_Boundary_NullCommandUndo() {
		CommandStack testCommandStack = new CommandStack();
		
		assertEquals(testCommandStack.getUndoStack().isEmpty(), true);
		assertEquals(testCommandStack.undoOperation(), false);
	}
	
	@Test
	public void undoOperation_Boundary_NullCommandRedo() {
		CommandStack testCommandStack = new CommandStack();
		
		assertEquals(testCommandStack.getUndoStack().isEmpty(), true);
		assertEquals(testCommandStack.redoOperation(), false);
	}*/
}
