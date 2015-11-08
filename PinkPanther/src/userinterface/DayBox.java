/* @@author A0122545M */
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
	//These variables help to position objects in the StackPane
	private int Y_POS_DEFAULT = 1;
	private int X_POS_LEFT = 0;
	private int X_POS_CENTRE = 1;
	private int H_GAP_GRID = 5;

	//These variables help calibrate the entire DayBox's size
	private int WIDTH_ENTIRE_BOX = 230;
	private int HEIGHT_ENTIRE_BOX = 40;
	private int ARC_ENTIRE_BOX = 4;

	//These variables help calibrate and size the box that displays the number of date
	private int WIDTH_DAY_NUMBER_BOX = 35;
	private int HEIGHT_DAY_NUMBER_BOX = 35;
	private int ARC_DAY_NUMBER_BOX = 4;
	private Color COLOR_DAY_NUMBER_BOX = Color.DIMGRAY;
	private float STROKE_WIDTH_DAY_NUMBER_BOX = 2.0f;
	private Color FONT_COLOR_TEXT = Color.WHITE;
	private String FONT_TYPE_DEFAULT = "Tahoma";
	private int FONT_SIZE_DEFAULT = 20;

	//These strings denote what the box will display
	private String DISPLAY_STRING_UNDATED = "~";
	private String DISPLAY_STRING_OTHERS = "!";
	
	private String SPLIT_DATE_DELIMITER = " ";
	private String SPACE_OFFSET = " ";
	
	//These variables belong to the object
	String dayString, monthString;
	GridPane grid;
	
	//Constructor
	public DayBox(String date) {
		instantiateGrid();
		extractDayAndMonth (date);
		
		Rectangle box = createDayHolderBox(date);
		grid.add(box, X_POS_CENTRE, Y_POS_DEFAULT);	

		StackPane indexBox = createDayNumberBox(dayString);
		grid.add(indexBox, X_POS_LEFT, Y_POS_DEFAULT);
		
		Text text = instantiateText();
		grid.add(text, X_POS_CENTRE, Y_POS_DEFAULT);
		
		this.getChildren().addAll(grid);
	}
	
	//Forms a string representation of the date in appropriately sized Text
	private Text instantiateText() {
		Text text = new Text(SPACE_OFFSET + monthString);
		text.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, 25));
		text.setTextAlignment(TextAlignment.LEFT);
		text.setFill(Color.DIMGRAY);
		return text;
	}
	
	//Creates a grid to place items into
	private void instantiateGrid() {
		grid = new GridPane();
	    grid.setHgap(H_GAP_GRID);
	    grid.setVgap(H_GAP_GRID);
	}
	
	//Breaks date string into dayString and monthString
	private void extractDayAndMonth (String date) {
		if (date.equals("Undated Tasks")) {
	    	dayString = DISPLAY_STRING_UNDATED;
	    	monthString = date;
	    } 
	    else if (date.equals(DISPLAY_STRING_OTHERS)) {
	    	dayString = DISPLAY_STRING_OTHERS;
	    	monthString = "";
	    }
	    else {
	    	dayString = date.split(SPLIT_DATE_DELIMITER)[0];
	    	monthString = date.split(SPLIT_DATE_DELIMITER)[1] + SPACE_OFFSET + date.split(SPLIT_DATE_DELIMITER)[2];
	    }
	}
	
	//Returns a Rectangle that will serve as the background for whole date row
	private Rectangle createDayHolderBox(String day) {
		Rectangle box = new Rectangle();
		box.setWidth(WIDTH_ENTIRE_BOX);
		box.setHeight(HEIGHT_ENTIRE_BOX);
		box.setArcWidth(ARC_ENTIRE_BOX);
		box.setArcHeight(ARC_ENTIRE_BOX);
		box.setFill(Color.TRANSPARENT);
		return box;
	}
	
	//Returns a gray colored box that displays current day of month
	private StackPane createDayNumberBox(String num) {
		//Configure box dimensions and colors
		Rectangle box = new Rectangle();
		box.setWidth(WIDTH_DAY_NUMBER_BOX);
		box.setHeight(HEIGHT_DAY_NUMBER_BOX);
		box.setArcWidth(ARC_DAY_NUMBER_BOX);
		box.setArcHeight(ARC_DAY_NUMBER_BOX);                
		box.setFill(COLOR_DAY_NUMBER_BOX);
		box.setStroke(COLOR_DAY_NUMBER_BOX);
		box.setStrokeWidth(STROKE_WIDTH_DAY_NUMBER_BOX);
        
		//Configure Text in box
		Text text = new Text(num);
		text.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_DEFAULT));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(FONT_COLOR_TEXT);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(box, text);
		return stackPane;
	}
}

