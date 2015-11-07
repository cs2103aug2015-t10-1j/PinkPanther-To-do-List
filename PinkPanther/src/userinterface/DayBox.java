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
	
	public DayBox(String day){
		instantiateGrid();
		extractDayAndMonth (day);
		
		Rectangle box = createDayHolderBox(day);
		grid.add(box, 1, 1);	

		StackPane indexBox = createIndexBox(dayString);
		grid.add(indexBox, 0, 1);
		
		Text text = instantiateText();
		grid.add(text, 1, 1);
		
		this.getChildren().addAll(grid);
	}
	
	private Text instantiateText(){
		Text text = new Text(" " + monthString);
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
		text.setTextAlignment(TextAlignment.LEFT);
		text.setFill(Color.DIMGRAY);
		return text;
	}
	
	private void instantiateGrid(){
		grid = new GridPane();
	    grid.setHgap(5);
	    grid.setVgap(5);
	}
	
	private void extractDayAndMonth (String day) {
		if (day.equals("Undated Tasks")){
	    	dayString = "~";
	    	monthString = day;
	    } 
	    else if (day.equals("!")){
	    	dayString = "!";
	    	monthString = "";
	    }
	    else {
	    	dayString = day.split(" ")[0];
	    	monthString = day.split(" ")[1] + " " + day.split(" ")[2];
	    }
	}
	
	private Rectangle createDayHolderBox(String day){
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
	
	private StackPane createIndexBox(String num){
		
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

