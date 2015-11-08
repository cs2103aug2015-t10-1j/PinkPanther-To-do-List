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
	private static int FONT_SIZE_SCENE_TITLE = 32;
	private String FONT_TYPE_DEFAULT = "Tahoma";
	
	private static int FONT_SIZE_SCENE_BOX_CONTENTS = 20;
	private static int FONT_SIZE_COMMANDS_TEXTS = 25;
	private static int FONT_SIZE_SHORTCUTS = 13;

	private static int X_POS_SCENE_TITLE = 2;
	private static int Y_POS_SCENE_TITLE = 1;
	private static int X_POS_TEXT_IN_GRID = 4;
	private static int Y_POS_TEXT_IN_GRID = 3;
	private static int X_POS_ICONBOX = 3;
	private static int Y_POS_ICONBOX = 3;
	private static int X_POS_NAMEBOX = 1;
	private static int Y_POS_NAMEBOX = 3;
	private static int X_POS_DESCRIPTION_BOX = 2;
	private static int Y_POS_DESCRIPTION_BOX = 3;
	private static int X_POS_STACKPANE = 2;
	private static int X_POS_LABEL_PANE = 1;
	private static int X_POS_DESCRIPTION_PANE = 2;
	private static int Y_POS_TEXTBOX_CHUNKS = 3;
	private static int X_POS_START_TEXTBOX_CHUNKS = 3;
	private static int Y_POS_SHORTCUTS_STARTING_POS = 15;
	
	private static float SIZE_STROKE_WIDTHS = 2.0f;
	private static int MULTIPLIER_BOX_TO_TEXT_SIZE = 10;
	
	private int HEIGHT_ENTIRE_BOX = 30;
	private int ARC_ENTIRE_BOX = 4;
	private static Color FONT_COLOR_DEFAULT = Color.DIMGRAY;
	

	private int WIDTH_PREFERRED_WINDOW = 1080;
	private int HEIGHT_PREFERRED_WINDOW = 1060;
	private int H_PADDING_HELPSCREEN = 5;
	private int V_PADDING_HELPSCREEN = 7;
	private Insets INSET_GRID_DEFAULT = new Insets(10, 10, 10, 10);
	
	private Color[] COLORS_ARRAY_STACKPANE_BOX = {(Color.RED), Color.web("D1D2D4"), 
			(Color.web("D1D2D4")), (Color.web("D4573A")), (Color.POWDERBLUE)};
	
	GridPane grid;
	int currentYPosition = 3;
	
	//Constructor
	public HelpScreen() {
		grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(H_PADDING_HELPSCREEN);
        grid.setVgap(V_PADDING_HELPSCREEN);
        grid.setPadding(INSET_GRID_DEFAULT);
        
		this.setPrefSize(WIDTH_PREFERRED_WINDOW, HEIGHT_PREFERRED_WINDOW);
		this.setContent(grid);
		
        Text scenetitle = new Text("Cheatsheet");
        scenetitle.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SCENE_TITLE));
        scenetitle.setFill(FONT_COLOR_DEFAULT);
        grid.add(scenetitle, X_POS_SCENE_TITLE, Y_POS_SCENE_TITLE);
        
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
	
	private GridPane getSizedGridPane(){
		GridPane gridPane = new GridPane();
		gridPane.setHgap(5);
		gridPane.setVgap(3);
		return gridPane;
	}
	
	private void implementShortcuts() {
		int currYPos = Y_POS_SHORTCUTS_STARTING_POS;
		
        Text hiddenCalendarText = new Text("  :  Hidden-Calendar Mode");
        hiddenCalendarText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SCENE_BOX_CONTENTS));
        hiddenCalendarText.setFill(FONT_COLOR_DEFAULT);
		GridPane addPane = getSizedGridPane();
        IndexBox nameBox = new IndexBox("F1");
        addPane.add(nameBox, X_POS_ICONBOX, Y_POS_ICONBOX);
        addPane.add(hiddenCalendarText, X_POS_TEXT_IN_GRID, Y_POS_TEXT_IN_GRID);
        grid.add(addPane, X_POS_STACKPANE , currYPos++);
        
        Text cheatSheetText = new Text("  :  Cheat Sheet");
        cheatSheetText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SCENE_BOX_CONTENTS));
        cheatSheetText.setFill(FONT_COLOR_DEFAULT);
		GridPane cheatPane = getSizedGridPane();
        IndexBox cheatBox = new IndexBox("F2");
        cheatPane.add(cheatBox, X_POS_ICONBOX, Y_POS_ICONBOX);
        cheatPane.add(cheatSheetText, X_POS_TEXT_IN_GRID, Y_POS_TEXT_IN_GRID);
        grid.add(cheatPane, X_POS_STACKPANE , currYPos++);
        
        Text minimizedText = new Text("  :  Minimize Program");
        minimizedText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SCENE_BOX_CONTENTS));
        minimizedText.setFill(FONT_COLOR_DEFAULT);
		GridPane minimizedPane = getSizedGridPane();
        IndexBox minimizedBox = new IndexBox("F3");
        minimizedPane.add(minimizedBox, X_POS_ICONBOX, Y_POS_ICONBOX);
        minimizedPane.add(minimizedText, X_POS_TEXT_IN_GRID, Y_POS_TEXT_IN_GRID);
        grid.add(minimizedPane, X_POS_STACKPANE , currYPos++);
        
        Text truncatedText = new Text("  :  Task Name Truncation");
        truncatedText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SCENE_BOX_CONTENTS));
        truncatedText.setFill(FONT_COLOR_DEFAULT);
		GridPane truncatedPane = getSizedGridPane();
        IndexBox truncatedBox = new IndexBox("F4");
        truncatedPane.add(truncatedBox, X_POS_ICONBOX, Y_POS_ICONBOX);
        truncatedPane.add(truncatedText, X_POS_TEXT_IN_GRID, Y_POS_TEXT_IN_GRID);
        grid.add(truncatedPane, X_POS_STACKPANE , currYPos++);
        
        Text lengthenText = new Text("  :  Lengthen Window");
        lengthenText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SCENE_BOX_CONTENTS));
        lengthenText.setFill(FONT_COLOR_DEFAULT);
		GridPane lengthenPane = getSizedGridPane();
        IndexBox lengthenBox = new IndexBox("F5");
        lengthenPane.add(lengthenBox, X_POS_ICONBOX, Y_POS_ICONBOX);
        lengthenPane.add(lengthenText, X_POS_TEXT_IN_GRID, Y_POS_TEXT_IN_GRID);
        grid.add(lengthenPane, X_POS_STACKPANE , currYPos++);
        
        Text shortenText = new Text("  :  Shorten Window");
        shortenText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SCENE_BOX_CONTENTS));
        shortenText.setFill(FONT_COLOR_DEFAULT);
		GridPane shortenPane = getSizedGridPane();
        IndexBox shortenBox = new IndexBox("F6");
        shortenPane.add(shortenBox, X_POS_ICONBOX, Y_POS_ICONBOX);
        shortenPane.add(shortenText, X_POS_TEXT_IN_GRID, Y_POS_TEXT_IN_GRID);
        grid.add(shortenPane, X_POS_STACKPANE , currYPos++);
        
        Text normalViewText = new Text("  :  View Normal View");
        normalViewText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SCENE_BOX_CONTENTS));
        normalViewText.setFill(FONT_COLOR_DEFAULT);
		GridPane normalViewPane = getSizedGridPane();
        IndexBox normalViewBox = new IndexBox("F7");
        normalViewPane.add(normalViewBox, X_POS_ICONBOX, Y_POS_ICONBOX);
        normalViewPane.add(normalViewText, X_POS_TEXT_IN_GRID, Y_POS_TEXT_IN_GRID);
        grid.add(normalViewPane, X_POS_STACKPANE , currYPos++);       
        
        Text overdueText = new Text("  :  View Overdue Tasks");
        overdueText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SCENE_BOX_CONTENTS));
        overdueText.setFill(FONT_COLOR_DEFAULT);
		GridPane overduePane = getSizedGridPane();
        IndexBox overdueBox = new IndexBox("F8");
        overduePane.add(overdueBox, X_POS_ICONBOX, Y_POS_ICONBOX);
        overduePane.add(overdueText, X_POS_TEXT_IN_GRID, Y_POS_TEXT_IN_GRID);
        grid.add(overduePane, X_POS_STACKPANE , currYPos++);
        
        Text allViewText = new Text("  :  View All Tasks");
        allViewText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SCENE_BOX_CONTENTS));
        allViewText.setFill(FONT_COLOR_DEFAULT);
		GridPane allViewPane = getSizedGridPane();
        IndexBox allViewBox = new IndexBox("F9");
        allViewPane.add(allViewBox, X_POS_ICONBOX, Y_POS_ICONBOX);
        allViewPane.add(allViewText, X_POS_TEXT_IN_GRID, Y_POS_TEXT_IN_GRID);
        grid.add(allViewPane, X_POS_STACKPANE , currYPos++);
	}
	
	private void implementAddBoxes() {
		GridPane labelPane = getSizedGridPane();
        IndexBox nameBox = new IndexBox("+");
        labelPane.add(nameBox, X_POS_NAMEBOX, Y_POS_NAMEBOX);
        Text addText = new Text("Add: ");
        addText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_COMMANDS_TEXTS));
        addText.setFill(FONT_COLOR_DEFAULT);
        labelPane.add(addText, X_POS_DESCRIPTION_BOX, Y_POS_DESCRIPTION_BOX);
        
		GridPane addPane = getSizedGridPane();
        
		int currXpos = X_POS_START_TEXTBOX_CHUNKS;
        StackPane commandRectangle = createColoredBox(" add ");
        addPane.add(commandRectangle, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle1 = createColoredBox("<Event Name>");
        addPane.add(commandRectangle1, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle2 = createColoredBox(",<Event Date>");
        addPane.add(commandRectangle2, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle3 = createColoredBox(",<Event Time>");
        addPane.add(commandRectangle3, currXpos++, Y_POS_TEXTBOX_CHUNKS);

        grid.add(labelPane, X_POS_LABEL_PANE, currentYPosition);
        grid.add(addPane, X_POS_DESCRIPTION_PANE, currentYPosition++);
	}
	
	private void implementDeleteBoxes() {
		GridPane labelPane = getSizedGridPane();
        IndexBox nameBox = new IndexBox("-");
        labelPane.add(nameBox, X_POS_NAMEBOX, Y_POS_NAMEBOX);
        Text addText = new Text("Delete: ");
        addText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_COMMANDS_TEXTS));
        addText.setFill(FONT_COLOR_DEFAULT);
        labelPane.add(addText, X_POS_DESCRIPTION_BOX, Y_POS_DESCRIPTION_BOX);
        
		GridPane addPane = getSizedGridPane();

		int currXpos = X_POS_START_TEXTBOX_CHUNKS;
        StackPane commandRectangle = createColoredBox(" del ");
        addPane.add(commandRectangle, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle1 = createColoredBox("<Event Date>");
        addPane.add(commandRectangle1, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle2 = createColoredBox(",<Event Index>");
        addPane.add(commandRectangle2, currXpos++, Y_POS_TEXTBOX_CHUNKS);

        grid.add(labelPane, X_POS_LABEL_PANE, currentYPosition);
        grid.add(addPane, X_POS_DESCRIPTION_PANE, currentYPosition++);
	}
	
	private void implementEditBoxes() {
		GridPane labelPane = getSizedGridPane();
        IndexBox nameBox = new IndexBox("✎");
        labelPane.add(nameBox, X_POS_NAMEBOX, Y_POS_NAMEBOX);
        Text addText = new Text("Edit: ");
        addText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_COMMANDS_TEXTS));
        addText.setFill(FONT_COLOR_DEFAULT);
        labelPane.add(addText, X_POS_DESCRIPTION_BOX, Y_POS_DESCRIPTION_BOX);
        
		GridPane addPane = getSizedGridPane();

		int currXpos = X_POS_START_TEXTBOX_CHUNKS;
        StackPane commandRectangle = createColoredBox(" del ");
        addPane.add(commandRectangle, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle1 = createColoredBox("<Event Date>");
        addPane.add(commandRectangle1, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle2 = createColoredBox(",<Event Index>");
        addPane.add(commandRectangle2, currXpos++, Y_POS_TEXTBOX_CHUNKS);

        grid.add(labelPane, X_POS_LABEL_PANE, currentYPosition);
        grid.add(addPane, X_POS_DESCRIPTION_PANE, currentYPosition++);
	}
	
	private void implementDoneBoxes() {
		GridPane labelPane = getSizedGridPane();
        IndexBox nameBox = new IndexBox("✓");
        labelPane.add(nameBox, X_POS_NAMEBOX, Y_POS_NAMEBOX);
        Text addText = new Text("Done: ");
        addText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_COMMANDS_TEXTS));
        addText.setFill(FONT_COLOR_DEFAULT);
        labelPane.add(addText, X_POS_DESCRIPTION_BOX, Y_POS_DESCRIPTION_BOX);
        
		GridPane addPane = getSizedGridPane();

		int currXpos = X_POS_START_TEXTBOX_CHUNKS;
        StackPane commandRectangle = createColoredBox(" del ");
        addPane.add(commandRectangle, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle1 = createColoredBox("<Event Date>");
        addPane.add(commandRectangle1, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle2 = createColoredBox(",<Event Index>");
        addPane.add(commandRectangle2, currXpos++, Y_POS_TEXTBOX_CHUNKS);

        grid.add(labelPane, X_POS_LABEL_PANE, currentYPosition);
        grid.add(addPane, X_POS_DESCRIPTION_PANE, currentYPosition++);
	}
	
	private void implementSearchBoxes() {
		GridPane labelPane = getSizedGridPane();
        IndexBox nameBox = new IndexBox("🔍");
        labelPane.add(nameBox, X_POS_NAMEBOX, Y_POS_NAMEBOX);
        Text addText = new Text("Search: ");
        addText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_COMMANDS_TEXTS));
        addText.setFill(FONT_COLOR_DEFAULT);
        labelPane.add(addText, X_POS_DESCRIPTION_BOX, Y_POS_DESCRIPTION_BOX);
        
		GridPane addPane = getSizedGridPane();

		int currXpos = X_POS_START_TEXTBOX_CHUNKS;
        StackPane commandRectangle = createColoredBox(" search ");
        addPane.add(commandRectangle, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle1 = createColoredBox("<Keyword>");
        addPane.add(commandRectangle1, currXpos++, Y_POS_TEXTBOX_CHUNKS);

        grid.add(labelPane, X_POS_LABEL_PANE, currentYPosition);
        grid.add(addPane, X_POS_DESCRIPTION_PANE, currentYPosition++);
	}
	
	private void implementViewBoxes() {
		GridPane labelPane = getSizedGridPane();
        IndexBox nameBox = new IndexBox("👓");
        labelPane.add(nameBox, X_POS_NAMEBOX, Y_POS_NAMEBOX);
        Text addText = new Text("View: ");
        addText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_COMMANDS_TEXTS));
        addText.setFill(FONT_COLOR_DEFAULT);
        labelPane.add(addText, X_POS_DESCRIPTION_BOX, Y_POS_DESCRIPTION_BOX);
        
		GridPane addPane = getSizedGridPane();

		int currXpos = X_POS_START_TEXTBOX_CHUNKS;
        StackPane commandRectangle = createColoredBox(" view ");
        addPane.add(commandRectangle, currXpos++, Y_POS_TEXTBOX_CHUNKS);
        StackPane commandRectangle1 = createColoredBox("<Date(s)>");
        addPane.add(commandRectangle1, currXpos++, Y_POS_TEXTBOX_CHUNKS);

        grid.add(labelPane, X_POS_LABEL_PANE, currentYPosition);
        grid.add(addPane, X_POS_DESCRIPTION_PANE, currentYPosition++);
	}
	

	private void implementUndoBoxes() {
		GridPane labelPane = getSizedGridPane();
        IndexBox nameBox = new IndexBox("←");
        labelPane.add(nameBox, X_POS_NAMEBOX, Y_POS_NAMEBOX);
        Text addText = new Text("Undo: ");
        addText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_COMMANDS_TEXTS));
        addText.setFill(FONT_COLOR_DEFAULT);
        labelPane.add(addText, X_POS_DESCRIPTION_BOX, Y_POS_DESCRIPTION_BOX);
        
		GridPane addPane = getSizedGridPane();

		int currXpos = X_POS_START_TEXTBOX_CHUNKS;
        StackPane commandRectangle = createColoredBox(" undo ");
        addPane.add(commandRectangle, currXpos++, Y_POS_TEXTBOX_CHUNKS);

        grid.add(labelPane, X_POS_LABEL_PANE, currentYPosition);
        grid.add(addPane, X_POS_DESCRIPTION_PANE, currentYPosition++);
	}
	

	private void implementRedoBoxes() {
		GridPane labelPane = getSizedGridPane();
        IndexBox nameBox = new IndexBox("→");
        labelPane.add(nameBox, X_POS_NAMEBOX, Y_POS_NAMEBOX);
        Text addText = new Text("Redo: ");
        addText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_COMMANDS_TEXTS));
        addText.setFill(FONT_COLOR_DEFAULT);
        labelPane.add(addText, X_POS_DESCRIPTION_BOX, Y_POS_DESCRIPTION_BOX);
        
		GridPane addPane = getSizedGridPane();

		int currXpos = X_POS_START_TEXTBOX_CHUNKS;
        StackPane commandRectangle2 = createColoredBox(" redo ");
        addPane.add(commandRectangle2, currXpos++, Y_POS_TEXTBOX_CHUNKS);

        grid.add(labelPane, X_POS_LABEL_PANE, currentYPosition);
        grid.add(addPane, X_POS_DESCRIPTION_PANE, currentYPosition++);
	}
	

	private void implementExitBoxes() {
		GridPane labelPane = getSizedGridPane();
		
        IndexBox nameBox = new IndexBox("X");
        labelPane.add(nameBox, X_POS_NAMEBOX, Y_POS_NAMEBOX);
        Text addText = new Text("Exit: ");
        addText.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_COMMANDS_TEXTS));
        addText.setFill(FONT_COLOR_DEFAULT);
        labelPane.add(addText, X_POS_DESCRIPTION_BOX, Y_POS_DESCRIPTION_BOX);
        
		GridPane addPane = getSizedGridPane();

		int currXpos = X_POS_START_TEXTBOX_CHUNKS;
        StackPane commandRectangle = createColoredBox(" exit ");
        addPane.add(commandRectangle, currXpos++, Y_POS_TEXTBOX_CHUNKS);

        grid.add(labelPane, X_POS_LABEL_PANE, currentYPosition);
        grid.add(addPane, X_POS_DESCRIPTION_PANE, currentYPosition++);
	}
	
	private StackPane createColoredBox(String boxText) {
		StackPane stackPane = new StackPane();
		Rectangle box = new Rectangle();
		
		int textLength = boxText.length();
		int boxWidth = textLength*MULTIPLIER_BOX_TO_TEXT_SIZE;
		
		box.setWidth(boxWidth);
		box.setHeight(HEIGHT_ENTIRE_BOX);
		box.setArcWidth(ARC_ENTIRE_BOX);
		box.setArcHeight(ARC_ENTIRE_BOX);               
		box.setStroke(FONT_COLOR_DEFAULT);
		box.setStrokeWidth(SIZE_STROKE_WIDTHS);
		box.setFill(COLORS_ARRAY_STACKPANE_BOX[1]);

		Text text = new Text(boxText);
		text.setFont(Font.font(FONT_TYPE_DEFAULT, FontWeight.BOLD, FONT_SIZE_SHORTCUTS));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(FONT_COLOR_DEFAULT);
		
		stackPane.getChildren().addAll(box, text);
		
		return stackPane;
	}
}
