package userinterface;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TextedTimeBox extends StackPane{
	
	public TextedTimeBox(String time){
		TimeBox box = new TimeBox();
		Text text = new Text(time);
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		
		this.getChildren().addAll(box, text);
	}
}