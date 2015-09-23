package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TransparentCircle extends Rectangle{
	public TransparentCircle() {
        setWidth(80);
        setHeight(80);
        setArcWidth(80);
        setArcHeight(80);                
        setFill(Color.TRANSPARENT);
        setStroke(Color.TRANSPARENT);
        setStrokeWidth(4.0f);
     }  
}