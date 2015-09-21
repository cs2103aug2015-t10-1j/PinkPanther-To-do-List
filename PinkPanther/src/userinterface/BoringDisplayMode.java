package userinterface;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class BoringDisplayMode extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setMaxWidth(1);
        grid.setPadding(new Insets(10, 10, 10, 10));
//        grid.setGridLinesVisible(true);
    	
        TextField userTextField = new TextField("Input Command");
        userTextField.setMinHeight(50);
        userTextField.setMinWidth(1000);
        userTextField.setStyle(""
        + "-fx-font-size: 30px;"
        + "-fx-font-weight: bold;"
        + "-fx-font-family: Tahoma;"
        + "-fx-text-fill: DIMGRAY;"
        + "-fx-border-width: 4px;"
        + "-fx-border-color: DIMGRAY;"
        + "-fx-background-color: WHITE");
       // userTextField.getStyleClass().add("best");
        grid.add(userTextField, 0, 1);
        
        TextBox text = new TextBox("44444444444444444444444444444444444444444444444444444444444444", 1050, 650);
        text.setLayoutX(30);
        text.setLayoutY(20);
        
        grid.add(text, 0, 0);
        
        final Text actiontarget = new Text("Please enter an input");
        grid.add(actiontarget, 0, 2);
        actiontarget.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        actiontarget.setFill(Color.WHITE);    
        
        
        
        
        
        
        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	if ((userTextField.getText() != null && !userTextField.getText().isEmpty())) {
                        text.editText(userTextField.getText());
                        actiontarget.setText(" " + userTextField.getText());
                        userTextField.setText("");
                    } else {
                    	text.editText("No input detected");
                    	actiontarget.setText(" " + "No input detected");
                    };
                }
                
            }
        });
        
        
        
        
        
        
        
        
        
        
        
        Scene scene = new Scene(grid, 1080, 800);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(HelloWorld.class.getResource("a.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    	
    	
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    class TextBox extends Group {
        private Text text;
        private Rectangle rectangle;
        private Rectangle clip;
        public StringProperty textProperty() { return text.textProperty(); }

        TextBox(String string, double width, double height) {
          this.text = new Text(string);
          text.setTextAlignment(TextAlignment.LEFT);
          text.setFill(Color.DIMGRAY);
          text.setTextOrigin(VPos.TOP);
          text.setFont(Font.font("Tahoma", 25));
          text.setFontSmoothingType(FontSmoothingType.LCD);

          this.rectangle = new Rectangle(width, height);
          rectangle.setFill(Color.LIGHTPINK);
          rectangle.setArcWidth(30);
          rectangle.setArcHeight(30);
          rectangle.setStroke(Color.DIMGRAY);
          rectangle.setStrokeWidth(4.0f);
          rectangle.setOpacity(.5);

          this.clip = new Rectangle(width, height);
          text.setClip(clip);

          this.getChildren().addAll(rectangle, text);
        }

        @Override protected void layoutChildren() {
          final double w = rectangle.getWidth();
          final double h = rectangle.getHeight();
          clip.setWidth(w);
          clip.setHeight(h*1.4);
          clip.setLayoutX(0);
          clip.setLayoutY(-h/2);

          text.setWrappingWidth(w * 0.9);
          text.setLayoutX(w / 2 - text.getLayoutBounds().getWidth() / 2);
          text.setLayoutY(20);
        }
        
        void editText(String newText){
        	this.text.setText(newText);
        
        }
    }

}