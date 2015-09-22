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
	
	private TaskType taskTypeStore;
	
	// Indexes of various arrays
	private static final int COMMAND_INDEX = 0;
	private static final int COMMAND_CONTENTS_INDEX = 1;
	

	// Returns array containing the command separated from the input
	public static String[] inputSplitter(String userInput){
		String[] splitInput = new String[2];
		splitInput[COMMAND_INDEX] = getFirstWord(userInput);
		splitInput[COMMAND_CONTENTS_INDEX] = removeFirstWord(userInput);
		return splitInput;
		
		// remember to handle exceptions here
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
	
	
	
	// Auxiliary methods
	
	private static String getFirstWord(String userInput) {
		String commandTypeString = userInput.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	private static String removeFirstWord(String userInput) {
		String commandContent = userInput.replaceFirst(getFirstWord(userInput), "").trim();
		return commandContent;
	}
	
}
