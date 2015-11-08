/* @@author A0122545M */
package userinterface;

import common.Task;
import common.TaskType;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TaskBox extends StackPane{
	private static int MAX_LENGTH_TASKNAME = 45;
	
	//These coordinates help position items on grid
	private static int POSITION_MAIN_BOX_X_POS = 1;
	private static int POSITION_DEFAULT_Y_COORD = 1;
	private static int POSITION_TIMESPAN_START_BOX = 65;
	private static int POSITION_DEFAULT_LEFT_ALIGNED_X_COORD = 0;
	private static int POSITION_DEFAULT_CENTRE_ALIGNED_X_COORD = 1;
	private static int POSITION_DEFAULT_RIGHT_ALIGNED_X_COORD = 2;
	private static int SIZE_PADDING_GRID_H = 5;
	private static int SIZE_PADDING_GRID_V = 0;
	private static int SIZE_PADDING_TIMEBOX_V_GAP = 10;
	
	//These settings help set the font and text to align properly within the grid
	private static String DEFAULT_FONT_NAME = "Tahoma";
	private static int FONTSIZE_TASK_NAME = 20;
	private static int FONTSIZE_TIMESPAN_ARROW = 15;
	private static String OFFSET_TASK_NAME = "     ";
	private static String OFFSET_DEADLINE_INDICATOR = "by ";
	private static String OFFSET_TRUNCATED_DOTS = "...";

	//These settings help set the size and colors of the boxes
	private static int SIZE_WIDTH_TASKHOLDERBOX = 600;
	private static int SIZE_HEIGHT_TASKHOLDERBOX = 40;
	private static int SIZE_ARC_TASKHOLDERBOX = 4;
	private static int SIZE_WIDTH_AND_HEIGHT_INDEXBOX = 20;
	private static int SIZE_ARC_INDEXBOX = 4;
	private static float THICKNESS_DEFAULT_STROKE_WIDTH = 2.0f;
	private static Color COLOR_DEFAULT_TIMEBOX_COLOR = Color.DIMGRAY.deriveColor(0, 1.2, 1, 0.6);
	private static Color COLOR_DEFAULT_TASKNAME_COLOR = Color.web("212121");
	private static ColumnConstraints COLUMN_CONSTRAINTS_ROW2 = new ColumnConstraints(50, 150, 100);
	private static ColumnConstraints COLUMN_CONSTRAINTS_ROW1 = new ColumnConstraints(5);
	private static Color[] COLORS_ARRAY_TASKBOX = {(Color.web("EFBAFF")), (Color.web("C1D5F8")), 
			(Color.web("B9EEB4")), (Color.web("EEBCB0")), (Color.POWDERBLUE)};
	private static Color[] COLORS_ARRAY_INDEXBOX = {(Color.web("D652FF")), Color.web("6495ED"), 
			(Color.web("51D444")), (Color.web("D4573A")), (Color.RED)};
	
	
	//Constructor
	public TaskBox(int index, Task inputTask, boolean isTruncatedMode) {
		TaskType inputTaskType = inputTask.getTaskType();
		String[] splitName = splitTaskNameIntoArray(inputTask.getName(), inputTask, inputTaskType);
		int numOfColumnsName = splitName.length;
		
		GridPane grid = instantiateTaskGrid(index, inputTask, 
				isTruncatedMode, inputTaskType, numOfColumnsName, splitName);
		
		this.getChildren().addAll(grid);
	}
	
	/* Breaks all input information down and constructs a GridPane and returns it
	 * 
	 * @param index: Index of task, numOfColumnsName: Number of columns the name occupies in total
	 * 			splitName: Task name split into String array
	 * 
	 * @return GridPane with all Task information displayed
	 */
	private GridPane instantiateTaskGrid(int index, Task inputTask, 
			boolean isTruncatedMode, TaskType inputTaskType, int numOfColumnsName, String[] splitName){
		GridPane grid = new GridPane();
		setGridConstraints(grid);
		addTaskNameRectangleToGrid(grid, inputTaskType, isTruncatedMode, numOfColumnsName);
		addNameTextToTaskBox(isTruncatedMode, splitName, numOfColumnsName, grid);
		addTimeBoxesToTaskBox(grid, index, numOfColumnsName, inputTask, inputTaskType);
		addIndexBoxToTaskBox(grid, index, inputTaskType);
		return grid;
	}
	private void addNameTextToTaskBox(boolean isTruncatedMode, String[] splitName, int numOfColumnsName, GridPane grid){
		int currYPos = 1;
		if (isTruncatedMode) {
			Text text = new Text(OFFSET_TASK_NAME + splitName[0]);
			if (numOfColumnsName > 1) {
				text.setText(text.getText() + OFFSET_TRUNCATED_DOTS);
			}
			text.setTextAlignment(TextAlignment.LEFT);
			text.setFont(Font.font(DEFAULT_FONT_NAME, FontWeight.NORMAL, FONTSIZE_TASK_NAME));
			text.setFill(COLOR_DEFAULT_TASKNAME_COLOR);
			grid.add(text, POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, currYPos++);
		} else {
			for (int i=0; i<numOfColumnsName; i++) {
				Text text = new Text(OFFSET_TASK_NAME + splitName[i]);
				text.setTextAlignment(TextAlignment.LEFT);
				text.setFont(Font.font(DEFAULT_FONT_NAME, FontWeight.NORMAL, FONTSIZE_TASK_NAME));
				text.setFill(COLOR_DEFAULT_TASKNAME_COLOR);
				grid.add(text, POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, currYPos++);
			}
		}
	}
	private void addIndexBoxToTaskBox(GridPane grid, int index, TaskType inputTaskType){
		if (index != 0) {
			StackPane indexBox = createIndexBox(index, inputTaskType);
			GridPane.setConstraints(indexBox , POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, 1);
			grid.add(indexBox, POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, POSITION_DEFAULT_Y_COORD);
		}
	}
	private void addTimeBoxesToTaskBox(GridPane grid, int index, int numOfColumnsName, Task inputTask, TaskType inputTaskType){
		GridPane timeSpanBoxes = createTimeSpanBoxes(inputTask);
		int currRectYPos = 1;
		if (numOfColumnsName > 1 && inputTask.getTaskType() != TaskType.FLOATING) {
			Rectangle box = createTaskHolderBox(inputTaskType);
			grid.add(box, POSITION_DEFAULT_Y_COORD, currRectYPos++);
			grid.add(timeSpanBoxes, POSITION_TIMESPAN_START_BOX, currRectYPos -1);
		} else {
			grid.add(timeSpanBoxes, POSITION_TIMESPAN_START_BOX, POSITION_DEFAULT_Y_COORD);
		}
		
		if (index != 0) {
			StackPane indexBox = createIndexBox(index, inputTaskType);
			GridPane.setConstraints(indexBox , POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, 1);
			grid.add(indexBox, POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, POSITION_DEFAULT_Y_COORD);
		}
	}
	private String[] splitTaskNameIntoArray(String taskName, Task inputTask, TaskType inputTaskType){
		String[] splitName;
		if (inputTaskType == TaskType.EVENT) {
			splitName = inputTask.getDisplayName(MAX_LENGTH_TASKNAME);
		} else {
			splitName = inputTask.getDisplayName(MAX_LENGTH_TASKNAME);
		}
		
		return splitName;
	}
	private void addTaskNameRectangleToGrid(GridPane grid, TaskType inputTaskType, boolean isTruncatedMode, int numOfColumnsName){
		int currRectYPos = 1;
		Rectangle mainBox = createTaskHolderBox(inputTaskType);
		grid.add(mainBox, POSITION_MAIN_BOX_X_POS, currRectYPos++);
		if (!isTruncatedMode) {
			for (int i=1; i<numOfColumnsName; i++) {
				Rectangle box = createTaskHolderBox(inputTaskType);
				grid.add(box, POSITION_MAIN_BOX_X_POS, currRectYPos++);
			}
		}
	}
	private void setGridConstraints(GridPane grid){
	    grid.setPadding(new Insets(SIZE_PADDING_GRID_H));
	    grid.setHgap(SIZE_PADDING_GRID_H);
	    grid.setVgap(SIZE_PADDING_GRID_V);

	    ColumnConstraints column1 = COLUMN_CONSTRAINTS_ROW1;
	    ColumnConstraints column2 = COLUMN_CONSTRAINTS_ROW2;
	    grid.getColumnConstraints().addAll(column1, column2);
	}
	
	/* Gets a task type and creates an appropriately colored rectangle
	 * @return appropriately colored Rectangle
	 */
	private Rectangle createTaskHolderBox(TaskType inputTaskType) {
		Rectangle box = new Rectangle();
		box.setWidth(SIZE_WIDTH_TASKHOLDERBOX);
		box.setHeight(SIZE_HEIGHT_TASKHOLDERBOX);
		box.setArcWidth(SIZE_ARC_TASKHOLDERBOX);
		box.setArcHeight(SIZE_ARC_TASKHOLDERBOX);                
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(0f);
		switch (inputTaskType) {
		case FLOATING:
			box.setFill(COLORS_ARRAY_TASKBOX[0]);
			break;
		case EVENT:
			box.setFill(COLORS_ARRAY_TASKBOX[1]);
			break;
		case TODO:
			box.setFill(COLORS_ARRAY_TASKBOX[2]);
			break;
		default:
			box.setFill(COLORS_ARRAY_TASKBOX[3]);
			break;
		
		}
		
		return box;
	}
	
	//@return a red box for misc uses
	private Rectangle createTaskHolderBox() {
		Color col = (Color.SALMON);
		Rectangle box = new Rectangle();
		box.setWidth(SIZE_WIDTH_TASKHOLDERBOX);
		box.setHeight(SIZE_HEIGHT_TASKHOLDERBOX);
		box.setArcWidth(SIZE_ARC_TASKHOLDERBOX);
		box.setArcHeight(SIZE_ARC_TASKHOLDERBOX);                
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(0f);
		box.setFill(col);
		return box;
	}
	
	/* Detects date ranges of a Task and creates a GridPane that
	 * holds Rectangles that display the time constraints of the task
	 * @return GridPane with colored boxes that display time constraints of task
	 */
	private GridPane createTimeSpanBoxes(Task inputTask) {
		GridPane timeSpanGridPane = new GridPane();
		timeSpanGridPane.setVgap(SIZE_PADDING_TIMEBOX_V_GAP);
		addStartTimeBox(timeSpanGridPane, inputTask);
		addEndTimeBox(timeSpanGridPane, inputTask);
		addTimespanArrow(timeSpanGridPane, inputTask);
		return timeSpanGridPane;
	}
	private void addTimespanArrow(GridPane timeSpanGridPane, Task inputTask){
		if (inputTask.getStartTime()!=null && inputTask.getEndTime()!=null) {
			Text dash = new Text("▸");
			dash.setFont(Font.font(DEFAULT_FONT_NAME, FontWeight.BOLD, FONTSIZE_TIMESPAN_ARROW));
			dash.setTextAlignment(TextAlignment.LEFT);
			dash.setFill(COLOR_DEFAULT_TIMEBOX_COLOR);
			
			timeSpanGridPane.add(dash, POSITION_DEFAULT_CENTRE_ALIGNED_X_COORD, POSITION_DEFAULT_Y_COORD);
			
		}
	}
	private void addEndTimeBox(GridPane timeSpanGridPane, Task inputTask){
		if (inputTask.getEndTime()!= null) {
			TextedTimeBox timeBox;
			switch (inputTask.getTaskType()) {
			case DEADLINE:
				timeBox = new TextedTimeBox(OFFSET_DEADLINE_INDICATOR + inputTask.getEndTime().toString());
				break;
			default:
				timeBox = new TextedTimeBox(inputTask.getEndTime().toString());
				break;
			}
			timeSpanGridPane.add(timeBox, POSITION_DEFAULT_RIGHT_ALIGNED_X_COORD, POSITION_DEFAULT_Y_COORD);
		} else {
			TextedTimeBox startTimeBox = new TextedTimeBox("");
			timeSpanGridPane.add(startTimeBox, POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, POSITION_DEFAULT_Y_COORD);			
		}
	}
	private void addStartTimeBox(GridPane timeSpanGridPane, Task inputTask){
		if (inputTask.getStartTime()!= null) {
			TextedTimeBox startTimeBox = new TextedTimeBox(inputTask.getStartTime().toString());
			if (inputTask.getEndTime()!= null) {
				timeSpanGridPane.add(startTimeBox, POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, POSITION_DEFAULT_Y_COORD);
			} else {
				timeSpanGridPane.add(startTimeBox, POSITION_DEFAULT_RIGHT_ALIGNED_X_COORD, POSITION_DEFAULT_Y_COORD);
			}
			
		} else {
			TextedTimeBox startTimeBox = new TextedTimeBox("");
			timeSpanGridPane.add(startTimeBox, POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, POSITION_DEFAULT_Y_COORD);
		}
		
	}
	
	/* Gets the task index and its type, and creates an appropriately colored
	 * square with the index displayed in the middle.
	 * @return appropriately colored square with index displayed
	 */
	private StackPane createIndexBox(int index, TaskType inputTaskType) {
		
		StackPane stackPane = new StackPane();
		
		Rectangle box = new Rectangle();
		

		box.setWidth(SIZE_WIDTH_AND_HEIGHT_INDEXBOX);
		box.setHeight(SIZE_WIDTH_AND_HEIGHT_INDEXBOX);
		box.setArcWidth(SIZE_ARC_INDEXBOX);
		box.setArcHeight(SIZE_ARC_INDEXBOX);               
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(THICKNESS_DEFAULT_STROKE_WIDTH);

		switch (inputTaskType) {
		case FLOATING:
			box.setFill(COLORS_ARRAY_INDEXBOX[0]);
			break;
		case EVENT:
			box.setFill(COLORS_ARRAY_INDEXBOX[1]);
			break;
		case TODO:
			box.setFill(COLORS_ARRAY_INDEXBOX[2]);
			break;
		default:
			box.setFill(COLORS_ARRAY_INDEXBOX[3]);
			break;
		
		}
		Text text = new Text (String.valueOf(index));
		
		text.setFont(Font.font(DEFAULT_FONT_NAME, FontWeight.BOLD, 13));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		if (index == 0) {
			text.setText("");
		}
		stackPane.getChildren().addAll(box, text);
		
		return stackPane;
	}
	
	
	/*
	 * Bottom two constructors are for misc uses of TaskBox,
	 * to display non-task items in the same form as a TaskBox.
	 * Will return a RED box, with an indexBox marked '!'.
	 * For the use of OVERDUE TASKS.
	 */
	//created a red '!' indexbox for misc uses
	private StackPane createIndexBox() {
		Color col = (Color.RED);
		
		StackPane stackPane = new StackPane();
		
		Rectangle box = new Rectangle();
		box.setWidth(SIZE_WIDTH_AND_HEIGHT_INDEXBOX);
		box.setHeight(SIZE_WIDTH_AND_HEIGHT_INDEXBOX);
		box.setArcWidth(SIZE_ARC_INDEXBOX);
		box.setArcHeight(SIZE_ARC_INDEXBOX);               
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(THICKNESS_DEFAULT_STROKE_WIDTH);
		box.setFill(col);
		
		Text text = new Text ("!");
		text.setFont(Font.font(DEFAULT_FONT_NAME, FontWeight.BOLD, 13));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		stackPane.getChildren().addAll(box, text);
		
		return stackPane;
	}
	//Constructor for TaskBox for misc uses
	public TaskBox(String inputText) {
		String splitName = inputText;
		int currRectYPos = 1;
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5));
	    grid.setHgap(SIZE_PADDING_GRID_H);
	    grid.setVgap(SIZE_PADDING_GRID_V);

		Rectangle mainBox = createTaskHolderBox();
		grid.add(mainBox, POSITION_DEFAULT_Y_COORD, currRectYPos++);
		
	    ColumnConstraints column1 = COLUMN_CONSTRAINTS_ROW1;
	    ColumnConstraints column2 = COLUMN_CONSTRAINTS_ROW2;
	    grid.getColumnConstraints().addAll(column1, column2);
		
		int currYPos = 1;
		Text text = new Text(OFFSET_TASK_NAME + splitName);
		text.setTextAlignment(TextAlignment.LEFT);
		text.setFont(Font.font(DEFAULT_FONT_NAME, FontWeight.NORMAL, FONTSIZE_TASK_NAME));
		text.setFill(Color.WHITE);
		grid.add(text, POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, currYPos++);
			
		StackPane indexBox = createIndexBox();
		grid.add(indexBox, POSITION_DEFAULT_LEFT_ALIGNED_X_COORD, POSITION_DEFAULT_Y_COORD);
		
		this.getChildren().addAll(grid);
	}
}

