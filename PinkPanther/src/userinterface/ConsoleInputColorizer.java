/* @@author A0122545M */
package userinterface;

import java.util.ArrayList;

import common.Task;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.Controller;
import parser.*;

public class ConsoleInputColorizer {
	private String[] wordList = {};
	public ConsoleInputColorizer() {}
	private String inputString;
	private Controller controller;
	CommandParser parser = new CommandParser();
	
	/* 
	 * Gets string input and splits it into an array of strings, then calls colorize()
	 * @return FlowPane of colored texts
	 */
	public FlowPane parseInputToArray(String input) {
		if (input != null) {
			wordList = input.trim().split("[ ]+");
			inputString = input;
		}
	    return colorize();
	}
	
	public void setController(Controller mainController) {
		controller = mainController;
	}

	/*Uses wordList and parses them through appropriate parsers.
	 *Searches for targeted tasks if they exist or breaks them down into representations of Tasks
	 *@return FlowPane of colored texts
	 */
	public FlowPane colorize() {
	    ArrayList<Text> textChunks = new ArrayList<>();
	    FlowPane bundle = new FlowPane();
	    
	    String offset = "";
	    textChunks.add(customize(offset, Color.TRANSPARENT));
    	bundle.getChildren().add(customize(offset, Color.TRANSPARENT));
	        switch (wordList[0].toLowerCase()) {
	        	
	        case "add":
	        	AddStringParser addParser = new AddStringParser();
	        	textChunks.add(customize("Adding \u25b6 ", Color.GREEN));
	        	if (wordList.length > 1) {
		        	String taskInfo = inputString.split(" ", 2)[1];	
		        	if (taskInfo != null) {
		        		textChunks.addAll(breakTaskIntoFlowPane(addParser.parse(taskInfo)));
		        	}
	        	}
	        	break;
	        	
	        case "del":
	        case "delete":
	        	textChunks.add(customize("Delete  \u25b6", Color.GREEN));
	        	if (wordList.length > 1) {
		        	String taskInfo = inputString.split(" ", 2)[1];	
		        	if (taskInfo != null) {
		        		Task task = controller.findTask(taskInfo);
		        		if (task != null) {
		        			textChunks.addAll(breakTaskIntoFlowPane(task));
		        		} else {
				        	textChunks.add(customize(" [Valid task date and index not detected yet]", Color.RED));
			        	}
		        	}
	        	}
	        break;
	        
	        case "done":
	        	textChunks.add(customize("  Done  \u25b6", Color.GREEN));
	        	if (wordList.length > 1) {
		        	String taskInfo = inputString.split(" ", 2)[1];	
		        	if (taskInfo != null) {
		        		Task task = controller.findTask(taskInfo);
		        		if (task != null) {
		        			textChunks.addAll(breakTaskIntoFlowPane(task));
		        		} else {
				        	textChunks.add(customize(" [Valid task date and index not detected yet]", Color.RED));
			        	}
		        	}
	        	}
	        break;
	        
	        case "edit":
	        	textChunks.add(customize("Editing \u25b6", Color.GREEN));
	        	if (wordList.length > 1) {
		        	String taskInfo = inputString.split(" ", 2)[1];	
		        	if (taskInfo != null) {
		        		Task task = controller.findTask(taskInfo);
		        		if (task != null) {
		        			textChunks.addAll(breakTaskIntoFlowPane(task));
		        		} else {
				        	textChunks.add(customize(" [Specify task date and index]", Color.RED));
			        	}
		        	}
	        	}
	        break;
	        
	        case "exit":
	        	textChunks.add(customize("Exit command detected: Program will close if you press ENTER.", Color.RED));
	        break;
	        
	        case "search":
	        	textChunks.add(customize("Search \u25b6 ", Color.GREEN));
	        	if (wordList.length > 1) {
		        	String taskInfo = inputString.split(" ", 2)[1];
		        	textChunks.add(customize(trimWord(taskInfo, 55), Color.BLACK));
	        	}
	        break;
	        
	        case "view":
	        	textChunks.add(customize("    View \u25b6 ", Color.GREEN));
	        	if (wordList.length > 1) {
		        	String taskInfo = inputString.split(" ", 2)[1];
		        	textChunks.add(customize(trimWord(taskInfo, 55), Color.BLACK));
	        	}
	        break;
	        
	        case "invalid":
	        case "unrecognized":
	        	textChunks.add(customize(inputString, Color.RED));
	        break;
	        
	        default :
	        	textChunks.add(customize("             \u25b6  ", Color.BROWN));
	        	textChunks.add(customize(trimWord(inputString, 50), Color.BLACK));
	        break;
	        	
	        }

	    bundle.getChildren().addAll(textChunks);
	    return bundle;
	}
	
	//Ensures word is not too long and overflow window width by trimming it
	private String trimWord(String input, int maxLength) {
		if (input.length() >= maxLength) {
			return input.substring(0, maxLength) + "...";
		}
		return input;
	}
	
	//Returns a colored Text representation of a string in specified color
	public Text customize(String word, Color color) {
		Text newText = new Text();
		newText.setText(word);
		newText.setFill(color);
		newText.setFont(Font.font("Tahoma", FontWeight.BOLD, 17));
		return newText;
	}

	/* Gets a Task object and attempts to break it into text form.
	 * Will colorize each fragment accordingly, then return the ArrayList of these Texts.
	 * @return ArrayList<Text> of appropriately colored texts
	 */
	private ArrayList<Text> breakTaskIntoFlowPane(Task task) {
	    ArrayList<Text> textChunks = new ArrayList<>();
	    FlowPane bundle = new FlowPane();
	    
	    try {
	    if (task.getName() != null) {
	    	String taskName = task.getName();
	    	textChunks.add(customize("\"" + trimWord(taskName, 20) + "\"", Color.BLACK));
	    }
	    
	    if(task.getStartTime()!=null) {
	    	textChunks.add(customize(" [⏰  " + task.getStartTime().toString(), Color.DARKCYAN));
	    	if(task.getEndTime()!=null) {
		    	textChunks.add(customize(" \u25b6 " + task.getEndTime().toString(), Color.DARKCYAN));
	    	}
	    	textChunks.add(customize("]", Color.DARKCYAN));
	    } else if(task.getEndTime()!=null) {
	    	textChunks.add(customize(" [by " + task.getEndTime().toString() + "]", Color.DARKCYAN));
	    }
	    if(task.getStartDate()!=null) {
	    	textChunks.add(customize(" [📅  " + task.getStartDate().toString(), Color.TOMATO));
	    	if(task.getEndDate()!=null && !task.getEndDate().equals(task.getStartDate())) {
		    	textChunks.add(customize(" \u25b6 " + task.getEndDate().toString(), Color.TOMATO));
		    }
	    	textChunks.add(customize("]", Color.TOMATO));
	    }
	    else if(task.getEndDate()!=null) {
	    	textChunks.add(customize(" [" + task.getEndDate().toString() + "]", Color.TOMATO));
	    }
	    
	    } catch (NullPointerException e) {
	    	textChunks.add(customize(" [Please specify valid task name]", Color.RED));
	    }
	    
	    bundle.getChildren().addAll(textChunks);
	    return textChunks;
		
	}
}
