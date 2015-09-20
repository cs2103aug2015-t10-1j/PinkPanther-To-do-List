package logic;

import common.Event;
/*
 * Add class description
 */
public class AddCommand implements Command{
	EventHandler handler;
	Event eventRef;
	
	public AddCommand(EventHandler handler){
		this.handler=handler;
	}
	
	public String execute(Event event){	
		handler.addEvent(event);
		eventRef=event;
		return event.getName()+" is added";
	}
	
	public void undo(){
		handler.deleteEvent(eventRef);
	}
	
	public void redo(){
		handler.addEvent(eventRef);
	}
}
