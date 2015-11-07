/* @@author Baron */
package userinterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.SortedMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
import java.util.logging.*;
 
public class PrettyDisplay extends Application {
	
	//Variables to help track current state of GUI
    private double currentScrollYPos = 0;
    private boolean isTruncatedMode = false;
    private int currentStageHeight = DEFAULT_STAGE_HEIGHT;
    
    //Instantiated objects that GUI uses and needs to point to at various instances
	Text scenetitle = new Text(DEFAULT_SCENE_TITLE);
    Controller mainController;
    GridPane calendarGrid;
    GridPane programMainGrid;
    ScrollPane calendarScrollPane;
    TextField userTextField;
    Scene scene;
    Stage objPrimaryStage;
    ProgramState programState;
    FlowPane programFeedback;
    HelpScreen helpScreen = new HelpScreen();
    
    //Default strings used in various instances
    private static String DEFAULT_SCENE_TITLE = "                         To-Do List";
    private static String STRING_INVALID_COMMAND = "Unrecognized command. Press PAGE_UP for Help Screen.";
    private static String STRING_DEFAULT_FEEDBACK = "Input command into the field below";
    
    //Default values for objects' positions in program window
    private static int DEFAULT_STAGE_HEIGHT = 1060;
    private static int DEFAULT_USER_INPUT_YPOS = 5;
    private static int DEFAULT_USER_FEEDBACK_YPOS = 2;
    
    //Enumerator and state to note which state the program is in
    private enum CurrentState {VIEWING_CALENDAR, VIEWING_HELPSCREEN, VIEWING_HIDDEN}
    private CurrentState currentState = CurrentState.VIEWING_CALENDAR;
    private static Logger logger = Logger.getLogger("PrettyDisplay");
    
    public static void main(String [] args) {
    	launch();
    }
    
    /*
     * start() initiates the whole program at runtime. It creates the controller class,
     * which then instantiates all other objects the program needs
     */
    @Override
    public void start(Stage primaryStage) {
    	setProgramHeightToUserScreen();
    	objPrimaryStage = primaryStage;
    	mainController = new Controller();
//    	primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("PPLogo.png")));
    	primaryStage.setTitle("PinkPanther: The best to-do list");
    	Image logoImage = new Image("PPLogo.png");
    	primaryStage.getIcons().add(logoImage);
        //Holds all calendar items
        implementCalendarGrid();
        //Holds content of Grid together with grid1
        implementScrollPane();
        //Holds calendar grid and textboxes/buttons below
        implementMainGrid();
        //Holds the user input box
        implementUserTextField();
        //Text that displays after-action (e.g added x event)
        implementUserFeedback("Input command in the field above"); // to remove
        //Set feedback to default string
        setUserFeedback(parseAndColorize(STRING_DEFAULT_FEEDBACK));
        //Allows keyboard inputs to be read as commands
        implementKeystrokeEvents(primaryStage);
        //Implements the scene
        implementScene();
        
        setStage(primaryStage);
        logger.log(Level.INFO, "GUI successfully instantiated");
    }
    
    private void setProgramHeightToUserScreen() {
    	Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        currentStageHeight = (int) (primScreenBounds.getHeight() - 10);
    }

    //Implements main Grid: Holds contents for the Calendar
    void implementCalendarGrid() {    
        calendarGrid = new GridPane();
        calendarGrid.setAlignment(Pos.TOP_LEFT);
        calendarGrid.setHgap(1);
        calendarGrid.setVgap(1);
        calendarGrid.setPadding(new Insets(10, 10, 10, 10));
        populateGrid(calendarGrid);	
    }
    //calendarScrollPane holds mainCalendarGrid
    void implementScrollPane() {
    	calendarScrollPane = new ScrollPane();
        calendarScrollPane.setPrefSize(1080, 1060);
        calendarScrollPane.setContent(calendarGrid);
        calendarScrollPane.setStyle("-fx-background-color: transparent;");
    }
    //mainGrid holds all other grids in the program
    void implementMainGrid() {
        programMainGrid = new GridPane();
        programMainGrid.setAlignment(Pos.TOP_LEFT);
        programMainGrid.setHgap(1);
        programMainGrid.setVgap(1);
        programMainGrid.setPadding(new Insets(25, 25, 25, 25));
        programMainGrid.add(calendarScrollPane,0,0);
        
    }
    //UserTextField is the text box that the user types in
    void implementUserTextField() {
        userTextField = new TextField();
        userTextField.setStyle(""
        + "-fx-font-size: 17px;"
        + "-fx-font-weight: bold;"
        + "-fx-font-family: Tahoma;"
        + "-fx-text-fill: BLACK;"
        + "-fx-border-width: 6px;"
        + "-fx-border-color: DIMGRAY;"
        + "-fx-background-color: WHITE");
        programMainGrid.add(userTextField, 0, DEFAULT_USER_INPUT_YPOS);
    }
    //UserFeedback is the text that displays messages to the user, above the input box
    void implementUserFeedback(String newInput) {
        programFeedback = new FlowPane();
        programMainGrid.add(programFeedback, 0, DEFAULT_USER_FEEDBACK_YPOS);
    }
    //This function allows program to handle keyboard presses by the user
    void implementKeystrokeEvents(Stage primaryStage) {
        userTextField.setOnKeyPressed(
        		new EventHandler<KeyEvent>()
			        {
			            @Override
			            public void handle(KeyEvent ke)
			            {
			            	executeKeypress(ke, primaryStage);
			            }
			        });
    }
    //Sets scene to correct dimensions
    void implementScene() {
        scene = new Scene(programMainGrid, 720, DEFAULT_STAGE_HEIGHT);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(this.getClass().getResource("PinkPanther.css").toExternalForm());
    }
    //Sets stage to correct dimensions
    void setStage(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        setStageHeight();
    }
	void setStageHeight() {
		objPrimaryStage.setHeight(currentStageHeight);
	}
	void setScrollPaneHeight() {
        calendarScrollPane.setPrefSize(1080, currentStageHeight);
		objPrimaryStage.setHeight(currentStageHeight);
	}
	
	/* Function adds all needed objects to the scene
	 * This function is called whenever the calendar switches view or
	 * needs to refresh due to a change in calendar items
	 */
    void populateGrid(GridPane grid) {
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 33));
        scenetitle.setFill(Color.DIMGRAY);
        grid.add(scenetitle, 1, 0);
        
        try{
        	scenetitle.setText(programState.getTitle());
        } catch (NullPointerException e) {
        	scenetitle.setText(DEFAULT_SCENE_TITLE);
        }
        
    	programState = mainController.getProgramState();
    	setUserTextField(programState.getInputBoxText());
    	unpackTasks(grid);
    }
    
    /* Function has 3 unpack calls, one for overdueTasks, another for floatingTasks,
     * and the last for datedTasks.
     */
    void unpackTasks(GridPane grid) {
    	int currTaskIndex = 1;
    	int currentYPos = 1;
    	
    	if (programState != null) {
    		currentYPos = displayNumOverdueTasks(grid, currTaskIndex, currentYPos);
        	currentYPos = unpackFloatingTasks(grid, currTaskIndex, currentYPos);
        	currentYPos = unpackDatedTasks(grid, currTaskIndex, currentYPos);
    	}

        logger.log(Level.INFO, "All tasks unpacked from ProgramState");
    }
    
    //Function requests for the list of Overdue tasks from ProgramState and adds them to calendar grid
    int displayNumOverdueTasks(GridPane grid, int currTaskIndex, int currentYPos) {
		if (programState.getTitle().equals(DEFAULT_SCENE_TITLE)) {
	    	SortedMap<LocalDate,ArrayList<Task>> overdueList = programState.getOverdueList();
	    	if (overdueList != null) {
	    		grid.add(new TransparentCircle(), 1, currentYPos++);
	    		int numOverdueTasks = 0;
	    		for(LocalDate date:overdueList.keySet()) {
	    			for(@SuppressWarnings("unused") Task task:overdueList.get(date)) { 
	    				numOverdueTasks++;
	    			}
	    		}
	    		
	    		if (numOverdueTasks > 0) {
		    		currentYPos++;
		    		TaskBox overdueTaskReminder = new TaskBox(numOverdueTasks + " OVERDUE TASKS : CLICK F8 TO VIEW ");
					currTaskIndex++;
		    		grid.add(overdueTaskReminder, 1, currentYPos);
		    		currentYPos++;
	    		}
	    	}
		}
    	return currentYPos;
    }

    //Function requests for the list of Undated(floating) tasks from ProgramState and adds them to calendar grid
    int unpackDatedTasks(GridPane grid, int currTaskIndex, int currentYPos) {
    	//for unpacking datedTasks
    	SortedMap<LocalDate,ArrayList<Task>> todoList = programState.getTodoList();
    	if (todoList != null) {
	    	for(LocalDate date:todoList.keySet()) { //looping through dates which have Tasks inside
	    		int totalDeadline = 0, totalEvent = 0, totalTodo = 0;
	    		
	    		grid.add(new TransparentCircle(), 1, currentYPos++);
	    		String month = date.getMonth().toString();
	    		month = month.substring(0, 1)+ month.substring(1, month.length()).toLowerCase();
	    		String year = String.valueOf(date.getYear() %100);
	    		String currDayNum = Integer.toString(date.getDayOfMonth());
	    		int currDayXPos = 1;
	    		DayBox dayBox = new DayBox(currDayNum + " " + month + " " +year);
	    		int dayBoxYPos = currentYPos;
	    		grid.add(dayBox, currDayXPos++, dayBoxYPos);
	    		currentYPos ++;
	    		int currXPos = 1;
	    		currTaskIndex = 1;
	    		
	    		
	    		//count total number of tasks of particular types
				for(Task task:todoList.get(date)) { 
					switch(task.getTaskType()) {
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
					if (task.getClash()) {
						IndexBox testBox = new IndexBox(0);
						grid.add(testBox, currXPos+14, currentYPos+1);
					}
					
		    		currentYPos++;
	    			TaskBox taskBox = new TaskBox(currTaskIndex, task, isTruncatedMode);
	    			currTaskIndex++;
		    		grid.add(taskBox, currXPos, currentYPos);
	
		    		
				}
				
				
				GridPane miniTaskIndicators = new GridPane();
				miniTaskIndicators.setHgap(10);
				miniTaskIndicators.setVgap(5);
				currDayXPos = 54;
	    		for (int i=0; i<3; i++) {
	    			if (i==0 && totalDeadline != 0) {
		    			IndexBox blankColoredBox = new IndexBox(totalDeadline, TaskType.DEADLINE);
		    			miniTaskIndicators.add(blankColoredBox, currDayXPos--, 3);
	    			} else if (i==1 && totalTodo != 0) {
		    			IndexBox blankColoredBox = new IndexBox(totalTodo, TaskType.TODO);
		    			miniTaskIndicators.add(blankColoredBox, currDayXPos--, 3);
	    			} else if (i==2 && totalEvent != 0) {
		    			IndexBox blankColoredBox = new IndexBox(totalEvent, TaskType.EVENT);
		    			miniTaskIndicators.add(blankColoredBox, currDayXPos--, 3);
	    			}
	    			
	    		}
	    		grid.add(miniTaskIndicators, 1, dayBoxYPos);
				
	    		
				currentYPos++;
			}

	    	
	    }
    	return currentYPos;
    }

    //Function requests for the list of Dated tasks from ProgramState and adds them to calendar grid
    int unpackFloatingTasks(GridPane grid, int currTaskIndex, int currentYPos) {
    	//for unpacking floatingTasks
    	ArrayList<Task> floatingTasks = programState.getFloatingList();	
    	if (floatingTasks != null && floatingTasks.size() != 0) {
    		grid.add(new TransparentCircle(), 1, currentYPos++);
    		int totalFloat = 0;

    		currentYPos++;
    		currentYPos++;
	    	int currFloatXPos = 1;
    		DayBox dayBox = new DayBox("Undated Tasks");
    		int dayTextYPos = currentYPos;
    		grid.add(dayBox, 1, currentYPos++);
	    	for (int i=0; i<floatingTasks.size(); i++) {
	    		totalFloat++;
	    		currentYPos++;
	    		TaskBox taskBox = new TaskBox(i+1, floatingTasks.get(i), isTruncatedMode);
	    		currTaskIndex++;
	    		grid.add(taskBox, currFloatXPos, currentYPos);

	    	}

			GridPane miniTaskIndicators = new GridPane();
			miniTaskIndicators.setHgap(10);
			miniTaskIndicators.setVgap(5);
			IndexBox blankColoredBox = new IndexBox(totalFloat, TaskType.FLOATING);
			miniTaskIndicators.add(blankColoredBox, 54, dayTextYPos);
			grid.add(miniTaskIndicators, 1, dayTextYPos);
	    	currentYPos++;
    	}
    	
    	return currentYPos;
    }

    //Function calls Logic to process a command by user or this class itself
    void callControllerToAddCommand() {
    	String command = userTextField.getText();
        logger.log(Level.INFO, "Attempting to get Controller to execute command: " + command);
    	mainController.addCommand(command);
    	setUserFeedback();
    	calendarGrid.getChildren().clear();
    	scenetitle.setText(programState.getTitle());
        populateGrid(calendarGrid);
        if(mainController.getProgramState().getExitState()) {
        	closeWindow();
        }

    }
    
    //This function transforms a string of text to a colorized FlowPane
    FlowPane parseAndColorize(String text) {
    	if (text.equals("INVALID")) {
    		text = STRING_INVALID_COMMAND;
    	}
    	ConsoleInputColorizer colorizer = new ConsoleInputColorizer();
    	colorizer.setController(mainController);
    	FlowPane colorizedText = colorizer.parseInputToArray(text);
    	return colorizedText;
    }
    
    //Sets userFeedBack. Input must be parsed through parseAndColorize() function first.
    void setUserFeedback(FlowPane feedback) {
    	if (!programMainGrid.getChildren().remove(programFeedback)) {
    		if (currentState != CurrentState.VIEWING_HELPSCREEN) {
    			programMainGrid.getChildren().remove(2);
    		}
    	}
    	programFeedback = feedback;
    	programMainGrid.add(programFeedback , 0, DEFAULT_USER_FEEDBACK_YPOS);
    }
    
  //Sets userFeedBack as STRING_DEFAULT_FEEDBACK
    void setUserFeedback() {
    	FlowPane feedback;
    	if (Display.showFeedBack() != null) {
    		feedback = parseAndColorize(Display.showFeedBack());
    	} else {
    		feedback = parseAndColorize(STRING_DEFAULT_FEEDBACK);
    	}
    	
    	setUserFeedback(feedback);
    }
    
    //Changes the text currently in user's input box
    public void setUserTextField(String text) {
    	if(userTextField!=null) {
    		userTextField.setText(text);
    	}
    }
    
    public void clearTextField() {
    	userTextField.clear();
    }
	
    //Function handles all keyboard presses accordingly
	void executeKeypress(KeyEvent ke, Stage stage) {
		Stage primaryStage = stage;
		
		if (ke.getCode().equals(KeyCode.ENTER)) {
			processUserEnter();
        }
        
        else if (ke.getCode().equals(KeyCode.DOWN)) {
    		scrollDown(0.25f);
        }
        else if (ke.getCode().equals(KeyCode.UP)) {
    		scrollUp(0.25f);
        }
        else if (ke.getCode().equals(KeyCode.F2)) {
        	attemptToggleHelpScreenView();
        }
        else if (ke.getCode().equals(KeyCode.PAGE_UP) || ke.getCode().equals(KeyCode.F6)) {
        	if (currentStageHeight > 210) {
        		currentStageHeight -= 30;
        	}
        	setScrollPaneHeight();
        	setStageHeight();
        }
        else if (ke.getCode().equals(KeyCode.PAGE_DOWN) || ke.getCode().equals(KeyCode.F5)) {
        	if (currentStageHeight < DEFAULT_STAGE_HEIGHT) {
        		currentStageHeight += 30;
        	}
        	setScrollPaneHeight();
        	setStageHeight();
        }
        else if (ke.getCode().equals(KeyCode.ESCAPE) || ke.getCode().equals(KeyCode.F3)) {
        	//minimize program to tray
            primaryStage.setIconified(true);
        }
        else if (ke.getCode().equals(KeyCode.F1)) {
        	if (currentState == CurrentState.VIEWING_HELPSCREEN) {
	        	attemptToggleHelpScreenView();
			}
        	attemptToggleCalendarHiddenMode(stage);
        } 
        else if (ke.getCode().equals(KeyCode.F4) && currentState == CurrentState.VIEWING_CALENDAR) {
        	isTruncatedMode = !isTruncatedMode;
        	calendarGrid.getChildren().clear();
        	scenetitle.setText(programState.getTitle());
            populateGrid(calendarGrid);
        }
        else if (ke.getCode().equals(KeyCode.BACK_SPACE)) {
        	processBackSpace();
        }
		

        else if (ke.getCode().equals(KeyCode.F7)) {
			userTextField.setText("view normal");
			callControllerToAddCommand();
			if (currentState == CurrentState.VIEWING_HELPSCREEN) {
	        	attemptToggleHelpScreenView();
			}
        }
        else if (ke.getCode().equals(KeyCode.F8)) {
			userTextField.setText("view overdue");
			callControllerToAddCommand();
			if (currentState == CurrentState.VIEWING_HELPSCREEN) {
	        	attemptToggleHelpScreenView();
			}
        }
        else if (ke.getCode().equals(KeyCode.F9)) {
			userTextField.setText("view all");
			callControllerToAddCommand();
			if (currentState == CurrentState.VIEWING_HELPSCREEN) {
	        	attemptToggleHelpScreenView();
			}
        }
        
        else if (ke.getCode().isLetterKey() ||  ke.getCode().isDigitKey() || ke.getCode().equals(KeyCode.SPACE)) {
        	//for all other non-reserved keystrokes
        	processNonReservedKeys(ke);
        }
		
	}
	
	void processBackSpace() {
    	String userText = userTextField.getText();
    	
    	if (userText.length() <= 1) {
    		userText = STRING_DEFAULT_FEEDBACK;
		} else {
    		userText = userText.substring(0, userText.length()-1);
    	}
    	
    	FlowPane colorizedText = parseAndColorize(userText);
    	setUserFeedback(colorizedText);
	}
	
	void processNonReservedKeys(KeyEvent ke) {
    	String userText = userTextField.getText();
    	
    	if (ke.getCode().isLetterKey()) {
    		userText = userText + ke.getCode().toString().toLowerCase();
    	} else if (ke.getCode().isDigitKey()) {
    		userText = userText + ke.getCode().getName();
    	} else if (ke.getCode().equals(KeyCode.SPACE)) {
    		userText = userText + " ";
    	}
    	
    	
    	
    	FlowPane colorizedText = parseAndColorize(userText);
    	setUserFeedback(colorizedText);
	}
	
	//Called when user presses ENTER button, and processes text in input box accordingly
	void processUserEnter() {
    	if ((userTextField.getText() != null && !userTextField.getText().isEmpty())) {
			callControllerToAddCommand();
        }
	}
	
	void scrollUp(double scrollValue) {
		switch (currentState) {
		case VIEWING_CALENDAR: 
			calendarScrollPane.setVvalue(calendarScrollPane.getVvalue() - scrollValue);
			break;
		default:
			helpScreen.setVvalue(helpScreen.getVvalue() - scrollValue);
			break;
		}
	}
	void scrollDown(double scrollValue) {
		switch (currentState) {
		case VIEWING_CALENDAR: 
			calendarScrollPane.setVvalue(calendarScrollPane.getVvalue() + scrollValue);
			break;
		default:
			helpScreen.setVvalue(helpScreen.getVvalue() + scrollValue);
			break;
		}
	}
	
	void attemptToggleHelpScreenView() {
		if (currentState != CurrentState.VIEWING_HIDDEN) {
			if (currentState == CurrentState.VIEWING_CALENDAR) {
				viewHelpScreen();
			} else if (currentState == CurrentState.VIEWING_HELPSCREEN) {
				hideHelpScreen();
			}
		}
	}
	
	//Shifts program to CheatSheet screen. Only accessible in Calendar mode.
	void viewHelpScreen() {
    	programMainGrid.getChildren().remove(calendarScrollPane);
    	programMainGrid.add(helpScreen, 0, 0);
		currentState = CurrentState.VIEWING_HELPSCREEN;
	}

	//Shifts program back to Calendar screen from CheatSheet screen.
	void hideHelpScreen() {
		programMainGrid.getChildren().clear();
		programMainGrid.add(calendarScrollPane,0,0);
        programMainGrid.add(userTextField, 0, DEFAULT_USER_INPUT_YPOS);
        setUserFeedback();
		currentState = CurrentState.VIEWING_CALENDAR;
	}

	void attemptToggleCalendarHiddenMode(Stage stage) {
		if (currentState == CurrentState.VIEWING_CALENDAR) {
			hideCalendar(stage);
		} else if (currentState == CurrentState.VIEWING_HIDDEN) {
			unHideCalendar(stage);
		}
	}
	
	//Restores screen back to last configured height and unhides calendar
	void unHideCalendar(Stage stage) {
        stage.setHeight(currentStageHeight);
        scenetitle.setText(programState.getTitle());
        calendarScrollPane.setDisable(false);
 		calendarScrollPane.setVvalue(currentScrollYPos);
		currentState = CurrentState.VIEWING_CALENDAR;
	}
	
	//Downsizes window and hides calendar, locking scrollbar.
	//Can only be accessed in Calendar mode
	void hideCalendar(Stage stage) {
		if (currentState != CurrentState.VIEWING_HELPSCREEN) {
	    stage.setHeight(200);
	    scenetitle.setText("   Crouching Tiger; Hidden Calendar");
	    calendarScrollPane.setDisable(true);
	    calendarScrollPane.setVvalue(0);
		currentState = CurrentState.VIEWING_HIDDEN;
		}
	}

	public void closeWindow() {
        logger.log(Level.INFO, "Exit command called");
		Platform.exit();
	}


}