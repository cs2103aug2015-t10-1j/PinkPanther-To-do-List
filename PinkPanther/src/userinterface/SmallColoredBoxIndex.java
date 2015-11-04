/* @@author Baron */
package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SmallColoredBoxIndex extends Rectangle{
	public SmallColoredBoxIndex() {
        setWidth(20);
        setHeight(20);
        setArcWidth(4);
        setArcHeight(4);                
        setFill(Color.CORNFLOWERBLUE);
        setStroke(Color.DIMGRAY);
        setStrokeWidth(2.0f);
     }  
}