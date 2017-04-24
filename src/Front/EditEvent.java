package Front;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage; 

public class EditEvent extends Application {
	@Override
	public void start(Stage primaryStage){ 
		Button saveEvent = new Button("Save"); 
		Button deleteEvent = new Button("Delete");
		Button cancelEvent = new Button("Cancel");
		
		/*Playing around with Positioning*/
		VBox root = new VBox(); 
		root.setStyle("-fx-background-color: LIGHTGREY");
		root.setSpacing(10);

		/* Pane Positioning to get buttons beside each other */
		Pane buttns = new Pane(); 
		saveEvent.setTranslateX(75);
		saveEvent.setTranslateY(250);
		deleteEvent.setTranslateX(142);
		deleteEvent.setTranslateY(250);
		cancelEvent.setTranslateX(220);
		cancelEvent.setTranslateY(250);
		saveEvent.setPadding(new Insets(10,10,10,10));
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
		
		final TextArea description = new TextArea();
		description.setPromptText("Description ");
		description.setPrefWidth(20);
		description.setPrefHeight(100);
		description.wrapTextProperty();
		GridPane.setConstraints(description, 0, 2);
		grid.getChildren().add(description);
		
		/* Address textfield and button to upload*/
		final TextField address = new TextField();
		address.setPromptText("Address ");
		address.setAlignment(Pos.BASELINE_LEFT);
		address.setPrefWidth(200);
		GridPane.setConstraints(address, 0, 3);
		grid.getChildren().add(address);
		
		Button uploadEvent = new Button("Upload");
		uploadEvent.setTranslateX(300);
		uploadEvent.setTranslateY(170);
		uploadEvent.setPadding(new Insets(5,10,5,10));
		
		buttns.getChildren().addAll (grid,uploadEvent, saveEvent, deleteEvent, cancelEvent); 
		root.getChildren().addAll(buttns); 
		Scene scene = new Scene(root, 400, 350);
		
		/* Here is what the buttons will do */
		saveEvent.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event) { 
			Label label = new Label("Save Event");
			label.setTextFill(Color.RED);
			buttns.getChildren().clear(); 
			buttns.getChildren().addAll(label);
			primaryStage.show(); 
				
			}
		});
		deleteEvent.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event){ 
			Label label = new Label("Delete Event");
			label.setTextFill(Color.RED);
			buttns.getChildren().clear(); 
			buttns.getChildren().addAll(label); 
			primaryStage.show(); 
			}
		}); 
		
		cancelEvent.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event){ 
			Label label = new Label("Cancel Event");
			label.setTextFill(Color.RED);
			buttns.getChildren().clear(); 
			buttns.getChildren().addAll(label); 
			primaryStage.show(); 
			}
		}); 
		
		uploadEvent.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event){ 
			Label label = new Label("Upload Event");
			label.setTextFill(Color.RED);
			buttns.getChildren().clear(); 
			buttns.getChildren().addAll(label); 
			primaryStage.show(); 
			}
		}); 		
		
		/* Displaying the stage */
		primaryStage.setTitle("Edit Event");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}
	public static void main(String[] args) {
		launch(args); 
	}
}
