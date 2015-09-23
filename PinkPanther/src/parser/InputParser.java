package parser;

import common.Task;
import common.TaskType;
import java.time.LocalDate;
import java.time.LocalTime;

// import java.util.ArrayList;

public class InputParser {
	
	private static final int COMMAND_INDEX = 0;
	private static final int COMMAND_CONTENTS_INDEX = 1;
	private static final int TASKNAME_INDEX = 0;
	private static final int START = 0;
	private static final int END = 1;
	
	/*private static final String[] TIME_INDICATORS = {"pm", "am", "p.m.", "a.m.", ":"};*/
	private static final String[] SINGLE_DEADLINE_INDICATORS = {"by", "due", "at"};
	private static final String[] DATE_INDICATORS = {"today", "tonight", "tomorrow", "next week", 
			"next month", "this monday", "this tuesday", "this wednesday", "this thursday", 
			"this friday", "this saturday", "this sunday", "next monday", "next tuesday", 
			"next wednesday", "next thursday", "next friday", "next saturday", "next sunday"};
	private static final String[] MONTH_INDICATORS = {"JANUARY", "JAN", "1", "FEBRUARY", "FEB", "2",
			"MARCH", "MAR", "3", "APRIL", "APR", "4", "MAY", "5", "JUNE", "JUN", "6", "JULY", 
			"JUL", "7", "AUGUST", "AUG", "8", "SEPTEMBER", "SEPT", "SEP", "9", "OCTOBER", "OCT", "10", 
			"NOVEMBER", "NOV", "11", "DECEMBER", "DEC", "12"};
	private static final String[] TIME_INDICATORS = {"now", "tonight"};
	
	private static final String[] DATE_PATTERNS_SLASH = {"dd/MM/yy", "d/MM/yy", "dd/M/yy", "d/M/yy",
			"dd/MMM/yy", "d/MMM/yy", "dd/MM/yyyy", "d/MM/yyyy", "dd/M/yyyy", "d/M/yyyy", 
			"dd/MMM/yyyy", "d/MMM/yyyy"};
	
	private static final String[] DATE_PATTERNS_DASH = {"dd-MM-yy", "d-MM-yy", "dd-M-yy", "d-M-yy",
			"dd-MM-yyyy", "d-MM-yyyy", "dd-M-yyyy", "d-M-yyyy"};

	
	
	
	public static String[] inputSplitter(String userInput){
		String[] splitInput = new String[2];
		splitInput[COMMAND_INDEX] = getFirstWord(userInput);
		splitInput[COMMAND_CONTENTS_INDEX] = removeFirstWord(userInput);
		return splitInput;
		
		// remember to handle exceptions here
	}
	
	private static String getFirstWord(String userInput) {
		String commandTypeString = userInput.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	private static String removeFirstWord(String userInput) {
		String commandContent = userInput.replaceFirst(getFirstWord(userInput), "").trim();
		return commandContent;
	}
	
	public static Task addTaskParser(String commandContent){
		
		String[] info = commandContent.split(",");
		// to add escape sequence, String[] reSplice(String[] info){} needed;
		
		// create a floating task
		if (info.length == 1) {
			return addFloating(info);
		}
		
		// create an event
		if (findLegitDateTime(info) == 2) {
			return addEvent(info);
		
		// create a deadline or to-do
		} else if (findLegitDateTime(info) == 1){
			return addSingleDated(info);
		
		// invalid task to create; no task created
		} else {
			return null;
		}
	}


	private static Task addFloating(String[] details) {
		Task floating = new Task(details[TASKNAME_INDEX]);
		return floating;
	}
	
	public static int findLegitDateTime(String[] details) {
		int dateCounter = 0;
		int timeCounter = 0;
		for (int i = 1; i < details.length; i++) {
			dateCounter += findLegitDates(details[i]);
			timeCounter += findLegitTimes(details[i]);
		}
		return Math.max(dateCounter, timeCounter);
	}
	
	public static int findLegitDates(String possiblyDates) {
		
		// find 1 date
		if (isLegitDate(possiblyDates)){
			return 1;
		}
		
		boolean isSingleDeadline = false;
		for (int i = 0; i < SINGLE_DEADLINE_INDICATORS.length; i++){
			if (getFirstWord(possiblyDates).equalsIgnoreCase(SINGLE_DEADLINE_INDICATORS[i])){
				isSingleDeadline = true;
			}
		}
		if (isSingleDeadline && isLegitDate(removeFirstWord(possiblyDates))) {
			return 1;
		}
		
		// trying to find 2 dates
		if (possiblyDates.contains(" to " )) {
			return findTwoDates(" to ", possiblyDates);
		}
		
		if (possiblyDates.contains("-")) {
			return findTwoDates("-", possiblyDates);
		}
		
		return 0;
	}
	
	public static boolean isLegitDate(String possiblyDate){
		
		// trivial case
		for (int i = 0; i < DATE_INDICATORS.length; i++){
			if (possiblyDate.trim().equalsIgnoreCase(DATE_INDICATORS[i])){
				return true;
			}
		}
		
		// delimiter accounted for
		String[] dateSplitByDelimiter = possiblyDate.split("/| ");
		if (dateSplitByDelimiter.length == 2 || dateSplitByDelimiter.length == 3) {
			return isLegitDate(dateSplitByDelimiter);
		} 
		
		String[] dateSplitByDash = possiblyDate.split("-");
		if (dateSplitByDash.length == 3) {
			return isLegitDate(dateSplitByDash);
		}
		
		return false;
	}
	
	public static boolean isLegitDate(String[] dateDetails){
		
		// check for non-empty input
		for (int i = 0; i < dateDetails.length; i++){
			if (dateDetails[i].trim().isEmpty()){
				return false;
			}
		}
		int year;
		//check for legit year
		if (dateDetails.length == 3) {
			if (!dateDetails[2].trim().replaceAll("[0-9]","").isEmpty()) {
				return false;
			}
			if (Integer.parseInt(dateDetails[2].trim()) < LocalDate.now().getYear()) {
				return false;
			}
			if (Integer.parseInt(dateDetails[2].trim()) > (LocalDate.now().getYear()+100)) {
				return false;
			}
			year = Integer.parseInt(dateDetails[2].trim());
		} else {
			year = LocalDate.now().getYear();
		}
		
		// check for legit month
		boolean isLegitMonth = false;
		for (int i = 0; i < MONTH_INDICATORS.length; i++) {
			if (dateDetails[1].trim().equalsIgnoreCase(MONTH_INDICATORS[i])) {
				isLegitMonth = true;
				break;
			}
		}
		if (!isLegitMonth) {
			return false;
		}
		
		// check for legit day
		if (!dateDetails[0].trim().replaceAll("[0-9]","").isEmpty()) {
			return false;
		}
		
		String month = dateDetails[1].toUpperCase();
		
		switch (month) {
			case "JANUARY": 
			case "JAN":
			case "1":
			case "MARCH":
			case "MAR":
			case "3":
			case "MAY":
			case "5":
			case "JULY":
			case "JUL":
			case "7":
			case "AUGUST":
			case "AUG": 
			case "8":
			case "OCTOBER":
			case "OCT":
			case "10":
			case "DECEMBER":
			case "DEC":
			case "12":
				if (Integer.parseInt(dateDetails[0].trim()) > 31) {
					return false;
				}
				return true;
			case "APRIL":
			case "APR":
			case "4":
			case "JUNE":
			case "JUN":
			case "SEPTEMBER":
			case "SEPT":
			case "SEP":
			case "9":
			case "NOVEMBER":
			case "NOV":
			case "11" :
				if (Integer.parseInt(dateDetails[0].trim()) > 30) {
					return false;
				}
				return true;
				
			case "FEBRUARY:":
			case "FEB":
			case "2":
				LocalDate testDate = LocalDate.of(year, 1, 1);
				if (testDate.isLeapYear()) {
					if (Integer.parseInt(dateDetails[0].trim()) > 29){
						return false;
					}
				} else {
					if (Integer.parseInt(dateDetails[0].trim()) > 28){
						return false;
					}
				}
		}
		
		return true;
	}
	
	public static int findTwoDates(String delimiter, String dates) {
		int occurrence = dates.length() - dates.replace(delimiter, "").length();
		if (occurrence == delimiter.length()) {
			String[] splitDates = dates.split(delimiter);
			if (isLegitDate(splitDates[0].trim()) && isLegitDate(splitDates[1].trim())) {
				return 2;
			}
			
			if (isLegitDate(splitDates[1])){
				if (splitDates[0].replaceAll("[0-9]", "").trim().isEmpty() 
						&& !splitDates[0].trim().isEmpty()) {
					return 2;
				} else {
					return 0;
				}
			}
		}
		return 0;
	}
	
	
	public static int findLegitTimes(String possiblyTimes) {
		// find 1 time
		if (isLegitTime(possiblyTimes)){
			return 1;
		}
				
		boolean isSingleDeadline = false;
		for (int i = 0; i < SINGLE_DEADLINE_INDICATORS.length; i++){
			if (getFirstWord(possiblyTimes).equalsIgnoreCase(SINGLE_DEADLINE_INDICATORS[i])){
				isSingleDeadline = true;
			}
		}
		if (isSingleDeadline && isLegitTime(removeFirstWord(possiblyTimes))) {
			return 1;
		}
				
		// trying to find 2 times
		if (possiblyTimes.contains(" to " )) {
			return findTwoTimes(" to ", possiblyTimes);
		}
				
		if (possiblyTimes.contains("-")) {
			return findTwoTimes("-", possiblyTimes);
		}
				
		return 0;
	}
	
	private static boolean isLegitTime(String possiblyTime){
		
		// trivial case
		for (int i = 0; i < TIME_INDICATORS.length; i++){
			if (possiblyTime.trim().equalsIgnoreCase(TIME_INDICATORS[i])){
				return true;
			}
		}
		
		String[] time = possiblyTime.split(":|\\.");
		if (time.length == 2 || time.length == 1) {
			return isLegitTime(time);
		}
		return false;
	}
	
	public static boolean isLegitTime(String[] timeDetails) {
			
		for (int i = 0; i < timeDetails.length; i++){
			if (timeDetails[i].trim().isEmpty()){
				return false;
			}
		}
		
		// 24hrs or singledigit + am/pm
		if (timeDetails.length == 1) {
			// 24hrs
			if (timeDetails[0].trim().replaceAll("[0-9]", "").isEmpty() && timeDetails[0].trim().length() == 4){
				return isLegit24hr(Integer.parseInt(timeDetails[0].trim()));
			}
			// singledigit + am/pm
			if (timeDetails[0].contains("pm")){
				String timeHour = timeDetails[0].replaceFirst("pm", "").trim();
				if (timeHour.replaceAll("[0-9]","").isEmpty() && !timeHour.isEmpty()){
					return isLegitHour(Integer.parseInt(timeHour)+11);
				}
			} else if (timeDetails[0].contains("am")) {
				String timeHour = timeDetails[0].replaceFirst("am", "").trim();
				if (timeHour.replaceAll("[0-9]","").isEmpty() && !timeHour.isEmpty()){
					return isLegitHour(Integer.parseInt(timeHour));
				}
			}
		} else {
			if ( !timeDetails[0].trim().replaceAll("[0-9]", "").isEmpty()){
				return false;
			}
			
			if (timeDetails[1].trim().replaceAll("[0-9]", "").isEmpty() && timeDetails[1].trim().length() == 2) {
				return isLegitHour(Integer.parseInt(timeDetails[0].trim())) 
						&& isLegitMin( (Integer.parseInt(timeDetails[1].trim())) );
			}
			
			if (timeDetails[1].contains("pm")){
				String timeMin = timeDetails[1].replaceFirst("pm", "").trim();
				if (timeMin.replaceAll("[0-9]", "").isEmpty() && !timeMin.isEmpty()) {
					return isLegitHour(Integer.parseInt(timeDetails[0].trim()) + 11) 
							&& isLegitMin(Integer.parseInt(timeMin));
				}
			} else if (timeDetails[1].contains("am")) {
				String timeMin = timeDetails[1].replaceFirst("am", "").trim();
				if (timeMin.replaceAll("[0-9]", "").isEmpty() && !timeMin.isEmpty()) {
					return isLegitHour(Integer.parseInt(timeDetails[0].trim())) 
							&& isLegitMin(Integer.parseInt(timeMin));
				}
			}
		}
		return false;
	}
	
	private static boolean isLegit24hr (int fourDigits) {
		return isLegitHour(fourDigits / 100) && isLegitMin(fourDigits % 100);
	}
	
	private static boolean isLegitHour(int hr) {
		return hr >= 0 &&  hr <= 23;
	}
	
	private static boolean isLegitMin(int min) {
		return min >= 0 && min <= 59;
	}
	
	public static int findTwoTimes(String delimiter, String times) {
		int occurrence = times.length() - times.replace(delimiter, "").length();
		if (occurrence == delimiter.length()) {
			String[] splitTimes = times.split(delimiter);
			if (isLegitTime(splitTimes[0]) && isLegitTime(splitTimes[1])) {
				return 2;
			}
			
			// eg 3-5pm or 3 to 5pm
			if (isLegitTime(splitTimes[1])){
				if (isLegitTime(splitTimes[0] + "pm")) {
					return 2;
				} else {
					return 0;
				}
			}
		}
		return 0;
	}

	
	private static Task addEvent(String[] details) {
		LocalDate[] dates = new LocalDate[2];
		LocalTime[] times = new LocalTime[2];
		DateParser dps = new DateParser();
		TimeParser tps = new TimeParser();
		dates = dps.findDates(details);
		times = tps.findTimes(details);
		Task event = new Task(details[TASKNAME_INDEX], dates[START], 
				 times[START], dates[END], times[END]);			
		return event;
	}
	
	private static Task addSingleDated(String[] details) {
		SingleDateParser sdps = new SingleDateParser();
		LocalDate date = sdps.findDate(details);
		LocalTime time = sdps.findTime(details);
		
		if (isDeadline(details)) {
			Task deadline = new Task(details[TASKNAME_INDEX], date, time, TaskType.DEADLINE );
			return deadline;
		} else {
			Task toDoAt = new Task(details[TASKNAME_INDEX], date, time, TaskType.TODO);
			return toDoAt;
		}
	}
	
	// look for keywords like "by" or "due"
	private static boolean isDeadline(String[] details) {
		return true;
	}
	
	private static boolean isValidDate(String dateInput, String definedPattern) {
		
		LocalDate date = null;
		
	
	}
		
}
