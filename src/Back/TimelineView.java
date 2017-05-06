package Back;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;



import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class TimelineView extends Application{

	public static int daysnumber ;
	
	  
	
    @Override
	public void start(Stage primaryStage){ 
    	
		HBox root = new HBox();
		
		GridPane buttons = new GridPane();
		
		System.out.println("Days= "+daysnumber);
		
		
		
		/* Buttons with attributes*/
		Button addEvent = new Button("Add Event"); 
		Button editEvent = new Button("Edit Event"); 
		Button editTimeline = new Button("Edit Timeline"); 
		Button saveTimeline = new Button("Save Timeline"); 
		
		/*Buttons get the same size */
		addEvent.setMaxWidth(Double.MAX_VALUE);
		editEvent.setMaxWidth(Double.MAX_VALUE);
		editTimeline.setMaxWidth(Double.MAX_VALUE);
		saveTimeline.setMaxWidth(Double.MAX_VALUE);
		buttons.setHgap(15);
	    buttons.setVgap(10);
	    
	    /*Position of the buttons */
	    GridPane.setMargin(addEvent, new Insets(5, 0, 0, 0));
		GridPane.setConstraints(addEvent, 52, 0);
		GridPane.setConstraints(editEvent, 52, 1);
		GridPane.setConstraints(editTimeline, 52, 2);
		GridPane.setConstraints(saveTimeline, 52, 3);
		editTimeline.setMinWidth(Region.USE_PREF_SIZE);
		saveTimeline.setMinWidth(Region.USE_PREF_SIZE);
	
		/* The background of the buttons */
		Rectangle backgroundButtons = new Rectangle(150, 170);
		GridPane.setConstraints(backgroundButtons,26, 0, 29, 5, HPos.RIGHT, VPos.TOP);
		backgroundButtons.setFill(Color.LIGHTGREY);
		backgroundButtons.setStroke(Color.BLACK);
		backgroundButtons.setStrokeWidth(1);
		
		/* The scroll bar with its property (Will have to change the content) */
		ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setVbarPolicy(ScrollBarPolicy.NEVER);
        sp.setPrefSize(900, 600);
        sp.setContent(buttons);
        sp.setFitToWidth(true);
	    
       
        Rectangle r = new Rectangle(100,100);
        r.setArcHeight(15);
        r.setArcWidth(15);
        buttons.setConstraints(r, 0, 10);
        
        int c = 0;
        for(int a = 0; a < 4; a++){  //instead of 4 we add the daysnumber value
        Rectangle r1 = new Rectangle(100,100);
        r1.setArcHeight(15);
        r1.setArcWidth(15);
        c = c +5 ;   //      
        buttons.setConstraints(r1, c, 10);
        buttons.getChildren().add(r1);
        }
        
        
		/* Getting the children */
		buttons.getChildren().addAll(backgroundButtons, addEvent,editEvent, editTimeline, saveTimeline,r); 
		root.getChildren().addAll(buttons,sp); 
		
		//days 
		
      //  buttons.getChildren().add(r); 
		
		/* Displaying the stage */
		Scene scene = new Scene(root,900,600); 
		primaryStage.setTitle("Timeline View");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}

   

	public static void main(String[] args) {
		launch(args); 

	}

}

