/* @@author A0122545M */
package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//This class creates some spacing in between grids, without altering gridV/H spacing attribute
public class TransparentCircle extends Rectangle{
	public TransparentCircle() {
        setWidth(20);
        setHeight(20);       
        setFill(Color.TRANSPARENT);
        setStroke(Color.TRANSPARENT);
        setStrokeWidth(4.0f);
     }  
}