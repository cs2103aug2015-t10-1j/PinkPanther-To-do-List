package userinterface;

import java.util.Random;

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
 
public class PrettyDisplay extends Application {
	
    Text scenetitle = new Text("Calendar");
	boolean isViewingHelpScreen = false;
    boolean isCalendarHidden = false;
    boolean isShortened = false;
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
    
    public static void main(String[] args){
    	PrettyDisplay pD = new PrettyDisplay();
    	pD.runApp();
    }
    
    public void runApp() {
        launch();
    }
     
    @Override
    public void start(Stage primaryStage) {
    	fillPage ("Input command in the field above", primaryStage);
    	objPrimaryStage = primaryStage;
    	mainController = new Controller();
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
        + "-fx-text-fill: DIMGRAY;"
        + "-fx-border-width: 6px;"
        + "-fx-border-color: DIMGRAY;"
        + "-fx-background-color: PINK");
        grid2.add(userTextField, 0, 1);
    }
    void implementActionTarget(String newInput){
        actiontarget = new Text(newInput);
        grid2.add(actiontarget, 0, 2);
        actiontarget.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        actiontarget.setFill(Color.WHITE);     
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
        scene = new Scene(grid2, 1080, 850);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(this.getClass().getResource("a.css").toExternalForm());

    }
    void setStage(Stage primaryStage){
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    void populateGrid(GridPane grid){
    	int currentYPos = 1;
        //Scene title
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 66));
        scenetitle.setFill(Color.DIMGRAY);
        grid.add(scenetitle, 1, 0);
        
		
		TransparentRect tRect = new TransparentRect();
		grid.add(tRect, 2, 0);

        //for the actual calendar items
        TextedColorDayBox daydBox = new TextedColorDayBox("Float");
    	grid.add(daydBox, 0, currentYPos++);

    	int currTaskIndex = 1;
    	
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
		        		TextedTaskBox taskBox = new TextedTaskBox("Meetin with boss at meeting roomdddddd" , "08:00pm", "09:00pm", currTaskIndex);
		        		currTaskIndex++;
		        		grid.add(taskBox, currentXPos++, currentYPos);
		            }
		        	currentYPos++;
		        	
        	}
        	if (i == 5 && currentYPos < 8){
        		for (int k=currentYPos; k<7; k++){
        			TransparentCircle circle = new TransparentCircle();
        			grid.add(circle, 0, k);
        		}
        	
        	}
        }
    }

    void callControllerToAddCommand(){
    	String command = userTextField.getText();
    	actiontarget.setFill(Color.WHITE);                	
    	actiontarget.setText(command);
    	mainController.addCommand(command);
    	calendarGrid.getChildren().clear();
    	userTextField.clear();
        populateGrid(calendarGrid);

    }
    
    public void setActionResult(String text){
    	actiontarget.setText(text);
    	
    	if (text.equals("Invalid Command")){
    		actiontarget.setFill(Color.FIREBRICK);
    	}
    	else{
    		actiontarget.setFill(Color.WHITE);    		
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
        else if (ke.getCode().equals(KeyCode.RIGHT) && !isCalendarHidden)
        {
        	if (!isViewingHelpScreen){
        		viewHelpScreen();
        	}
        	else {
        		hideHelpScreen();
        	}
        }
        else if (ke.getCode().equals(KeyCode.LEFT))
        {
            primaryStage.setIconified(true);
        }
        else if (ke.getCode().equals(KeyCode.END) && !isViewingHelpScreen)
        {
        	if (isCalendarHidden){
        		unHideCalendar(primaryStage);
        	}
        	else{
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
    	grid2.add(helpScreen, 0, 0);
        actiontarget.setFill(Color.WHITE);
		actiontarget.setText("Viewing cheatsheet: Press RIGHT key to resume");
    	isViewingHelpScreen = true;
	}
	
	void hideHelpScreen(){
		grid2.getChildren().clear();
		grid2.add(s1,0,0);
        grid2.add(userTextField, 0, 1);
        grid2.add(actiontarget, 0, 2);
        grid2.add(hbBtn, 0, 2);
        actiontarget.setFill(Color.WHITE);
		actiontarget.setText("Input command into the field above");
		isViewingHelpScreen = false;
	}
	
	void unHideCalendar(Stage stage){
        stage.setHeight(890);
        scenetitle.setText("Calendar");
        s1.setDisable(false);
        actiontarget.setFill(Color.WHITE);
		actiontarget.setText("Input command into the field above");
        isCalendarHidden = false;
// 		s1.setVvalue(currentScrollYPos);		
	}
	
	void hideCalendar(Stage stage){
        stage.setHeight(390);
        scenetitle.setText("Calendar\n Hidden");
        s1.setDisable(true);
        s1.setVvalue(0);
        actiontarget.setFill(Color.WHITE);
		actiontarget.setText("Hidden calendar mode: Press END to restore calendar");
        isCalendarHidden = true;
	}
	
	//currently not working..
	public void closeWindow(){
		Platform.exit();
	}


}