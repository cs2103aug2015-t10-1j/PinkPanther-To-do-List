package userinterface;

import java.util.ArrayList;

import common.Task;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import parser.*;
import javax.swing.*;

public class ConsoleInputColorizer {
	private String[] wordList = {};
	public ConsoleInputColorizer() {}
	private String inputString;
	CommandParser parser = new CommandParser();
	
	public FlowPane parseInputToArray(String input) {
		
		if (input != null){
			wordList = input.trim().split("[ ]+");
			inputString = input;
		}
	    return colorize();
	}

	public FlowPane colorize() {

	    ArrayList<Text> textChunks = new ArrayList<>();
	    FlowPane bundle = new FlowPane();
	    
	    String offset = "-";
	    textChunks.add(customize(offset, Color.TRANSPARENT));
    	bundle.getChildren().add(customize(offset, Color.TRANSPARENT));
	    //Todo: use regex to check for valid words
//	    for (int i=0; i<wordList.length; i++){
//	        String spaced = wordList[i] + " ";
	        switch (wordList[0].toLowerCase()) {
	        	
	        case "add":
	        	AddStringParser addParser = new AddStringParser();
	        	textChunks.add(customize("Adding \u25b6 ", Color.BROWN));
	      //  	bundle.getChildren().add(customize("Adding \u25b6", Color.BLACK));
	        	if (wordList.length > 1){
		        	String taskInfo = inputString.split(" ", 2)[1];	
		        	try{
		        		textChunks.addAll(breakTaskIntoFlowPane(addParser.parse(taskInfo)));
		        	} catch (ArrayIndexOutOfBoundsException e) {
		        		
		        	}
	        	}
	        	break;
	        	
	        case "del":
	        	textChunks.add(customize("Deleting \u25b6", Color.BROWN));
	        	//need CS to help me search for Task object?
	            textChunks.add(customize(" [ENTER TASK DATE],[ENTER TASK INDEX]", Color.RED));
	        break;
	        
	        case "edit":
	        	textChunks.add(customize("Editing \u25b6", Color.BROWN));
	        	//need CS to help me search for Task object?
	            textChunks.add(customize(" [ENTER TASK DATE],[ENTER TASK INDEX]", Color.RED));
	        break;
	        
	        case "exit":
	        	textChunks.add(customize("Exit command detected: Program will close if you press ENTER.", Color.RED));
	        break;
	        
	        case "invalid":
	        	textChunks.add(customize("Invalid command. Press PageUp for a list of valid commands", Color.RED));
	        break;
	        
	        default :
	        	textChunks.add(customize(inputString, Color.BLACK));
	        break;
	        	
	        }

	    bundle.getChildren().addAll(textChunks);
	    return bundle;
	}
	
	public Text customize(String word, Color color) {
//	    return TextBuilder.create().text(word).setFill(Paint.valueOf(color)).build();
		Text newText = new Text();
		newText.setText(word);
		newText.setFill(color);
		newText.setFont(Font.font("Tahoma", FontWeight.BOLD, 17));
		return newText;
	}

	
	private ArrayList<Text> breakTaskIntoFlowPane(Task task){
	    ArrayList<Text> textChunks = new ArrayList<>();
	    FlowPane bundle = new FlowPane();
	    
	    String taskName = task.getName();
	    if (taskName != null)
	    	textChunks.add(customize("\"" + taskName + "\"", Color.BLACK));
	    if(task.getStartTime()!=null){
	    	textChunks.add(customize(" [⏰  " + task.getStartTime().toString(), Color.GREEN));
	    	if(task.getEndTime()!=null){
		    	textChunks.add(customize(" \u25b6 " + task.getEndTime().toString(), Color.GREEN));
	    	}
	    	textChunks.add(customize("]", Color.GREEN));
	    } else if(task.getEndTime()!=null){
	    	textChunks.add(customize("[by " + task.getEndTime().toString() + "]", Color.GREEN));
	    }
	    if(task.getStartDate()!=null){
	    	textChunks.add(customize(" [📅  " + task.getStartDate().toString(), Color.BLUE));
	    	if(task.getEndDate()!=null && !task.getEndDate().equals(task.getStartDate())){
		    	textChunks.add(customize(" \u25b6 " + task.getEndDate().toString(), Color.BLUE));
		    }
	    	textChunks.add(customize("]", Color.BLUE));
	    }
	    else if(task.getEndDate()!=null){
	    	textChunks.add(customize(" [" + task.getEndDate().toString() + "]", Color.BLACK));
	    }
	    
	    bundle.getChildren().addAll(textChunks);
	    return textChunks;
		
	}
}
