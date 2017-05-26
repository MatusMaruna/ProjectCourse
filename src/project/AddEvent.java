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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class AddEvent extends Application {
	/* fields */
	static Connection con = null;
	static PreparedStatement pre = null;
	static ResultSet result = null;
	static TextField name;
	static TextField address;
	static TextArea description;
	public int id;
	static TableView<Event> table;
	static ObservableList<Event> data;
	static FileChooser filechooser;
	static Button browse;
	static File file;
	static DatePicker sdate;
	static DatePicker edate;
	public static ImageView imv;
	public static Image im;
	static FileInputStream fil;
	static Button delete;
	static Button saveButton;
	static Button load;
	static Button update;
	static Label dur; 
	static Label startDate = new Label("Start Date"); 
	CheckBox durational = new CheckBox("Non-durational");

	private Boolean pass;
	
	static ObservableList<String> options = FXCollections.observableArrayList();
	@SuppressWarnings("rawtypes")
	static TableColumn idcol, col1, col2, col3, col4;
	static ComboBox<String> combox;

	//Event boundaries
	public Date TimelinesFirstDate;
	public Date TimelinesLastDate;
	public Date EventFirstdate;
	public Date EventLastDate;
	
	/* Main method */
    public AddEvent(int id,Date beging,Date theend){
    	this.id = id;
    	this.TimelinesFirstDate = beging;
    	this.TimelinesLastDate = theend;
    	this.pass = true;
    }
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Add Event");

		/* method to for checking connection to the database */
		CheckConnection();
   
		durational.setOnAction(e-> {
			if(durational.isSelected() == true){ 
				edate.setDisable(true);
				startDate = new Label("Date"); 
			}else{ 
				startDate = new Label("Start Date"); 
				edate.setDisable(false);
			}
		});
		name = new TextField();
		name.setPromptText("Title");
		name.setMaxWidth(200);

		description = new TextArea();
		description.setPromptText("Description ");
		description.setMaxWidth(200);
		description.setMaxHeight(100);
		

		address = new TextField();
		address.setPromptText("File Path");
		address.setPrefSize(170, 10);
		address.setEditable(false);

		sdate = new DatePicker();
		sdate.setPromptText("Date");

		edate = new DatePicker();
		edate.setPromptText("Date");
		edate.setMaxHeight(100);

		/*
		 * SaveEvent is a name of a record in the database so insert and save
		 * values in to the database
		 */
		
		BorderPane borderP = new BorderPane();
		saveButton = new Button("Save");
		saveButton.setPrefWidth(85);
		saveButton.setPrefHeight(25);
		saveButton.setFont(new Font(11));
		saveButton.setPadding(new Insets(10, 10, 10, 10));
		saveButton.setOnAction(e -> {
			if ( name.getText().isEmpty() || sdate.getEditor().getText().isEmpty() 
			    || edate.getEditor().getText().isEmpty()) 
			{
                	pass = false;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("infor dialog");
				alert.setHeaderText(null);
				alert.setContentText("Title - Start Date - End Date can not be empty");
				alert.showAndWait();
				
				
			} else {
          
				/*checking if the end and start date of the event are inside the timeline s boundaries   */
				
				//fromDate
				try {
					LocalDate	start = sdate.getValue(); 
					LocalDate	end = edate.getValue(); 	
					
					EventFirstdate = java.sql.Date.valueOf(start);
					EventLastDate = java.sql.Date.valueOf(end);
					
					if( EventLastDate.after(TimelinesLastDate)   ){
						 
						    pass = false;
						    Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Alert");
							alert.setHeaderText("Out of boundaries");
							alert.setContentText("The event can not end after the timeline ends.");
							alert.showAndWait();
							
							ClearFields();
							refreshTable();
					 }
					 else if(  TimelinesFirstDate.after(EventFirstdate)   )  {
						 pass = false;
						    Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Alert");
							alert.setHeaderText("Out of boundaries");
							alert.setContentText("The event can not start before the timeline starts.");
							alert.showAndWait();
							
							ClearFields();
							refreshTable();
					 }
					
					 else if(sdate.getValue().isAfter(edate.getValue())&& durational.isSelected()==false) {
						pass = false;
					    Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Alert");
						alert.setHeaderText("Out of boundaries");
						alert.setContentText("Start date is after end date");
						alert.showAndWait();
					
						ClearFields();
						refreshTable();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}	
				
				
				/* if one if of the top is error so the boolean  pass = false else we can pass 
				     */
				
				
			if(pass == true){	
				String query = "INSERT INTO SaveEvent (ID ,Name, Description, StartDate, EndDate,Image) VALUES (?,?,?,?,?,?)";
				try {
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
					pre = con.prepareStatement(query);
					pre.setInt(1, id);
					pre.setString(2, name.getText());
					pre.setString(3, description.getText());
					pre.setString(4, df.format(EventFirstdate));
					pre.setString(5, df.format(EventLastDate));
							
					if(file != null){
					fil = new FileInputStream(file);
					pre.setBinaryStream(6, (InputStream) fil, (int) file.length());
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
							pre.setString(5, df.format(EventFirstdate));
							pre.execute();
							
							pre.close();
							ClearFields();
							refreshTable();
						} else {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Alert");
							alert.setHeaderText(null);
							alert.setContentText("Event Created!");
							alert.showAndWait();
							pre.execute();
	
							pre.close();
							ClearFields();
							refreshTable();
							durational = new CheckBox("Is non-durational ?"); 
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					AddEvent ed = new AddEvent(id,TimelinesFirstDate,TimelinesLastDate);
					try {
						ed.start(primaryStage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					primaryStage.show();			
				}
             		else{
				AddEvent ed = new AddEvent(id,TimelinesFirstDate,TimelinesLastDate);
				try {
					ed.start(primaryStage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				primaryStage.show();
			}
			}
		});

		/*
		 * uploaded files with the filechooser and save in the database
		 */
		filechooser = new FileChooser();
		filechooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files ", "*.png", "*.jpg"), new ExtensionFilter("All Files", "*.*"));

		browse = new Button("...");
		browse.setPrefWidth(10);
		browse.setPrefHeight(25);
		browse.setFont(new Font(10));
		browse.setMinHeight(25);
		//browse.setPadding(new Insets(10, 10, 10, 10));
		browse.setOnAction(e -> {
			file = filechooser.showOpenDialog(primaryStage);
			if (file != null) {

				// deskt.open(file);
				address.setText(file.getAbsolutePath());
				im = new Image(file.toURI().toString(), 100, 150, true, true);
				imv = new ImageView(im);
				imv.setFitHeight(1050);
				imv.setFitWidth(100);
				imv.setPreserveRatio(true);

				BorderPane.setAlignment(imv, Pos.TOP_LEFT);

			}
		});

		/*
		 * table consist of columns in the database where the name, description
		 * and file are stored
		 */
		table = new TableView<>();
		data = FXCollections.observableArrayList();

		col1 = new TableColumn<>("Name");
		col1.setMaxWidth(100);
		col1.setCellValueFactory(new PropertyValueFactory<>("Name"));

		col2 = new TableColumn<>("Description");
		col2.setMaxWidth(100);
		col2.setCellValueFactory(new PropertyValueFactory<>("Description"));

		col3 = new TableColumn<>("StartDate");
		col3.setMaxWidth(100);
		col3.setCellValueFactory(new PropertyValueFactory<>("StartDate"));

		col4 = new TableColumn<>("EndDate");
		col4.setMaxWidth(100);
		col4.setCellValueFactory(new PropertyValueFactory<>("EndDate"));

		table.getColumns().addAll( col1, col2, col3, col4);
		table.setTableMenuButtonVisible(true);
		col1.getTypeSelector();

		/* cancelButton by clearing the fields */
		Button cancelButton = new Button("Cancel");
		cancelButton.setPrefWidth(85);
		cancelButton.setPrefHeight(25);
		cancelButton.setFont(new Font(11));
		cancelButton.setPadding(new Insets(10, 10, 10, 10));
		cancelButton.setOnAction(e -> {	
			LoadTimeline ctl = new LoadTimeline();
			try {
				ctl.start(primaryStage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			primaryStage.show();
		});
		borderP.setRight(table);
		borderP.setPadding(new Insets(20, 20, 20, 20));
		
		HBox hb1 = new HBox();
		hb1.getChildren().addAll(AddEvent.address, AddEvent.browse);	
		
		
		GridPane grigp = new GridPane();
		grigp.setAlignment(Pos.CENTER);
		grigp.add(AddEvent.address, 0, 5);
		grigp.add(AddEvent.browse, 1, 5);
		grigp.setPadding(new Insets(10, 10, 10, 10));
		grigp.setHgap(10);

		sdate.setValue(LocalDate.now());
		Label endDate = new Label("End Date");
		endDate.setAlignment(Pos.CENTER);

		edate.setValue(LocalDate.now());
		
		HBox hb = new HBox();
		hb.getChildren().addAll(saveButton, cancelButton);
		hb.setSpacing(50);
		hb.setAlignment(Pos.BOTTOM_CENTER);
		hb.setPadding(new Insets(10, 10, 10, 10));
		

		VBox vb1 = new VBox();
		vb1.getChildren().addAll(name, durational, description, grigp, startDate, sdate, endDate, edate, hb);
		vb1.setAlignment(Pos.CENTER);
		vb1.setSpacing(10);
		
		HBox hb2 = new HBox();
		hb2.getChildren().addAll(vb1, table);
		hb2.setSpacing(10);
		hb2.setPadding(new Insets(10, 10, 10, 10));

		
		
		borderP.setPadding(new Insets(10, 10, 10, 40));
		Scene scene = new Scene(hb2, 610, 500);
		scene.getStylesheets().add("project/application.css");
		refreshTable();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public  void refreshTable() {
		data.clear();
		try {
			String sql = "SELECT * FROM SaveEvent WHERE id= '"+id + "'";
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				data.add(new Event(result.getInt("Id"), result.getString("Name"), result.getString("Description"),
						result.getString("StartDate"), result.getString("EndDate")));

				table.setItems(data);
			}
			pre.close();
			result.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static void ClearFields() {
		name.clear();
		description.clear();
		address.clear();
		
	}
	 public static LocalDate fromDate(Date date) {
		    Instant instant = Instant.ofEpochMilli(date.getTime());
		    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
		        .toLocalDate();
		  }
	/* Database connection */
	private void CheckConnection() throws SQLException {
		con = Sqlconnection.DbConnector();
		if (con == null) {
			System.out.println("Not connected");
			System.exit(1);
		} else {
			System.out.println("connected");
		}
	}
}