package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TransparentRect extends Rectangle{
	public TransparentRect(){
        setWidth(400);
        setHeight(60);
        setArcWidth(20);
        setArcHeight(20);                
        setFill(Color.TRANSPARENT);
        setStroke(Color.TRANSPARENT);
        setStrokeWidth(4.0f);
        
     }  
}
