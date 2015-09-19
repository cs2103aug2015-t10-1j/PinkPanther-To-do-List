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
	
	public String deleteEvent(LocalDate date, ArrayList<Integer>indices){
		String output="";
		Collections.sort(indices);
		for(Integer index:indices){
			Event event=eventList.get(searchEventByIndexAndDate(date,index));
			output=event.getName()+" is deleted\n"+output;
			eventList.remove(event);
		}
		return output;
	}
	
	public int searchEventByIndexAndDate(LocalDate date,int eventNumber){
		int startDateIndex=0;
		for(Event event:eventList){
			if(event.getDate().equals(date)){
				break;
			}
			startDateIndex++;
		}
		
		return startDateIndex+eventNumber-1;
		
	}
	
	
}
