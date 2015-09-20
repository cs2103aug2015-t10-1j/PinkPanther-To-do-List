package logic;

import java.time.LocalDate;

import common.Event;
/*
 * Add class description
 */
public class ModifyCommand implements Command{
	EventHandler handler;
	Event unmodified;
	Event modified;
	
	public ModifyCommand(EventHandler handler){
		this.handler=handler;
	}
	
	public String execute(LocalDate date,int eventIndex, Event event){
		unmodified=handler.searchEventByIndexAndDate(date, eventIndex);
		modified=event;
		if(modified.getName().equals("")){
			modified.setName(unmodified.getName());
		}
		if(modified.getDate()==null){
			modified.setDate(unmodified.getDate());
		}
		if(modified.getStartTime()==null){
			modified.setStartTime(unmodified.getStartTime());
		}
		if(modified.getEndTime()==null){
			modified.setEndTime(unmodified.getEndTime());
		}
		handler.deleteEvent(unmodified);
		handler.addEvent(modified);
		return unmodified.getName()+" is modified";
		
	}
	
	public void undo(){
		handler.deleteEvent(modified);
		handler.addEvent(unmodified);
	}
	
	public void redo(){
		handler.deleteEvent(unmodified);
		handler.addEvent(modified);
	}
}
