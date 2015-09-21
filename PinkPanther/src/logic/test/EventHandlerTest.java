package logic.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import common.Event;
import logic.EventHandler;

public class EventHandlerTest {
	EventHandler handler;
	
	@Test
	public void test(){
		handler=new EventHandler(true);
		Event e1=new Event("event one",LocalDate.of(2015,9,15),LocalTime.of(15, 30));
		Event e2=new Event("event two",LocalDate.of(2015, 9, 20),LocalTime.of(9, 15));
		Event e3=new Event("event three",LocalDate.of(2015, 9, 25),LocalTime.of(7, 45));
		ArrayList<Event>eventList=new ArrayList<Event>(Arrays.asList(e1,e2,e3));
		handler.addEvents(eventList);
		Event result1=handler.searchEventByIndexAndDate(LocalDate.of(2015, 9, 20), 1);
		assertEquals(LocalTime.of(9, 15),result1.getStartTime());
		Event result2=handler.searchEventByIndexAndDate(LocalDate.of(2015, 9, 20), 2);
		assertNull(result2);
		Event result3=handler.searchEventByIndexAndDate(LocalDate.of(2015, 9, 25), 3);
		assertNull(result3);
	}
}
