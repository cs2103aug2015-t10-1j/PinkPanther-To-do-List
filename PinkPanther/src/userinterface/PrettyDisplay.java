package userinterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.SortedMap;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import logic.Controller;
import common.*;
 
public class PrettyDisplay extends Application {
	
    Text scenetitle = new Text(DEFAULT_SCENE_TITLE);
	boolean isViewingHelpScreen = false;
    boolean isCalendarHidden = false;
    double currentScrollYPos = 0;
    Controller mainController;
    GridPane calendarGrid;
    GridPane grid2;
    ScrollPane s1;
    TextField userTextField;
    Scene scene;
    Stage objPrimaryStage;
    HBox hbBtn;
    Color defaultActionTargetColor = Color.BLACK;
    ProgramState programState;
    FlowPane programFeedback;
    
    private static String DEFAULT_SCENE_TITLE = "                      Your Calendar";
    private static String STRING_INVALID_COMMAND = "Unrecognized command. Press PAGE_UP for Help Screen.";
    private static String STRING_DEFAULT_FEEDBACK = "Input command into the field above";
    
    private enum CurrentState {VIEWING_CALENDAR, VIEWING_HELPSCREEN, VIEWING_HIDDEN}
    private CurrentState currentState = CurrentState.VIEWING_CALENDAR;
    
    public static void main(String [ ] args){
    	PrettyDisplay prettyDisplay = new PrettyDisplay();
    	prettyDisplay.runApp();
    }
    
    public void runApp() {
        launch();
    }
    
    @Override
    public void start(Stage primaryStage) {
    	objPrimaryStage = primaryStage;
    	mainController = new Controller();
    	primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream( "PPLogo.png" ))); 
    	
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
         implementActionTarget("Input command in the field above"); // to remove

         setUserFeedback(parseAndColorize("Input command into the field above"));
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
        calendarGrid.setHgap(1);
        calendarGrid.setVgap(1);
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
        grid2.setHgap(1);
        grid2.setVgap(1);
        grid2.setPadding(new Insets(25, 25, 25, 25));
        grid2.add(s1,0,0);
        
    }
    void implementUserTextField(){
        userTextField = new TextField();
        userTextField.setStyle(""
        + "-fx-font-size: 17px;"
        + "-fx-font-weight: bold;"
        + "-fx-font-family: Tahoma;"
        + "-fx-text-fill: BLACK;"
        + "-fx-border-width: 6px;"
        + "-fx-border-color: DIMGRAY;"
        + "-fx-background-color: WHITE");
        grid2.add(userTextField, 0, 1);
    }
    void implementActionTarget(String newInput){
        programFeedback = new FlowPane();
        grid2.add(programFeedback, 0, 2);
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
        scene = new Scene(grid2, 720, 650);
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
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 33));
        scenetitle.setFill(Color.DIMGRAY);
        grid.add(scenetitle, 1, 0);

    	programState = mainController.getProgramState();
        
    	setUserTextField(programState.getInputBoxText());
    	
        //for the actual calendar items
    	int currTaskIndex = 1;

    	int currentYPos = 1;
    	
    	//for unpacking floatingTasks
    	if (programState != null){
        	ArrayList<Task> floatingTasks = programState.getFloatingList();
	    	if (floatingTasks != null && floatingTasks.size() != 0){
	    		int totalFloat = 0;

	    		currentYPos++;
	    		currentYPos++;
		    	int currFloatXPos = 1;
	    		DayBox dayBox = new DayBox("Floating Tasks");
	    		grid.add(dayBox, 1, currentYPos++);
		    	for (int i=0; i<floatingTasks.size(); i++){
		    		totalFloat++;
		    		currentYPos++;
		    		TaskBox taskBox = new TaskBox(i+1, floatingTasks.get(i));
		    		currTaskIndex++;
		    		grid.add(taskBox, currFloatXPos, currentYPos);
	
		    	}

				GridPane miniTaskIndicators = new GridPane();
				miniTaskIndicators.setHgap(10);
				miniTaskIndicators.setVgap(5);
    			IndexBox blankColoredBox = new IndexBox(totalFloat, TaskType.FLOATING);
    			miniTaskIndicators.add(blankColoredBox, 54, 3);
    			grid.add(miniTaskIndicators, 1, 3);
		    	currentYPos++;
	    	}
	    	
	    	SortedMap<LocalDate,ArrayList<Task>> todoList = programState.getTodoList();
	    	if (todoList != null){
		    	for(LocalDate date:todoList.keySet()){ //looping through dates which have Tasks inside
		    		int totalDeadline = 0, totalEvent = 0, totalTodo = 0;
		    		
		    		grid.add(new TransparentCircle(), 1, currentYPos++);
		    		String month = date.getMonth().toString().substring(0, 3);
		    		String currDayNum = Integer.toString(date.getDayOfMonth());
		    		int currDayXPos = 1;
		    		DayBox dayBox = new DayBox(currDayNum + " " + month);
		    		int dayBoxYPos = currentYPos;
		    		grid.add(dayBox, currDayXPos++, dayBoxYPos);
		    		currentYPos ++;
		    		int currXPos = 1;
		    		currTaskIndex = 1;
		    		
		    		
		    		
					for(Task task:todoList.get(date)){ //looping through tasks for specified date
						switch(task.getTaskType()){
							case DEADLINE:
								totalDeadline++;
								break;
							case TODO:
								totalTodo++;
								break;
							default:
								totalEvent++;
								break;
						}
						if (task.getClash()){
							IndexBox testBox = new IndexBox(0);
							grid.add(testBox, currXPos+14, currentYPos+1);
						}
						
			    		currentYPos++;
		    			TaskBox taskBox = new TaskBox(currTaskIndex, task);
		    			currTaskIndex++;
			    		grid.add(taskBox, currXPos, currentYPos);
		
			    		
					}
					
					
					GridPane miniTaskIndicators = new GridPane();
					miniTaskIndicators.setHgap(10);
					miniTaskIndicators.setVgap(5);
					currDayXPos = 54;
		    		for (int i=0; i<3; i++){
		    			if (i==0 && totalDeadline != 0){
			    			IndexBox blankColoredBox = new IndexBox(totalDeadline, TaskType.DEADLINE);
			    			miniTaskIndicators.add(blankColoredBox, currDayXPos--, 3);
		    			} else if (i==1 && totalTodo != 0){
			    			IndexBox blankColoredBox = new IndexBox(totalTodo, TaskType.TODO);
			    			miniTaskIndicators.add(blankColoredBox, currDayXPos--, 3);
		    			} else if (i==2 && totalEvent != 0){
			    			IndexBox blankColoredBox = new IndexBox(totalEvent, TaskType.EVENT);
			    			miniTaskIndicators.add(blankColoredBox, currDayXPos--, 3);
		    			}
		    			
		    		}
		    		grid.add(miniTaskIndicators, 1, dayBoxYPos);
					
		    		
					currentYPos++;
				}

		    	
		    }
    	}
    }

    void callControllerToAddCommand(){
    	String command = userTextField.getText();
    	mainController.addCommand(command);
    	setUserFeedback();
    	calendarGrid.getChildren().clear();
        populateGrid(calendarGrid);
        if(mainController.getProgramState().getExitState()){
        	closeWindow();
        }

    }
    
    FlowPane parseAndColorize(String text){
    	if (text.equals("INVALID")){
    		text = STRING_INVALID_COMMAND;
    	}
    	ConsoleInputColorizer colorizer = new ConsoleInputColorizer();
    	FlowPane colorizedText = colorizer.parseInputToArray(text);
    	return colorizedText;
    }
    
    void setUserFeedback(FlowPane feedback){
    	grid2.getChildren().remove(2);
    	grid2.add(feedback, 0, 2);
    }
    void setUserFeedbackFromHelpScreen(FlowPane feedback){
   // 	grid2.getChildren().remove(2);
    	grid2.add(feedback, 0, 2);
    }
    
    public void setUserTextField(String text){
    	if(userTextField!=null){
    		userTextField.setText(text);
    	}
    }
    
    void setUserFeedback(){
    	FlowPane feedback = parseAndColorize(Display.showFeedBack());
    	grid2.getChildren().remove(2);
    	grid2.add(feedback, 0, 2);
    }
    
    public void clearTextField(){
    	userTextField.clear();
    }

	public void setMainController(Controller controller){
		this.mainController = controller;
	}
	
	private void displayInvalidInput(){
    	setUserFeedback(parseAndColorize(STRING_INVALID_COMMAND));
	}
	
	void executeKeypress(KeyEvent ke, Stage stage){
		Stage primaryStage = stage;
		
		if (ke.getCode().equals(KeyCode.ENTER)) {
			processUserEnter();
        }
        
        else if (ke.getCode().equals(KeyCode.DOWN) && currentState == CurrentState.VIEWING_CALENDAR) {
    		scrollDown(0.25f);
        }
        else if (ke.getCode().equals(KeyCode.UP) && currentState == CurrentState.VIEWING_CALENDAR) {
    		scrollUp(0.25f);
        }
        else if (ke.getCode().equals(KeyCode.PAGE_UP)) {
        	attemptToggleHelpScreenView();
        }
        else if (ke.getCode().equals(KeyCode.PAGE_DOWN)) {
        	//minimize program to tray
            primaryStage.setIconified(true);
        }
        else if (ke.getCode().equals(KeyCode.END)) {
        	attemptToggleCalendarHiddenMode(stage);
        } 
        else if (ke.getCode().equals(KeyCode.ESCAPE)){
        	//do nothing
        }
        else if (currentState != CurrentState.VIEWING_HELPSCREEN){ //currently a hack to fix some bug
        	//for all other non-reserved keystrokes
        	processNonReservedKeys(ke);
        }
		
	}
	
	void processNonReservedKeys(KeyEvent ke){
    	String userText = userTextField.getText();
    	
    	if (ke.getCode().isLetterKey()){
    		userText = userText + ke.getCode().toString().toLowerCase() + " ";
    	}
    	
    	else if (ke.getCode().equals(KeyCode.BACK_SPACE)){
    		if (userText.length() <= 1) {
    			userText = STRING_DEFAULT_FEEDBACK;
    		} else {
    			userText = userText.substring(0, userText.length()-1);
    		}
    	}
    	
    	FlowPane colorizedText = parseAndColorize(userText);
    	setUserFeedback(colorizedText);
	}
	
	void processUserEnter(){
    	if ((userTextField.getText() != null && !userTextField.getText().isEmpty())) {
			callControllerToAddCommand();
        } else {
        	displayInvalidInput();
        }
	}
	
	void scrollUp(double scrollValue){
		s1.setVvalue(s1.getVvalue() - scrollValue);		
	}
	void scrollDown(double scrollValue){
		s1.setVvalue(s1.getVvalue() + scrollValue);		
	}
	
	void attemptToggleHelpScreenView(){
		if (currentState != CurrentState.VIEWING_HIDDEN) {
			if (currentState == CurrentState.VIEWING_CALENDAR) {
				viewHelpScreen();
			} else if (currentState == CurrentState.VIEWING_HELPSCREEN) {
				hideHelpScreen();
			}
		}
	}
	
	void viewHelpScreen(){
    	HelpScreen helpScreen = new HelpScreen();
    	grid2.getChildren().remove(s1);
    	grid2.add(helpScreen, 0, 0);
    	isViewingHelpScreen = true;
    	isCalendarHidden = true;
		currentState = CurrentState.VIEWING_HELPSCREEN;
	}
	
	void hideHelpScreen(){
		grid2.getChildren().clear();
		grid2.add(s1,0,0);
        grid2.add(userTextField, 0, 1);
        setUserFeedbackFromHelpScreen(parseAndColorize("Input command into the field above"));
		isViewingHelpScreen = false;
		currentState = CurrentState.VIEWING_CALENDAR;
	}
	
	void unHideCalendar(Stage stage){
        stage.setHeight(690);
        scenetitle.setText(DEFAULT_SCENE_TITLE);
        s1.setDisable(false);
        isCalendarHidden = false;
 		s1.setVvalue(currentScrollYPos);
		currentState = CurrentState.VIEWING_CALENDAR;
	}
	
	void hideCalendar(Stage stage){
		if (currentState != CurrentState.VIEWING_HELPSCREEN){
	    stage.setHeight(200);
	    scenetitle.setText("   Crouching Tiger; Hidden Calendar");
	    s1.setDisable(true);
	    s1.setVvalue(0);
	    isCalendarHidden = true;
		currentState = CurrentState.VIEWING_HIDDEN;
		}
	}
	
	void attemptToggleCalendarHiddenMode(Stage stage){
		if (currentState != CurrentState.VIEWING_HELPSCREEN){
			if (currentState == CurrentState.VIEWING_CALENDAR){
				hideCalendar(stage);
			} else if (currentState == CurrentState.VIEWING_HIDDEN){
				unHideCalendar(stage);
			}
		}
	}
	
	public void closeWindow(){
		Platform.exit();
	}


}