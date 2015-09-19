package logic;

import java.time.LocalDate;
import java.util.ArrayList;

import common.Event;

public class DeleteCommand implements Command{
	EventHandler handler;
	ArrayList<Event> eventRef;
	
	public DeleteCommand(EventHandler handler){
		this.handler=handler;
	}
	
	public String execute(LocalDate date,ArrayList<Integer>deleteIndex){
		String output="";
		
		for(Integer index:deleteIndex){
			eventRef.add(handler.searchEventByIndexAndDate(date,index));
		}
		
		for(Event event:eventRef){
			handler.deleteEvent(event);
			output=event.getName()+" is deleted\n"+output;
		}
		return output;
	}
	
	public void undo(){
		handler.addEvents(eventRef);
	}
	
	public void redo(){
		handler.deleteEvents(eventRef);
	}
}
