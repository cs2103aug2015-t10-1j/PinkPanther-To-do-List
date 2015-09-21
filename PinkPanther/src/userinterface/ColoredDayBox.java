package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColoredDayBox extends Rectangle{
	public ColoredDayBox() {
        setWidth(80);
        setHeight(80);
        setArcWidth(80);
        setArcHeight(80);                
        setFill(Color.RED.deriveColor(0, 1.2, 1, 0.6));
        setStroke(Color.RED);
     }  
}