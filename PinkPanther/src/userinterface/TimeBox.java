package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TimeBox extends Rectangle{
	private static int currColorIndex = 0;
	
	private Color[] colArray = {(Color.DARKBLUE), (Color.BLUEVIOLET), (Color.FIREBRICK)};
	public TimeBox() {
        setWidth(85);
        setHeight(20);
        setArcWidth(2);
        setArcHeight(2);
       // Color textColor = colArray[currColorIndex];

        Color textColor = Color.DIMGRAY;
        
     //   Color textColor = isStartTime ? colArray[0] : colArray[1];
        setFill(textColor.deriveColor(0, 1.2, 1, 0.6));
   //     setStroke(textColor);
        
   //     isStartTime = !isStartTime;
        
        currColorIndex++;
        if (currColorIndex >= colArray.length){
        	currColorIndex = 0;
        }
     }  
}