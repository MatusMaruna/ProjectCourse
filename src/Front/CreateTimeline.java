package Front;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import Back.LoadTimeline;
import Back.Timeline;
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

public class CreateTimeline extends Application {
	String des; 
	String tlt; 
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
		createTimeline.setTranslateX(75);
		createTimeline.setTranslateY(250);
		cancelTimeline.setTranslateX(205);
		cancelTimeline.setTranslateY(250);
		createTimeline.setPadding(new Insets(10,10,10,10));
		cancelTimeline.setPadding(new Insets(10,10,10,10));
		
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
	    
		
		buttns.getChildren().addAll (grid, createTimeline,cancelTimeline); 
		root.getChildren().addAll(buttns); 
		Scene scene = new Scene(root, 300, 300);
		
		/* Here is what the buttons will do */
		createTimeline.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override 
			public void handle(ActionEvent event) { 
				des= description.getText(); 
				tlt = title.getText(); 
				start = startDatePicker.getValue(); 
				end = endDatePicker.getValue(); 
				//Timeline line = new Timeline(tlt,start,end,des); 
				//System.out.println(line.toString());  
				
				
				/*converting localdate-> yyyy/mm/dd to date-> dd/mm/yyyy calculating the days between them and starting the 
				 timelineview class*/
				

				
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
				  
				tm.start(primaryStage);
				primaryStage.show();

			}
		});
		cancelTimeline.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event){ 
				TimelineBuilder builder = new TimelineBuilder(); 
				builder.start(primaryStage);
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
