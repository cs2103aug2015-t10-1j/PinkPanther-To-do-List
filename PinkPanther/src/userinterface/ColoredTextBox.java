package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColoredTextBox extends Rectangle{
	public ColoredTextBox() {
        setWidth(220);
        setHeight(60);
        setArcWidth(20);
        setArcHeight(20);                
        setFill(Color.BLUEVIOLET.deriveColor(0, 1.2, 1, 0.6));
        setStroke(Color.BLUEVIOLET);
     }  
}
