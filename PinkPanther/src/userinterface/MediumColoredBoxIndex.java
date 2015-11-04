/* @@author Baron */
package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MediumColoredBoxIndex extends Rectangle{
	public MediumColoredBoxIndex() {
        setWidth(30);
        setHeight(20);
        setArcWidth(20);
        setArcHeight(20);                
        setFill(Color.DIMGRAY.deriveColor(0, 1.2, 1, 0.6));
        setStroke(Color.DIMGRAY);
        setStrokeWidth(2.0f);
     }  
}