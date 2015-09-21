package logic;

import common.Task;

import java.util.ArrayList;
import java.time.LocalDate;
/*
 * Add class description
 */
public class PopulateLists {
	TaskHandler handler;
	
	public PopulateLists(TaskHandler handler) {
		this.handler = handler;
	}
	
	public ArrayList<ArrayList<Task>> populateTodoList() {
		ArrayList<Task> taskList = this.handler.getTodoList();
		ArrayList<ArrayList<Task>> overallContainer;
		
		ArrayList<LocalDate> dates = obtainDates(taskList); //Get the various dates in eventList.
		
		int taskListSize = taskList.size();
		int numOfDates = dates.size();
		
		for (int i = 0; i < numOfDates; i++) {
			overallContainer.add(packageEventsWithSameDate(dates.get(i), taskList));
		}
		
		return overallContainer;
	}
	
	public ArrayList<Task> populateFloatingList() {
		return handler.getFloatingList();
	}

	protected ArrayList<Task> packageEventsWithSameDate(LocalDate currentDate, ArrayList<Task> taskList) {
		ArrayList<Task> packagedList = new ArrayList<Task>();
		
		for (Task event: taskList) {
			if (event.getDate().equals(currentDate)) {
				packagedList.add(event);
			}
		}
		
		return packagedList;
	}

	protected static ArrayList<LocalDate> obtainDates(ArrayList<Task> eventList) {
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
