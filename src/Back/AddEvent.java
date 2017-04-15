package Back;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import Back.Sqlconnect;

public class AddEvent extends Application {

	/* fields */
	Connection con = null;
	PreparedStatement pre = null;
	ResultSet result = null;
	TextField name;
	TextArea textArea;
	TextArea description;

	TableView<CreatEvent> table;
	ObservableList<CreatEvent> data;

	private FileChooser filechooser;
	private Button brows;
	private File file;
	// private Desktop deskt = Desktop.getDesktop();
	DatePicker sdate, edate;
	private ImageView imv;
	private Image im;
	private FileInputStream fil;

	/* main method */
	public static void main(String[] args) {

		launch(args);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Add Event ");

		/* method to for checking connection to the database */
		//CheckConection();

		name = new TextField();
		name.setFont(new Font(20));
		name.setPromptText("Enter Name");
		name.setMaxWidth(300);

		description = new TextArea();

		description.setPromptText("Description");
		description.setFont(new Font(30));
		description.setPrefSize(200, 20);
		// des.setEditable(false);
		description.setMaxWidth(300);
		textArea = new TextArea();

		textArea.setPromptText("File Path");
		textArea.setFont(new Font(10));
		textArea.setPrefSize(100, 10);
		textArea.setEditable(false);
		textArea.setMaxWidth(300);

		sdate = new DatePicker();
		sdate.setPromptText("Date");

		edate = new DatePicker();
		edate.setPromptText("Date");
		edate.setMaxHeight(100);
		/*
		 * SaveEvent is a name of a record in the database so insert and save
		 * values in to the database
		 */
		Button savbut = new Button("Save");
		savbut.setFont(new Font(30));
//		savbut.setOnAction(e -> {
//
//			String query = "INSERT INTO database (Name, Description,StartDate, EndDate, Image) VALUES (?,?,?,?,?)";
//			try {
//				pre = con.prepareStatement(query);
//				pre.setString(1, name.getText());
//				pre.setString(2, description.getText());
//
//				pre.setString(3, sdate.getEditor().getText());
//
//				pre.setString(4, edate.getEditor().getText());
//
//				fil = new FileInputStream(file);
//				pre.setBinaryStream(5, (InputStream) fil, (int) file.length());
//
//				Alert alet = new Alert(AlertType.INFORMATION);
//				alet.setTitle("infor dialog");
//				alet.setHeaderText(null);
//				alet.setContentText("user created");
//				alet.showAndWait();
//				pre.execute();
//
//				pre.close();
//				ClearFields();
//				refresTable();
//			} catch (SQLException e1) {
//				// label.setText("SQL error");
//			} catch (FileNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//		});

	
		Button delete = new Button("Delete");
		delete.setFont(new Font(30));
//		delete.setOnAction(e -> {
//			Alert alet = new Alert(AlertType.CONFIRMATION);
//			alet.setTitle("Comfrimation");
//			alet.setHeaderText(null);
//
//			alet.setContentText("Are u sure u wanna delete?");
//			Optional<ButtonType> action = alet.showAndWait();
//			if (action.get() == ButtonType.OK) {
//				try {
//
//					String query = "delete from database where Name= ?";
//					pre = con.prepareStatement(query);
//
//					pre.setString(1, name.getText());
//
//					pre.executeUpdate();
//
//					pre.close();
//				} catch (SQLException e2) {
//
//					e2.printStackTrace();
//				}
//				ClearFields();
//				refresTable();
//
//			}
//		});
		
		
		/*
		 * uploaded files with the filechooser and save in the database
		 */
		filechooser = new FileChooser();
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*text"),
				new ExtensionFilter("Image Files ", "*.png", "*.jpg"), new ExtensionFilter("All Files", "*.*"));

		brows = new Button("upload");
		brows.setFont(new Font(20));
		brows.setOnAction(e -> {

			file = filechooser.showOpenDialog(primaryStage);
			if (file != null) {

				// deskt.open(file);
				textArea.setText(file.getAbsolutePath());

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

		TableColumn col1 = new TableColumn<>("Name");
		col1.setMaxWidth(100);
		col1.setCellValueFactory(new PropertyValueFactory<>("Name"));

		TableColumn col2 = new TableColumn<>("Description");
		col2.setMaxWidth(100);
		col2.setCellValueFactory(new PropertyValueFactory<>("Description"));

		TableColumn col3 = new TableColumn<>("StartDate");
		col3.setMaxWidth(100);
		col3.setCellValueFactory(new PropertyValueFactory<>("StartDate"));

		TableColumn col4 = new TableColumn<>("EndDate");
		col4.setMaxWidth(100);
		col4.setCellValueFactory(new PropertyValueFactory<>("EndDate"));

		table.getColumns().addAll(col1, col2, col3, col4);
		table.setTableMenuButtonVisible(true);
		col1.getTypeSelector();

		/* canecel by claering the fields */
		Button cancel = new Button("Cancel");
		cancel.setFont(new Font(30));
		cancel.setOnAction(e -> {
			ClearFields();

		});
		
		// bp.setCenter(table);
		// table.setPrefSize(50, 10);
		// bp.setPadding(new Insets(10, 50, 50, 10));

		GridPane grigp = new GridPane();
		grigp.add(name, 0, 1);
		grigp.setVgap(15);
		grigp.add(new Label("Description:"), 0, 3);

		GridPane.setConstraints(description, 0, 4);
		grigp.getChildren().add(description);

		grigp.setAlignment(Pos.CENTER);

		grigp.add(textArea, 0, 6);
		grigp.add(brows, 1, 6);
		Label startDate = new Label("Start Date");
		
		sdate.setPadding(new Insets (10,10,10,10));
		Label endDate = new Label("End Date");
		endDate.setAlignment(Pos.CENTER);
		edate.setPadding(new Insets (10,10,10,10));
		
		HBox hb = new HBox();
		hb.getChildren().addAll(savbut, delete ,cancel);
		hb.setSpacing(50);
		hb.setPadding(new Insets(30, 0, 0, 90));
		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(startDate, sdate, endDate, edate);
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);
		
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(grigp, vbox, hb);
		vb.setSpacing(20);
		Scene scene = new Scene(vb, 600, 600, Color.rgb(200, 139, 128));

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/*
	 * this method helps to add new event each time one is created instead of
	 * adding the same thing more than once
	 */

	public void refresTable() {
		data.clear();

		try {
			String sql = "select * from database";
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				data.add(new CreatEvent(result.getString("Name"), result.getString("Description"),
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
	private void ClearFields() {
		name.clear();
		description.clear();
		textArea.clear();
		sdate.setValue(null);
		edate.setValue(null);
	}
	/* database connection */

//	private void CheckConection() throws SQLException {
//
//		con = Sqlconnect.DbConnector();
//		if (con == null) {
//			System.out.println("Not connected");
//			System.exit(1);
//		} else {
//			System.out.println("connected");
//		}
//
//	}

}
