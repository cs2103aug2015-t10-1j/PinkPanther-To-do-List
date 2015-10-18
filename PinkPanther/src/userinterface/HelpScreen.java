package userinterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 66));
        scenetitle.setFill(Color.DIMGRAY);
        grid.add(scenetitle, 2, 2);
        
        
        TextedColorDayBox addBox = new TextedColorDayBox("add");
        grid.add(addBox, 4, 1);
     /*   
        TextedTaskBox addInfo = new TextedTaskBox("   add yourEventName, 5pm-8pm", "", "", 0);
        grid.add(addInfo, 5, 1);
        
        
        TextedColorDayBox delBox = new TextedColorDayBox("delete");
        grid.add(delBox, 4, 2);
        
        TextedTaskBox delInfo = new TextedTaskBox("   Instructions for del", "", "", 0);
        grid.add(delInfo, 5, 2);
        
        
        TextedColorDayBox editBox = new TextedColorDayBox("edit");
        grid.add(editBox, 4, 3);
        
        TextedTaskBox editInfo = new TextedTaskBox("   Instructions for edit", "", "", 0);
        grid.add(editInfo, 5, 3);
        
        
        TextedColorDayBox undoBox = new TextedColorDayBox("undo");
        grid.add(undoBox, 4, 4);
        
        TextedTaskBox undoInfo = new TextedTaskBox("   Instructions for undo", "", "", 0);
        grid.add(undoInfo, 5, 4);
        
        

        TextedColorDayBox redoBox = new TextedColorDayBox("redo");
        grid.add(redoBox, 4, 5);
        
        TextedTaskBox redoInfo = new TextedTaskBox("   Instructions for redo", "", "", 0);
        grid.add(redoInfo, 5, 5);
        
        
        
        TextedColorDayBox saveBox = new TextedColorDayBox("save");
        grid.add(saveBox, 4, 6);

        TextedTaskBox saveInfo = new TextedTaskBox("   Instructions for save", "", "", 0);
        grid.add(saveInfo, 5, 6);
        
        */
	}
}
