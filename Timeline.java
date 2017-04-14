package plugg;

import java.util.ArrayList;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.StackPane;

public class Timeline {
	
	ArrayList<Event> list = new ArrayList<Event>();
	String title;

	
	
	public Timeline(String s){
		title=s;
		
		
	}
	
	
	public void addEvent(Event e){
		list.add(e);
		
	}
	public void deleteEvent(Event e){
		
		list.remove(e);
		
	}
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	public void getEvent(Event e){
		if(list.contains(e))
		System.out.println(e.toString());
	}
	
	public Scene graphical(){
		GridPane paneroot = new GridPane();
		
		
		


	          for(int n = 0; n<list.size() ; n++)
	             {
	                StackPane stackPane = new StackPane();
	                stackPane.setPrefSize(150.0, 200.0);
	                stackPane.getChildren().addAll(new Label("Event: "+list.get(n).toString()));
	                paneroot.add(stackPane,n,1);
	             }
	         	paneroot.setGridLinesVisible(true);
		
		
		Scene scene = new Scene(paneroot,500,500);
		
		
		
		
		return scene;
		
	}
	
	
	
	
	
	
}
