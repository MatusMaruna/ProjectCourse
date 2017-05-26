package project;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;


import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;;

public class TimelineView extends Application {

	public static int daysnumber;
	public static Date firstdate;
	public static Date lastdate;
	private String title;
	private String start;
	private String end;
	private String dis;
	public int TimeID;
	public Timeline tm;
	public Event event;
	public ArrayList<Event> events = new ArrayList<Event>();
	private String[] weekdays = { "Sun." , "Mon." , "Tue." , "Wed." , "Thu." , "Fri." , "Sat."};

	private Color[] color = { Color.BLUE, Color.RED, Color.AQUA, Color.YELLOW, Color.PURPLE, Color.HOTPINK };

	VBox vbox = new VBox();
	GridPane buttons = new GridPane();

	public TimelineView() {
	}

	public TimelineView(String tlt, Date start, Date end, String dis) {
		// tlt,start,end,des
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		this.title = tlt;
		this.start = df.format(start);
		this.end = df.format(end);
		this.dis = dis;

	}

	@Override
	public void start(Stage primaryStage) throws SQLException {

		/*
		 * creating an object for the timeline with the correct title and
		 * getting the id so we can work with the events
		 */
		Connection con = Sqlconnection.DbConnector();
		try {

			
			String sql1 = "select * from Timeline ";
			PreparedStatement pre = con.prepareStatement(sql1);
			ResultSet result = pre.executeQuery();
			while (result.next()) {
			  	tm = new Timeline(result.getRow(), result.getString("Title"), result.getString("Description"),

						result.getString("StartDate"), result.getString("EndDate"));

				if (tm.getTitle().equals(title) && (tm.getDescription()).equals(dis)
						&& (tm.getStartTime()).equals(start) && (tm.getEndTime()).equals(end)) {
				
					TimeID = tm.getId();
					

				}

			}
			 
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/* taking the events that belong to that timeline */

		
		String sql = "select * from SaveEvent where Id='"
				+ TimeID + "'";
		try {
			PreparedStatement pre1 = con.prepareStatement(sql);
			
		
			
			ResultSet result1 = pre1.executeQuery();
			while (result1.next()) {events.add(new Event(result1.getInt("ID"), result1.getString("Name"), result1.getString("Description"),

					result1.getString("StartDate"), result1.getString("EndDate")));}
			
			result1.close();
			pre1.close();
			
			}catch(Exception e3){
				
			}
			

		String editname = title;

		/* Buttons with attributes */
		Button addEvent = new Button("Add Event");
		Button editEvent = new Button("Edit Event");
		Button editTimeline = new Button("Edit Timeline");
		Button homeBut = new Button("Home");

		
		
		System.out.println(events.size());
		/* Buttons get the same size */
		addEvent.setMaxWidth(Double.MAX_VALUE);
		editEvent.setMaxWidth(Double.MAX_VALUE);
		editTimeline.setMaxWidth(Double.MAX_VALUE);
		homeBut.setMaxWidth(Double.MAX_VALUE);

		/* Position of the buttons */
		GridPane.setMargin(addEvent, new Insets(5, 5, 5, 5));
		GridPane.setConstraints(addEvent, 52, 0);
		GridPane.setMargin(editEvent, new Insets(5, 5, 5, 5));
		GridPane.setConstraints(editEvent, 52, 1);
		GridPane.setMargin(editTimeline, new Insets(5, 5, 5, 5));
		GridPane.setConstraints(editTimeline, 52, 2);


		GridPane.setMargin(homeBut, new Insets(5, 5, 5, 5));
		GridPane.setConstraints(homeBut, 52, 2);

		editTimeline.setMinWidth(Region.USE_PREF_SIZE);

		/* The background of the buttons */
		Rectangle backgroundButtons = new Rectangle(120, 110);
		GridPane.setConstraints(backgroundButtons, 24, 0, 29, 5, HPos.RIGHT, VPos.TOP);
		backgroundButtons.setFill(Color.BLACK);
		backgroundButtons.setStroke(Color.BLACK);
		backgroundButtons.setStrokeWidth(1);

		/* The scroll bar with its property (Will have to change the content) */
		ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        
		sp.setContent(buttons);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		sp.setPrefViewportWidth(1000);
		sp.setPrefViewportHeight(1000);

		/*
		 * storing the Timelines dates inside this array so we will be able to
		 * print them
		 */
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<String> days = printing(getDaysBetweenDates(firstdate, lastdate));
		//adding the last day bcs the previus array will not include the last date
        days.add(df.format(lastdate));
        
		/*
		 * This loop will print the rectangles with the dates inside and the
		 * line for the events, for now a line will appear from the EventStart
		 * integer till the EventEnd integer, in the future the line will appear
		 * between the first and last day of the event
		 */

		
		// Calculating the duration of all the events in the list
		Date StartDate = null;
		Date EndDate = null;
		
		//event start point and end point
		int EndPoint = 0;
		int StrartPoint = 0;
		int Eventduration = 0;
		
		for(int i = 0; i < events.size(); i ++) {
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				StartDate = sdf.parse(events.get(i).getStartDate());
				EndDate = sdf.parse(events.get(i).getEndDate());

				/*
				 * If the duration is 0 then is non durational even - added in
				 * the event class a method that just returns false if is not
				 * durational - true if it is
				 */
				EndPoint = LoadTimeline.daysBetween(firstdate, EndDate) ;
				StrartPoint = LoadTimeline.daysBetween(firstdate, StartDate) ;
                Eventduration = LoadTimeline.daysBetween(StartDate, EndDate);
                
                
                events.get(i).setEndPoint(EndPoint);
				events.get(i).setEventDuration(Eventduration);
				events.get(i).setStartPoint(StrartPoint);

			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		
		int EventStart = 0;
		int EventEnd = 0;
		int theDay = firstdate.getDay();
	
		
		// rectangles/dates printing also the day for example if it is Monday it would be Mon
       
		for (int a = 0; a < days.size() ; a++) {
			
			/*This part will print the rectangles that represent a date*/
			Rectangle r1 = new Rectangle(120, 80);
			r1.strokeLineCapProperty();
			r1.setFill(Color.DARKORANGE);
			GridPane.setConstraints(r1, a, 2);
			
			
			/*The part that print the dates inside the rectangles*/
			Label lb = new Label();
            lb.setText("  "+days.get(a));
            GridPane.setConstraints(lb, a, 2);
			
			
			/*This part will find out the day and print above the dates */
            if(theDay == 7)
				theDay = 0;
        
            Label weekday = new Label();
            weekday.setText("  "+weekdays[theDay]);
			weekday.setTextFill(Color.DARKORANGE);
			weekday.setFont(Font.font("Cambria", FontWeight.SEMI_BOLD, 15));
			theDay ++;
			GridPane.setConstraints(weekday, a, 1);
			
			
			//adding everything to the grid
	    	buttons.getChildren().addAll(weekday,r1, lb);

		}
       
		
		
		// line/events printing
		int possision = 3;
		int thecolor = 0;
		
		
		//grid possisioning
		int GridStartPossision = 0;
		int GridEndPossision = 0;
		int duration = 0;
		
		for (int k = 0; k < events.size(); k++) {
			
			
        
	   if (events.get(k).durational()) {
                   
		        /*Bcs of the vgap the line will lose some width but with this method we are adding it back */
		        duration = events.get(k).getEventduration() * 5 ; 
				
				
				//line possisioning 
				EventStart = events.get(k).getStartPoint() * 120 ;
				EventEnd = events.get(k).getEndPoint()* 120 + 120 + duration;
				
				
				//gridpane possisioning
				/*if the event has one day  it would print the line for 1 rectangle this part will make it print it correctly*/
				
			    GridEndPossision  = events.get(k).getEndPoint()+1;
				GridStartPossision = events.get(k).getStartPoint();
				
				
             
				
				
				/*This part will print the line that represent the event*/
				Line ln = new Line(EventStart, possision, EventEnd , possision);
				ln.smoothProperty();
				ln.setStrokeWidth(15);
                ln.setStroke(color[thecolor]);
                GridPane.setConstraints(ln, GridStartPossision, possision, GridEndPossision , possision);
				
                
                /*Printing the name of the events inside the lines*/
                Label thetitle = new Label();
				thetitle.setText("                      "+ events.get(k).getName());
				thetitle.setFont(Font.font("Cambria", FontWeight.BLACK, 15));
				GridPane.setConstraints(thetitle, GridStartPossision , possision, GridEndPossision , possision);
				
				
				/*The event view method*/
				String nm = events.get(k).getName();
                ln.setOnMouseClicked(e -> {
					try {
						EventView view = new EventView(nm); // This will display
															// the info about
															// the event found
															// by name
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // what happens when you click the black line

				});
				

				
				
				
				if (thecolor == 5)
					thecolor = 0;
				else
					thecolor++;
				
				possision ++ ;

			buttons.getChildren().addAll(ln, thetitle);
			} else {

				/*
				 * adding a circle above the days if there is a non durational event
				 */
				
				EventStart = events.get(k).getStartPoint();
				
				/*Printing the circle*/
				Circle r1 = new Circle(25);
				r1.strokeLineCapProperty();
				r1.setFill(Color.WHITE);
				GridPane.setConstraints(r1, EventStart, possision);
				
				/*Printing the title*/
				Label thetitle = new Label();
				thetitle.setText("  " + events.get(k).getName());
				thetitle.setFont(Font.font("Verdana", FontWeight.MEDIUM, 15));
				GridPane.setConstraints(thetitle, EventStart, possision);
				
				
				String nm = events.get(k).getName();
				r1.setOnMouseClicked(e -> {
					try {
						EventView view = new EventView(nm); // This will display
															// the info about
													        // the event found
															// by name
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // what happens when you click the black line

				});

				
				possision ++;
				
                
				buttons.getChildren().addAll(r1, thetitle);

			}
	   
		}

		/* giving functionality to the buttons */
		addEvent.setTextFill(Color.BLUE);
		addEvent.setOnAction(e -> {

			AddEvent add = new AddEvent(TimeID,firstdate,lastdate);
			try {
				add.start(primaryStage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			primaryStage.show();

		});
		editEvent.setTextFill(Color.BLUE);
		editEvent.setOnAction(e -> {

			EditEvent event = new EditEvent(TimeID,firstdate,lastdate);

			try {
				event.start(primaryStage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			primaryStage.show();
			primaryStage.setTitle("Edit Event");
		});
		editTimeline.setTextFill(Color.BLUE);
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

		homeBut.setTextFill(Color.BLUE);
		homeBut.setOnAction(e -> {

			TimelineBuilder timebult = new TimelineBuilder();

			try {
				timebult.start(primaryStage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			primaryStage.show();

		});

		GridPane gp = new GridPane();
	     
		HBox hb = new HBox();
		/* Getting the children + just playing around with the colours */
		buttons.setStyle("-fx-background-color:  #000000;");
		buttons.setHgap(5);
		buttons.setVgap(10);
		backgroundButtons.setStyle("-fx-background-color: red;");
		hb.setAlignment(Pos.TOP_RIGHT);
		sp.setStyle("-fx-background-color:  blue;");
		
		hb.getChildren().addAll(homeBut);
		hb.setPadding(new Insets(20, 10, 10, 10));
		
		vbox.setPadding(new Insets(10, 20, 20, 20));
		gp.getChildren().addAll(backgroundButtons, addEvent, editEvent, editTimeline);

		vbox.getChildren().addAll(gp, hb, buttons, sp);
		vbox.setStyle("-fx-background-color: lightgrey;");
		/* Displaying the stage */
		Scene scene = new Scene(vbox, 1200, 700);
		primaryStage.setTitle("Timeline View");
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();
	}

	/*
	 * creating a method that will store the days between the start - end date
	 */

	private List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startdate);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		while (calendar.getTime().before(enddate)) {
			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		printing(dates);
		return dates;
	}

	/* putting the dates into an array list so we ill be able to print them */
	private ArrayList<String> printing(List<Date> thelist) {
		ArrayList<String> dates = new ArrayList<String>();

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		for (int a = 0; a < thelist.size(); a++) {
			dates.add(df.format(thelist.get(a)));
		}

		return dates;
	}
}

