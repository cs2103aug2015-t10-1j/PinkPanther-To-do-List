/* @@author GB */

package tests;
import logic.Controller;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import common.Display;
import common.ProgramState;

public class Controller_Test {
	Controller controller = new Controller();
	ProgramState state = controller.getProgramState();
	LocalDate date = LocalDate.of(2015, 11, 7);
	
	@Before
	public void setUp() {
		controller.addCommand("clear");
		controller.addCommand("add do homework, 2 to 5pm, 7 nov");
		controller.addCommand("add feed the dog,6pm,7 nov");
	}
	
	@Test
	public void addTask_positive(){

		controller.addCommand("add revise for exam, 7 to 8pm, 7 nov");
		String name = state.getTodoList().get(date).get(1).getName();
		assertEquals(name,"revise for exam");
		assertEquals(state.getTodoList().get(date).size(),3);
		String feedbackString = "Task added: " + "[" + "2015-11-07" + "] " + "\"" + "revise for exam" + "\"";
		assertEquals(Display.showFeedBack(),feedbackString);
	}
	
	@Test
	public void addTask_negative(){
		controller.addCommand("add do homework, 2 to 5pm, 7 nov");
		assertEquals(state.getTodoList().get(date).size(),2);
		assertEquals(Display.showFeedBack(),"You have already added this task");
	}
	
	@Test
	public void deleteTask_positive(){
		controller.addCommand("del 7 nov,1");
		assertEquals(state.getTodoList().get(date).size(),1);
		String name = state.getTodoList().get(date).get(0).getName();
		assertEquals(name,"feed the dog");
		String feedbackString = "\""+"do homework"+"\""+" has been deleted from "+"2015-11-07";
		assertEquals(Display.showFeedBack(),feedbackString);
	}
	
	@Test
	public void deleteTask_negative(){
		controller.addCommand("del 7 nov,3");
		assertEquals(state.getTodoList().get(date).size(),2);
		String feedbackString = "Task or tasks do not exist";
		assertEquals(Display.showFeedBack(),feedbackString);
	}
	
	@Test
	public void deleteMultipleTask_positive(){
		controller.addCommand("del 7 nov,1 2");
		assertEquals(state.getTodoList().containsKey(date),false);
		assertEquals(Display.showFeedBack(),"Tasks have been deleted from 2015-11-07");
	}
	
	@Test
	public void deleteMultipleTask_negative(){
		controller.addCommand("del 7 nov,4 5");
		assertEquals(state.getTodoList().containsKey(date),true);
		assertEquals(state.getTodoList().get(date).size(),2);
		assertEquals(Display.showFeedBack(),"Task or tasks do not exist");
	}
	
	@Test
	public void editTask_positive(){
		controller.addCommand("edit 7 nov,1");
		assertEquals(state.getInputBoxText(),"do homework, 2.00PM to 5.00PM, 7/11/15");
		assertEquals(Display.showFeedBack(),"Edit the task in text box, then press ENTER.");
	}
	
	@Test
	public void editTask_negative(){
		controller.addCommand("edit 7 nov,3");
		assertEquals(state.getInputBoxText(),"edit 7 nov,3");
		assertEquals(Display.showFeedBack(),"Task or tasks do not exist");
	}
	
	@Test
	public void taskEdited_postive(){
		controller.addCommand("edit 7 nov,2");
		String editedString = "feed the cat,6.00PM,7/11/15";
		controller.addCommand(editedString);
		assertEquals(state.getTodoList().get(date).get(1).getName(),"feed the cat");
		assertEquals(Display.showFeedBack(),"\"feed the dog\" has been modified");
	}
	
	@Test
	public void taskEdited_negative(){
		controller.addCommand("edit 7 nov,2");
		String editedString = "       ";
		controller.addCommand(editedString);
		assertEquals(state.getTodoList().get(date).get(1).getName(),"feed the dog");
		assertEquals(Display.showFeedBack(),"You have not entered any task details.");
	}
	
	@Test
	public void doneTask_positive(){
		controller.addCommand("done 7 nov,1");
		assertEquals(state.getTodoList().get(date).size(),1);
		String feedbackString = "Marked as done: [" + "2015-11-07" + "] " + "\"" + "do homework" + "\"";
		assertEquals(Display.showFeedBack(),feedbackString);
		
		controller.addCommand("view done");
		assertEquals(state.getTodoList().get(date).get(0).getName(),"do homework");
	}
	
	@Test
	public void doneTask_negative(){
		controller.addCommand("done 7 nov,3");
		assertEquals(state.getInputBoxText(),"done 7 nov,3");
		assertEquals(Display.showFeedBack(),"Task or tasks do not exist");
	}
	
	@Test
	public void searchByKeyword_test(){
		controller.addCommand("search homework");
		assertEquals(state.getTodoList().get(date).get(0).getName(),"do homework");
	}
	
	@Test
	public void redo_undo_test(){
		controller.addCommand("undo");
		controller.addCommand("undo");
		assertEquals(state.getTodoList().containsKey(date),false);
		controller.addCommand("redo");
		assertEquals(state.getTodoList().get(date).get(0).getName(),"do homework");
		controller.addCommand("redo");
		assertEquals(state.getTodoList().get(date).get(1).getName(),"feed the dog");
	}
	
	@Test
	public void randomWord_test(){
		controller.addCommand("what is a quantum computer");
		assertEquals(state.getInputBoxText(),"what is a quantum computer");
		assertEquals(Display.showFeedBack(),"Unrecognized command. Press F2 for Help Screen.");
	}
	
	

}
