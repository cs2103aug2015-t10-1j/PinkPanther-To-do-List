/* @@author A0122545M */
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
    private int currentStageHeight = HEIGHT_STAGE_DEFAULT;
    
    //Instantiated objects that GUI uses and needs to point to at various instances
	Text sceneTitle = new Text(TITLE_CALENDAR_SCENE);
    Controller mainController;
    GridPane calendarGrid;
    GridPane programMainGrid;
    ScrollPane calendarScrollPane;
    TextField userTextField;
    Scene scene;
    Stage objPrimaryStage;
    ProgramState programState;
    FlowPane programFeedback;
    ConsoleInputColorizer colorizer;
    HelpScreen helpScreen = new HelpScreen();
    
    //Default strings used in various instances
    private static String TITLE_CALENDAR_SCENE = "                         To-Do List";
    private static String TITLE_HIDDEN_CALENDAR = "   Crouching Tiger; Hidden Calendar";
    private static String STRING_INVALID_COMMAND = "Unrecognized command. Press PAGE_UP for Help Screen.";
    private static String STRING_DEFAULT_FEEDBACK = "Input command into the field below";
    private static String STRING_DEFAULT_PROGRAM_TITLE = "PinkPanther: The best to-do list";
    private static String STRING_LOGO_DIRECTORY = "PPLogo.png";
    
    //Default values for objects' positions and sizes in program window
    private static int HEIGHT_STAGE_DEFAULT = 1020;
    private static int OFFSET_SCREEN_PROGRAM_BUFFER = 10;
    private static int WIDTH_STAGE_DEFAULT = 1080;
    private static int YPOS_USER_INPUT_DEFAULT = 5;
    private static int YPOS_USER_FEEDBACK_DEFAULT = 2;
    private static int YPOS_TASK_INDICATOR_BOX = 3;
    private static int XPOS_TASK_INDICATOR_BOX = 55;
    private static int PARAMETER_GRID_GAP = 1;
    private static Insets PADDING_CALENDAR_GRID = new Insets(10, 10, 10, 10);
    private static Insets PADDING_MAIN_GRID = new Insets(25, 25, 25, 25);
    private static int WIDTH_PROGRAM_DEFAULT = 720;
    private static int POSITION_DEFAULT_X_LEFT = 0;
    private static int POSITION_DEFAULT_X_CENTRE = 1;
    private static int POSITION_DEFAULT_Y_FIRST_ITEM = 0;
    private static int HEIGHT_HIDDEN_CALENDAR = 200;
    private static int FONTSIZE_SCENE_TITLE = 33;
    private static float AMOUNT_SCROLL_PER_CLICK = 0.25f;
    private static int OFFSET_X_POS_CLASH_INDICATOR = 3;
    private static int OFFSET_Y_POS_CLASH_INDICATOR = 1;
    private static int STEP_PROGRAM_HEIGHT_CHANGE = 30;
    private static int HEIGHT_MINIMUM_PROGRAM = 210;
    private static int HGAP_MINI_TASK_INDICATORS = 10;
    private static int VGAP_MINI_TASK_INDICATORS = 5;
    
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
    	objPrimaryStage = primaryStage;
    	mainController = new Controller();
    	implementSceneObjects();
        setStage(primaryStage);
        logger.log(Level.INFO, "GUI successfully instantiated");
    }
    
    private void implementSceneObjects(){
    	setProgramHeightToUserScreen();
    	objPrimaryStage.setTitle(STRING_DEFAULT_PROGRAM_TITLE);
    	Image logoImage = new Image(STRING_LOGO_DIRECTORY);
    	objPrimaryStage.getIcons().add(logoImage);
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
        implementKeystrokeEvents(objPrimaryStage);
        //Implements the scene
        implementScene();
    }
    
    private void setProgramHeightToUserScreen() {
    	Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        setCurrentStageHeight((int) (primScreenBounds.getHeight() - OFFSET_SCREEN_PROGRAM_BUFFER));
    }

    //Implements main Grid: Holds contents for the Calendar
    private void implementCalendarGrid() {    
        calendarGrid = new GridPane();
        calendarGrid.setAlignment(Pos.TOP_LEFT);
        calendarGrid.setHgap(PARAMETER_GRID_GAP);
        calendarGrid.setVgap(PARAMETER_GRID_GAP);
        calendarGrid.setPadding(PADDING_CALENDAR_GRID);
        populateGrid(calendarGrid);	
    }
    //calendarScrollPane holds mainCalendarGrid
    private void implementScrollPane() {
    	calendarScrollPane = new ScrollPane();
        calendarScrollPane.setPrefSize(WIDTH_STAGE_DEFAULT, HEIGHT_STAGE_DEFAULT);
        calendarScrollPane.setContent(calendarGrid);
        calendarScrollPane.setStyle("-fx-background-color: transparent;");
    }
    //mainGrid holds all other grids in the program
    private void implementMainGrid() {
        programMainGrid = new GridPane();
        programMainGrid.setAlignment(Pos.TOP_LEFT);
        programMainGrid.setHgap(PARAMETER_GRID_GAP);
        programMainGrid.setVgap(PARAMETER_GRID_GAP);
        programMainGrid.setPadding(PADDING_MAIN_GRID);
        programMainGrid.add(calendarScrollPane,POSITION_DEFAULT_X_LEFT, POSITION_DEFAULT_Y_FIRST_ITEM);
        
    }
    //UserTextField is the text box that the user types in
    private void implementUserTextField() {
        userTextField = new TextField();
        userTextField.setStyle(""
        + "-fx-font-size: 17px;"
        + "-fx-font-weight: bold;"
        + "-fx-font-family: Tahoma;"
        + "-fx-text-fill: BLACK;"
        + "-fx-border-width: 6px;"
        + "-fx-border-color: DIMGRAY;"
        + "-fx-background-color: WHITE");
        programMainGrid.add(userTextField, POSITION_DEFAULT_X_LEFT, YPOS_USER_INPUT_DEFAULT);
    }
    //UserFeedback is the text that displays messages to the user, above the input box
    private void implementUserFeedback(String newInput) {
        programFeedback = new FlowPane();
        programMainGrid.add(programFeedback, POSITION_DEFAULT_X_LEFT, YPOS_USER_FEEDBACK_DEFAULT);
        colorizer = new ConsoleInputColorizer();
        colorizer.setController(mainController);
    }
    //This function allows program to handle keyboard presses by the user
    private void implementKeystrokeEvents(Stage primaryStage) {
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
    private void implementScene() {
        scene = new Scene(programMainGrid, WIDTH_PROGRAM_DEFAULT, HEIGHT_STAGE_DEFAULT);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(this.getClass().getResource("PinkPanther.css").toExternalForm());
    }
    //Sets stage to correct dimensions
    private void setStage(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        setStageHeight();
    }
    private void setStageHeight() {
		objPrimaryStage.setHeight(getCurrentStageHeight());
	}
    private void setScrollPaneHeight() {
        calendarScrollPane.setPrefSize(WIDTH_STAGE_DEFAULT, getCurrentStageHeight());
		objPrimaryStage.setHeight(getCurrentStageHeight());
	}
	
	/* Function adds all needed objects to the scene
	 * This function is called whenever the calendar switches view or
	 * needs to refresh due to a change in calendar items
	 */
    private void populateGrid(GridPane grid) {
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, FONTSIZE_SCENE_TITLE));
        sceneTitle.setFill(Color.DIMGRAY);
        grid.add(sceneTitle, POSITION_DEFAULT_X_CENTRE, POSITION_DEFAULT_Y_FIRST_ITEM);
        
        try{
        	sceneTitle.setText(programState.getTitle());
        } catch (NullPointerException e) {
        	sceneTitle.setText(TITLE_CALENDAR_SCENE);
        }
        
    	programState = mainController.getProgramState();
    	setUserTextField(programState.getInputBoxText());
    	unpackTasks(grid);
    }
    
    /* Function has 3 unpack calls, one for overdueTasks, another for floatingTasks,
     * and the last for datedTasks.
     */
    private void unpackTasks(GridPane grid) {
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
    private int displayNumOverdueTasks(GridPane grid, int currTaskIndex, int currentYPos) {
		if (programState.getTitle().equals(TITLE_CALENDAR_SCENE)) {
	    	SortedMap<LocalDate,ArrayList<Task>> overdueList = programState.getOverdueList();
	    	if (overdueList != null) {
	    		grid.add(new TransparentCircle(), POSITION_DEFAULT_X_CENTRE, currentYPos++);
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
		    		grid.add(overdueTaskReminder, POSITION_DEFAULT_X_CENTRE, currentYPos);
		    		currentYPos++;
	    		}
	    	}
		}
    	return currentYPos;
    }

    //Function requests for the list of Undated(floating) tasks from ProgramState and adds them to calendar grid
    private int unpackDatedTasks(GridPane grid, int currTaskIndex, int currentYPos) {
    	//for unpacking datedTasks
    	SortedMap<LocalDate,ArrayList<Task>> todoList = programState.getTodoList();
    	if (todoList != null) {
	    	for(LocalDate date:todoList.keySet()) { //looping through dates which have Tasks inside
	    		int totalDeadline = 0, totalEvent = 0, totalTodo = 0;
	    		
	    		grid.add(new TransparentCircle(), POSITION_DEFAULT_X_CENTRE, currentYPos++);
	    		String month = date.getMonth().toString();
	    		month = month.substring(0, POSITION_DEFAULT_X_CENTRE)+ month.substring(1, month.length()).toLowerCase();
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
						IndexBox clashIndicatorBox = new IndexBox();
						grid.add(clashIndicatorBox, currXPos + OFFSET_X_POS_CLASH_INDICATOR, 
								currentYPos + OFFSET_Y_POS_CLASH_INDICATOR);
					}
					
		    		currentYPos++;
	    			TaskBox taskBox = new TaskBox(currTaskIndex, task, isTruncatedMode);
	    			currTaskIndex++;
		    		grid.add(taskBox, currXPos, currentYPos);
				}
				
				GridPane miniTaskIndicators = new GridPane();
				miniTaskIndicators.setHgap(HGAP_MINI_TASK_INDICATORS);
				miniTaskIndicators.setVgap(VGAP_MINI_TASK_INDICATORS);
				currDayXPos = XPOS_TASK_INDICATOR_BOX;
	    		for (int i=0; i<3; i++) {
	    			if (i==0 && totalDeadline != 0) {
		    			IndexBox blankColoredBox = new IndexBox(totalDeadline, TaskType.DEADLINE);
		    			miniTaskIndicators.add(blankColoredBox, currDayXPos--, YPOS_TASK_INDICATOR_BOX);
	    			} else if (i==1 && totalTodo != 0) {
		    			IndexBox blankColoredBox = new IndexBox(totalTodo, TaskType.TODO);
		    			miniTaskIndicators.add(blankColoredBox, currDayXPos--, YPOS_TASK_INDICATOR_BOX);
	    			} else if (i==2 && totalEvent != 0) {
		    			IndexBox blankColoredBox = new IndexBox(totalEvent, TaskType.EVENT);
		    			miniTaskIndicators.add(blankColoredBox, currDayXPos--, YPOS_TASK_INDICATOR_BOX);
	    			}
	    		}
	    		grid.add(miniTaskIndicators, POSITION_DEFAULT_X_CENTRE, dayBoxYPos);
				currentYPos++;
			}
	    }
    	return currentYPos;
    }

    //Function requests for the list of Dated tasks from ProgramState and adds them to calendar grid
    private int unpackFloatingTasks(GridPane grid, int currTaskIndex, int currentYPos) {
    	//for unpacking floatingTasks
    	ArrayList<Task> floatingTasks = programState.getFloatingList();	
    	if (floatingTasks != null && floatingTasks.size() != 0) {
    		grid.add(new TransparentCircle(), POSITION_DEFAULT_X_CENTRE, currentYPos++);
    		int totalFloat = 0;

    		currentYPos++;
    		currentYPos++;
	    	int currFloatXPos = 1;
    		DayBox dayBox = new DayBox("Undated Tasks");
    		int dayTextYPos = currentYPos;
    		grid.add(dayBox, POSITION_DEFAULT_X_CENTRE, currentYPos++);
	    	for (int i=0; i<floatingTasks.size(); i++) {
	    		totalFloat++;
	    		currentYPos++;
	    		TaskBox taskBox = new TaskBox(i+1, floatingTasks.get(i), isTruncatedMode);
	    		currTaskIndex++;
	    		grid.add(taskBox, currFloatXPos, currentYPos);

	    	}

			GridPane miniTaskIndicators = new GridPane();
			miniTaskIndicators.setHgap(HGAP_MINI_TASK_INDICATORS);
			miniTaskIndicators.setVgap(VGAP_MINI_TASK_INDICATORS);
			IndexBox blankColoredBox = new IndexBox(totalFloat, TaskType.FLOATING);
			miniTaskIndicators.add(blankColoredBox, XPOS_TASK_INDICATOR_BOX, dayTextYPos);
			grid.add(miniTaskIndicators, POSITION_DEFAULT_X_CENTRE, dayTextYPos);
	    	currentYPos++;
    	}
    	
    	return currentYPos;
    }

    //Function calls Logic to process a command by user or this class itself
    private void callControllerToAddCommand() {
    	String command = userTextField.getText();
        logger.log(Level.INFO, "Attempting to get Controller to execute command: " + command);
    	mainController.addCommand(command);
    	setUserFeedback();
    	calendarGrid.getChildren().clear();
    	sceneTitle.setText(programState.getTitle());
        populateGrid(calendarGrid);
        if(mainController.getProgramState().getExitState()) {
        	closeWindow();
        }
    }
    
    //This function transforms a string of text to a colorized FlowPane
    private FlowPane parseAndColorize(String text) {
    	if (text.equals("INVALID")) {
    		text = STRING_INVALID_COMMAND;
    	}
    	FlowPane colorizedText = colorizer.parseInputToArray(text);
    	return colorizedText;
    }
    
    //Sets userFeedBack. Input must be parsed through parseAndColorize() function first.
    private void setUserFeedback(FlowPane feedback) {
    	if (!programMainGrid.getChildren().remove(programFeedback)) {
    		if (currentState != CurrentState.VIEWING_HELPSCREEN) {
    			programMainGrid.getChildren().remove(2);
    		}
    	}
    	programFeedback = feedback;
    	programMainGrid.add(programFeedback , POSITION_DEFAULT_X_LEFT, YPOS_USER_FEEDBACK_DEFAULT);
    }
    
  //Sets userFeedBack as STRING_DEFAULT_FEEDBACK
    private void setUserFeedback() {
    	FlowPane feedback;
    	if (Display.showFeedBack() != null) {
    		feedback = parseAndColorize(Display.showFeedBack());
    	} else {
    		feedback = parseAndColorize(STRING_DEFAULT_FEEDBACK);
    	}
    	
    	setUserFeedback(feedback);
    }
    
    //Changes the text currently in user's input box
    private void setUserTextField(String text) {
    	if(userTextField!=null) {
    		userTextField.setText(text);
    		userTextField.positionCaret(text.length());
    	}
    }
    
    private void clearTextField() {
    	userTextField.clear();
    }
	
    //Function handles all keyboard presses accordingly
    private void executeKeypress(KeyEvent ke, Stage stage) {
		Stage primaryStage = stage;
		
		if (ke.getCode().equals(KeyCode.ENTER)) {
			processUserEnter();
        }
        
        else if (ke.getCode().equals(KeyCode.DOWN)) {
    		scrollDown(AMOUNT_SCROLL_PER_CLICK);
        }
        else if (ke.getCode().equals(KeyCode.UP)) {
    		scrollUp(AMOUNT_SCROLL_PER_CLICK);
        }
        else if (ke.getCode().equals(KeyCode.F2)) {
        	attemptToggleHelpScreenView();
        }
        else if (ke.getCode().equals(KeyCode.PAGE_UP) || ke.getCode().equals(KeyCode.F6)) {
        	if (getCurrentStageHeight() > HEIGHT_MINIMUM_PROGRAM) {
        		setCurrentStageHeight(getCurrentStageHeight() - STEP_PROGRAM_HEIGHT_CHANGE);
        	}
        	setScrollPaneHeight();
        	setStageHeight();
        }
        else if (ke.getCode().equals(KeyCode.PAGE_DOWN) || ke.getCode().equals(KeyCode.F5)) {
        	if (getCurrentStageHeight() < HEIGHT_STAGE_DEFAULT + STEP_PROGRAM_HEIGHT_CHANGE) {
        		setCurrentStageHeight(getCurrentStageHeight() + STEP_PROGRAM_HEIGHT_CHANGE);
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
        	sceneTitle.setText(programState.getTitle());
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
	
    private void processBackSpace() {
    	String userText = userTextField.getText();
    	
    	if (userText.length() <= 1) {
    		userText = STRING_DEFAULT_FEEDBACK;
		} else {
    		userText = userText.substring(0, userText.length()-1);
    	}
    	
    	FlowPane colorizedText = parseAndColorize(userText);
    	setUserFeedback(colorizedText);
	}
	
    private void processNonReservedKeys(KeyEvent ke) {
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
    private void processUserEnter() {
    	if ((userTextField.getText() != null && !userTextField.getText().isEmpty())) {
			callControllerToAddCommand();
        }
	}
	
    private void scrollUp(double scrollValue) {
		switch (currentState) {
		case VIEWING_CALENDAR: 
			calendarScrollPane.setVvalue(calendarScrollPane.getVvalue() - scrollValue);
			break;
		default:
			helpScreen.setVvalue(helpScreen.getVvalue() - scrollValue);
			break;
		}
	}
    private void scrollDown(double scrollValue) {
		switch (currentState) {
		case VIEWING_CALENDAR: 
			calendarScrollPane.setVvalue(calendarScrollPane.getVvalue() + scrollValue);
			break;
		default:
			helpScreen.setVvalue(helpScreen.getVvalue() + scrollValue);
			break;
		}
	}
	
    private void attemptToggleHelpScreenView() {
		if (currentState != CurrentState.VIEWING_HIDDEN) {
			if (currentState == CurrentState.VIEWING_CALENDAR) {
				viewHelpScreen();
			} else if (currentState == CurrentState.VIEWING_HELPSCREEN) {
				hideHelpScreen();
			}
		}
	}
	
	//Shifts program to CheatSheet screen. Only accessible in Calendar mode.
    private void viewHelpScreen() {
    	programMainGrid.getChildren().remove(calendarScrollPane);
    	programMainGrid.add(helpScreen, POSITION_DEFAULT_X_LEFT, POSITION_DEFAULT_Y_FIRST_ITEM);
		currentState = CurrentState.VIEWING_HELPSCREEN;
	}

	//Shifts program back to Calendar screen from CheatSheet screen.
    private void hideHelpScreen() {
		programMainGrid.getChildren().clear();
		programMainGrid.add(calendarScrollPane,POSITION_DEFAULT_X_LEFT, POSITION_DEFAULT_Y_FIRST_ITEM);
        programMainGrid.add(userTextField, POSITION_DEFAULT_X_LEFT, YPOS_USER_INPUT_DEFAULT);
        setUserFeedback();
		currentState = CurrentState.VIEWING_CALENDAR;
	}

    private void attemptToggleCalendarHiddenMode(Stage stage) {
		if (currentState == CurrentState.VIEWING_CALENDAR) {
			hideCalendar(stage);
		} else if (currentState == CurrentState.VIEWING_HIDDEN) {
			unHideCalendar(stage);
		}
	}
	
	//Restores screen back to last configured height and unhides calendar
    private void unHideCalendar(Stage stage) {
        stage.setHeight(getCurrentStageHeight());
        sceneTitle.setText(programState.getTitle());
        calendarScrollPane.setDisable(false);
 		calendarScrollPane.setVvalue(currentScrollYPos);
		currentState = CurrentState.VIEWING_CALENDAR;
	}
	
	//Downsizes window and hides calendar, locking scrollbar.
	//Can only be accessed in Calendar mode
    private void hideCalendar(Stage stage) {
		if (currentState != CurrentState.VIEWING_HELPSCREEN) {
	    stage.setHeight(HEIGHT_HIDDEN_CALENDAR);
	    sceneTitle.setText(TITLE_HIDDEN_CALENDAR);
	    calendarScrollPane.setDisable(true);
	    calendarScrollPane.setVvalue(0);
		currentState = CurrentState.VIEWING_HIDDEN;
		}
	}

	private void closeWindow() {
        logger.log(Level.INFO, "Exit command called");
		Platform.exit();
	}

	private int getCurrentStageHeight() {
		return currentStageHeight;
	}

	private void setCurrentStageHeight(int currentStageHeight) {
		this.currentStageHeight = currentStageHeight;
	}


}