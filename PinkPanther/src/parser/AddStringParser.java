package parser;

import common.Task;
import common.TaskType;
import common.Auxiliary;
import logic.Display;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddStringParser implements Parser {
	
	private LocalDate startDateStore;
	private LocalDate endDateStore;
	private LocalTime startTimeStore;
	private LocalTime endTimeStore;
	private TaskType taskTypeStore;
	
	// indexes of various arrays
	private static final int INDEX_TASKNAME = 0;
	private static final int INDEX_TASK_DETAIL = 1;
	
	// word list
	private static final String[] LIST_TWO_DATE_MARKERS = {" to ", "-"};
	private static final String[] LIST_DEADLINE_MARKERS = {"by", "due", "before"}; 
	private static final String[] LIST_START_MARKERS = {"at", "after"};
	
	private static SingleDateParser sdp = new SingleDateParser();
	private static SingleTimeParser stp = new SingleTimeParser();

	
	// main functionality
	public Task parse(String commandContent){
		
		clearStores();
		String[] userInfo = commandContent.split(",");
		userInfo = Auxiliary.trimStringArray(userInfo);
		int validDateTimes = findValidDateTime(userInfo);
		
		// does not accept empty input
		if (Auxiliary.isEmptyArray(userInfo)) {
			return null;
		}
		
		// create a floating task
		if (userInfo.length == 1 || validDateTimes == 0) {
			return addFloating(commandContent);
		}
		
		// create an event
		// should we catch the exception or be lenient?
		if (validDateTimes == 2)  {
			try {
				return addEvent(userInfo);
			} catch (Exception e) {
				Display.setFeedBack(e.getMessage());
			}
			return null;
		
		// create a deadline or to-do
		} else if (validDateTimes == 1)  {
			return addSingleDated(userInfo);
		
		// invalid task to create; no task created
		} else {
			Display.setFeedBack("Invalid add command");
			return null;
		}
	}
	
	
	// input processing logic methods
	// beware of exceptions!
	public int findValidDateTime(String [] possiblyDateTime) {
		int dateCounter = 0;
		int timeCounter = 0;
		for (int i = INDEX_TASK_DETAIL; i < possiblyDateTime.length; i++) {
			int dateCount = countValidDates(possiblyDateTime[i]);
			int timeCount = countValidTimes(possiblyDateTime[i]);
			dateCounter += dateCount;
			timeCounter += timeCount;
			if (dateCount == 0 && timeCount == 0 ) {
				return 0;
			}
		}
		if (Math.max(dateCounter, timeCounter) > 2) {
			return 0;
		}
		return Math.max(dateCounter, timeCounter);
	}
	
	// counts number of dates
	private int countValidDates(String dateTimeInfo) {
		
		// itself is a date
		if (isSingleDate(dateTimeInfo, sdp)) {
			// parse it as start date if start date is not already defined
			if (startDateStore == null) {
				setStartDate(sdp.parse(dateTimeInfo));
			} else {
				setEndDate(sdp.parse(dateTimeInfo));
			}
			return 1;
		}
		
		// starts with a date indicator
		if (isSingleDate(Auxiliary.removeFirstWord(dateTimeInfo), sdp)) {
			String dateIndicator = Auxiliary.getFirstWord(dateTimeInfo);
			
			for (int i = 0; i < LIST_DEADLINE_MARKERS.length; i++) {
				if (dateIndicator.equalsIgnoreCase(LIST_DEADLINE_MARKERS[i])) {
					setEndDate(sdp.parse(Auxiliary.removeFirstWord(dateTimeInfo)));
					setTaskType(TaskType.DEADLINE);
					return 1;
				}
			}
			for (int i = 0; i < LIST_START_MARKERS.length; i++) {
				if (dateIndicator.equalsIgnoreCase(LIST_START_MARKERS[i])) {
					if (taskTypeStore != TaskType.DEADLINE) {
						setTaskType(TaskType.TODO);
					}
					setStartDate(sdp.parse(Auxiliary.removeFirstWord(dateTimeInfo)));
					return 1;
				}
			}
			return 0;
		}
		
		// trying to find 2 dates
		for (int i= 0; i < LIST_TWO_DATE_MARKERS.length; i++) {
			if (dateTimeInfo.contains(LIST_TWO_DATE_MARKERS[i])) {
				return findTwoDates(LIST_TWO_DATE_MARKERS[i], dateTimeInfo);
			}
		}
		return 0;
	}
	
	private boolean isSingleDate (String date, SingleDateParser parser) {
		
		if (parser.parse(date) != null) {
			return true;
		}
		return false;
	}
	
	private int findTwoDates(String delimiter, String dates) {
		int delimOccurrence = dates.length() - dates.replaceAll(delimiter, "").length();
		if (delimOccurrence == delimiter.length()) {
			String[] dateTokens = dates.split(delimiter);
			dateTokens = Auxiliary.trimStringArray(dateTokens);
			
			if (isValidDateRange(dateTokens[0], dateTokens[1])) {
				return 2;
			}
		}
		return 0;
	}
	
	private boolean isValidDateRange(String date1, String date2) {
		LocalDate earlierDate = sdp.parse(date1);
		LocalDate laterDate = sdp.parse(date2);
		
		// case: need to convert first date to a valid date
		if (laterDate != null) {
			if (Auxiliary.isNumber(date1)) {
				int dayOfDate1 = Integer.parseInt(date1);
				earlierDate = laterDate.withDayOfMonth(dayOfDate1);
			}
		}
		
		// case: both dates are valid dates
		if (earlierDate != null && laterDate != null) {
			// check chronological
			if (!earlierDate.isAfter(laterDate)) {
				setStartDate(earlierDate);
				setEndDate(laterDate);
				return true;
			}
			return false;
		}
		return false;
	}

	
	private int countValidTimes (String dateTimeInfo) {
		
		// itself is a time
		if (isSingleTime(dateTimeInfo, stp)) {
			// parse it as start time if start time is not already defined
			if (startTimeStore == null) {
				setStartTime(stp.parse(dateTimeInfo));
			} else {
				setEndTime (stp.parse(dateTimeInfo));
			}
			return 1;
		}
		
		// starts with a time indicator
		if (isSingleTime(Auxiliary.removeFirstWord(dateTimeInfo), stp)) {
			String timeIndicator = Auxiliary.getFirstWord(dateTimeInfo);
			
			for (int i = 0; i < LIST_DEADLINE_MARKERS.length; i++) {
				if (timeIndicator.equalsIgnoreCase(LIST_DEADLINE_MARKERS[i])) {
					setEndTime(stp.parse(Auxiliary.removeFirstWord(dateTimeInfo)));
					setTaskType(TaskType.DEADLINE);
					return 1;
				}
			}
			for (int i = 0; i < LIST_START_MARKERS.length; i++) {
				if (timeIndicator.equalsIgnoreCase(LIST_START_MARKERS[i])) {
					if (taskTypeStore != TaskType.DEADLINE) {
						setTaskType(TaskType.TODO);
					}
					setStartTime(stp.parse(Auxiliary.removeFirstWord(dateTimeInfo)));
					return 1;
				}
			}
			return 0;
		}
		
		// trying to find 2 times
		for (int i= 0; i < LIST_TWO_DATE_MARKERS.length; i++) {
			if (dateTimeInfo.contains(LIST_TWO_DATE_MARKERS[i])) {
				return findTwoTimes(LIST_TWO_DATE_MARKERS[i], dateTimeInfo);
			}
		}
		return 0;
	}
	
	private boolean isSingleTime (String time, SingleTimeParser parser) {
		
		if (parser.parse(time) != null) {
			return true;
		}
		return false;
	}
	
	private int findTwoTimes(String delimiter, String times) {
		int delimOccurrence = times.length() - times.replaceAll(delimiter, "").length();
		if (delimOccurrence == delimiter.length()) {
			String[] timeTokens = times.split(delimiter);
			timeTokens = Auxiliary.trimStringArray(timeTokens);
			
			if (isValidTimeRange(timeTokens[0], timeTokens[1])) {
				return 2;
			}
		}
		return 0;
	}
	
	private boolean isValidTimeRange(String time1, String time2) {
		LocalTime earlierTime = stp.parse(time1);
		LocalTime laterTime = stp.parse(time2);
		
		// case: need to append "am/pm" to first time
		if (laterTime != null) {
			if (time2.contains("pm")) {
				time1 += "pm";
			} else if (time2.contains("am")) {
				time1 += "am";
			}
			earlierTime = stp.parse(time1);
		}
		
		// case: both times are valid times
		if (earlierTime != null && laterTime != null) {
			// check chronological
			if (!earlierTime.isAfter(laterTime)) {
				setStartTime(earlierTime);
				setEndTime(laterTime);
				return true;
			}
			return false;
		}
		return false;
	}
	
	// task adding methods
	private Task addFloating(String details) {
		Task floating = new Task(details);
		return floating;
	}
	
	private Task addEvent(String[] details) throws Exception {		
		
		// case: 1 T 2 D (append time to contents? and add dated(i.e. no time) event)
		// case: 0 T 2 D (add dated event)
		if (startTimeStore == null || endTimeStore == null) {
			if (startDateStore.equals(endDateStore)) {
				return addSingleDated(details);
			}
			setStartTime(null);
			setEndTime(null);
			if (!endDateStore.isAfter(startDateStore)) {
				throw new Exception("Not chronological!");
			}
		} else {
			// case: 2 T 0 D (from time T1-T2 today)
			if (startDateStore == null && endDateStore == null) {
				setStartDate(LocalDate.now());
				setEndDate(LocalDate.now());
			}
			// case: 2 T 1 D (from time T1-T2 on 1D)
			if (startDateStore == null || endDateStore == null) {
				if (startDateStore != null) {
					setEndDate(startDateStore);
				} else {
					setStartDate(endDateStore);
				}
			}
			// case: 2 T 2 D (ok)
			LocalDateTime earlier = startDateStore.atTime(startTimeStore);
			LocalDateTime later = endDateStore.atTime(endTimeStore);
			if (!later.isAfter(earlier)) {
				if(!later.isBefore(earlier)) {
					return addSingleDated(details);
				}
				throw new Exception("Not chronological!");
			}
		}
		
		Task event = new Task(details[INDEX_TASKNAME], startDateStore, 
				 startTimeStore, endDateStore, endTimeStore);			
		return event;
	}
	
	// throw exception when more than one date or time found?
	private Task addSingleDated(String[] details) {
		
		// case: 1 T 0 D (add todo/deadline today)
		if (startDateStore == null && endDateStore == null) {
			setStartDate(LocalDate.now());
			setEndDate(LocalDate.now());
		}
		// case: 1 T 1 D (add todo or deadline)
		// case: 0 T 1 D (add dated event on 1D)
		if (taskTypeStore == TaskType.DEADLINE) {
			if (endDateStore == null && startDateStore != null) {
				setEndDate(startDateStore);
			}
			if (endTimeStore == null && startTimeStore != null) {
				setEndTime(startTimeStore);
			}
			Task deadline = new Task(details[INDEX_TASKNAME], endDateStore, endTimeStore,
					TaskType.DEADLINE);
			return deadline;
		} else if (taskTypeStore == TaskType.TODO 
				|| (startDateStore != null)) {
			Task toDoAt = new Task(details[INDEX_TASKNAME], startDateStore, startTimeStore,
					TaskType.TODO);
			return toDoAt;
		} else {
			return null;
		}
	}
	
	
	// auxiliary methods
	private void clearStores() {
		setStartDate(null);
		setEndDate(null);
		setStartTime(null);
		setEndTime(null);
		setTaskType(null);
	}
	
	// accessors for testing
	public LocalDate getStartDate() {
		return startDateStore;
	}
	
	public LocalDate getEndDate() {
		return endDateStore;
	}
	
	public LocalTime getStartTime() {
		return startTimeStore;
	}
	
	public LocalTime getEndTime() {
		return endTimeStore;
	}
	
	public TaskType getTaskType() {
		return taskTypeStore;
	}
	
	// mutators
	public void setStartDate(LocalDate date) {
		startDateStore = date;
	}
	
	public void setEndDate(LocalDate date) {
		endDateStore = date;
	}
	
	public void setStartTime(LocalTime time) {
		startTimeStore = time;
	}
	
	public void setEndTime(LocalTime time) {
		endTimeStore = time;
	}
	
	public void setTaskType(TaskType type) {
		taskTypeStore = type;
	}
}
