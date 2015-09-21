package logic;

import common.Event;
import java.util.ArrayList;
import java.time.LocalDate;
/*
 * Add class description
 */
public class DisplayCommand {
	TaskHandler handler;
	Event eventRef;
	
	public DisplayCommand(TaskHandler handler) {
		this.handler = handler;
	}
	
	public ArrayList<ArrayList<Event>> executeDatedTasks() {
		ArrayList<Event> taskList = this.handler.getTaskList();
		ArrayList<ArrayList<Event>> overallContainer;
		
		ArrayList<LocalDate> dates = obtainDates(taskList); //Get the various dates in eventList.
		
		int taskListSize = taskList.size();
		int numOfDates = dates.size();
		
		for (int i = 0; i < numOfDates; i++) {
			overallContainer.add(packageEventsWithSameDate(dates.get(i), taskList));
		}
		
		return overallContainer;
	}
	
	public ArrayList<ArrayList<Event>> executeFloatingTasks() {
		return this.handler.getFloatingList();
	}

	protected ArrayList<Event> packageEventsWithSameDate(LocalDate currentDate, ArrayList<Event> taskList) {
		ArrayList<Event> packagedList = new ArrayList<Event>();
		
		for (Event event: taskList) {
			if (event.getDate().equals(currentDate)) {
				packagedList.add(event);
			}
		}
		
		return packagedList;
	}

	protected static ArrayList<LocalDate> obtainDates(ArrayList<Event> eventList) {
		ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
		int eventListSize = eventList.size();
		
		for (int i = 0; i < eventListSize; i++) {
			LocalDate currentDate = eventList.get(i).getDate();
			
			if (!hasDate(dates, currentDate)) {
				dates.add(eventList.get(i).getDate());
			}
		}
		
		return dates;
	}
	
	protected static boolean hasDate(ArrayList<LocalDate> dates, LocalDate currentDate) {
		for (LocalDate date: dates) {
			if (currentDate.equals(date)) {
				return true;
			}
		}
		
		return false;
	}
}
