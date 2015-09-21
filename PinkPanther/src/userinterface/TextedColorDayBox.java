package userinterface;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TextedColorDayBox extends StackPane{
	public TextedColorDayBox(String name){
		ColoredDayBox box = new ColoredDayBox();
		Text text = new Text(name);
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		this.getChildren().addAll(box, text);
	}
}