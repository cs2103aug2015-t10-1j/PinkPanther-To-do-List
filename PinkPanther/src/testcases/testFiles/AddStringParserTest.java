/* @@author A0126473E */
package testcases.testFiles;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import parser.AddStringParser;
import parser.SingleDateParser;
import common.Task;

public class AddStringParserTest {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/YYYY");
	String dateToday = LocalDate.now().format(dtf);
	String dateNextWeek = LocalDate.now().plusWeeks(1).format(dtf);
	
	AddStringParser asp = new AddStringParser();
	SingleDateParser sdp = new SingleDateParser();
	
	@Test
	public void testAddFloating() {
		String[] taskContents = {"something, yes", "cats are cool",
				"eat string beans, 24 feg, 5p.m.", 
				"dinner, candies, 24/7, no, next friday",
				"24/7/2015", "do laundry, 11-7pm",
				"watch the football games, 10pm, 2/7, 5am, 3/7",
				"HOLIDAYS!, 9-1 august"};
		String[] expectedDetails = {"something, yes", "cats are cool",
				"eat string beans, 24 feg, 5p.m.", 
				"dinner, candies, 24/7, no, next friday",
				"24/7/2015", "do laundry, 11-7pm",
				"watch the football games, 10pm, 2/7, 5am, 3/7",
				"HOLIDAYS!, 9-1 august"};
		for (int i = 0; i < taskContents.length; i++) {
			Task actualTask = asp.parse(taskContents[i]);
			Task expectedTask = new Task(expectedDetails[i]);
			assertEquals(expectedTask.toString(), actualTask.toString());
		}
	}
	
	@Test
	public void testAddTodoPositive() {
		String[] taskContents = {"something, 4/9", "whatever, today",
				"water the plants, afternoon", 
				"dine with cats, dogs and rabbits, at 2pm",
				"explore Narnia, after 7pm, 15/6", ",5/8/1989"};
		String[] expectedDetails = {"something, at 4/9/2015", 
				"whatever, at " + dateToday,
				"water the plants, at 12.00PM, " + dateToday, 
				"dine with cats, dogs and rabbits, at 2.00PM, " +dateToday,
				"explore Narnia, at 7.00PM, 15/6/2015", ", at 5/8/1989"};
		for (int i = 0; i < taskContents.length; i++) {
			Task actualTask = asp.parse(taskContents[i]);
			Task expectedTask = new Task(expectedDetails[i]);
			assertEquals(expectedTask.toString(), actualTask.toString());
		}
	}
	
	@Test
	public void testAddDeadlinePositive() {
		String[] taskContents = {"something, by 4/9", "whatever, due 5pm",
				"water the plants, before 23/4, at 2pm", 
				"dine with cats, dogs and rabbits, by 17 jun, by 9.28pm"};
		String[] expectedDetails = {"something, by 4/9/2015", 
				"whatever, by 5.00PM, " + dateToday,
				"water the plants, by 2.00PM, 23/4/2015", 
				"dine with cats, dogs and rabbits, by 9.28PM, 17/6/2015"};
		for (int i = 0; i < taskContents.length; i++) {
			Task actualTask = asp.parse(taskContents[i]);
			Task expectedTask = new Task(expectedDetails[i]);
			assertEquals(expectedTask.toString(), actualTask.toString());
		}
	}
	
	@Test
	public void testAddEventsPositive() {
		String[] taskContents = {"something, 2-3pm",
				"whatever, 4 to 5 jan 2016, 2-4am",
				"feed cats, today, next week, 10am, 1733"};
		String[] expectedDetails = {"something, 2.00PM to 3.00PM, " + dateToday, 
				"whatever, 2.00AM to 4.00AM, 4/1/2016 to 5/1/2016",
				"feed cats, 10.00AM to 5.33PM, " + dateToday + " to " + dateNextWeek};
		for (int i = 0; i < taskContents.length; i++) {
			Task actualTask = asp.parse(taskContents[i]);
			Task expectedTask = new Task(expectedDetails[i]);
			assertEquals(expectedTask.toString(), actualTask.toString());
		}
	}
	
	@Test
	public void testAddEmpty() {
		String[] taskContents = {"", ",", ",,,,,,"};
		for (int i = 0; i < taskContents.length; i++) {
			Task actualTask = asp.parse(taskContents[i]);
			assertEquals(null, actualTask);
		}
	}
}
