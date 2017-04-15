package Back;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Timeline {
	/* Timeline attributes */
	int id; 
	ArrayList<Event> events = new ArrayList<Event>();
	String title;
	LocalDate startTime; 
	LocalDate endTime;
	String description; 

	/* Timeline Constructor */
	public Timeline(String title, LocalDate startTime, LocalDate endTime, String description){
		setTitle(title); 
		setStartTime(startTime);
		setEndTime(endTime); 
		setDescription(description); 
	}
	/* Setters / Validators */
	public void setTitle(String title){
		if(title.length()>0){
			this.title = title; 
		}else{ 
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Title must have a value");

			alert.showAndWait();
		}
	}
	public void setStartTime(LocalDate startTime){ 
		if(startTime != null){
			this.startTime = startTime; 
		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Start date must have a value");

			alert.showAndWait();
	}
	}
	public void setEndTime(LocalDate endTime){ 
		if(startTime.isBefore(endTime)){ 
			this.endTime = endTime; 
		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("End date must have a value greater than start date");

			alert.showAndWait(); 
		}
	}
	public void setDescription(String description){ 
		if(description.length()>0){
			this.description = description; 
		}else{ 
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Description must have a value");

			alert.showAndWait();
		}	
	}
	/* Getters */
	public String getTitle(){ 
		return title; 
	}
	public LocalDate getStartTime(){
		return startTime; 
	}
	public LocalDate getEndTime(){ 
		return endTime; 
	}
	public String getDescription(){ 
		return description; 
	}
	/* Event manipulators */
	public void addEvent(Event e){
		events.add(e);
		
	}
	public void deleteEvent(Event e){
		
		events.remove(e);
		
	}
	public boolean hasEvents(){
		if(events.isEmpty()){ 
			return true; 
		}else{
			return false; 
		}
	}
	public void getEvent(Event e){
		if(events.contains(e))
		System.out.println(e.toString());
	}
	
	public Scene graphical(){
		GridPane paneroot = new GridPane();
		
		
		


	          for(int n = 0; n<events.size() ; n++)
	             {
	                StackPane stackPane = new StackPane();
	                stackPane.setPrefSize(150.0, 200.0);
	                stackPane.getChildren().addAll(new Label("Event: "+events.get(n).toString()));
	                paneroot.add(stackPane,n,1);
	             }
	         	paneroot.setGridLinesVisible(true);
		
		
		Scene scene = new Scene(paneroot,500,500);
		
		
		
		
		return scene;
		
	}
	@Override
	public String toString(){ 
	return "Timeline: " + "\n" + "Title : " + title + "\n" +  "Description: " + description + "\n" +  "Start Date: " + startTime + "\n" + "End Date: " + endTime; 
	}
	
	
	
	
}
