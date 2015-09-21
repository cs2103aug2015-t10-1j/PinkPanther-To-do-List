package parser;

import common.Task;
//import common.Deadline;
import java.time.LocalDate;
import java.time.LocalTime;

//import java.util.ArrayList;

public class InputParser {
	
	private static final int COMMAND_INDEX = 0;
	private static final int COMMAND_CONTENTS_INDEX = 1;
	private static final int TASKNAME_INDEX = 0;
	
	// ArrayList<String> keywords?;
	
	public InputParser(){
		
	}
	
	public String[] inputSplitter(String userInput){
		String[] splitInput = new String[2];
		splitInput[COMMAND_INDEX] = getFirstWord(userInput);
		splitInput[COMMAND_CONTENTS_INDEX] = removeFirstWord(userInput);
		return splitInput;
	}
	
	private static String getFirstWord(String userInput) {
		String commandTypeString = userInput.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	private static String removeFirstWord(String userInput) {
		String commandContent = userInput.replaceFirst(getFirstWord(userInput), "").trim();
		return commandContent;
	}
	
	
}
