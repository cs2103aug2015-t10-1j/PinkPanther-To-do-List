package userinterface;

import common.TaskType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HelpScreen extends ScrollPane{
	public HelpScreen(){
		
		
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
			

		this.setPrefSize(1080, 660);
		this.setContent(grid);
		this.setStyle("-fx-background-color: transparent;");
        
        

        Text scenetitle = new Text("Cheatsheet");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 32));
        scenetitle.setFill(Color.DIMGRAY);
        grid.add(scenetitle, 1, 1);
        
        
        IndexBox addBox = new IndexBox("+");
        grid.add(addBox, 1, 3);
        StackPane addCommandRectangle = createColoredBox("add", 0);
        grid.add(addCommandRectangle, 2, 3);
        StackPane addCommandRectangle2 = createColoredBox("<Event Name>", 0);
        grid.add(addCommandRectangle2, 3, 3);

	}
	
	private StackPane createColoredBox(String boxText, int colorIndex){
		Color[] col = {(Color.RED), Color.web("6495ED"), 
				(Color.web("51D444")), (Color.web("D4573A")), (Color.POWDERBLUE)};
		
		StackPane stackPane = new StackPane();
		
		Rectangle box = new Rectangle();
		
		int textLength = boxText.length();
		int boxWidth = textLength*10;
		
		box.setWidth(boxWidth);
		box.setHeight(30);
		box.setArcWidth(4);
		box.setArcHeight(4);               
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(2.0f);
		box.setFill(col[colorIndex]);

		Text text = new Text(boxText);
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		stackPane.getChildren().addAll(box, text);
		
		return stackPane;
	}
}
