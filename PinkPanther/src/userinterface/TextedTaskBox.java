package userinterface;

import common.Task;
import common.TaskType;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TextedTaskBox extends StackPane{
	public TextedTaskBox(String name, String startTime, String endTime, int index){
		ColoredTextBox box = new ColoredTextBox();
	
		GridPane grid = new GridPane();
	//	grid.setGridLinesVisible(true);
	    grid.setPadding(new Insets(5));
	    grid.setHgap(5);
	    grid.setVgap(5);

	    ColumnConstraints column1 = new ColumnConstraints(5);
	    ColumnConstraints column2 = new ColumnConstraints(50, 150, 100);
	    grid.getColumnConstraints().addAll(column1, column2);
		
		Text text = new Text("     " + name);
		text.setTextAlignment(TextAlignment.LEFT);
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		text.setFill(Color.BLACK);

		GridPane.setConstraints(text, 0, 2);
		grid.add(text, 0, 2);
		
		
		if (index != 0){
			IndexBox indexBox = new IndexBox(index);
			GridPane.setConstraints(indexBox , 0, 3);
			grid.add(indexBox, 0, 3);
		}
		
		
		if (!startTime.equals("")){
			TextedTimeBox startTimeBox = new TextedTimeBox(startTime);
			GridPane.setConstraints(startTimeBox , 4, 3);
			grid.add(startTimeBox, 4, 3);
		}
		
		if (!endTime.equals("")){
			TextedTimeBox timeBox = new TextedTimeBox(endTime);
	
			GridPane.setConstraints(timeBox , 10, 3);
			grid.add(timeBox, 10, 3);
		}
		
		if (!startTime.equals("") && !endTime.equals("")){
			Text dash = new Text("-");
			text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			text.setTextAlignment(TextAlignment.LEFT);
			text.setFill(Color.BLACK);

			GridPane.setConstraints(dash, 7, 3);
			grid.add(dash, 7, 3);
		}
		
		this.getChildren().addAll(box, grid);
		
	}
	
	
	public TextedTaskBox(int index, Task inputTask){
		TaskType inputTaskType = inputTask.getTaskType();
		
		ColoredTextBox box = new ColoredTextBox();
		
		switch (inputTaskType){
		case FLOATING:
			box.setColor(0);
			break;
		case EVENT:
			box.setColor(1);
			break;
		case TODO:
			box.setColor(2);
			break;
		default:
			box.setColor(3);
			break;
		
		}
		
		GridPane grid = new GridPane();
	//	grid.setGridLinesVisible(true);
	    grid.setPadding(new Insets(5));
	    grid.setHgap(5);
	    grid.setVgap(5);

	    ColumnConstraints column1 = new ColumnConstraints(5);
	    ColumnConstraints column2 = new ColumnConstraints(50, 150, 100);
	    grid.getColumnConstraints().addAll(column1, column2);
		
		Text text = new Text("     " + inputTask.getName());
		text.setTextAlignment(TextAlignment.LEFT);
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		text.setFill(Color.BLACK);

		GridPane.setConstraints(text, 0, 2);
		grid.add(text, 0, 2);
		
		
		if (index != 0){
			IndexBox indexBox = new IndexBox(index);
			GridPane.setConstraints(indexBox , 0, 3);
			grid.add(indexBox, 0, 3);
		}
		
		if (inputTask.getStartTime()!= null){
			TextedTimeBox startTimeBox = new TextedTimeBox(inputTask.getStartTime().toString());
			GridPane.setConstraints(startTimeBox , 4, 3);
			grid.add(startTimeBox, 4, 3);
		}
		
		if (inputTask.getEndTime()!= null){
			TextedTimeBox timeBox = new TextedTimeBox(inputTask.getEndTime().toString());
	
			GridPane.setConstraints(timeBox , 10, 3);
			grid.add(timeBox, 10, 3);
		}
		
		if (inputTask.getStartTime()!=null && inputTask.getEndTime()!=null){
			Text dash = new Text("-");
			text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			text.setTextAlignment(TextAlignment.LEFT);
			text.setFill(Color.BLACK);

			GridPane.setConstraints(dash, 7, 3);
			grid.add(dash, 7, 3);
		}
		
		this.getChildren().addAll(box, grid);
		
	}
}