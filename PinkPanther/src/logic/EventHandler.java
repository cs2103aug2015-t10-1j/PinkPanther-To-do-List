package logic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import storage.EventStorage;
import common.*;

public class EventHandler {
	ArrayList<Event>eventList;
	
	public EventHandler(){
		
		eventList=EventStorage.readFromFile();
	}
	
	public void addEvent(Event event){
		eventList.add(event);
		Collections.sort(eventList);
		EventStorage.writeToFile(eventList);
	}
	
	public void addEvents(ArrayList<Event>events){
		for(Event event:events){
			eventList.add(event);
		}
		Collections.sort(eventList);
		EventStorage.writeToFile(eventList);
	}
	
	public void deleteEvent(Event event){
		eventList.remove(event);
		EventStorage.writeToFile(eventList);
	}
	
	
	public void deleteEvents(ArrayList<Event> events){
		for(Event event:events){
			eventList.remove(event);
		}
		EventStorage.writeToFile(eventList);
	}
	
	public Event searchEventByIndexAndDate(LocalDate date,int eventIndex){
		int startDateIndex=0;
		for(Event event:eventList){
			if(event.getDate().equals(date)){
				break;
			}
			startDateIndex++;
		}
		
		return eventList.get(startDateIndex+eventIndex-1);	
	}
	
	
}
