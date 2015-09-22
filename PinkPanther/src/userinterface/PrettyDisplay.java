package userinterface;

import java.io.File;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
 
public class PrettyDisplay extends Application {
	boolean isViewingHelpScreen = false;
	
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(Stage primaryStage) {
    	fillPage ("Add <EventName> || Delete <EventIndex> || ");
    }
    
    void fillPage(String newInput){
    	Stage primaryStage = new Stage();    	
        primaryStage.setTitle("PinkPanther: The best to-do list");
        
        //Main Grid: Holds contents for the Calendar
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        populateGrid(grid);
        
        /*
        //Holds main Grid
        GridPane grid1 = new GridPane();
        grid1.setAlignment(Pos.TOP_LEFT);
        grid1.setHgap(10);
        grid1.setVgap(10);
        grid1.setPadding(new Insets(25, 25, 25, 25));
        */
        
        //Holds content of Grid together with grid1
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(1080, 660);
        s1.setContent(grid);
        s1.setStyle("-fx-background-color: transparent;");
        
        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.TOP_LEFT);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(25, 25, 25, 25));

      //  grid1.add(grid, 0, 0);
        grid2.add(s1,0,0);
        
        TextField userTextField = new TextField("Input Command");
        userTextField.setStyle(""
        + "-fx-font-size: 30px;"
        + "-fx-font-weight: bold;"
        + "-fx-font-family: Tahoma;"
        + "-fx-text-fill: DIMGRAY;"
        + "-fx-border-width: 6px;"
        + "-fx-border-color: DIMGRAY;"
        + "-fx-background-color: PINK");
        grid2.add(userTextField, 0, 1);
        
        //text that displays after-action (e.g added x event)
        Text actiontarget = new Text(newInput);
        grid2.add(actiontarget, 0, 2);
        actiontarget.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        actiontarget.setFill(Color.WHITE);      
        
        //add button
        Button btn = new Button("Enter");
        btn.getStyleClass().add("button1");
        Button scrollUpButton = new Button("Scroll Up");
        scrollUpButton.getStyleClass().add("button1");
        Button scrollDownButton = new Button("Scroll Down");
        scrollDownButton.getStyleClass().add("button1");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.TOP_RIGHT);
        hbBtn.getChildren().addAll(btn, scrollUpButton, scrollDownButton);
        grid2.add(hbBtn, 0, 2);

        Scene scene = new Scene(grid2, 1080, 850);
       
        
 
    	btn.setOnAction(new EventHandler<ActionEvent>() {
       	 
        	@Override
            public void handle(ActionEvent e) {
        		if ((userTextField.getText() != null && !userTextField.getText().isEmpty())) {
                    actiontarget.setFill(Color.WHITE);                	
                	actiontarget.setText(userTextField.getText());
                	userTextField.clear();
            		grid.getChildren().clear();
                    populateGrid(grid);
                	
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("No input detected!");
                };
             }
         });

        scrollUpButton.setOnAction(new EventHandler<ActionEvent>() {
       	 
        	@Override
            public void handle(ActionEvent e) {
        		s1.setVvalue(s1.getVvalue() - 0.2f);
             }
         });
        
        scrollDownButton.setOnAction(new EventHandler<ActionEvent>() {
          	 
        	@Override
            public void handle(ActionEvent e) {
        		s1.setVvalue(s1.getVvalue() + 0.2f);
             }
         });
        
        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	if ((userTextField.getText() != null && !userTextField.getText().isEmpty())) {
                        actiontarget.setFill(Color.WHITE);                	
                    	actiontarget.setText(userTextField.getText());
                		grid.getChildren().clear();
                    	userTextField.clear();
                        populateGrid(grid);
                    	
                    } else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("No input detected!");
                    };
                }
                
                else if (ke.getCode().equals(KeyCode.DOWN))
                {
            		s1.setVvalue(s1.getVvalue() + 0.05f);
                }
                else if (ke.getCode().equals(KeyCode.UP))
                {
            		s1.setVvalue(s1.getVvalue() - 0.05f);
                }
                else if (ke.getCode().equals(KeyCode.RIGHT))
                {
                	if (!isViewingHelpScreen){
	                	HelpScreen helpScreen = new HelpScreen();
	                	grid2.getChildren().remove(s1);
	                	grid2.add(helpScreen, 0, 0);
	                	isViewingHelpScreen = true;
                	}
                	else {
                		grid2.getChildren().clear();
                		grid2.add(s1,0,0);
                        grid2.add(userTextField, 0, 1);
                        grid2.add(actiontarget, 0, 2);
                        grid2.add(hbBtn, 0, 2);
                		isViewingHelpScreen = false;
                	}
                }
            }
        });
        
        
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        scene.getStylesheets().clear();
        scene.getStylesheets().add(HelloWorld.class.getResource("a.css").toExternalForm());
      
        primaryStage.show();
        
    }
    
    void populateGrid(GridPane grid){
    	int currentYPos = 1;
        //Scene title
        Text scenetitle = new Text("Calendar");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 66));
        scenetitle.setFill(Color.DIMGRAY);
        grid.add(scenetitle, 1, 0);
        
		
		TransparentRect tRect = new TransparentRect();
		grid.add(tRect, 2, 0);

        //for the actual calendar items
        TextedColorDayBox daydBox = new TextedColorDayBox("Float");
    	grid.add(daydBox, 0, currentYPos++);
    	
    	for (int i=1; i<14; i++){
        	Random ran = new Random();
        	int randomNumTasks = ran.nextInt(5);
        	if (randomNumTasks!=0){
	        	TextedColorDayBox dayBox = new TextedColorDayBox("Day\n"+ i);
	        	grid.add(dayBox, 0, currentYPos);
	
	
	    			int currentXPos = 1;
		        	for (int j=0; j<randomNumTasks; j++){
		        		if(j==2){
		        			currentYPos++;
		        			TransparentCircle circle = new TransparentCircle();
		        			grid.add(circle, 0, currentYPos);
		    	        	currentXPos = 1;
		        		}
		        		TextedTaskBox taskBox = new TextedTaskBox("Meetin with boss at meeting roomdddddd" , "08:00pm", "09:00pm");
		        		grid.add(taskBox, currentXPos++, currentYPos);
		            }
		        	currentYPos++;
		        	
        	}
        	System.out.println(i + " " + currentYPos);
        	if (i == 5 && currentYPos < 8){
        		for (int k=currentYPos; k<7; k++){
        			System.out.println("extra circle added at " + k);
        			TransparentCircle circle = new TransparentCircle();
        			grid.add(circle, 0, k);
        		}
        	
        	}
        }
    }
}