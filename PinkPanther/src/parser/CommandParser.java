package parser;

import common.Task;
import common.TaskType;
import java.time.LocalDate;
import java.time.LocalTime;

public class CommandParser {
	
	private LocalDate startDateStore;
	private LocalDate endDateStore;
	private LocalTime startTimeStore;
	private LocalTime endTimeStore;
	// not sure if need this
	private TaskType taskTypeStore;
	
	// indexes of various arrays
	private static final int INDEX_COMMAND = 0;
	private static final int INDEX_COMMAND_CONTENTS = 1;
	private static final int INDEX_TASKNAME = 0;
	private static final int INDEX_TASK_DETAIL = 1;
	
	// word list
	private static final String[] LIST_TWO_DATE_MARKERS = {" to ", "-"};
	private static final String[] LIST_DEADLINE_MARKERS = {"by", "due", "before"}; 
	private static final String[] LIST_START_MARKERS = {"at", "after"};


	// main functionality
	public static String[] inputSplitter(String userInput){
		String[] splitInput = new String[2];
		splitInput[INDEX_COMMAND] = getFirstWord(userInput);
		splitInput[INDEX_COMMAND_CONTENTS] = removeFirstWord(userInput);
		return splitInput;
		// remember to handle exceptions here
	}
	
	public Task addTaskParser(String commandContent){
		
		clearStores();
		String[] userInfo = commandContent.split(",");
		userInfo = trimStringArray(userInfo);
		
		// create a floating task
		if (userInfo.length == 1) {
			return addFloating(userInfo);
		}
		
		// will overwrite any legit things already parsed into time date stores
		if (findValidDateTime(userInfo) == 0) {
			return addFloating(userInfo);
		}
		
		// create an event
		if (findValidDateTime(userInfo) == 2)  {
			return addEvent(userInfo);
		
		// create a deadline or to-do
		} else if (findValidDateTime(userInfo) == 1)  {
			return addSingleDated(userInfo);
		
		// invalid task to create; no task created
		} else {
			return null;
		}
	}
	
	
	// input processing logic methods
	// beware of exceptions!
	public int findValidDateTime(String [] possiblyDateTime) {
		int dateCounter = 0;
		int timeCounter = 0;
		for (int i = INDEX_TASK_DETAIL; i < possiblyDateTime.length; i++) {
			dateCounter += countValidDates(possiblyDateTime[i]);
			timeCounter += countValidTimes(possiblyDateTime[i]);
			
			// if there is one invalid time or date token - add the whole thing to task contents?
				// return 0;
			// if there are more than 2 dates or times - add the whole thing to task contents?
				// return 0;
			// case: 2 T 2 D (ok)
			// case: 2 T 1 D (from time T1-T2 on 1D)
			// case: 2 T 0 D (from time T1-T2 today)
			// case: 1 T 2 D (append time to contents? and add dated(i.e. no time) event)
			// case: 0 T 2 D (add dated event)
			// case: 1 T 1 D (add todo or deadline)
			// case: 1 T 0 D (add todo/deadline today)
			// case: 0 T 1 D (add dated event today)
		}
		return Math.max(dateCounter, timeCounter);
	}
	
	// counts number of dates
	private int countValidDates(String dateTimeInfo) {
		
		SingleDateParser sdp = new SingleDateParser();
		
		// itself is a date
		if (isSingleDate(dateTimeInfo, sdp)) {
			// parse it as start date if start date is not already defined
			if (startDateStore == null) {
				setStartDate(sdp.parse(dateTimeInfo));
			} else {
				setEndDate (sdp.parse(dateTimeInfo));
			}
			return 1;
		}
		
		// starts with a date indicator
		if (isSingleDate(removeFirstWord(dateTimeInfo), sdp)) {
			String dateIndicator = getFirstWord(dateTimeInfo);
			
			for (int i = 0; i < LIST_DEADLINE_MARKERS.length; i++) {
				if (dateIndicator.equalsIgnoreCase(LIST_DEADLINE_MARKERS[i])) {
					setEndDate(sdp.parse(removeFirstWord(dateTimeInfo)));
					setTaskType(TaskType.DEADLINE);
					return 1;
				}
			}
			for (int i = 0; i < LIST_START_MARKERS.length; i++) {
				if (dateIndicator.equalsIgnoreCase(LIST_START_MARKERS[i])) {
					setStartDate(sdp.parse(removeFirstWord(dateTimeInfo)));
					setTaskType(TaskType.TODO);
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
			dateTokens = trimStringArray(dateTokens);
			
			if (isValidDateRange(dateTokens[0], dateTokens[1])) {
				return 2;
			}
		}
		return 0;
	}
	
	private boolean isValidDateRange(String date1, String date2) {
		SingleDateParser sdp2 = new SingleDateParser();
		LocalDate earlierDate = sdp2.parse(date1);
		LocalDate laterDate = sdp2.parse(date2);
		
		// case: need to convert first date to a valid date
		if (laterDate != null) {
			if (isNumber(date1)) {
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
		return 0;
	}

	
	// task adding methods
	private Task addFloating(String[] details) {
		Task floating = new Task(details[INDEX_TASKNAME]);
		return floating;
	}
	
	private Task addEvent(String[] details) {
		// append time to task name if there is only 1 time
		// append all details to task name if startDateTime to endDateTime is not chronological
		Task event = new Task(details[INDEX_TASKNAME], startDateStore, 
				 startTimeStore, endDateStore, endTimeStore);			
		return event;
	}
	
	// throw exception when more than one date or time found?
	private Task addSingleDated(String[] details) {
		if (taskTypeStore == TaskType.DEADLINE) {
			Task deadline = new Task(details[INDEX_TASKNAME], endDateStore, endTimeStore,
					TaskType.DEADLINE );
			return deadline;
		} else if (taskTypeStore == TaskType.TODO) {
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
	
	private static String getFirstWord(String userInput) {
		String commandTypeString = userInput.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	private static String removeFirstWord(String userInput) {
		String commandContent = userInput.replaceFirst(getFirstWord(userInput), "").trim();
		return commandContent;
	}
	
	public static String[] trimStringArray(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}
	
	private static boolean isNumber(String possiblyNumber) {
		return !possiblyNumber.isEmpty() && possiblyNumber.replaceAll("[0-9]","").isEmpty();
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
