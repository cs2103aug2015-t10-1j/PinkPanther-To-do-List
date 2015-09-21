package userinterface;

import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
 
public class HelloWorld extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    /*
    @Override
    public void start(Stage primaryStage) {
    	
    	ScrollPane sp = new ScrollPane();
    	
    	
        primaryStage.setTitle("PinkPanther: The best to-do list");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        sp.setContent(grid);
        

        
        //Input command text label and box
        int commandBoxYPos = 1;
        Label command = new Label("Input Command:");
        grid.add(command, 0, commandBoxYPos);
        TextField userTextField = new TextField("Input Command");
        grid.add(userTextField, 1, commandBoxYPos,3,1);
        
        //text that displays after-action (e.g added x event)
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, commandBoxYPos+1);
        
        //add button
        Button btn = new Button("Enter");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 5, commandBoxYPos);
        
        
        //Scene title
        Text scenetitle = new Text("PinkPanther Calendar");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        
        //for the actual calendar items
        TextedColorDayBox daydBox = new TextedColorDayBox("Float");
    	grid.add(daydBox, 0, 3);
        
        for (int i=4; i<20; i++){
        	TextedColorDayBox dayBox = new TextedColorDayBox("3"+ i);
        	grid.add(dayBox, 0, i);
        	
        	Random ran = new Random();
        	int randomNumTasks = ran.nextInt(4)+1;
        	for (int j=1; j<randomNumTasks; j++){
        		TextedTaskBox taskBox = new TextedTaskBox("Taskname here " + j , "12pm");
        		grid.add(taskBox, j, i);
            }
        	
        }
        
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
       	 
        	@Override
            public void handle(ActionEvent e) {
                if ((userTextField.getText() != null && !userTextField.getText().isEmpty())) {
                    actiontarget.setFill(Color.BLUE);                	
                	actiontarget.setText(userTextField.getText());
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("No input detected!");
                }
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
                        actiontarget.setFill(Color.BLUE);                	
                    	actiontarget.setText(userTextField.getText());
                    	fillPage(userTextField.getText());
                    	primaryStage.close();
                    } else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("No input detected!");
                    };
                }
                
                else if (ke.getCode().equals(KeyCode.KP_RIGHT))
                {
                	fillPage(userTextField.getText());
                }
            }
        });
        
        
        
        Scene scene = new Scene(sp, 1080, 800);
       
        primaryStage.setScene(scene);

        scene.setFill(Color.RED);
      //  scene.getStylesheets().add(this.getClass().getResource("pinkpanther.css").toExternalForm());
    //    scene.getStylesheets().add("a.css");

        System.out.println(this.getClass().getResource("TextedColorDayBox.java"));
        
        primaryStage.show();
    }
    
    
    */
    
    

    @Override
    public void start(Stage primaryStage) {
    	fillPage ("Input Command");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    void fillPage(String newInput){
    	Stage primaryStage = new Stage();
    	System.out.println("new scene");
    	
    	ScrollPane sp = new ScrollPane();
    	
        primaryStage.setTitle("PinkPanther: The best to-do list");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        sp.setContent(grid);
        
        
        //Input command text label and box
        int commandBoxYPos = 1;
        Label command = new Label("Input Command:");
        grid.add(command, 0, commandBoxYPos);
        TextField userTextField = new TextField("Input Command");
        grid.add(userTextField, 1, commandBoxYPos,3,1);
        String lastInput = "";
        
        //text that displays after-action (e.g added x event)
        final Text actiontarget = new Text(newInput);
        grid.add(actiontarget, 1, commandBoxYPos+1);
        
        //add button
        Button btn = new Button("Enter");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 5, commandBoxYPos);
        
        
        //Scene title
        Text scenetitle = new Text("PinkPanther Calendar");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);


        Scene scene = new Scene(sp, 1080, 800);

        
        //for the actual calendar items
        TextedColorDayBox daydBox = new TextedColorDayBox("Float");
    	grid.add(daydBox, 0, 3);
        
        for (int i=4; i<20; i++){
        	TextedColorDayBox dayBox = new TextedColorDayBox("3"+ i);
        	grid.add(dayBox, 0, i);
        	
        	Random ran = new Random();
        	int randomNumTasks = ran.nextInt(4)+1;
        	for (int j=1; j<randomNumTasks; j++){
        		TextedTaskBox taskBox = new TextedTaskBox("Taskname here " + j , "12pm");
        		grid.add(taskBox, j, i);
            }
        	
        }
        
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
       	 
        	@Override
            public void handle(ActionEvent e) {
                if ((userTextField.getText() != null && !userTextField.getText().isEmpty())) {
                    actiontarget.setFill(Color.BLUE);                	
                	actiontarget.setText(userTextField.getText());
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("No input detected!");
                }
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
                        actiontarget.setFill(Color.BLUE);                	
                    	actiontarget.setText(userTextField.getText());
                    	//fillPage(userTextField.getText());
                    	//primaryStage.close();
                    	
                        primaryStage.setScene(new Scene(sp, 1080, 800));
                    } else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("No input detected!");
                    };
                }
                
                else if (ke.getCode().equals(KeyCode.KP_RIGHT))
                {
                	fillPage(userTextField.getText());
                }
            }
        });
        
        
        
       
        primaryStage.setScene(scene);

        scene.setFill(Color.RED);
      //  scene.getStylesheets().add(this.getClass().getResource("pinkpanther.css").toExternalForm());
    //    scene.getStylesheets().add("a.css");

       // System.out.println(this.getClass().getResource("TextedColorDayBox.java"));
        
       primaryStage.show();
        
    }
}



