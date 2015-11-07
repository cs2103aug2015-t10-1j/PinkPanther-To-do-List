/* @@author A0122545M */
package userinterface;

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
	GridPane grid;
	
	//Constructor
	public HelpScreen() {
		grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(7);
        grid.setPadding(new Insets(10, 10, 10, 10));
        
		this.setPrefSize(1080, 1060);
		this.setContent(grid);
		this.setStyle("-fx-background-color: transparent;");
		
        Text scenetitle = new Text("Cheatsheet");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 32));
        scenetitle.setFill(Color.DIMGRAY);
        grid.add(scenetitle, 2, 1);
        
        implementAddBoxes();
        implementDeleteBoxes();
        implementEditBoxes();
        implementDoneBoxes();
        implementSearchBoxes();
        implementViewBoxes();
        implementUndoBoxes();
        implementRedoBoxes();
        implementExitBoxes();
        implementShortcuts();

	}
	
	private void implementShortcuts() {
		int currYPos = 15;
		
        Text hiddenCalendarText = new Text("  :  Hidden-Calendar Mode");
        hiddenCalendarText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        hiddenCalendarText.setFill(Color.DIMGRAY);
		GridPane addPane = new GridPane();
		addPane.setHgap(5);
		addPane.setVgap(3);
        IndexBox nameBox = new IndexBox("F1");
        addPane.add(nameBox, 3, 3);
        addPane.add(hiddenCalendarText, 4, 3);
        grid.add(addPane, 2, currYPos++);
        
        Text cheatSheetText = new Text("  :  Cheat Sheet");
        cheatSheetText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        cheatSheetText.setFill(Color.DIMGRAY);
		GridPane cheatPane = new GridPane();
		cheatPane.setHgap(5);
		cheatPane.setVgap(3);
        IndexBox cheatBox = new IndexBox("F2");
        cheatPane.add(cheatBox, 3, 3);
        cheatPane.add(cheatSheetText, 4, 3);
        grid.add(cheatPane, 2, currYPos++);
        
        Text minimizedText = new Text("  :  Minimize Program");
        minimizedText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        minimizedText.setFill(Color.DIMGRAY);
		GridPane minimizedPane = new GridPane();
		minimizedPane.setHgap(5);
		minimizedPane.setVgap(3);
        IndexBox minimizedBox = new IndexBox("F3");
        minimizedPane.add(minimizedBox, 3, 3);
        minimizedPane.add(minimizedText, 4, 3);
        grid.add(minimizedPane, 2, currYPos++);
        
        Text truncatedText = new Text("  :  Task Name Truncation");
        truncatedText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        truncatedText.setFill(Color.DIMGRAY);
		GridPane truncatedPane = new GridPane();
		truncatedPane.setHgap(5);
		truncatedPane.setVgap(3);
        IndexBox truncatedBox = new IndexBox("F4");
        truncatedPane.add(truncatedBox, 3, 3);
        truncatedPane.add(truncatedText, 4, 3);
        grid.add(truncatedPane, 2, currYPos++);
        
        Text lengthenText = new Text("  :  Lengthen Window");
        lengthenText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        lengthenText.setFill(Color.DIMGRAY);
		GridPane lengthenPane = new GridPane();
		lengthenPane.setHgap(5);
		lengthenPane.setVgap(3);
        IndexBox lengthenBox = new IndexBox("F5");
        lengthenPane.add(lengthenBox, 3, 3);
        lengthenPane.add(lengthenText, 4, 3);
        grid.add(lengthenPane, 2, currYPos++);
        
        Text shortenText = new Text("  :  Shorten Window");
        shortenText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        shortenText.setFill(Color.DIMGRAY);
		GridPane shortenPane = new GridPane();
		shortenPane.setHgap(5);
		shortenPane.setVgap(3);
        IndexBox shortenBox = new IndexBox("F6");
        shortenPane.add(shortenBox, 3, 3);
        shortenPane.add(shortenText, 4, 3);
        grid.add(shortenPane, 2, currYPos++);
        
        Text normalViewText = new Text("  :  View Normal View");
        normalViewText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        normalViewText.setFill(Color.DIMGRAY);
		GridPane normalViewPane = new GridPane();
		normalViewPane.setHgap(5);
		normalViewPane.setVgap(3);
        IndexBox normalViewBox = new IndexBox("F7");
        normalViewPane.add(normalViewBox, 3, 3);
        normalViewPane.add(normalViewText, 4, 3);
        grid.add(normalViewPane, 2, currYPos++);       
        
        Text overdueText = new Text("  :  View Overdue Tasks");
        overdueText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        overdueText.setFill(Color.DIMGRAY);
		GridPane overduePane = new GridPane();
		overduePane.setHgap(5);
		overduePane.setVgap(3);
        IndexBox overdueBox = new IndexBox("F8");
        overduePane.add(overdueBox, 3, 3);
        overduePane.add(overdueText, 4, 3);
        grid.add(overduePane, 2, currYPos++);
        
        Text allViewText = new Text("  :  View All Tasks");
        allViewText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        allViewText.setFill(Color.DIMGRAY);
		GridPane allViewPane = new GridPane();
		allViewPane.setHgap(5);
		allViewPane.setVgap(3);
        IndexBox allViewBox = new IndexBox("F9");
        allViewPane.add(allViewBox, 3, 3);
        allViewPane.add(allViewText, 4, 3);
        grid.add(allViewPane, 2, currYPos++);
	}
	
	private void implementAddBoxes() {
		GridPane labelPane = new GridPane();
		labelPane.setHgap(5);
		labelPane.setVgap(3);
        IndexBox nameBox = new IndexBox("+");
        labelPane.add(nameBox, 1, 3);
        Text addText = new Text("Add: ");
        addText.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        addText.setFill(Color.DIMGRAY);
        labelPane.add(addText, 2, 3);
        
		GridPane addPane = new GridPane();
		addPane.setHgap(5);
		addPane.setVgap(3);
        
        StackPane addCommandRectangle = createColoredBox(" add ", 1);
        addPane.add(addCommandRectangle, 3, 3);
        StackPane addCommandRectangleN = createColoredBox("<Event Name>", 1);
        addPane.add(addCommandRectangleN, 4, 3);
        StackPane addCommandRectangleD2 = createColoredBox(",<Event Date>", 2);
        addPane.add(addCommandRectangleD2, 5, 3);
        StackPane addCommandRectangleT = createColoredBox(",<Event Time>", 2);
        addPane.add(addCommandRectangleT, 6, 3);

        grid.add(labelPane, 1, 3);
        grid.add(addPane, 2, 3);
	}
	
	private void implementDeleteBoxes() {
		GridPane labelPane = new GridPane();
		labelPane.setHgap(5);
		labelPane.setVgap(3);
        IndexBox nameBox = new IndexBox("-");
        labelPane.add(nameBox, 1, 3);
        Text addText = new Text("Delete: ");
        addText.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        addText.setFill(Color.DIMGRAY);
        labelPane.add(addText, 2, 3);
        
		GridPane addPane = new GridPane();
		addPane.setHgap(5);
		addPane.setVgap(3);
        
        StackPane addCommandRectangle = createColoredBox(" del ", 1);
        addPane.add(addCommandRectangle, 3, 3);
        StackPane addCommandRectangleN = createColoredBox("<Event Date>", 1);
        addPane.add(addCommandRectangleN, 4, 3);
        StackPane addCommandRectangleD2 = createColoredBox(",<Event Index>", 2);
        addPane.add(addCommandRectangleD2, 5, 3);

        grid.add(labelPane, 1, 4);
        grid.add(addPane, 2, 4);
	}
	
	private void implementEditBoxes() {
		GridPane labelPane = new GridPane();
		labelPane.setHgap(5);
		labelPane.setVgap(3);
        IndexBox nameBox = new IndexBox("✎");
        labelPane.add(nameBox, 1, 3);
        Text addText = new Text("Edit: ");
        addText.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        addText.setFill(Color.DIMGRAY);
        labelPane.add(addText, 2, 3);
        
		GridPane addPane = new GridPane();
		addPane.setHgap(5);
		addPane.setVgap(3);
        
        StackPane addCommandRectangle = createColoredBox(" del ", 1);
        addPane.add(addCommandRectangle, 3, 3);
        StackPane addCommandRectangleN = createColoredBox("<Event Date>", 1);
        addPane.add(addCommandRectangleN, 4, 3);
        StackPane addCommandRectangleD2 = createColoredBox(",<Event Index>", 2);
        addPane.add(addCommandRectangleD2, 5, 3);

        grid.add(labelPane, 1, 5);
        grid.add(addPane, 2, 5);
	}
	
	private void implementDoneBoxes() {
		GridPane labelPane = new GridPane();
		labelPane.setHgap(5);
		labelPane.setVgap(3);
        IndexBox nameBox = new IndexBox("✓");
        labelPane.add(nameBox, 1, 3);
        Text addText = new Text("Done: ");
        addText.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        addText.setFill(Color.DIMGRAY);
        labelPane.add(addText, 2, 3);
        
		GridPane addPane = new GridPane();
		addPane.setHgap(5);
		addPane.setVgap(3);
        
        StackPane addCommandRectangle = createColoredBox(" del ", 1);
        addPane.add(addCommandRectangle, 3, 3);
        StackPane addCommandRectangleN = createColoredBox("<Event Date>", 1);
        addPane.add(addCommandRectangleN, 4, 3);
        StackPane addCommandRectangleD2 = createColoredBox(",<Event Index>", 2);
        addPane.add(addCommandRectangleD2, 5, 3);

        grid.add(labelPane, 1, 6);
        grid.add(addPane, 2, 6);
	}
	
	private void implementSearchBoxes() {
		GridPane labelPane = new GridPane();
		labelPane.setHgap(5);
		labelPane.setVgap(3);
        IndexBox nameBox = new IndexBox("🔍");
        labelPane.add(nameBox, 1, 3);
        Text addText = new Text("Search: ");
        addText.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        addText.setFill(Color.DIMGRAY);
        labelPane.add(addText, 2, 3);
        
		GridPane addPane = new GridPane();
		addPane.setHgap(5);
		addPane.setVgap(3);
        
        StackPane addCommandRectangle = createColoredBox(" search ", 1);
        addPane.add(addCommandRectangle, 3, 3);
        StackPane addCommandRectangleN = createColoredBox("<Keyword>", 1);
        addPane.add(addCommandRectangleN, 4, 3);

        grid.add(labelPane, 1, 7);
        grid.add(addPane, 2, 7);
	}
	
	private void implementViewBoxes() {
		GridPane labelPane = new GridPane();
		labelPane.setHgap(5);
		labelPane.setVgap(3);
        IndexBox nameBox = new IndexBox("👓");
        labelPane.add(nameBox, 1, 3);
        Text addText = new Text("View: ");
        addText.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        addText.setFill(Color.DIMGRAY);
        labelPane.add(addText, 2, 3);
        
		GridPane addPane = new GridPane();
		addPane.setHgap(5);
		addPane.setVgap(3);
        
        StackPane addCommandRectangle = createColoredBox(" view ", 1);
        addPane.add(addCommandRectangle, 3, 3);
        StackPane addCommandRectangleN = createColoredBox("<Date(s)>", 1);
        addPane.add(addCommandRectangleN, 4, 3);

        grid.add(labelPane, 1, 8);
        grid.add(addPane, 2, 8);
	}
	

	private void implementUndoBoxes() {
		GridPane labelPane = new GridPane();
		labelPane.setHgap(5);
		labelPane.setVgap(3);
        IndexBox nameBox = new IndexBox("←");
        labelPane.add(nameBox, 1, 3);
        Text addText = new Text("Undo: ");
        addText.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        addText.setFill(Color.DIMGRAY);
        labelPane.add(addText, 2, 3);
        
		GridPane addPane = new GridPane();
		addPane.setHgap(5);
		addPane.setVgap(3);
        
        StackPane addCommandRectangle = createColoredBox(" undo ", 1);
        addPane.add(addCommandRectangle, 3, 3);

        grid.add(labelPane, 1, 9);
        grid.add(addPane, 2, 9);
	}
	

	private void implementRedoBoxes() {
		GridPane labelPane = new GridPane();
		labelPane.setHgap(5);
		labelPane.setVgap(3);
        IndexBox nameBox = new IndexBox("→");
        labelPane.add(nameBox, 1, 3);
        Text addText = new Text("Redo: ");
        addText.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        addText.setFill(Color.DIMGRAY);
        labelPane.add(addText, 2, 3);
        
		GridPane addPane = new GridPane();
		addPane.setHgap(5);
		addPane.setVgap(3);
        
        StackPane addCommandRectangle2 = createColoredBox(" redo ", 1);
        addPane.add(addCommandRectangle2, 3, 3);

        grid.add(labelPane, 1, 10);
        grid.add(addPane, 2, 10);
	}
	

	private void implementExitBoxes() {
		GridPane labelPane = new GridPane();
		labelPane.setHgap(5);
		labelPane.setVgap(3);
        IndexBox nameBox = new IndexBox("X");
        labelPane.add(nameBox, 1, 3);
        Text addText = new Text("Exit: ");
        addText.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        addText.setFill(Color.DIMGRAY);
        labelPane.add(addText, 2, 3);
        
		GridPane addPane = new GridPane();
		addPane.setHgap(5);
		addPane.setVgap(3);
        
        StackPane addCommandRectangle = createColoredBox(" exit ", 1);
        addPane.add(addCommandRectangle, 3, 3);

        grid.add(labelPane, 1, 11);
        grid.add(addPane, 2, 11);
	}
	
	private StackPane createColoredBox(String boxText, int colorIndex) {
		Color[] col = {(Color.RED), Color.web("D1D2D4"), 
				(Color.web("D1D2D4")), (Color.web("D4573A")), (Color.POWDERBLUE)};
		
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
		text.setFill(Color.DIMGRAY);
		
		stackPane.getChildren().addAll(box, text);
		
		return stackPane;
	}
}
