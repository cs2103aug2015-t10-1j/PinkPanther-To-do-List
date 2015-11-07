/* @@author Baron */
package userinterface;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TextedTimeBox extends StackPane{
	
	//Constructor
	public TextedTimeBox(String time) {
		Rectangle box = createTimeBox();
		Text text = new Text(time);
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		if (time.equals("")) {
			box.setFill(Color.TRANSPARENT);
		}
		this.getChildren().addAll(box, text);
	}
	
	//Create a colored rectangle to place Text inside
	private Rectangle createTimeBox() {
		Rectangle box = new Rectangle();
		box.setWidth(80);
		box.setHeight(20);
		box.setArcWidth(20);
		box.setArcHeight(20);

        Color textColor = Color.DIMGRAY;
        box.setFill(textColor.deriveColor(0, 1.2, 1, 0.6));
        
        return box;
     } 
}