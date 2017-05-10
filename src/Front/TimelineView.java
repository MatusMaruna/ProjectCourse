package Front;
import javafx.scene.shape.Shape;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import Back.AddEvent;
import Back.EditEvent;
import Back.Sqlconnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;


import Back.EditTimeline;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Calendar;
import  java.util.Date ;
import java.util.GregorianCalendar;
import java.util.List;
import Back.Sqlconnection; 
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TimelineView extends Application{

	public static int daysnumber ;
	
	public static Date firstdate ;
	public static Date lastdate ;
	 
	 private String title;
	 private String start;
	 private String end;
	 private String dis;
	 
     public	TimelineView(){}
 
 public TimelineView(String tlt, Date start, Date end,String dis){
	 //tlt,start,end,des
	 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	 this.title = tlt;
	 this.start = df.format(start);
	 this.end = df.format(end);
	 this.dis = dis;
	 
 }
	
	
    @Override
	public void start(Stage primaryStage){ 
    	
		HBox root = new HBox();
		
		GridPane buttons = new GridPane();
		
		System.out.println("Days= "+daysnumber);
		System.out.println(title);
		String editname = title;
		
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
		buttons.setHgap(2);
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
	    
       /*storing dates inside this array */
        ArrayList<String> days = printing(getDaysBetweenDates(firstdate, lastdate));
       
       
       
       
       /*This loop will print the rectangles with the dates inside and the line for the events,
        for now a line will appear from the EventStart integer till the EventEnd integer, in the future the line will appear between
        the first and last day of the event*/
        
        int EventStart = 0;
        int EventEnd = 0;
        
        
        
        for(int a = 0; a < daysnumber; a++){  
        Rectangle r1 = new Rectangle(150,100);
        
        
        
        Label lb = new Label();
        
        lb.setText(days.get(a));
       
        r1.strokeLineCapProperty();
        r1.setFill(Color.AQUA);
        
        
        //line printing
        if(a > EventStart && a < EventEnd){
        	
       	 Line ln = new Line(EventStart,10,EventEnd*150,10);
       	 ln.setFill(Color.BLACK);
            ln.smoothProperty();
            ln.setStrokeWidth(10);
            buttons.setConstraints(ln,EventStart,10,EventEnd*150,10);
            buttons.getChildren().add(ln);
            
       }
       
       
        buttons.setConstraints(lb, a, 10);
        buttons.setConstraints(r1, a, 10);
        
        buttons.getChildren().addAll(r1,lb);
        }
        
        /*giving functionality to the buttons */
    	addEvent.setOnAction(e -> {

			AddEvent add = new AddEvent();
			try {
				add.start(primaryStage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			primaryStage.show();

		});
        
        
    	editEvent.setOnAction(e -> {

			EditEvent event = new EditEvent();

			try {
				event.start(primaryStage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			primaryStage.show();
			primaryStage.setTitle("Edit Event");
		}); 
    	
    	
        
    	editTimeline.setOnAction(e -> {
          
			EditTimeline event = new EditTimeline(editname);
           
			try {
				event.start(primaryStage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			primaryStage.show();
			primaryStage.setTitle("Edit Timeline");
		}); 
        
    	
    	/*Saving Timeline in the TimeLine table inside the database, we did not connect that table yet 
    	 so it would only appear inside the database  */
    	
    	saveTimeline.setOnAction(e -> {
    		
    		 
    	    try {
    	    	String query = "INSERT INTO TimeLine (Title, Description, StartDate, EndDate) VALUES (?,?,?,?)";
    	    	 Connection con = Sqlconnection.DbConnector();
    	         PreparedStatement pre = con.prepareStatement(query);
    			System.out.println(title+"ti ston poutso");
    			pre.setString(1, title);
    			pre.setString(2, dis);

    			pre.setString(3, start);
                pre.setString(4, end);
    		 
    			pre.execute();
    			pre.close();
    			
    			System.out.println("Saved");
    			
    		} catch (SQLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
			
		}); 
        
        
    	
    	
    	
        
        
        
        
        
		/* Getting the children */
		buttons.getChildren().addAll(backgroundButtons, addEvent,editEvent, editTimeline, saveTimeline); 
		root.getChildren().addAll(buttons,sp); 
		
		
		
    
		
		/* Displaying the stage */
		Scene scene = new Scene(root,900,600); 
		primaryStage.setTitle("Timeline View");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}
    
    
    /*creating a method that will store the days between the start - end date */
    
    
    private List<Date> getDaysBetweenDates(Date startdate, Date enddate)
    {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);
       // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        while (calendar.getTime().before(enddate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        printing(dates);
        return dates;
    }
    /*putting the dates into an array list so we ill be able to print them*/
    private ArrayList<String> printing(List<Date> thelist){
    	ArrayList<String> dates = new ArrayList<String>();
    	
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for(int a = 0; a < thelist.size(); a++){
        dates.add(df.format(thelist.get(a)));	
        }
        
        return dates;
    }
    
    
   

	public static void main(String[] args) {
		launch(args); 

	}

}

