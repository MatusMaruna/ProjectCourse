package Back;

import java.time.LocalDate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage; 

public class EditTimeline extends Application {
	@Override
	public void start(Stage primaryStage){ 
		Button submitToTimeline = new Button("Submit"); 
		Button deleteEvent = new Button("Delete");
		Button cancelEvent = new Button("Cancel");
		
		/*Playing around with Positioning*/
		VBox root = new VBox(); 
		root.setStyle("-fx-background-color: LIGHTGREY");
		root.setSpacing(10);

		/* Pane Positioning to get buttons beside each other */
		Pane buttns = new Pane(); 
		submitToTimeline.setTranslateX(75);
		submitToTimeline.setTranslateY(250);
		deleteEvent.setTranslateX(142);
		deleteEvent.setTranslateY(250);
		cancelEvent.setTranslateX(205);
		cancelEvent.setTranslateY(250);
		submitToTimeline.setPadding(new Insets(10,10,10,10));
		deleteEvent.setPadding(new Insets(10,10,10,10));
		cancelEvent.setPadding(new Insets(10,10,10,10));
		
		/* Grid with title and description*/
		GridPane grid = new GridPane();
		grid.setHgap(20);
	    	grid.setVgap(10);
	    	grid.setPadding(new Insets(10, 0, 0, 80));
		final TextField title = new TextField();
		title.setPromptText("Title");
		title.setAlignment(Pos.BASELINE_LEFT);
		GridPane.setConstraints(title, 0, 1);
		grid.getChildren().add(title);
		
		final TextField description = new TextField();
		description.setPromptText("Description ");
		description.setAlignment(Pos.BASELINE_LEFT);
		GridPane.setConstraints(description, 0, 2);
		grid.getChildren().add(description);
		
		
		/* Datepicker */
		DatePicker startDatePicker = new DatePicker();
	    	DatePicker endDatePicker = new DatePicker();
	    
	    	startDatePicker.setValue(LocalDate.now());
	    	endDatePicker.setValue(startDatePicker.getValue().plusDays(1));

	    	grid.add(new Label("Start Date:"), 0, 3);
	    	GridPane.setConstraints(startDatePicker, 0, 4);
	    	grid.getChildren().add(startDatePicker);
	    
	    	grid.add(new Label("End Date:"), 0, 5);
	    	GridPane.setConstraints(endDatePicker, 0, 6);
	    	grid.getChildren().add(endDatePicker);
	    
		
		buttns.getChildren().addAll (grid, submitToTimeline, deleteEvent, cancelEvent); 
		root.getChildren().addAll(buttns); 
		Scene scene = new Scene(root, 300, 300);
		
		/* Here is what the buttons will do */
		submitToTimeline.setOnAction(new EventHandler<ActionEvent>(){	
			@Override 
			public void handle(ActionEvent event) { 
			Label label = new Label("Add new Timeline script here");
			label.setTextFill(Color.RED);
			buttns.getChildren().clear(); 
			buttns.getChildren().addAll(label);
			primaryStage.show(); 
			}
		});
		
		deleteEvent.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event){ 
			Label label = new Label("Load new Timeline script here");
			label.setTextFill(Color.RED);
			buttns.getChildren().clear(); 
			buttns.getChildren().addAll(label); 
			primaryStage.show(); 
			}
		}); 
		
		cancelEvent.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event){ 
			Label label = new Label("Load new Timeline script here");
			label.setTextFill(Color.RED);
			buttns.getChildren().clear(); 
			buttns.getChildren().addAll(label); 
			primaryStage.show(); 
			}
		}); 
		
		/* Displaying the stage */
		primaryStage.setTitle("Edit Timeline");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}
	public static void main(String[] args) {
		launch(args); 
	}
}