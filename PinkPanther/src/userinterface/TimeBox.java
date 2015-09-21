package userinterface;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TimeBox extends Rectangle{
	public TimeBox() {
        setWidth(60);
        setHeight(20);
        setArcWidth(10);
        setArcHeight(10);                
        setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
        setStroke(Color.WHITE);
     }  
}