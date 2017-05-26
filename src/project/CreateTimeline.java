package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.LoadTimeline;
import project.Timeline; 

public class CreateTimeline extends Application {
	public String des; 
	public String tlt; 
	LocalDate start; 
	LocalDate end; 
	
	@Override
	public void start(Stage primaryStage){ 
		Button createTimeline = new Button("Create"); 
		Button cancelTimeline = new Button("Cancel");
		
		
		/*Playing around with Positioning*/
		VBox root = new VBox(); 
		root.setStyle("-fx-background-color: LIGHTGREY");
		root.setSpacing(10);

		/* Pane Positioning to get buttons beside each other */
		Pane buttns = new Pane(); 
		createTimeline.setTranslateX(65);
		createTimeline.setTranslateY(250);
		cancelTimeline.setTranslateX(185);
		cancelTimeline.setTranslateY(250);
		createTimeline.setPadding(new Insets(10,10,10,10));
		cancelTimeline.setPadding(new Insets(10,10,10,10));
		
		/* Grid with title and description*/
		GridPane grid = new GridPane();
		grid.setHgap(20);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(10, 0, 0, 65));
		final TextField title = new TextField();
		title.setPromptText("Title");
		title.setAlignment(Pos.BASELINE_LEFT);
		GridPane.setConstraints(title, 0, 1);
		grid.getChildren().add(title);
		
		final TextField description = new TextField();
		description.setPromptText("Description (optional) ");
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
	    
	
		buttns.getChildren().addAll (grid, createTimeline,cancelTimeline); 
		
		root.getChildren().addAll(buttns); 
		Scene scene = new Scene(root, 300, 300);
		
		/* Here is what the buttons will do */
		createTimeline.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override 
			public void handle(ActionEvent event) { 
			    if(title.getText().isEmpty()||startDatePicker.getValue()==null||endDatePicker.getValue()==null){
			    	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error ");
					alert.setHeaderText("Fill in required fields");
					alert.setContentText("Only description is optional");
					alert.showAndWait();
			    }
				
				
				else if(startDatePicker.getValue().isAfter(endDatePicker.getValue())){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error ");
				alert.setHeaderText("Invalid dates");
				alert.setContentText("End date cannot be earlier than start date");
				alert.showAndWait();
				}
				else{
				des= description.getText(); 
				tlt = title.getText(); 
				start = startDatePicker.getValue(); 
				end = endDatePicker.getValue(); 
				//Timeline line = new Timeline(tlt,start,end,des); 
				//System.out.println(line.toString());  
				
				
				/*converting localdate-> yyyy/mm/dd to date-> dd/mm/yyyy calculating the days between them and starting the 
				 timelineview class
				 
				 also saving the timeline before we go to timelineview class
				 */
				
				
				
				
				try {
	    	    	
				
				 Calendar cal1 = new GregorianCalendar();
				 Calendar cal2 = new GregorianCalendar();

				 Date date = java.sql.Date.valueOf(start);
				 Date date2 = java.sql.Date.valueOf(end);
				 
				 TimelineView tm = new TimelineView(tlt,date,date2,des); 
				 
				 
				 
				 cal1.setTime(date);
				 TimelineView.firstdate = date;
				 
      
				 cal2.setTime(date2);
				 TimelineView.lastdate = date2;
				 
				 LoadTimeline ld = new LoadTimeline();
				  
				  TimelineView.daysnumber = ld.daysBetween(cal1.getTime(),cal2.getTime());
				  
				  DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				  
				 				  
				  
				    
				     String query = "INSERT INTO TimeLine (Title, Description, StartDate, EndDate) VALUES (?,?,?,?)  ";
	    	    	 Connection con = Sqlconnection.DbConnector();
	    	         PreparedStatement pre = con.prepareStatement(query);
	    	       
	    	     
	    	         
	    			System.out.println(title);
	    			pre.setString(1, tlt);
	    			pre.setString(2, des);

	    			pre.setString(3, df.format(date));
	                pre.setString(4, df.format(date2));
 
	            
	                pre.execute();
	               
				  
				  
				
				
	    		     pre.close();
	    		  
	    		  tm.start(primaryStage);
	    		  
	    		  
	    		  
				
    		/*	pre1.executeQuery();
                pre1.close();*/
    			
    				   
    			       
    			} catch (SQLException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
               
    			
    			
    		
			}
			}
		});
		cancelTimeline.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event){ 
				TimelineBuilder builder = new TimelineBuilder(); 
				try {
					builder.start(primaryStage);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				primaryStage.show();  
			}
		}); 
		
		/* Displaying the stage */
		primaryStage.setTitle("Create Timeline");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}
	public static void main(String[] args) {
		launch(args); 
	}
}