package storage;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import common.Event;

import org.junit.Test;

public class EventStorageTest {

	@Test
	public void test() {
		Event e1=new Event("event one",LocalDate.of(2015,9,15),LocalTime.of(15, 30));
		Event e2=new Event("event two",LocalDate.of(2015, 9, 20),LocalTime.of(9, 15));
		Event e3=new Event("event three",LocalDate.of(2015, 9, 25),LocalTime.of(7, 45));
		ArrayList<Event>eventList=new ArrayList<Event>(Arrays.asList(e1,e2,e3));
		EventStorage.writeToFile(eventList);
		ArrayList<Event>anotherList=EventStorage.readFromFile();
		assertEquals(anotherList.get(1).getDate(),eventList.get(1).getDate());
	}

}
