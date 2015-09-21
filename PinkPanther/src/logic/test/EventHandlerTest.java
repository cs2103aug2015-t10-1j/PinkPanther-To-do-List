package logic.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import common.Task;
import logic.TaskHandler;

public class TaskHandlerTest {
	TaskHandler handler;
	
	@Test
	public void test(){
		handler=new TaskHandler();
		Task e1=new Task("Task one",LocalDate.of(2015,9,15),LocalTime.of(15, 30));
		Task e2=new Task("Task two",LocalDate.of(2015, 9, 20),LocalTime.of(9, 15));
		Task e3=new Task("Task three",LocalDate.of(2015, 9, 25),LocalTime.of(7, 45));
		ArrayList<Task>TaskList=new ArrayList<Task>(Arrays.asList(e1,e2,e3));
		handler.addTasks(TaskList);
		Task result1=handler.searchTodoByIndexAndDate(LocalDate.of(2015, 9, 20), 1);
		assertEquals(LocalTime.of(9, 15),result1.getStartTime());
		Task result2=handler.searchTodoByIndexAndDate(LocalDate.of(2015, 9, 20), 2);
		assertNull(result2);
		Task result3=handler.searchTodoByIndexAndDate(LocalDate.of(2015, 9, 25), 3);
		assertNull(result3);
	}
}
