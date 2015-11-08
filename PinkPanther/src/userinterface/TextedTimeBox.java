/* @@author A0122545M */
package userinterface;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TextedTimeBox extends StackPane{
	
	//To adjust dimensions/color of box
	private int WIDTH_TIME_BOX = 80;
	private int HEIGHT_TIME_BOX = 20;
	private int ARC_TIME_BOX = 20;
	private Color BOX_COLOR_TRANSPARENT = Color.TRANSPARENT;
	private Color BOX_COLOR_DEFAULT = Color.DIMGRAY.deriveColor(0, 1.2, 1, 0.6);
	
	//To adjust size/color of text
	private int FONT_SIZE_DEFAULT = 15;
	private String FONT_TYPE_DEFAULT = "Tahoma";
	private Color FONT_COLOR_DEFAULT = Color.WHITE;
	
	//Constructor
	public TextedTimeBox(String time) {
		Rectangle box = createTimeBox();
		Text text = new Text(time);
		text.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_DEFAULT));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(FONT_COLOR_DEFAULT);
		
		if (time.equals("")) {
			box.setFill(BOX_COLOR_TRANSPARENT);
		}
		this.getChildren().addAll(box, text);
	}
	
	//Create a colored rectangle to place Text inside
	private Rectangle createTimeBox() {
		Rectangle box = new Rectangle();
		box.setWidth(WIDTH_TIME_BOX);
		box.setHeight(HEIGHT_TIME_BOX);
		box.setArcWidth(ARC_TIME_BOX);
		box.setArcHeight(ARC_TIME_BOX);

        box.setFill(BOX_COLOR_DEFAULT);
        
        return box;
     } 
}