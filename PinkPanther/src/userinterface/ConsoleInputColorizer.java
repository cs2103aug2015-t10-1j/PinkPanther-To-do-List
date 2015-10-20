package userinterface;

import java.util.ArrayList;

import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;

public class ConsoleInputColorizer {
	private String[] wordList = {};

	public ConsoleInputColorizer() {}

	public FlowPane parseInputToArray(String input) {
	    wordList = input.trim().split("[ ]+");

	    return colorize();
	}

	public FlowPane colorize() {

	    ArrayList<Text> textChunks = new ArrayList<>();
	    FlowPane bundle = new FlowPane();
	    
	    String offset = "   ";
	    textChunks.add(customize(offset, Color.BROWN));
	    //Todo: use regex to check for valid words
	    for (int i=0; i<wordList.length; i++){
	        String spaced = wordList[i].toLowerCase() + " ";
	        switch (wordList[i]) {
	            case "add":
	                textChunks.add(customize(spaced, Color.BLUE));
	                if (i == wordList.length - 1 && i == 0)
	                	textChunks.add(customize("[task name], [task date], [task time]", Color.RED));
	                break;
	            case "del":
	                textChunks.add(customize(spaced, Color.BLUE));
	                if (i == wordList.length - 1 && i == 0)
	                	textChunks.add(customize("[task date],[index]", Color.RED));
	                break;
	            case "edit":
	                textChunks.add(customize(spaced, Color.BLUE));
	                if (i == wordList.length - 1 && i == 0)
	                	textChunks.add(customize("[task date],[index]", Color.RED));
	                break;
	            default:
	                textChunks.add(customize(spaced, Color.BLACK));
	                break;
	        }
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

}
