package userinterface;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TimeBox extends Rectangle{
	private static int currColorIndex = 0;
	
	private static boolean isStartTime = true;
	private Color[] colArray = {(Color.DARKBLUE), (Color.BLUEVIOLET), (Color.FIREBRICK)};
	public TimeBox() {
        setWidth(80);
        setHeight(20);
        setArcWidth(10);
        setArcHeight(10);
        Color textColor = colArray[currColorIndex];
        
     //   Color textColor = isStartTime ? colArray[0] : colArray[1];
        setFill(textColor.deriveColor(0, 1.2, 1, 0.6));
        setStroke(textColor);
        
   //     isStartTime = !isStartTime;
        
        currColorIndex++;
        if (currColorIndex >= colArray.length){
        	currColorIndex = 0;
        }
     }  
}