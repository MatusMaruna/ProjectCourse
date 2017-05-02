package Front;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TimelineView extends Application {
	@Override
	public void start(Stage primaryStage){ 
		HBox root = new HBox();
		VBox buttons = new VBox(); 
		Button addEvent = new Button("Add Event"); 
		Button editEvent = new Button("Edit Event"); 
		buttons.setAlignment(Pos.TOP_LEFT);
		root.getChildren().addAll(buttons); 
		
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

