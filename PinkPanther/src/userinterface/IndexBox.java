/* @@author A0122545M */
package userinterface;

import common.TaskType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class IndexBox extends StackPane{
	//For styling of the font
	private static int FONTSIZE_DEFAULT = 13;
	private static Color[] COLOR_ARRAY_BOX = {(Color.web("D652FF")), Color.web("6495ED"), 
			(Color.web("51D444")), (Color.web("D4573A")), (Color.POWDERBLUE)};
	private static String FONT_TYPE = "Tahoma";

	//These dimensions size the IndexBoxes correctly
	private static int BOX_WIDTH_HEIGHT_DEFAULT = 20;
	private static int BOX_ARC_SIZE_DEFAULT = 4;
	private static float BOX_STROKE_SIZE_DEFAULT = 2.0f;
	
	//Constructor for colored box according to task type
	public IndexBox(int index, TaskType inputTaskType) {
		
		Rectangle box = new Rectangle();
		box.setWidth(BOX_WIDTH_HEIGHT_DEFAULT);
		box.setHeight(BOX_WIDTH_HEIGHT_DEFAULT);
		box.setArcWidth(BOX_ARC_SIZE_DEFAULT);
		box.setArcHeight(BOX_ARC_SIZE_DEFAULT);               
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(BOX_STROKE_SIZE_DEFAULT);

		switch (inputTaskType) {
		case FLOATING:
			box.setFill(COLOR_ARRAY_BOX[0]);
			break;
		case EVENT:
			box.setFill(COLOR_ARRAY_BOX[1]);
			break;
		case TODO:
			box.setFill(COLOR_ARRAY_BOX[2]);
			break;
		default:
			box.setFill(COLOR_ARRAY_BOX[3]);
			break;
		}
		
		Text text = new Text(Integer.toString(index));
		text.setFont(Font.font(FONT_TYPE, FontWeight.BOLD, FONTSIZE_DEFAULT));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		if (index==0) {
			text.setText("");
		}
		this.getChildren().addAll(box, text);
		
	}
	
	//Constructor for RED index box
	public IndexBox () {		
		Rectangle box = new Rectangle();
		box.setWidth(BOX_WIDTH_HEIGHT_DEFAULT);
		box.setHeight(BOX_WIDTH_HEIGHT_DEFAULT);
		box.setArcWidth(BOX_ARC_SIZE_DEFAULT);
		box.setArcHeight(BOX_ARC_SIZE_DEFAULT);               
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(BOX_STROKE_SIZE_DEFAULT);
		
		Text text = new Text();
		text.setFont(Font.font(FONT_TYPE, FontWeight.BOLD, FONTSIZE_DEFAULT));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		text.setText("!");
		box.setFill(Color.RED);

		this.getChildren().addAll(box, text);
	}
	
	//Constructor for non-digit IndexBox
	public IndexBox (String textInput) {
		Rectangle box = new Rectangle();
		box.setWidth(BOX_WIDTH_HEIGHT_DEFAULT);
		box.setHeight(BOX_WIDTH_HEIGHT_DEFAULT);
		box.setArcWidth(BOX_ARC_SIZE_DEFAULT);
		box.setArcHeight(BOX_ARC_SIZE_DEFAULT);               
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(BOX_STROKE_SIZE_DEFAULT);

		Text text = new Text(textInput);
		text.setFont(Font.font(FONT_TYPE, FontWeight.BOLD, FONTSIZE_DEFAULT));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);

		box.setFill(Color.DIMGRAY);
		this.getChildren().addAll(box, text);
	}
}