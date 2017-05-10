package Back;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class AddEvent extends Application {
	
	/* fields */
	static Connection con = null;
	static PreparedStatement pre = null;
	static ResultSet result = null;
	static TextField name;
	static TextField address;
	static TextArea description;
	static TableView<Event> table;
	static ObservableList<Event> data;
	static FileChooser filechooser;
	static Button brows;
	static File file;
	static DatePicker sdate;
	static DatePicker edate;
	static ImageView imv;
	static Image im;
	static FileInputStream fil;
	static Button delete;
	static Button savbut;
	static Button load;
	static Button update;
	@SuppressWarnings("rawtypes")
	static TableColumn col1, col2, col3, col4;

	/* main method */
	public static void main(String[] args) {

		launch(args);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("New Event");

		/* method to for checking connection to the database */
		CheckConection();

		name = new TextField();
		name.setFont(new Font(20));
		name.setPromptText("Enter Title");
		name.setMaxWidth(300);

		description = new TextArea();
		description.setPromptText("Description ");
		description.setPrefWidth(20);
		description.setPrefHeight(100);

		address = new TextField();
		address.setFont(new Font(10));
		address.setPrefSize(100, 10);
		address.setEditable(false);
		address.setMaxWidth(300);
		address.setPromptText("Address ");

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
		savbut = new Button("Save");
		savbut.setFont(new Font(15));
		savbut.setOnAction(e -> {
			if (name.getText().isEmpty() || description.getText().isEmpty() || sdate.getEditor().getText().isEmpty()
					|| edate.getEditor().getText().isEmpty() || address.getText().isEmpty()) {

				Alert alet = new Alert(AlertType.INFORMATION);
				alet.setTitle("infor dialog");
				alet.setHeaderText(null);
				alet.setContentText("None of the fields must be left empty");
				alet.showAndWait();
			} else {

				String query = "INSERT INTO SaveEvent (Name, Description, StartDate, EndDate,Image) VALUES (?,?,?,?,?)";
				try {
					pre = con.prepareStatement(query);
					pre.setString(1, name.getText());
					pre.setString(2, description.getText());

					pre.setString(3, sdate.getEditor().getText());

					pre.setString(4, edate.getEditor().getText());
					fil = new FileInputStream(file);
					pre.setBinaryStream(5, (InputStream) fil, (int) file.length());

					Alert alet = new Alert(AlertType.INFORMATION);
					alet.setTitle("infor dialog");
					alet.setHeaderText(null);
					alet.setContentText("event created");
					alet.showAndWait();
					pre.execute();

					pre.close();
					ClearFields();
					refresTable();

				} catch (SQLException e1) {
					e1.printStackTrace(); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}

		});

		/*
		 * uploaded files with the filechooser and save in the database
		 */
		filechooser = new FileChooser();
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*text"),
				new ExtensionFilter("Image Files ", "*.png", "*.jpg"), new ExtensionFilter("All Files", "*.*"));

		brows = new Button("upload");
		brows.setFont(new Font(15));
		brows.setOnAction(e -> {

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

		table.getColumns().addAll(col1, col2, col3, col4);
		table.setTableMenuButtonVisible(true);
		col1.getTypeSelector();

		/* canecel by claering the fields */
		Button cancel = new Button("Cancel");
		cancel.setFont(new Font(15));
		cancel.setOnAction(e -> {

			LoadTimeline ctl = new LoadTimeline();
			try {
				ctl.start(primaryStage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			primaryStage.show();

		});

		borderP.setCenter(table);
		borderP.setPadding(new Insets(20, 20, 20, 20));

		GridPane grigp = new GridPane();
		grigp.add(name, 0, 1);
		grigp.setVgap(10);
		grigp.add(new Label("Description:"), 0, 3);

		GridPane.setConstraints(description, 0, 4);
		grigp.getChildren().add(description);

		grigp.setAlignment(Pos.CENTER);

		grigp.add(AddEvent.address, 0, 5);
		grigp.add(AddEvent.brows, 1, 5);
		grigp.setHgap(10);

		Label startDate = new Label("Start Date");
		sdate.setPadding(new Insets(7, 7, 7, 7));
		sdate.setValue(LocalDate.now());
		Label endDate = new Label("End Date");
		endDate.setAlignment(Pos.CENTER);

		edate.setPadding(new Insets(7, 7, 7, 7));
		edate.setValue(LocalDate.now());
		HBox hb = new HBox();
		hb.getChildren().addAll(cancel, savbut);
		hb.setSpacing(50);
		hb.setAlignment(Pos.BOTTOM_CENTER);
		VBox vbox = new VBox();
		vbox.getChildren().addAll(startDate, sdate, endDate, edate);
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);

		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(grigp, vbox, hb);

		vb.setSpacing(30);
		borderP.setLeft(vb);
		borderP.setPadding(new Insets(10, 10, 10, 40));
		Scene scene = new Scene(borderP, 700, 500, Color.rgb(200, 139, 128));

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void refresTable() {
		data.clear();

		try {
			String sql = "select * from Timeline ";
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				data.add(new Event(result.getString("Title"), result.getString("Description"),

						result.getString("StartDate"), result.getString("EndDate")));

				table.setItems(data);

			}
			pre.close();
			result.close();
		} catch (SQLException e1) {

			e1.printStackTrace();

		}
	}

	/*
	 * Clear all fields after the add button is click instead of deleting the
	 * textfield and rewritting a new event
	 */
	static void ClearFields() {
		name.clear();
		description.clear();
		address.clear();
		sdate.setValue(null);
		edate.setValue(null);
	}
	/* database connection */

	private void CheckConection() throws SQLException {

		con = Sqlconnection.DbConnector();
		if (con == null) {
			System.out.println("Not connected");
			System.exit(1);
		} else {
			System.out.println("connected");
		}

	}

}
