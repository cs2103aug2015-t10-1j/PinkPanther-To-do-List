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
	
	public String addEvent(Event event){
		eventList.add(event);
		Collections.sort(eventList);
		EventStorage.writeToFile(eventList);
		return event.getName()+" is added";
	}
	
	public Event searchEventByIndexAndDate(LocalDate date,int eventNumber){
		int startDateIndex=0;
		for(Event event:eventList){
			if(event.getDate().equals(date)){
				break;
			}
			startDateIndex++;
		}
		int eventIndex=startDateIndex+eventNumber-1;
		return eventList.get(eventIndex);
		
	}
}
