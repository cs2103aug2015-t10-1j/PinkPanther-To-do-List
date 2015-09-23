package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoringBigPanel extends Rectangle{
	public BoringBigPanel () {
        setWidth(1050);
        setHeight(700);
        setArcWidth(80);
        setArcHeight(80);                
        setFill(Color.PINK.deriveColor(0, 1.2, 1, 0.6));
        setStroke(Color.DIMGRAY);
        setStrokeWidth(4.0f);
     }  
}
