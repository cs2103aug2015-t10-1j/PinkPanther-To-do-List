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
        
        

        Text scenetitle = new Text("Cheat Sheet");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 66));
        scenetitle.setFill(Color.DIMGRAY);
        grid.add(scenetitle, 0, 2);
        
        
        TextedColorDayBox addBox = new TextedColorDayBox("add");
        grid.add(addBox, 3, 1);
        
        TextedTaskBox addInfo = new TextedTaskBox("   Instructions for add", "", "");
        grid.add(addInfo, 4, 1);
        
        
        TextedColorDayBox delBox = new TextedColorDayBox("delete");
        grid.add(delBox, 3, 2);
        
        TextedTaskBox delInfo = new TextedTaskBox("   Instructions for del", "", "");
        grid.add(delInfo, 4, 2);
        
        
        TextedColorDayBox editBox = new TextedColorDayBox("edit");
        grid.add(editBox, 3, 3);
        
        TextedTaskBox editInfo = new TextedTaskBox("   Instructions for edit", "", "");
        grid.add(editInfo, 4, 3);
        
        
        TextedColorDayBox undoBox = new TextedColorDayBox("undo");
        grid.add(undoBox, 3, 4);
        
        TextedTaskBox undoInfo = new TextedTaskBox("   Instructions for undo", "", "");
        grid.add(undoInfo, 4, 4);
        
        

        TextedColorDayBox redoBox = new TextedColorDayBox("redo");
        grid.add(redoBox, 3, 5);
        
        TextedTaskBox redoInfo = new TextedTaskBox("   Instructions for redo", "", "");
        grid.add(redoInfo, 4, 5);
        
        
        
        TextedColorDayBox saveBox = new TextedColorDayBox("save");
        grid.add(saveBox, 3, 6);

        TextedTaskBox saveInfo = new TextedTaskBox("   Instructions for save", "", "");
        grid.add(saveInfo, 4, 6);
        
        
	}
}
