package userinterface;

import common.Task;
import common.TaskType;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class DayBox extends StackPane{
	public DayBox(String day){
	    String dayString, monthString;
	    if (day.equals("Floating Tasks")){
	    	dayString = "~";
	    	monthString = day;
	    } else {
	    	dayString = day.split(" ")[0];
	    	monthString = day.split(" ")[1];
	    }

		Rectangle box = createDayHolderBox(day);
		
		GridPane grid = new GridPane();
		StackPane indexBox = createIndexBox(dayString);
		grid.add(indexBox, 0, 1);
		grid.add(box, 1, 1);
	//	grid.add(box, 1, 1);
	//	grid.setGridLinesVisible(true);
	    grid.setHgap(5);
	    grid.setVgap(5);
		

		Text text = new Text(" " + monthString);
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
		text.setTextAlignment(TextAlignment.LEFT);
		text.setFill(Color.DIMGRAY);

		grid.add(text, 1, 1);
//		grid.setGridLinesVisible(true);
		this.getChildren().addAll(grid);

	}
	
	private Rectangle createDayHolderBox(String day){
		Color[] col = {(Color.POWDERBLUE), (Color.KHAKI), 
				(Color.POWDERBLUE), (Color.LIGHTGREEN), (Color.POWDERBLUE)};
		
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
		StackPane stackPane = new StackPane();

		Rectangle box = new Rectangle();
		

		box.setWidth(35);
		box.setHeight(35);
		box.setArcWidth(4);
		box.setArcHeight(4);                
		box.setFill(Color.DIMGRAY);
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(2.0f);
        
		Text text = new Text(num);
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		stackPane.getChildren().addAll(box, text);
		
		return stackPane;
	}
}

