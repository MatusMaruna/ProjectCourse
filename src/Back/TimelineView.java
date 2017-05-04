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

public class TimelineView extends Application implements Initializable{


	/*make sure that the application.css and the timelineview.fxml is in the same package */	
	
	public static String labelname;
	
	@FXML
	private Label LabelNameView;
	
	// Event Listener on Button.onAction
	@FXML
	public void addEvent(ActionEvent event) {
		AddEvent add = new AddEvent();
		Stage stage = new Stage();
		try {
			
			add.start(stage);
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		stage.show();

	}
	
	
	// Event Listener on Button.onAction
	@FXML
	public void editEvent(ActionEvent event) {
		EditEvent event1 = new EditEvent();
		Stage stage = new Stage();
		try {
			event1.start(stage);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		stage.show();
		stage.setTitle("Edit Event");
	}
	
	
	// Event Listener on Button.onAction
	@FXML
	public void editTimeline(ActionEvent event) {
		EditTimeline timeline = new EditTimeline();
		Stage stage = new Stage();
		try {
			timeline.start(stage);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		stage.show();
		stage.setTitle("Edit Timeline");
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		 try {
			
			
    	    Parent root = FXMLLoader.load(getClass().getResource("timelineview.fxml"));
    	   
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			
			primaryStage.setScene(scene);
			primaryStage.show();

			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		
		launch(args);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LabelNameView.setText(labelname);
		
	}
}
