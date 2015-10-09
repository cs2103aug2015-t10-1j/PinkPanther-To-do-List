package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColoredTextBox extends Rectangle{
	private static Color[] col = {(Color.ORANGE), (Color.KHAKI), 
			(Color.POWDERBLUE), (Color.LIGHTGREEN), (Color.POWDERBLUE)};
	private static int currCol = 0;
	
	public ColoredTextBox() {
        setWidth(400);
        setHeight(60);
        setArcWidth(20);
        setArcHeight(20);                
        setFill(col[currCol].deriveColor(0, 1.2, 1, 0.6));
        setStroke(Color.DIMGRAY);
        setStrokeWidth(4.0f);
        
        

        currCol++;
        if (currCol >= col.length){
        	currCol = 0;
        }
     }  
	
	public void setColor(int i){
		this.setFill(col[i].deriveColor(0, 1.2, 1, 0.6));
	
	}
}
