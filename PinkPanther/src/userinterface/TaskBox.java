/* @@author Baron */
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
	public TaskBox(int index, Task inputTask, boolean isTruncatedMode){
		TaskType inputTaskType = inputTask.getTaskType();
		
		String[] splitName;
		if (inputTaskType == TaskType.EVENT){
			splitName = inputTask.getDisplayName(45);
		} else {
			splitName = inputTask.getDisplayName(45);
		}
	//    System.out.println(splitName);
		int numOfColumnsName = splitName.length;
		int currRectYPos = 1;
		
		GridPane grid = new GridPane();

		Rectangle mainBox = createTaskHolderBox(inputTaskType);
		grid.add(mainBox, 1, currRectYPos++);
		if (!isTruncatedMode){
			for (int i=1; i<numOfColumnsName; i++){
				Rectangle box = createTaskHolderBox(inputTaskType);
				grid.add(box, 1, currRectYPos++);
			}
		}
		
	//	grid.setGridLinesVisible(true);
	    grid.setPadding(new Insets(5));
	    grid.setHgap(5);
	    grid.setVgap(0);

	    ColumnConstraints column1 = new ColumnConstraints(5);
	    ColumnConstraints column2 = new ColumnConstraints(50, 150, 100);
	    grid.getColumnConstraints().addAll(column1, column2);
		
	    
		int currYPos = 1;
		if (isTruncatedMode){
			Text text = new Text("     " + splitName[0]);
			if (numOfColumnsName > 1){
				text.setText(text.getText() + "...");
			}
			text.setTextAlignment(TextAlignment.LEFT);
			text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			text.setFill(Color.web("212121"));
			grid.add(text, 0, currYPos++);
		} else {
			for (int i=0; i<numOfColumnsName; i++){
				Text text = new Text("     " + splitName[i]);
				text.setTextAlignment(TextAlignment.LEFT);
				text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
				text.setFill(Color.web("212121"));
				grid.add(text, 0, currYPos++);
			}
		}
//		Text text = new Text("     " + inputTask.getName());
//		text.setTextAlignment(TextAlignment.LEFT);
//		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//		text.setFill(Color.BLACK);
//		grid.add(text, 0, 1);

		GridPane timeSpanBoxes = createTimeSpanBoxes(inputTask);
		
		if (numOfColumnsName > 1 && inputTask.getTaskType() != TaskType.FLOATING){
			Rectangle box = createTaskHolderBox(inputTaskType);
			grid.add(box, 1, currRectYPos++);
			grid.add(timeSpanBoxes, 65, currRectYPos -1);
			
		} else {
			grid.add(timeSpanBoxes, 65, 1);
		}
		
		
		
		if (index != 0){
		//	IndexBox indexBox = new IndexBox(index);
			StackPane indexBox = createIndexBox(index, inputTaskType);
			GridPane.setConstraints(indexBox , 0, 1);
			grid.add(indexBox, 0, 1);
		}
		
		
//		grid.setGridLinesVisible(true);
		this.getChildren().addAll(grid);
		
	}
	
	private Rectangle createTaskHolderBox(TaskType inputTaskType){
		Color[] col = {(Color.web("EFBAFF")), (Color.web("C1D5F8")), 
				(Color.web("B9EEB4")), (Color.web("EEBCB0")), (Color.POWDERBLUE)};
		Rectangle box = new Rectangle();
		box.setWidth(600);
		box.setHeight(40);
		box.setArcWidth(4);
		box.setArcHeight(4);                
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(0f);
		switch (inputTaskType){
		case FLOATING:
			box.setFill(col[0]);
			break;
		case EVENT:
			box.setFill(col[1]);
			break;
		case TODO:
			box.setFill(col[2]);
			break;
		default:
			box.setFill(col[3]);
			break;
		
		}
		
		return box;
	}
	
	//returns a red box
	private Rectangle createTaskHolderBox(){
		Color col = (Color.SALMON);
		Rectangle box = new Rectangle();
		box.setWidth(600);
		box.setHeight(40);
		box.setArcWidth(4);
		box.setArcHeight(4);                
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(0f);
		box.setFill(col);
		return box;
	}
	
	private GridPane createTimeSpanBoxes(Task inputTask){
		GridPane timeSpanGridPane = new GridPane();
		timeSpanGridPane.setVgap(10);
		
		
		if (inputTask.getStartTime()!= null){
			TextedTimeBox startTimeBox = new TextedTimeBox(inputTask.getStartTime().toString());
			if (inputTask.getEndTime()!= null){
				timeSpanGridPane.add(startTimeBox, 0, 1);
			} else {
				timeSpanGridPane.add(startTimeBox, 2, 1);
			}
			
		} else {
			TextedTimeBox startTimeBox = new TextedTimeBox("");
			timeSpanGridPane.add(startTimeBox, 0, 1);
		}
		
		
		
		if (inputTask.getEndTime()!= null){
			TextedTimeBox timeBox;
			switch (inputTask.getTaskType()){
			case DEADLINE:
				timeBox = new TextedTimeBox("by " + inputTask.getEndTime().toString());
				break;
			default:
				timeBox = new TextedTimeBox(inputTask.getEndTime().toString());
				break;
			}
			timeSpanGridPane.add(timeBox, 2, 1);
		} else {
			TextedTimeBox startTimeBox = new TextedTimeBox("");
			timeSpanGridPane.add(startTimeBox, 0, 1);			
		}
		
		if (inputTask.getStartTime()!=null && inputTask.getEndTime()!=null){
			Text dash = new Text("-");
			dash.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
			dash.setTextAlignment(TextAlignment.LEFT);
			dash.setFill(Color.DIMGRAY);
			
			timeSpanGridPane.add(dash, 1, 1);
			
		}
//		timeSpanGridPane.setGridLinesVisible(true);
		return timeSpanGridPane;
	}
	
	private StackPane createIndexBox(int index, TaskType inputTaskType){
		Color[] col = {(Color.web("D652FF")), Color.web("6495ED"), 
				(Color.web("51D444")), (Color.web("D4573A")), (Color.RED)};
		
		StackPane stackPane = new StackPane();
		
		Rectangle box = new Rectangle();
		

		box.setWidth(20);
		box.setHeight(20);
		box.setArcWidth(4);
		box.setArcHeight(4);               
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(2.0f);

		switch (inputTaskType){
		case FLOATING:
			box.setFill(col[0]);
			break;
		case EVENT:
			box.setFill(col[1]);
			break;
		case TODO:
			box.setFill(col[2]);
			break;
		default:
			box.setFill(col[3]);
			break;
		
		}
		Text text = new Text (String.valueOf(index));
		
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		if (index==0){
			text.setText("");
		}
		stackPane.getChildren().addAll(box, text);
		
		return stackPane;
	}
	
	//created a red '!' indexbox
	private StackPane createIndexBox(){
		Color col = (Color.RED);
		
		StackPane stackPane = new StackPane();
		
		Rectangle box = new Rectangle();
		

		box.setWidth(20);
		box.setHeight(20);
		box.setArcWidth(4);
		box.setArcHeight(4);               
		box.setStroke(Color.DIMGRAY);
		box.setStrokeWidth(2.0f);
		box.setFill(col);
		
		Text text = new Text ("!");
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		
		stackPane.getChildren().addAll(box, text);
		
		return stackPane;
	}
	
	
	public TaskBox(String inputText){
		String splitName = inputText;
	//    System.out.println(splitName);
		int currRectYPos = 1;
		
		GridPane grid = new GridPane();

		Rectangle mainBox = createTaskHolderBox();
		grid.add(mainBox, 1, currRectYPos++);
		
		
	//	grid.setGridLinesVisible(true);
	    grid.setPadding(new Insets(5));
	    grid.setHgap(5);
	    grid.setVgap(0);

	    ColumnConstraints column1 = new ColumnConstraints(5);
	    ColumnConstraints column2 = new ColumnConstraints(50, 150, 100);
	    grid.getColumnConstraints().addAll(column1, column2);
		
	    
		int currYPos = 1;
		Text text = new Text("     " + splitName);
		text.setTextAlignment(TextAlignment.LEFT);
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		text.setFill(Color.WHITE);
		grid.add(text, 0, currYPos++);
			
		
		StackPane indexBox = createIndexBox();
		GridPane.setConstraints(indexBox , 0, 1);
		grid.add(indexBox, 0, 1);
		
		
		
//		grid.setGridLinesVisible(true);
		this.getChildren().addAll(grid);
		
	}
}

