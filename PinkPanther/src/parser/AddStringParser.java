/* @@author A0126473E */
package parser;

import common.Task;
import common.TaskType;
import common.Auxiliary;
import common.Display;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddStringParser implements Parser {
	
	// attributes
	private String taskNameStore;
	private LocalDate startDateStore;
	private LocalDate endDateStore;
	private LocalTime startTimeStore;
	private LocalTime endTimeStore;
	private TaskType taskTypeStore;
	
	// indexes and indicators
	private static final int INDEX_TASK_NAME = 0;
	private static final int INDEX_TASK_DETAIL = 1;
	private static final int INDEX_DATE = 0;
	private static final int INDEX_TIME = 1;
	
	// word list
	private static final String[] LIST_TWO_DATE_MARKERS = {" to ", "-"};
	private static final String[] LIST_DEADLINE_MARKERS = {"by", "due", "before"}; 
	private static final String[] LIST_START_MARKERS = {"at", "after"};
	
	// mythical stuff
	private static final LocalDate THE_MYTH_DAY = LocalDate.of(1979, 7, 11);
	
	// limits/min/max
	private static final int MAX_ALLOWED_DATES_TIMES = 2;
	private static final int NO_VALID_DATES_TIMES_FOUND = 0;
	private static final int SINGLE_DATE_TIME_FOUND = 1;
	private static final int DATE_TIME_RANGE_FOUND = 2;
	
	// messages
	private static final String MESSAGE_EMPTY_TASK = 
			"You have not entered any task details.";
	private static final String MESSAGE_PARSER_ERROR = 
				"You have entered invalid input that broke the program.";
	private static final String MESSAGE_EASTER_EGG = 
			"This day is too mythical for you to perform any tasks!";
	
	private static SingleDateParser sdp = new SingleDateParser();
	private static SingleTimeParser stp = new SingleTimeParser();

	
	@SuppressWarnings("unchecked")
	/**
	 * Return a Task based on commandContent.
	 * null is returned when commandContent is empty.
	 * 
	 * @param commandContent	What the user enters.
	 * @return	Task containing info from commandContent.
	 */
	public Task parse(String commandContent){
		
		clearStores();
		String[] userInfo = commandContent.split(",");
		userInfo = Auxiliary.trimStringArray(userInfo);
		
		if (Auxiliary.isEmptyArray(userInfo)) {
			Display.setFeedBack(MESSAGE_EMPTY_TASK);
			return null;
		}
		
		setTaskName(userInfo[INDEX_TASK_NAME]);
		int validDateTimes = findValidDateTime(userInfo);
		
		if (userInfo.length == 1 || validDateTimes == NO_VALID_DATES_TIMES_FOUND) {
			return addFloating(commandContent);
		} else if (validDateTimes == DATE_TIME_RANGE_FOUND)  {
			return addEvent(userInfo);
		} else if (validDateTimes == SINGLE_DATE_TIME_FOUND)  {
			return addSingleDated();
		} else {
			Display.setFeedBack(MESSAGE_PARSER_ERROR);
			return null;
		}
	}
	
	private int findValidDateTime(String [] possiblyDateTime) {
		int dateCounter = 0;
		int timeCounter = 0;
		for (int i = INDEX_TASK_DETAIL; i < possiblyDateTime.length; i++) {
			int dateCount = countValidDates(possiblyDateTime[i]);
			int timeCount = countValidTimes(possiblyDateTime[i]);
			dateCounter += dateCount;
			timeCounter += timeCount;
			
			if (dateCount == NO_VALID_DATES_TIMES_FOUND 
					&& timeCount == NO_VALID_DATES_TIMES_FOUND) {
				if (dateCounter == NO_VALID_DATES_TIMES_FOUND 
						&& timeCounter == NO_VALID_DATES_TIMES_FOUND) {
					appendTaskName(possiblyDateTime[i]);
				} else {
					return NO_VALID_DATES_TIMES_FOUND;
				}
			}
			
		}
		if (Math.max(dateCounter, timeCounter) > MAX_ALLOWED_DATES_TIMES) {
			return NO_VALID_DATES_TIMES_FOUND;
		}
		return Math.max(dateCounter, timeCounter);
	}
	
	/**
	 * Return the number of valid dates found in a string.
	 * If valid dates are found, date and time stores are updated.
	 * 
	 * @param dateTimeInfo	An input token from the user.
	 * @return	The number of valid dates found in input token.
	 */
	protected int countValidDates(String dateTimeInfo) {
		
		// case: itself is a date
		if (isSingleDateTime(dateTimeInfo, sdp)) {
			if (startDateStore == null) {
				setStartDate(sdp.parse(dateTimeInfo));
			} else {
				setEndDate(sdp.parse(dateTimeInfo));
			}
			return SINGLE_DATE_TIME_FOUND;
		}
		
		// case: starts with a date indicator and is a date
		if (isSingleDateTime(Auxiliary.removeFirstWord(dateTimeInfo), sdp)) {
			String dateIndicator = Auxiliary.getFirstWord(dateTimeInfo);
			
			for (int i = 0; i < LIST_DEADLINE_MARKERS.length; i++) {
				if (dateIndicator.equalsIgnoreCase(LIST_DEADLINE_MARKERS[i])) {
					setEndDate(sdp.parse(Auxiliary.removeFirstWord(dateTimeInfo)));
					setTaskType(TaskType.DEADLINE);
					return SINGLE_DATE_TIME_FOUND;
				}
			}
			for (int i = 0; i < LIST_START_MARKERS.length; i++) {
				if (dateIndicator.equalsIgnoreCase(LIST_START_MARKERS[i])) {
					if (taskTypeStore != TaskType.DEADLINE) {
						setTaskType(TaskType.TODO);
					}
					setStartDate(sdp.parse(Auxiliary.removeFirstWord(dateTimeInfo)));
					return SINGLE_DATE_TIME_FOUND;
				}
			}
			return NO_VALID_DATES_TIMES_FOUND;
		}
		
		// case: is a range of dates
		for (int i= 0; i < LIST_TWO_DATE_MARKERS.length; i++) {
			if (dateTimeInfo.contains(LIST_TWO_DATE_MARKERS[i])) {
				return findDuration(LIST_TWO_DATE_MARKERS[i], 
						dateTimeInfo, INDEX_DATE);
			}
		}
		// case: neither of the above
		return NO_VALID_DATES_TIMES_FOUND;
	}
	
	private int countValidTimes (String dateTimeInfo) {
		
		// case: itself is a time
		if (isSingleDateTime(dateTimeInfo, stp)) {
			if (startTimeStore == null) {
				setStartTime(stp.parse(dateTimeInfo));
			} else {
				setEndTime (stp.parse(dateTimeInfo));
			}
			return SINGLE_DATE_TIME_FOUND;
		}
		
		// case: starts with a time indicator and is a time
		if (isSingleDateTime(Auxiliary.removeFirstWord(dateTimeInfo), stp)) {
			String timeIndicator = Auxiliary.getFirstWord(dateTimeInfo);
			
			for (int i = 0; i < LIST_DEADLINE_MARKERS.length; i++) {
				if (timeIndicator.equalsIgnoreCase(LIST_DEADLINE_MARKERS[i])) {
					setEndTime(stp.parse(Auxiliary.removeFirstWord(dateTimeInfo)));
					setTaskType(TaskType.DEADLINE);
					return SINGLE_DATE_TIME_FOUND;
				}
			}
			for (int i = 0; i < LIST_START_MARKERS.length; i++) {
				if (timeIndicator.equalsIgnoreCase(LIST_START_MARKERS[i])) {
					if (taskTypeStore != TaskType.DEADLINE) {
						setTaskType(TaskType.TODO);
					}
					setStartTime(stp.parse(Auxiliary.removeFirstWord(dateTimeInfo)));
					return SINGLE_DATE_TIME_FOUND;
				}
			}
			return NO_VALID_DATES_TIMES_FOUND;
		}
		
		// case: is a range of times
		for (int i= 0; i < LIST_TWO_DATE_MARKERS.length; i++) {
			if (dateTimeInfo.contains(LIST_TWO_DATE_MARKERS[i])) {
				return findDuration(LIST_TWO_DATE_MARKERS[i], dateTimeInfo, INDEX_TIME);
			}
		}
		// case: neither of the above
		return NO_VALID_DATES_TIMES_FOUND;
	}
	
	private int findDuration(String delimiter, String dateTimes, int dateTimeIndicatorIndex) {
		int delimOccurrence = dateTimes.length() - dateTimes.replaceAll(delimiter, "").length();
		if (delimOccurrence == delimiter.length()) {
			String[] dateTimeTokens = dateTimes.split(delimiter);
			dateTimeTokens = Auxiliary.trimStringArray(dateTimeTokens);
			
			if (dateTimeTokens.length > 1) {
				if (dateTimeIndicatorIndex == INDEX_TIME 
						&& isValidTimeRange(dateTimeTokens[0], dateTimeTokens[1])) {
					return DATE_TIME_RANGE_FOUND;
				} else if (dateTimeIndicatorIndex == INDEX_DATE
						&& isValidDateRange(dateTimeTokens[0], dateTimeTokens[1])) {
					return DATE_TIME_RANGE_FOUND;
				}
			}
		}
		return NO_VALID_DATES_TIMES_FOUND;
	}
	
	private boolean isValidDateRange(String firstDate, String secondDate) {
		LocalDate earlierDate = sdp.parse(firstDate);
		LocalDate laterDate = sdp.parse(secondDate);
		
		// case: need to convert first date to a valid date
		if (laterDate != null) {
			if (Auxiliary.isNumber(firstDate)) {
				int dayOfFirstDate = Integer.parseInt(firstDate);
				if (dayOfFirstDate <= laterDate.getDayOfMonth()) {
					earlierDate = laterDate.withDayOfMonth(dayOfFirstDate);
				}
			}
		}
		
		// case: both dates are valid dates
		if (earlierDate != null && laterDate != null) {
			Boolean firstDateHasAppendedYear = sdp.fixDate(firstDate).getSecond();
			Boolean secondDateHasAppendedYear = sdp.fixDate(secondDate).getSecond();
			
			// if earlier date's year was appended
			if (firstDateHasAppendedYear && !secondDateHasAppendedYear) {
				earlierDate = earlierDate.withYear(laterDate.getYear());
				if (earlierDate.isAfter(laterDate)) {
					earlierDate = earlierDate.minusYears(1);
				}
				
			// if later date's year or both dates' years were appended
			} else if (secondDateHasAppendedYear) {
				if (!firstDateHasAppendedYear)
					laterDate = laterDate.withYear(earlierDate.getYear());
				if (laterDate.isBefore(earlierDate)) {
					laterDate = laterDate.plusYears(1);
				}
			}
			
			// check if chronological
			if (!earlierDate.isAfter(laterDate)) {
				setStartDate(earlierDate);
				setEndDate(laterDate);
				return true;
			}
		}
		return false;
	}
	
	private boolean isValidTimeRange(String firstTime, String secondTime) {
		LocalTime earlierTime = stp.parse(firstTime);
		LocalTime laterTime = stp.parse(secondTime);
		
		// case: need to append "am/pm" to first time
		if (laterTime != null) {
			if (secondTime.contains("pm") && !firstTime.contains("pm")) {
				firstTime += "pm";
				if (stp.parse(firstTime) != null) {
					earlierTime = stp.parse(firstTime);
				}
			} else if (secondTime.contains("am") && !firstTime.contains("am")) {
				firstTime += "am";
				if (stp.parse(firstTime) != null) {
					earlierTime = stp.parse(firstTime);
				}
			}
		}
		
		// case: both times are valid times
		if (earlierTime != null && laterTime != null) {
			
			// check if chronological
			if (!earlierTime.isAfter(laterTime)) {
				setStartTime(earlierTime);
				setEndTime(laterTime);
				return true;
			}
		}
		return false;
	}
	
	private Task addFloating(String details) {
		Task floating = new Task(details);
		return floating;
	}
	
	private Task addEvent(String[] details)  {		
		String taskFullDetails = Auxiliary.concatArray(details);
		
		// case: 0-1 T 0-1 D (2 deadlines/todo indicators entered)
		// case: 1 T 2 D (drop time and add untimed dated event)
		// case: 0 T 2 D (add dated event)
		if (startTimeStore == null || endTimeStore == null) {
			if (startDateStore == null || endDateStore == null) {
				return addFloating(taskFullDetails);
			}
			
//			assert startDateStore != null;
//			assert endDateStore != null;
			
			if (startDateStore.equals(endDateStore)) {
				return addSingleDated();
			}
			setStartTime(null);
			setEndTime(null);
			if (!endDateStore.isAfter(startDateStore)) {
				return addFloating(taskFullDetails);
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
			
			// case: 2 T 2 D
			if (endTimeStore.isBefore(startTimeStore)) {
				return addFloating(taskFullDetails);
			}
			if (endDateStore.isEqual(startDateStore)) {
				if  (!endTimeStore.isAfter(startTimeStore)){
					return addSingleDated();
				}
			} else if (endDateStore.isBefore(startDateStore)) {
				return addFloating(taskFullDetails);
			}
		}
		
		Task event = new Task(taskNameStore, startDateStore, 
				 startTimeStore, endDateStore, endTimeStore);			
		
		if (isDuringRestrictedDate(event)) {
			Display.setFeedBack(MESSAGE_EASTER_EGG);
			return null;
		}	
		return event;
	}
	
	private Task addSingleDated() {
		
		// case: 1 T 0 D (add todo/deadline today)
		if (startDateStore == null && endDateStore == null) {
			setStartDate(LocalDate.now());
			setEndDate(LocalDate.now());
		}
		
		// case: 1 T 1 D (add todo or deadline on 1D)
		// case: 0 T 1 D (add dated event on 1D)
		if (taskTypeStore == TaskType.DEADLINE) {
			if (endDateStore == null && startDateStore != null) {
				setEndDate(startDateStore);
			}
			if (endTimeStore == null && startTimeStore != null) {
				setEndTime(startTimeStore);
			}
			Task deadline = new Task(taskNameStore, endDateStore, endTimeStore,
					TaskType.DEADLINE);
			
			if (deadline.getDate().isEqual(THE_MYTH_DAY)) {
				Display.setFeedBack(MESSAGE_EASTER_EGG);
				return null;
			}
			return deadline;

		} else if (taskTypeStore == TaskType.TODO 
				|| (startDateStore != null)) {
			Task toDoAt = new Task(taskNameStore, startDateStore, startTimeStore,
					TaskType.TODO);
			if (toDoAt.getDate().isEqual(THE_MYTH_DAY)) {
				Display.setFeedBack(MESSAGE_EASTER_EGG);
				return null;
			}			
			return toDoAt;
			
		} else {
			return null;
		}
	}
	
	private boolean isDuringRestrictedDate(Task task) {
		return !(task.getStartDate().isAfter(THE_MYTH_DAY) 
				|| task.getEndDate().isBefore(THE_MYTH_DAY)); 
	}
	
	/**
	 * Resets all attributes of parser to null
	 */
	protected void clearStores() {
		setTaskName(null);
		setStartDate(null);
		setEndDate(null);
		setStartTime(null);
		setEndTime(null);
		setTaskType(null);
	}
	
	private boolean isSingleDateTime (String dateTime, Parser parser) {
		
		if (parser.parse(dateTime) != null) {
			return true;
		}
		return false;
	}
	
	// accessors for testing
	public String getTaskName() {
		return taskNameStore;
	}
	
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
	public void setTaskName(String newName) {
		taskNameStore = newName;
	}
	
	public void appendTaskName(String additionalDetails) {
		taskNameStore = taskNameStore + ", " + additionalDetails;
	}
	
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
