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
	
	public ArrayList<ArrayList<Event>> executeDateLineTask() {
		ArrayList<Event> dateLineList = this.handler.getDateLineList();
		ArrayList<ArrayList<Event>> overallContainer;
		
		ArrayList<LocalDate> dates = obtainDates(dateLineList); //Get the various dates in eventList.
		
		int eventListSize = dateLineList.size();
		int numOfDates = dates.size();
		
		for (int i = 0; i < numOfDates; i++) {
			overallContainer.add(packageEventsWithSameDate(dates.get(i), dateLineList));
		}
		
		return overallContainer;
	}

	private ArrayList<Event> packageEventsWithSameDate(LocalDate currentDate, ArrayList<Event> dateLineList) {
		ArrayList<Event> packagedList = new ArrayList<Event>();
		
		for (Event event: dateLineList) {
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
