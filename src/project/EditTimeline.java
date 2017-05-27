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
import javafx.scene.text.Font;
import javafx.stage.Stage; 

public class EditTimeline extends Application {
	public String theName;
	
	
	public Connection con ;
	public PreparedStatement pre ;
	public ResultSet result = null;
	public int TimeID;
	public Timeline tm;
	public Event event; 
	
	//timeline boundaries
	public Date EventFirstdate;
	public Date EventLastDate;
	public boolean pass ;
	public String dis;
	
	
	public  EditTimeline(String s){
		this.theName = s;
		this.pass = true;
	}
	
	
	@Override
	public void start(Stage primaryStage) throws SQLException, ParseException{ 	
		
		Connection con = Sqlconnection.DbConnector();
		
		try 
		{
			
		    String sql = "SELECT * FROM TimeLine";
		    PreparedStatement pre1 = con.prepareStatement(sql);
		    ResultSet result = pre1.executeQuery();
		    while (result.next())
		    {
		    	 if(result.getString("Title").equals(theName)){
			        this.TimeID = result.getRow();   
				    System.out.println(TimeID);
				    this.tm = new Timeline(result.getRow(), result.getString("Title"), result.getString("Description"),
							result.getString("StartDate"), result.getString("EndDate"));  
				   
			    }
		    }
		    
		    result.close();
			pre1.close();
			
		    
		} 
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		
		
		
		
	
		
		Button submit = new Button("Submit"); 
		Button deleteTimeline = new Button("Delete");
		Button cancel = new Button("Cancel");
		
		/*Playing around with Positioning*/
		VBox root = new VBox(); 
		//root.setStyle("-fx-background-color: LIGHTGREY");
		root.setSpacing(10);

		/* Pane Positioning to get buttons beside each other */
		Pane buttns = new Pane(); 
		submit.setTranslateX(75);
		submit.setTranslateY(250);
		deleteTimeline.setTranslateX(142);
		deleteTimeline.setTranslateY(250);
		cancel.setTranslateX(205);
		cancel.setTranslateY(250);
		submit.setPadding(new Insets(10,10,10,10));
		deleteTimeline.setPadding(new Insets(10,10,10,10));
		cancel.setPadding(new Insets(10,10,10,10));
		
		/* Grid with title and description*/
		GridPane grid = new GridPane();
		grid.setHgap(20);
	    	grid.setVgap(10);
	    	grid.setPadding(new Insets(10, 0, 0, 80));
		final TextField title = new TextField();
		title.setPromptText(theName);
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
	    
		
		buttns.getChildren().addAll (grid, submit, deleteTimeline, cancel); 
		root.getChildren().addAll(buttns); 
		Scene scene = new Scene(root, 300, 300);
		scene.getStylesheets().add("project/application.css");
		
		/* Here is what the buttons will do */
		submit.setOnAction(	e -> {			
			try 
			{  
				
				
				LocalDate	start = startDatePicker.getValue(); 
				LocalDate	end = endDatePicker.getValue(); 	
				
			 EventFirstdate = java.sql.Date.valueOf(start);
			 EventLastDate = java.sql.Date.valueOf(end);
				
			
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date temp;
				Date temp2;
			    String sql = "SELECT * FROM SaveEvent WHERE id ='"+ TimeID +"'";
			    PreparedStatement pre = con.prepareStatement(sql);
			    ResultSet result = pre.executeQuery();
			    ResultSet result2 = pre.executeQuery();
			    
			    if ( LoadTimeline.daysBetween( EventFirstdate, EventLastDate) <= 0 ){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("infor dialog");
					alert.setHeaderText(null);
					alert.setContentText("Start date needs to be less than end date.");
					alert.showAndWait();
					pass = false;
    			}
			  
		    	
		    	
			     while (result.next())
			    {   
			    
			    	   temp = sdf.parse(result.getString("StartDate"));  
				 if( LoadTimeline.daysBetween(temp, EventFirstdate) < 0 ){
				    	Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("infor dialog");
						alert.setHeaderText(null);
						alert.setContentText("Timeline out of boundaries the existing event "+result.getString("Name")+" starts before ");
						alert.showAndWait();
						pass = false;
						break;
					 }
				    
			    }
			     
			     
			     while (result2.next())
			     {   
			    	 temp2 = sdf.parse(result.getString("EndDate"));
			     if( LoadTimeline.daysBetween(temp2, EventLastDate)   > 0 ){
				    	Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("infor dialog");
						alert.setHeaderText(null);
						alert.setContentText("Timeline out of boundaries the existing event "+result.getString("Name")+" ends after ");
						alert.showAndWait();
						pass = false;
						break;
					 }
			     }
			    
			    
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			if(pass == true){
			
	    	try {
	    	   	String query =  "UPDATE TimeLine SET Title = ?, Description = ?, StartDate = ?, EndDate = ? WHERE rowid='"+ tm.getId() + "'";
	    	   	PreparedStatement pre = con.prepareStatement(query);
	    	   	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	    	pre = con.prepareStatement(query);
    			pre.setString(1, title.getText());
    			pre.setString(2, description.getText());

    			pre.setString(3, df.format(EventFirstdate));

    			pre.setString(4, df.format(EventLastDate));
    			
					pre.execute();
					pre.close();
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("infor dialog");
					alert.setHeaderText(null);
					alert.setContentText("Timeline changes submited");
					alert.showAndWait();	
				//	String tlt, Date start, Date end, String dis
		  LoadTimeline tm = new  LoadTimeline();
	    	tm.start(primaryStage);
		  primaryStage.show();
					
	    	}catch (SQLException e1) {
	    			e1.printStackTrace();
	    		} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
	    		
			}
			else{
				
				EditTimeline ed = new	EditTimeline(theName);
				try {
					ed.start(primaryStage);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				primaryStage.show();
				
			}
			
			
			
			}); 
		
		deleteTimeline.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override 
			public void handle(ActionEvent event)
			{ 
					
					try
					{
						String query = "DELETE FROM TimeLine WHERE rowid='"+ tm.getId() + "'";
						pre = con.prepareStatement(query);
						pre.execute();
					}
					catch (SQLException e) 
					{
						e.printStackTrace();
					}
					
					try
					{
						String query = "DELETE FROM SaveEvent WHERE ID='"+ tm.getId() + "'";
						pre = con.prepareStatement(query);
						pre.execute();
					}
					catch (SQLException e ) 
					{
						e.printStackTrace();
					}
				
				LoadTimeline edit = new LoadTimeline();
				try
				{
					edit.start(primaryStage);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
					primaryStage.show(); 	
				}
			
		}); 
		
		cancel.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event){ 
				LoadTimeline edit = new LoadTimeline();
			try{
				edit.start(primaryStage);
			}catch (Exception e){
				e.printStackTrace();
			}
			
			primaryStage.show(); 
			}
		}); 

		/* Displaying the stage */
		primaryStage.setTitle("Edit Timeline: "+theName);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}
	
	public static void main(String[] args) {
		launch(args); 
	}
}
