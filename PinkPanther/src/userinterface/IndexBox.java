package userinterface;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class IndexBox extends StackPane{
	
	public IndexBox(int index){
		if (index<10){
			SmallColoredBoxIndex box = new SmallColoredBoxIndex();
			Text text = new Text(Integer.toString(index));
			text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
			text.setTextAlignment(TextAlignment.CENTER);
			text.setFill(Color.WHITE);
			
			this.getChildren().addAll(box, text);
		}
		else{
			MediumColoredBoxIndex box = new MediumColoredBoxIndex();
			Text text = new Text(Integer.toString(index));
			text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
			text.setTextAlignment(TextAlignment.CENTER);
			text.setFill(Color.WHITE);
			
			this.getChildren().addAll(box, text);
		}
	}
}