package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javax.swing.JComboBox;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;


/*
This class will take the id of the timeline from the classes loadtimeline or timelineview create an event object for
each event and show a list in a combobox of the events stored in that timeline 
* */
public class EditEvent extends Application {

	public FileInputStream fil;
	public PreparedStatement pre = null;
	//event boundaries
	public Date TimelinesFirstDate;
	public Date TimelinesLastDate;
	public Date EventFirstdate;
	public Date EventLastDate;
	
	//Timeline s id 
   public int ID;
   
   //passing boolean if everything is right before saving it would be true else it would turn false and wont save false information
   public boolean pass;
   
   public EditEvent(int s,Date beging,Date theend){
	   this.ID = s;
	   this.TimelinesFirstDate = beging;
	   this.TimelinesLastDate  = theend;
	   this.pass = true;
   }
   
  public File file;
  private String theName;
  private TextField name = new TextField();
  private CheckBox durational = new CheckBox("Is durational?"); 
  
  private void setname(String s){
	   this.theName = s;
	   name.setText(theName);
	}
   
  private ArrayList<Event> ev = new ArrayList<Event>();
   
   public ObservableList<String> options = FXCollections.observableArrayList();
	@SuppressWarnings({ })
	@Override
	public void start(Stage primaryStage) throws Exception {
		/* method to for checking connection to the database */
		CheckConnection();
		
		Connection con = Sqlconnection.DbConnector();
		
		name.setPromptText("Title");
		name.setMaxWidth(300);
        setname(name.getText());	
		String sql = "SELECT* FROM SaveEvent WHERE Id='"+ ID + "'";
		try {
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet result = pre.executeQuery();
			while (result.next()) {ev.add(new Event(result.getInt("ID"), result.getString("Name"), result.getString("Description"),

					result.getString("StartDate"), result.getString("EndDate")));}
			
			result.close();
			pre.close();
			
		}catch(Exception e3){
				
		}
			
			
		for(int a = 0; a < ev.size(); a++){
			options.add(ev.get(a).getName());
		}
		
		TextArea description = new TextArea();
		description.setPromptText("Description");
		description.setPrefSize(285, 100);

		
		TextField address = new TextField();
		address.setPromptText("File Path");
		address.setPrefSize(100, 10);
		address.setEditable(false);
		address.setMaxWidth(300);

		DatePicker sdate = new DatePicker();
		sdate.setPromptText("Date");

		DatePicker edate = new DatePicker();
		edate.setPromptText("Date");
		edate.setMaxHeight(100);
		
		durational.setOnAction(e-> {
			if(durational.isSelected() == true){ 
				edate.setValue(sdate.getValue());
				edate.setDisable(true);
			}else{ 
				edate.setDisable(false);
			}
		});

		ComboBox<String> combox = new ComboBox<String>();
		combox.setMaxWidth(120);
		combox.setPrefHeight(25);
		combox.setPadding(new Insets(6, 10, 6, 10));
		combox.setPromptText("Edit Event");
		combox.setStyle("-fx-font: 10px \"Font\";");
		combox.setItems(options);		
		combox.setOnAction(e -> {
		    setname(combox.getSelectionModel().getSelectedItem().toString()); 
		    for(int a = 0; a < ev.size(); a++){
		       	 if((ev.get(a).getName()).equals(theName)){
		       		 description.setText(ev.get(a).getDescription());
		       		 sdate.getEditor().setText(ev.get(a).sDate);
		       		 edate.getEditor().setText(ev.get(a).eDate);
		       	 }	 
		    }
		});
		FileChooser filechooser = new FileChooser();
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*text"),
				new ExtensionFilter("Image Files ", "*.png", "*.jpg"), new ExtensionFilter("All Files", "*.*"));

		Button browse = new Button("Upload");
		browse.setPrefWidth(85);
		browse.setPrefHeight(25);
		browse.setFont(new Font(10));
		browse.setPadding(new Insets(10, 10, 10, 10));
		browse.setOnAction(e -> {

			file = filechooser.showOpenDialog(primaryStage);
			if (file != null) {
				address.setText(file.getAbsolutePath());

				Image	im = new Image(file.toURI().toString(), 100, 150, true, true);

				ImageView imv = new ImageView(AddEvent.im);
				imv.setFitHeight(1050);
				imv.setFitWidth(100);
			    imv.setPreserveRatio(true);

				BorderPane.setAlignment(imv, Pos.TOP_LEFT);
			}
		});

		Button update = new Button("Update");
		update.setPrefWidth(85);
		update.setPrefHeight(25);
		update.setFont(new Font(10));
		update.setPadding(new Insets(10, 10, 10, 10));
		update.setOnAction(e -> {
			if ( name.getText().isEmpty() 
					|| sdate.getEditor().getText().isEmpty() || edate.getEditor().getText().isEmpty()
					) {
                pass = false;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("infor dialog");
				alert.setHeaderText(null);
				alert.setContentText("Title - Start Date - End Date can not be empty");
				alert.showAndWait();
				
				
			} else {
				try {
					LocalDate start = sdate.getValue(); 
					LocalDate end = edate.getValue(); 	
					EventFirstdate = java.sql.Date.valueOf(start);
					EventLastDate = java.sql.Date.valueOf(end);
						
					if( EventLastDate.after(TimelinesLastDate)   ){
							 
						pass = false;
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Alert");
						alert.setHeaderText("Out of boundaries");
						alert.setContentText("The event can not end after the timeline ends.");
						alert.showAndWait();	
					}
					else if(  TimelinesFirstDate.after(EventFirstdate)   )  {
						pass = false;
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Alert");
						alert.setHeaderText("Out of boundaries");
						alert.setContentText("The event can not start before the timeline starts.");
						alert.showAndWait();	
					}
					else if(sdate.getValue().isAfter(edate.getValue())&& durational.isSelected() == false) {
						pass = false;
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Alert");
						alert.setHeaderText("Out of boundaries");
						alert.setContentText("Start date is after end date");
						alert.showAndWait();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}	
					
					
			/* if one if of the top is error so the boolean  pass = false else we can pass */
				if(pass == true){		
					String query = "update  SaveEvent set Name=?,Description=?,StartDate=?,EndDate=?,Image=? where Name='"
							+ theName + "'";
					try {
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					
						pre = con.prepareStatement(query);
					
						pre.setString(1, name.getText());
						pre.setString(2, description.getText());
						pre.setString(3, df.format(EventFirstdate));
						pre.setString(4, df.format(EventLastDate));
						
						
						if(file != null){
							fil = new FileInputStream(file);
							pre.setBinaryStream(5, (InputStream) fil, (int) file.length());
						}
						
						
						/*
						 * checking the start and end date if start date is greater
						 * than end date get an error message else do otherwise add
						 * this same code to the update button
						 */

						if ( sdate.getEditor().getText().equals(edate.getEditor().getText())|| durational.isSelected() == true
								|| edate == null   ) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Alert");
							alert.setHeaderText("Non durational event");
							alert.setContentText("The event will be non-durational");
							alert.showAndWait();
							
							pre.setString(4, df.format(EventFirstdate));
							pre.execute();	
							pre.close();
							
						} else {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Alert");
							alert.setHeaderText(null);
							alert.setContentText("Event Saved!");
							alert.showAndWait();
							
							pre.execute();
							pre.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}	
			
			
			
			EditEvent ed = new EditEvent(ID,TimelinesFirstDate,TimelinesLastDate);
			try {
				ed.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			primaryStage.show();
		
			
		}); 
	
		Button cancelEvent = new Button("Cancel");
		cancelEvent.setPrefWidth(85);
		cancelEvent.setPrefHeight(25);
		cancelEvent.setFont(new Font(10));
		cancelEvent.setPadding(new Insets(10, 10, 10, 10));
		cancelEvent.setOnAction(e -> {	  
			LoadTimeline ctl = new LoadTimeline();
			try {
				ctl.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.show();

		});

		BorderPane borderP = new BorderPane();
		Button delete = new Button("Delete");
		delete.setPrefWidth(85);
		delete.setPrefHeight(25);
		delete.setFont(new Font(10));
		delete.setPadding(new Insets(10, 10, 10, 10));
		delete.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confrimation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete the event?");
			Optional<ButtonType> action = alert.showAndWait();
			
			if (action.get() == ButtonType.OK) {
				try {
					
					String query = "delete from SaveEvent where  Name='"
					+theName + "'";
					PreparedStatement pre2 = con.prepareStatement(query);
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Confrimation");
					alert2.setHeaderText(null);
					alert2.setContentText("The event ("+theName+") has been deleted");
				    alert2.showAndWait();
					
					pre2.executeUpdate();
                   
					pre2.close();
				  	
					options.remove(name.getText());
				
					name.clear();
					address.clear();
					sdate.setValue(null);
					edate.setValue(null);
				
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			 
		});

		GridPane grigp = new GridPane();
		grigp.add(name, 0, 1);
		grigp.add(durational, 0, 2);
		grigp.add(new Label("Description:"), 0, 3);
		GridPane.setConstraints(description, 0, 4);
		grigp.getChildren().add(description);

		grigp.setAlignment(Pos.CENTER);

		grigp.add(address, 0, 5);
		grigp.add(browse, 1, 5);
		grigp.setVgap(10);
		grigp.setHgap(10);

		Label startDate = new Label("Start Date");
		sdate.setPadding(new Insets(7, 7, 7, 7));
		sdate.setValue(LocalDate.now());
		Label endDate = new Label("End Date");
		endDate.setAlignment(Pos.CENTER);

		edate.setPadding(new Insets(7, 7, 7, 7));
		edate.setValue(LocalDate.now());
		HBox hb = new HBox();
		hb.getChildren().addAll(combox,update, delete, cancelEvent);
		hb.setSpacing(5);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(startDate, sdate, endDate, edate);
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);

		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(grigp, vbox, hb);

		vb.setSpacing(10);
		borderP.setLeft(vb);
		borderP.setPadding(new Insets(10, 10, 10, 40));
		Scene scene = new Scene(borderP, 450, 500, Color.rgb(200, 139, 128));

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void CheckConnection() throws SQLException {
		AddEvent.con = Sqlconnection.DbConnector();
		if (AddEvent.con == null) {
			System.out.println("Not connected");
			System.exit(1);
		} else {
			System.out.println("connected");
		}
	}
}