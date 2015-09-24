package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SmallColoredBoxIndex extends Rectangle{
	public SmallColoredBoxIndex() {
        setWidth(20);
        setHeight(20);
        setArcWidth(20);
        setArcHeight(20);                
        setFill(Color.DIMGRAY.deriveColor(0, 1.2, 1, 0.6));
        setStroke(Color.DIMGRAY);
        setStrokeWidth(2.0f);
     }  
}