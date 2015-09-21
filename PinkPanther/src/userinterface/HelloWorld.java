package userinterface;

import logic.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
 
public class HelloWorld extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PinkPanther: The best to-do list");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("PinkPanther Calendar");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        int commandBoxYPos = 60;
        
        Label command = new Label("Input Command:");
        grid.add(command, 0, commandBoxYPos);

        TextField userTextField = new TextField("input");
        grid.add(userTextField, 1, commandBoxYPos);
        
        //text that displays after-action (e.g added x event)
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 61);
        
        //add button
        Button btn = new Button("Enter");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 2, 60);
        
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
        
        
        Scene scene = new Scene(grid, 1080, 800);
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    
}