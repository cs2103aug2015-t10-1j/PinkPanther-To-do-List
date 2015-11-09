/* @@author A0121585H */

package tests;
import logic.Controller;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import common.Display;
import common.ProgramState;

public class ControllerTest {
	Controller controller = new Controller();
	ProgramState state = controller.getProgramState();
	LocalDate date = LocalDate.of(2015, 11, 7);
	
	@Before
	public void setUp() {
		controller.addCommand("clear");
		controller.addCommand("add do homework, 2 to 5pm, 7 nov");
		controller.addCommand("add feed the dog,6pm,7 nov");
		controller.addCommand("add floating task");
	}
	
	@Test
	public void addTaskPositive(){

		controller.addCommand("add revise for exam, 7 to 8pm, 7 nov");
		String name = state.getTodoList().get(date).get(1).getName();
		assertEquals(name,"revise for exam");
		assertEquals(state.getTodoList().get(date).size(),3);
		String feedbackString = "Task added: " + "[" + "2015-11-07" + "] " + "\"" + "revise for exam" + "\"";
		assertEquals(Display.showFeedBack(),feedbackString);
	}
	
	@Test
	public void addTaskNegative(){
		controller.addCommand("add do homework, 2 to 5pm, 7 nov");
		assertEquals(state.getTodoList().get(date).size(),2);
		assertEquals(Display.showFeedBack(),"You have already added this task.");
	}
	
	@Test
	public void deleteTaskPositive(){
		controller.addCommand("del 7 nov,1");
		assertEquals(state.getTodoList().get(date).size(),1);
		String name = state.getTodoList().get(date).get(0).getName();
		assertEquals(name,"feed the dog");
		assertEquals(Display.showFeedBack(),"Task deleted: ["+"2015-11-07"+"] "+"\"" + "do homework" + "\"");
	}
	
	@Test
	public void deleteTaskNegative(){
		controller.addCommand("del 7 nov,3");
		assertEquals(state.getTodoList().get(date).size(),2);
		String feedbackString = "Error : Task or tasks do not exist.";
		assertEquals(Display.showFeedBack(),feedbackString);
	}
	
	@Test
	public void deleteMultipleTaskPositive(){
		controller.addCommand("del 7 nov,1 2");
		assertEquals(state.getTodoList().containsKey(date),false);
		assertEquals(Display.showFeedBack(),"Tasks deleted: [2015-11-07] " +"\"" + "do homework" + "\"...");
	}
	
	@Test
	public void deleteMultipleTaskNegative(){
		controller.addCommand("del 7 nov,4 5");
		assertEquals(state.getTodoList().containsKey(date),true);
		assertEquals(state.getTodoList().get(date).size(),2);
		assertEquals(Display.showFeedBack(),"Error : Task or tasks do not exist.");
	}
	
	@Test
	public void editTaskPositive(){
		controller.addCommand("edit 7 nov,1");
		assertEquals(state.getInputBoxText(),"do homework, 2.00PM to 5.00PM, 7/11/15");
		assertEquals(Display.showFeedBack(),"Edit the task in text box, then press ENTER.");
	}
	
	@Test
	public void editTaskNegative(){
		controller.addCommand("edit 7 nov,3");
		assertEquals(state.getInputBoxText(),"edit 7 nov,3");
		assertEquals(Display.showFeedBack(),"Error : Task or tasks do not exist.");
	}
	
	@Test
	public void taskEditedPostive(){
		controller.addCommand("edit 7 nov,2");
		String editedString = "feed the cat,6.00PM,7/11/15";
		controller.addCommand(editedString);
		assertEquals(state.getTodoList().get(date).get(1).getName(),"feed the cat");
		assertEquals(Display.showFeedBack(),"Task modified: "+"\""+"feed the dog"+"\"");
	}
	
	@Test
	public void taskEditedNegative(){
		controller.addCommand("edit 7 nov,2");
		String editedString = "       ";
		controller.addCommand(editedString);
		assertEquals(state.getTodoList().get(date).get(1).getName(),"feed the dog");
		assertEquals(Display.showFeedBack(),"You have not entered any task details.");
	}
	
	@Test
	public void doneTaskPositive(){
		controller.addCommand("done 7 nov,1");
		assertEquals(state.getTodoList().get(date).size(),1);
		String feedbackString = "Marked as done: [" + "2015-11-07" + "] " + "\"" + "do homework" + "\"";
		assertEquals(Display.showFeedBack(),feedbackString);
		
		controller.addCommand("view done");
		assertEquals(state.getTodoList().get(date).get(0).getName(),"do homework");
	}
	
	@Test
	public void doneTaskNegative(){
		controller.addCommand("done 7 nov,3");
		assertEquals(state.getInputBoxText(),"done 7 nov,3");
		assertEquals(Display.showFeedBack(),"Error : Task or tasks do not exist.");
	}
	
	@Test
	public void searchByKeyword(){
		controller.addCommand("search homework");
		assertEquals(state.getTodoList().get(date).get(0).getName(),"do homework");
	}
	
	@Test
	public void redoUndo(){
		controller.addCommand("undo");
		controller.addCommand("undo");
		controller.addCommand("undo");
		assertEquals(state.getTodoList().containsKey(date),false);
		controller.addCommand("redo");
		assertEquals(state.getTodoList().get(date).get(0).getName(),"do homework");
		controller.addCommand("redo");
		assertEquals(state.getTodoList().get(date).get(1).getName(),"feed the dog");
	}
	
	@Test
	public void randomWord(){
		controller.addCommand("what is a quantum computer");
		assertEquals(state.getInputBoxText(),"what is a quantum computer");
		assertEquals(Display.showFeedBack(),"Unrecognized command. Press F2 for Help Screen.");
	}
	
	@Test
	public void viewDateRangePositive(){
		controller.addCommand("view 7 nov");
		assertEquals(state.getTodoList().get(date).get(0).getName(),"do homework");
		assertEquals(state.getFloatingList(),null);
	}
	
	@Test
	public void viewDateRangeNegative(){
		controller.addCommand("view 7-5 nov 2015");
		assertEquals(state.getInputBoxText(),"view 7-5 nov 2015");
		assertEquals(Display.showFeedBack(),"Invalid view range specified!");
	}
	
	/* @@author A0122545M */
	@Test
	public void test_Controller_Return_To_Main_Calendar_Shortcut(){
		//This tests if Main Calendar can be displayed correctly by a call to controller
		state.setTitle("Viewing Helpscreen");
		controller.addCommand("view normal");
		assertEquals(state.getInputBoxText(),"");
		assertEquals(Display.showFeedBack(),"Input command into the field above.");
		assertEquals(state.getTitle(),"                         To-Do List");
		assertEquals((state.getOverdueList() != null), true);
	}
	
	/* @@author A0122545M */
	@Test
	public void test_Controller_View_All_Calendar_Shortcut(){
		//This test checks if View All mode is implemented correctly, and overdue tasks should be displayed
		for (int i=0; i<10; i++){
			state.setTitle("Random scene name");
			controller.addCommand("add this is an overdue task" + i);
			controller.addCommand("view all");
			assertEquals(state.getInputBoxText(),"");
			assertEquals(Display.showFeedBack(),"Input 'view normal' to return to main calendar.");
			assertEquals(state.getTitle(),"              ● Viewing: All Tasks ●");
			assertEquals((state.getOverdueList() != null), true);
		}
	}
	
	/* @@author A0122545M */
	@Test
	public void test_View_Overdue_Mode(){
		//This test is to check if program will change to Overdue mode correctly
		state.setTitle("Viewing Helpscreen");
		controller.addCommand("add this is a floating task");
		controller.addCommand("view overdue");
		assertEquals(state.getInputBoxText(),"");
		assertEquals(Display.showFeedBack(),"Input 'view normal' to return to main calendar.");
		assertEquals(state.getTitle(),"              ● Viewing: Overdue Tasks ●");
		//Floating list should be null as this screen does not show floating lists
		assertEquals(state.getFloatingList(),null);
	}
	

}
