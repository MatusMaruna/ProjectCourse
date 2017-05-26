package project;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class LoadTimeline extends Application {

	public static void main(String[] args) {

		launch(args);
	}
	public TableView<Timeline> table;
	public ObservableList<Timeline> data;
	public TableColumn col1, col2, col3, col4;
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {
		
		Connection con = Sqlconnection.DbConnector();
		GridPane gp = new GridPane();
		CheckConection();
		Scene scene = new Scene(gp, 700, 500, Color.rgb(200, 19, 128));
		

		stage.setScene(scene);
		stage.show();
		stage.setTitle("Load Timeline");
		VBox root = new VBox();
		//root.setStyle("-fx-background-color: LIGHTGREY");
		root.setSpacing(10);


	    table = new TableView<>();
		data = FXCollections.observableArrayList();
		col1 = new TableColumn<>("Name");
		col1.setMaxWidth(100);
		col2 = new TableColumn<>("Description");
		col2.setMaxWidth(100);
		col3 = new TableColumn<>("StartDate");
		col3.setMaxWidth(100);
		col4 = new TableColumn<>("EndDate");
		col4.setMaxWidth(100);
		table.getColumns().addAll(col1, col2, col3, col4);
		table.setTableMenuButtonVisible(true);
		col1.getTypeSelector();
	    data.clear();
	    
	    //grabbing all timelines from database on load and show them in the list
		try {
			String sql = "select * from Timeline ";
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet result = pre.executeQuery();
				while (result.next()) {
					data.add(new Timeline(result.getRow(), result.getString("Title"), result.getString("Description"),
					result.getString("StartDate"), result.getString("EndDate")));
					col1.setCellValueFactory(new PropertyValueFactory<>("title"));
					col2.setCellValueFactory(new PropertyValueFactory<>("description"));
					col3.setCellValueFactory(new PropertyValueFactory<>("startTime"));
					col4.setCellValueFactory(new PropertyValueFactory<>("endTime"));
					table.setItems(data);
					}
			pre.close();
			result.close();
			} catch (SQLException e1) {

			e1.printStackTrace();
			}
		

		BorderPane borderP = new BorderPane();
		
		Button editimeline = new Button("Edit Timeline");
		editimeline.setFont(new Font(11));
		editimeline.setPadding(new Insets(10, 10, 10, 10));
		editimeline.setPrefWidth(85);
		editimeline.setPrefHeight(25);
		
		editimeline.setOnAction(e -> {
			
			Timeline tm = table.getSelectionModel().getSelectedItem(); 
			if(table.getSelectionModel().getSelectedItem()==null){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error ");
				alert.setHeaderText("No timeline selected");
				alert.setContentText("Select a timeline in the list");
				alert.showAndWait();
				
			}
			else{
			
			
			try {
				EditTimeline timeline = new EditTimeline(tm.getTitle());
				timeline.start(stage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			stage.show();
			stage.setTitle(tm.getTitle());
			}
		});

		Button addevent = new Button("Add Event");
		addevent.setFont(new Font(11));
		addevent.setPadding(new Insets(10, 10, 10, 10));
		addevent.setPrefWidth(85);
		addevent.setPrefHeight(25);
		
		addevent.setOnAction(e -> {
           
			Timeline tom = table.getSelectionModel().getSelectedItem();
			if(table.getSelectionModel().getSelectedItem()==null){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error ");
				alert.setHeaderText("No timeline selected");
				alert.setContentText("Select a timeline in the list");
				alert.showAndWait();
				
			}
			else{
            	try {
            		 SimpleDateFormat ssdf = new SimpleDateFormat("dd/MM/yyyy");
            		 String date1 = tom.getStartTime();
                	 String date2 = tom.getEndTime();
            		
            		
				
					Date Startboundaries = ssdf.parse(date1);
					
					Date Endboundaries = ssdf.parse(date2);
					AddEvent add = new AddEvent(tom.getId(),Startboundaries,Endboundaries);
					add.start(stage);
					stage.show();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}	
			
			
			}

		});

		Button exit = new Button("New Timeline");
		exit.setFont(new Font(11));
		exit.setPadding(new Insets(10, 10, 10, 10));
		exit.setPrefWidth(85);
		exit.setPrefHeight(25);
		
		exit.setOnAction(e -> {

			TimelineBuilder builder = new TimelineBuilder();
			try {
				builder.start(stage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			stage.show();

		});

		Button editevent = new Button("Edit Event");
		editevent.setFont(new Font(11));
		editevent.setPadding(new Insets(10, 10, 10, 10));
		editevent.setPrefWidth(85);
		editevent.setPrefHeight(25);
		editevent.setOnAction(e -> {
			Timeline tm = table.getSelectionModel().getSelectedItem();
			if(table.getSelectionModel().getSelectedItem()==null){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error ");
				alert.setHeaderText("No timeline selected");
				alert.setContentText("Select a timeline in the list");
				alert.showAndWait();
				
			}
			else{
				
		        try {
				    SimpleDateFormat sadf = new SimpleDateFormat("dd/MM/yyyy");
				    String date1 = tm.getStartTime();
	               	String date2 = tm.getEndTime();
	               	
	               	
					Date Startboundaries = sadf.parse(date1);
					Date Endboundaries = sadf.parse(date2);
					
					
					EditEvent add = new EditEvent(tm.getId(),Startboundaries,Endboundaries);
					add.start(stage);
					
					stage.show();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}	
			}
		});
		
		Button viewTimeline = new Button("View Timeline");
		viewTimeline.setFont(new Font(11));
		viewTimeline.setPadding(new Insets(10, 10, 10, 10));
		viewTimeline.setPrefWidth(85);
		viewTimeline.setPrefHeight(25);
		
		viewTimeline.setOnAction(e -> {
              
                Timeline tm = table.getSelectionModel().getSelectedItem();
                if(table.getSelectionModel().getSelectedItem()==null){
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.setTitle("Error ");
    				alert.setHeaderText("No timeline selected");
    				alert.setContentText("Select a timeline in the list");
    				alert.showAndWait();
    				
    			}
    			else{
    			 try {
    			 String date1 = tm.getStartTime();
            	 String date2 = tm.getEndTime();
            	
            	/*Creating a Timeline object for the timeline that would be choosen from the list so we can manipulate
				  that timeline. F.e: EditTimeline or Save a timeline 
 				 Also converting localdate-> yyyy/mm/dd to date-> dd/mm/yyyy calculating the days between them and starting the 
				 timelineview class*/
				
            	 Calendar cal1 = new GregorianCalendar();
                 Calendar cal2 = new GregorianCalendar();

                 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                 Date Startdate = sdf.parse(date1);
                 cal1.setTime(Startdate);
                 TimelineView.firstdate = Startdate;
                 
                 Date Enddate = sdf.parse(date2);
                 cal2.setTime(Enddate);
                 TimelineView.lastdate = Enddate;
                  
            	  
                  TimelineView.daysnumber = daysBetween(cal1.getTime(),cal2.getTime());
            	  TimelineView event = new TimelineView(tm.getTitle(),Startdate,Enddate,tm.getDescription());

      		       // testing event = new testing(tm);
      			     event.start(stage);
      				
      				stage.show();
          			stage.setTitle(tm.getTitle());
      				
      			} catch (Exception e1) {

      				e1.printStackTrace();
      			}
      			
            	 
    		}
		});
		
		Button deleteTimeline = new Button("Delete");
		deleteTimeline.setPrefWidth(85);
		deleteTimeline.setPrefHeight(25);
		deleteTimeline.setFont(new Font(11));
		deleteTimeline.setPadding(new Insets(10,10,10,10));
		deleteTimeline.setOnAction(e -> {
		
			Timeline tm = table.getSelectionModel().getSelectedItem(); 
			
			if(table.getSelectionModel().getSelectedItem()==null){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error ");
				alert.setHeaderText("No timeline selected");
				alert.setContentText("Select a timeline in the list");
				alert.showAndWait();
		    }
		else{
			try{
				
				String query = "DELETE FROM TimeLine WHERE rowid='"+ tm.getId() + "'";
				PreparedStatement pre = con.prepareStatement(query);
				pre.execute();
			}
			catch (SQLException ef){
				ef.printStackTrace();
			}
			try
			{
				String query = "DELETE FROM SaveEvent WHERE ID='"+ tm.getId() + "'";
				PreparedStatement pre = con.prepareStatement(query);
				pre.execute();
			}
			catch (SQLException e1 ) {
				e1.printStackTrace();
			}
		
		data.remove(tm);
		
		}
		
		
		
		
		
		});
		
		
		
		
		VBox vbox = new VBox();
	
		//vbox.setStyle("-fx-background-color:  #FFFFFF;");
		vbox.getChildren().addAll(addevent, editevent ,editimeline ,deleteTimeline, viewTimeline,exit );
		vbox.setSpacing(20);
		vbox.setMaxWidth(100); 
		vbox.setPadding(new Insets(0, 10, 0, 0));
		vbox.setId("vbox");

		borderP.setPadding(new Insets(20, 20, 20, 10));
		Scene scene1 = new Scene(borderP, 500, 500, Color.rgb(200, 139, 128));
		scene1.getStylesheets().add("project/application.css");

		stage.setScene(scene1);
		stage.show();
		
		//borderP.setStyle("-fx-background-color:  #FFFFFF;");
		borderP.setLeft(vbox);
		borderP.setCenter(table);
		borderP.setId("borderP");

	}

	private void CheckConection() throws SQLException {

		AddEvent.con = Sqlconnection.DbConnector();
		if (AddEvent.con == null) {
			System.out.println("Not connected");
			System.exit(1);
		} else {
			System.out.println("connected");
		}

	}
	public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
}
}