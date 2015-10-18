package userinterface;

import common.TaskType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class IndexBox extends StackPane{
	
	public IndexBox(int index, TaskType inputTaskType){
		Color[] col = {(Color.web("D652FF")), Color.web("6495ED"), 
				(Color.web("D4573A")), (Color.web("51D444")), (Color.POWDERBLUE)};
		
		Rectangle box = new Rectangle();
		

		box.setWidth(20);
		box.setHeight(20);
		box.setArcWidth(4);
		box.setArcHeight(4);               
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(2.0f);

		switch (inputTaskType){
		case FLOATING:
			box.setFill(col[0]);
			break;
		case EVENT:
			box.setFill(col[1]);
			break;
		case TODO:
			box.setFill(col[2]);
			break;
		default:
			box.setFill(col[3]);
			break;
		
		}
		Text text = new Text(Integer.toString(index));
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		if (index==0){
			text.setText("");
		}
		this.getChildren().addAll(box, text);
		
	}
		
	
	public IndexBox(int index){
		Color[] col = {(Color.web("D652FF")), Color.web("6495ED"), 
				(Color.web("D4573A")), (Color.web("51D444")), (Color.POWDERBLUE)};
		
		Rectangle box = new Rectangle();
		

		box.setWidth(20);
		box.setHeight(20);
		box.setArcWidth(4);
		box.setArcHeight(4);               
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(2.0f);

		Text text = new Text(Integer.toString(index));
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		if (index==0){
			text.setText("!");
			box.setFill(Color.RED);
		}
		this.getChildren().addAll(box, text);
		
	}
}