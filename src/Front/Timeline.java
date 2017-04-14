package Front;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Timeline {
	/* Timeline attributes */
	int id; 
	ArrayList<Event> events = new ArrayList<Event>();
	String title;
	int startTime; 
	int endTime;
	String description; 

	/* Timeline Constructor */
	public Timeline(String title, int startTime, int endTime, String description){
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
			System.err.println("Error on title");
		}
	}
	public void setStartTime(int startTime){ 
		if(startTime>0){
			this.startTime = startTime; 
		}else{
			System.err.println("Error on startTime"); 
	}
	}
	public void setEndTime(int endTime){ 
		if(endTime>startTime){ 
			this.endTime = endTime; 
		}else{
			System.err.println("Error on endTime"); 
		}
	}
	public void setDescription(String description){ 
		if(description.length()>0){
			this.description = description; 
		}else{ 
			System.err.println("Error on description");
		}	
	}
	/* Getters */
	public String getTitle(){ 
		return title; 
	}
	public int getStartTime(){
		return startTime; 
	}
	public int getEndTime(){ 
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
	
	
	
	
	
	
}
