package userinterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import logic.Controller;
import common.*;
 
public class PrettyDisplay extends Application {
	
    Text scenetitle = new Text("Calendar");
	boolean isViewingHelpScreen = false;
    boolean isCalendarHidden = false;
    double currentScrollYPos = 0;
    Controller mainController;
    GridPane calendarGrid;
    GridPane grid2;
    ScrollPane s1;
    TextField userTextField;
    Text actiontarget;
    Scene scene;
    Stage objPrimaryStage;
    HBox hbBtn;
    Color defaultActionTargetColor = Color.BLACK;
    ProgramState programState;
    
    
    public void runApp() {
        launch();
    }
     
    @Override
    public void start(Stage primaryStage) {
 //   	fillPage ("Input command in the field above", primaryStage);
    	objPrimaryStage = primaryStage;
    	mainController = new Controller();
//    	mainController.setGui(this);
    	
    	 primaryStage.setTitle("PinkPanther: The best to-do list");
         

         //Holds all calendar items
         implementCalendarGrid();
         //Holds content of Grid together with grid1
         implementScrollPane();
         //Holds calendar grid and textboxes/buttons below
         implementMainGrid();
         //Holds the user input box
         implementUserTextField();
         //text that displays after-action (e.g added x event)
         implementActionTarget("Input command in the field above");
         //add buttons
     //    implementButtons();
         //Allows keyboard inputs to be read as commands
         implementKeystrokeEvents(primaryStage);
         //Implements the scene
         implementScene();
         
         setStage(primaryStage);
         
    }
    
    void fillPage(String newInput, Stage primaryStage){

        primaryStage.setTitle("PinkPanther: The best to-do list");
        
        //Holds all calendar items
        implementCalendarGrid();
        //Holds content of Grid together with grid1
        implementScrollPane();
        //Holds calendar grid and textboxes/buttons below
        implementMainGrid();
        //Holds the user input box
        implementUserTextField();
        //text that displays after-action (e.g added x event)
        implementActionTarget(newInput);
        //add buttons
        implementButtons();
        //Allows keyboard inputs to be read as commands
        implementKeystrokeEvents(primaryStage);
        //Implements the scene
        implementScene();
        
        setStage(primaryStage);
        
    }

    void implementCalendarGrid(){    
        //Main Grid: Holds contents for the Calendar
        calendarGrid = new GridPane();
        calendarGrid.setAlignment(Pos.TOP_LEFT);
        calendarGrid.setHgap(10);
        calendarGrid.setVgap(10);
        calendarGrid.setPadding(new Insets(10, 10, 10, 10));
        populateGrid(calendarGrid);	
    }
    void implementScrollPane(){
    	s1 = new ScrollPane();
        s1.setPrefSize(1080, 660);
        s1.setContent(calendarGrid);
        s1.setStyle("-fx-background-color: transparent;");
    }
    void implementMainGrid(){
        grid2 = new GridPane();
        grid2.setAlignment(Pos.TOP_LEFT);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(25, 25, 25, 25));
        grid2.add(s1,0,0);
    }
    void implementUserTextField(){
        userTextField = new TextField();
        userTextField.setStyle(""
        + "-fx-font-size: 30px;"
        + "-fx-font-weight: bold;"
        + "-fx-font-family: Tahoma;"
        + "-fx-text-fill: BLACK;"
        + "-fx-border-width: 6px;"
        + "-fx-border-color: DIMGRAY;"
        + "-fx-background-color: PINK");
        grid2.add(userTextField, 0, 1);
    }
    void implementActionTarget(String newInput){
        actiontarget = new Text(newInput);
        grid2.add(actiontarget, 0, 2);
        actiontarget.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        actiontarget.setFill(defaultActionTargetColor);     
    }
    void implementButtons(){
    	Button btn = new Button("Enter");
        btn.getStyleClass().add("button1");
        Button scrollUpButton = new Button("Scroll Up");
        scrollUpButton.getStyleClass().add("button1");
        Button scrollDownButton = new Button("Scroll Down");
        scrollDownButton.getStyleClass().add("button1");
        hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.TOP_RIGHT);
        hbBtn.getChildren().addAll(btn, scrollUpButton, scrollDownButton);
        grid2.add(hbBtn, 0, 2);
        

    	btn.setOnAction(new EventHandler<ActionEvent>() {
       	 
        	@Override
            public void handle(ActionEvent e) {
        		if ((userTextField.getText() != null && !userTextField.getText().isEmpty())) {
        			callControllerToAddCommand();
                } else {
                	displayInvalidInput();
                };
             }
         });

        scrollUpButton.setOnAction(new EventHandler<ActionEvent>() {
       	 
        	@Override
            public void handle(ActionEvent e) {
        		scrollUp(0.2f);
             }
         });
        
        scrollDownButton.setOnAction(new EventHandler<ActionEvent>() {
          	 
        	@Override
            public void handle(ActionEvent e) {
        		scrollDown(0.2f);
             }
         });

    }
    void implementKeystrokeEvents(Stage primaryStage){
        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
            	executeKeypress(ke, primaryStage);
            }
        });
        
    }
    void implementScene(){
        scene = new Scene(grid2, 1080, 650);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(this.getClass().getResource("a.css").toExternalForm());

    }
    void setStage(Stage primaryStage){
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    void populateGrid(GridPane grid){
     
        //Scene title
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 66));
        scenetitle.setFill(Color.DIMGRAY);
        grid.add(scenetitle, 1, 0);

    	programState = mainController.getProgramState();
        
    	setUserTextField(programState.getInputBoxText());
    	
    	
        //filler for rightGridColumn; to make sure length of display does not change
		TransparentRect tRect = new TransparentRect();
		grid.add(tRect, 2, 0);

        //for the actual calendar items
    	int currTaskIndex = 1;

    	int currentYPos = 1;
    	
    	System.out.println(programState.getFloatingList());
    	//for unpacking floatingTasks
    	if (programState != null){
        	ArrayList<Task> floatingTasks = programState.getFloatingList();
	    	if (floatingTasks != null && floatingTasks.size() != 0){
	            TextedColorDayBox daydBox = new TextedColorDayBox("Float");
	        	grid.add(daydBox, 0, currentYPos);
		    	int currFloatXPos = 1;
		    	for (int i=0; i<floatingTasks.size(); i++){
		    		if (currFloatXPos == 3){
	        			currentYPos++;
	        			TransparentCircle circle = new TransparentCircle();
	        			grid.add(circle, 0, currentYPos);
	        			currFloatXPos = 1;
	        		}
		    		
	//	    		String taskName = floatingTasks.get(i).getName();
		    		//TextedTaskBox taskBox = new TextedTaskBox(taskName , "", "", currTaskIndex);
		    		TextedTaskBox taskBox = new TextedTaskBox(i+1, floatingTasks.get(i));
		    		currTaskIndex++;
		    		grid.add(taskBox, currFloatXPos++, currentYPos);
	
		    	}
		    	currentYPos++;
	    	}
	    	
	    	TreeMap<LocalDate,ArrayList<Task>> todoList = programState.getTodoList();
	    	if (todoList != null){
		    	for(LocalDate date:todoList.keySet()){ //looping through dates which have Tasks inside
		    		String month = date.getMonth().toString().substring(0, 3);
		    		String currDayNum = Integer.toString(date.getDayOfMonth());
		            TextedColorDayBox currentDayBox = new TextedColorDayBox(currDayNum + "\n" + month);
		        	grid.add(currentDayBox, 0, currentYPos);
		    		
		    		int currXPos = 1;
		    		currTaskIndex = 1;
		    		
					for(Task task:todoList.get(date)){ //looping through tasks for specified date
						
						if (currXPos == 3){
		        			currentYPos++;
		        			TransparentCircle circle = new TransparentCircle();
		        			grid.add(circle, 0, currentYPos);
		        			currXPos = 1;
		        		}
						
		//	    		String taskName = task.getName();
		//	    		String startTime = task.getStartTimeString();
		 //   			String endTime = task.getEndTimeString();
			    		//TextedTaskBox taskBox = new TextedTaskBox(taskName , startTime, endTime, currTaskIndex);
		    			TextedTaskBox taskBox = new TextedTaskBox(currTaskIndex, task);
		    			currTaskIndex++;
			    		grid.add(taskBox, currXPos++, currentYPos);
		
			    		
					}
					currentYPos++;
				}
		    }
    	}
    }

    void callControllerToAddCommand(){
    	String command = userTextField.getText();
    	actiontarget.setFill(defaultActionTargetColor);                	
    	//actiontarget.setText(command);
    	
    	mainController.addCommand(command);
    	
    	//actiontarget.setText(showFeedBack(programState.getExitState());
        actiontarget.setText(Display.showFeedBack());
    	calendarGrid.getChildren().clear();
        populateGrid(calendarGrid);

    }
    
    public void setUserTextField(String text){
    	if(userTextField!=null){
    		userTextField.setText(text);
    	}
    }
    
    public void clearTextField(){
    	userTextField.clear();
    }
    
    public void setActionResult(String text){
    	actiontarget.setText(text);
    	
    	if (text.equals("Invalid Command")){
    		actiontarget.setFill(Color.FIREBRICK);
    	}
    	else{
    		actiontarget.setFill(defaultActionTargetColor);    		
    	}
    }
    

    
	public String getUserInput() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void displayInvalidInput(){
        actiontarget.setFill(Color.FIREBRICK);
        actiontarget.setText("No input detected!");
	}
	
	public void setMainController(Controller controller){
		this.mainController = controller;
	}
	
	void executeKeypress(KeyEvent ke, Stage stage){
		Stage primaryStage = stage;
		if (ke.getCode().equals(KeyCode.ENTER))
        {
        	if ((userTextField.getText() != null && !userTextField.getText().isEmpty())) {
    			callControllerToAddCommand();
            } else {
            	displayInvalidInput();
            };
        }
        
        else if (ke.getCode().equals(KeyCode.DOWN) && !isCalendarHidden)
        {
    		scrollDown(0.05f);
        }
        else if (ke.getCode().equals(KeyCode.UP) && !isCalendarHidden)
        {
    		scrollUp(0.05f);
        }
        else if (ke.getCode().equals(KeyCode.PAGE_UP) && !isCalendarHidden)
        {
        	if (!isViewingHelpScreen){
        		viewHelpScreen();
        	}
        	else {
        		hideHelpScreen();
        	}
        }
        else if (ke.getCode().equals(KeyCode.PAGE_DOWN))
        {
            primaryStage.setIconified(true);
        }
        else if (ke.getCode().equals(KeyCode.END) && !isViewingHelpScreen)
        {
        	if (isCalendarHidden){
        		unHideCalendar(primaryStage);
        	}
        	else{
        		currentScrollYPos = (s1.getVvalue());
        		hideCalendar(primaryStage);
        	}
        }
		
	}
	
	void scrollUp(double scrollValue){
		s1.setVvalue(s1.getVvalue() - scrollValue);		
	}
	void scrollDown(double scrollValue){
		s1.setVvalue(s1.getVvalue() + scrollValue);		
	}
	
	void viewHelpScreen(){
    	HelpScreen helpScreen = new HelpScreen();
    	grid2.getChildren().remove(s1);
    //	grid2.add(helpScreen, 0, 0);
    	grid2.add(new FirstOpenScreen(), 0, 0);
        actiontarget.setFill(defaultActionTargetColor);
		actiontarget.setText("Viewing cheatsheet: Press PAGEUP key to resume");
    	isViewingHelpScreen = true;
	}
	
	void hideHelpScreen(){
		grid2.getChildren().clear();
		grid2.add(s1,0,0);
        grid2.add(userTextField, 0, 1);
        grid2.add(actiontarget, 0, 2);
    //    grid2.add(hbBtn, 0, 2);
        actiontarget.setFill(defaultActionTargetColor);
        actiontarget.setText(Display.showFeedBack());
		//actiontarget.setText("Input command into the field above");
		isViewingHelpScreen = false;
	}
	
	void unHideCalendar(Stage stage){
        stage.setHeight(890);
        scenetitle.setText("Calendar");
        s1.setDisable(false);
        actiontarget.setFill(defaultActionTargetColor);
        actiontarget.setText(Display.showFeedBack());
		//actiontarget.setText("Input command into the field above");
        isCalendarHidden = false;
 		s1.setVvalue(currentScrollYPos);
	}
	
	void hideCalendar(Stage stage){
        stage.setHeight(390);
        scenetitle.setText("Calendar\n Hidden");
        s1.setDisable(true);
        s1.setVvalue(0);
        actiontarget.setFill(defaultActionTargetColor);
		actiontarget.setText("Hidden calendar mode: Press END to restore calendar");
        isCalendarHidden = true;
	}
	
	//currently not working..
	public void closeWindow(){
		Platform.exit();
	}


}