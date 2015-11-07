/* @@author Baron */
package userinterface;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class DayBox extends StackPane{
	String dayString, monthString;
	GridPane grid;
	
	//Constructor
	public DayBox(String date) {
		instantiateGrid();
		extractDayAndMonth (date);
		
		Rectangle box = createDayHolderBox(date);
		grid.add(box, 1, 1);	

		StackPane indexBox = createDayBox(dayString);
		grid.add(indexBox, 0, 1);
		
		Text text = instantiateText();
		grid.add(text, 1, 1);
		
		this.getChildren().addAll(grid);
	}
	
	//Forms a string representation of the date in appropriately sized Text
	private Text instantiateText() {
		Text text = new Text(" " + monthString);
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
		text.setTextAlignment(TextAlignment.LEFT);
		text.setFill(Color.DIMGRAY);
		return text;
	}
	
	//Creates a grid to place items into
	private void instantiateGrid() {
		grid = new GridPane();
	    grid.setHgap(5);
	    grid.setVgap(5);
	}
	
	//Breaks date string into dayString and monthString
	private void extractDayAndMonth (String date) {
		if (date.equals("Undated Tasks")) {
	    	dayString = "~";
	    	monthString = date;
	    } 
	    else if (date.equals("!")) {
	    	dayString = "!";
	    	monthString = "";
	    }
	    else {
	    	dayString = date.split(" ")[0];
	    	monthString = date.split(" ")[1] + " " + date.split(" ")[2];
	    }
	}
	
	//Returns a Rectangle that will serve as the background for whole date row
	private Rectangle createDayHolderBox(String day) {
		Rectangle box = new Rectangle();
		box.setWidth(230);
		box.setHeight(40);
		box.setArcWidth(4);
		box.setArcHeight(4);                
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(0f);
		box.setFill(Color.TRANSPARENT);
		return box;
	}
	
	//Returns a gray colored box that displays current day of month
	private StackPane createDayBox(String num) {
		//Configure box dimensions and colors
		Rectangle box = new Rectangle();
		box.setWidth(35);
		box.setHeight(35);
		box.setArcWidth(4);
		box.setArcHeight(4);                
		box.setFill(Color.DIMGRAY);
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(2.0f);
        
		//Configure Text in box
		Text text = new Text(num);
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(box, text);
		return stackPane;
	}
}

