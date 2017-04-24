package Back;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
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

public class EditTimeline extends Application {

	public static void main(String[] args) {

launch(args);
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public void start(Stage primaryStage) throws Exception {
	CheckConection();


	AddEvent.name = new TextField();
	AddEvent.name.setFont(new Font(20));
	AddEvent.name.setPromptText("Enter Name");
	AddEvent.name.setMaxWidth(300);

	AddEvent.description = new TextArea();

	AddEvent.description.setPromptText("Description");
	AddEvent.description.setFont(new Font(20));
	AddEvent.description.setPrefSize(100, 20);
	// des.setEditable(false);
	AddEvent.description.setMaxWidth(300);

	AddEvent.address = new TextField();
	AddEvent.address.setPromptText("File Path");
	AddEvent.address.setFont(new Font(5));
	AddEvent.address.setPrefSize(100, 10);
	AddEvent.address.setEditable(false);
	AddEvent.address.setMaxWidth(300);

	AddEvent.sdate = new DatePicker();
	AddEvent.sdate.setPromptText("Date");

	AddEvent.edate = new DatePicker();
	AddEvent.edate.setPromptText("Date");
	AddEvent.edate.setMaxHeight(100);

	AddEvent.table = new TableView<>();
	AddEvent.data = FXCollections.observableArrayList();

	AddEvent.col1 = new TableColumn<>("Name");
	AddEvent.col1.setMaxWidth(100);
	AddEvent.col1.setCellValueFactory(new PropertyValueFactory<>("Name"));

	AddEvent.col2 = new TableColumn<>("Description");
	AddEvent.col2.setMaxWidth(100);
	AddEvent.col2.setCellValueFactory(new PropertyValueFactory<>("Description"));

	AddEvent.col3 = new TableColumn<>("StartDate");
	AddEvent.col3.setMaxWidth(100);
	AddEvent.col3.setCellValueFactory(new PropertyValueFactory<>("StartDate"));

	AddEvent.col4 = new TableColumn<>("EndDate");
	AddEvent.col4.setMaxWidth(100);
	AddEvent.col4.setCellValueFactory(new PropertyValueFactory<>("EndDate"));

	AddEvent.table.getColumns().addAll(AddEvent.col1, AddEvent.col2, AddEvent.col3, AddEvent.col4);
	AddEvent.table.setTableMenuButtonVisible(true);
	AddEvent.col1.getTypeSelector();

	AddEvent.description = new TextArea();
	AddEvent.description.setPromptText("Description ");
	AddEvent.description.setPrefWidth(20);
	AddEvent.description.setPrefHeight(100);
	GridPane.setConstraints(AddEvent.description, 0, 2);

	AddEvent.address = new TextField();
	AddEvent.address.setPromptText("Address ");
	AddEvent.address.setAlignment(Pos.BASELINE_LEFT);
	AddEvent.address.setPrefWidth(200);
	GridPane.setConstraints(AddEvent.address, 0, 3);

	AddEvent.filechooser = new FileChooser();
	AddEvent.filechooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*text"),
			new ExtensionFilter("Image Files ", "*.png", "*.jpg"), new ExtensionFilter("All Files", "*.*"));
	
	AddEvent.brows = new Button("Upload");
	AddEvent.brows = new Button("upload");
	AddEvent.brows.setFont(new Font(15));
	AddEvent.brows.setOnAction(e -> {

		AddEvent.file = AddEvent.filechooser.showOpenDialog(primaryStage);
		if (AddEvent.file != null) {

			// deskt.open(file);
			AddEvent.address.setText(AddEvent.file.getAbsolutePath());

			AddEvent.im = new Image(AddEvent.file.toURI().toString(), 100, 150, true, true);

			AddEvent.imv = new ImageView(AddEvent.im);
			AddEvent.imv.setFitHeight(1050);
			AddEvent.imv.setFitWidth(100);
			AddEvent.imv.setPreserveRatio(true);

			BorderPane.setAlignment(AddEvent.imv, Pos.TOP_LEFT);
		}
	});

	AddEvent.update = new Button("update");
	AddEvent.update.setFont(new Font(15));
	AddEvent.update.setOnAction(e -> {

		// if (valfileds() & valName() & valId() & valEm()) {

		String query = "update  SaveEvent set Name=?,Description=?,StartDate=?,EndDate=?,Image=? where Name='"
				+ AddEvent.name.getText() + "'";
		try {
			AddEvent.pre = AddEvent.con.prepareStatement(query);
			AddEvent.pre.setString(1,AddEvent.name.getText());
			AddEvent.pre.setString(2,AddEvent.description.getText());
			AddEvent.pre.setString(3, AddEvent.sdate.getEditor().getText());
			AddEvent.pre.setString(4, AddEvent.edate.getEditor().getText());

			AddEvent.fil = new FileInputStream(AddEvent.file);
			AddEvent.pre.setBinaryStream(5, (InputStream) AddEvent.fil, (int) AddEvent.file.length());

			Alert alet = new Alert(AlertType.INFORMATION);
			alet.setTitle("infor dialog");
			alet.setHeaderText(null);
			alet.setContentText("user details updated");
			alet.showAndWait();
			AddEvent.pre.execute();
       
			AddEvent.pre.close();
			AddEvent.ClearFields();
			AddEvent.refresTable();
		} catch (SQLException e1) {
			// label.setText("SQL error");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	});
	

	Button cancelEvent = new Button("Cancel");
	cancelEvent.setFont(new Font(15));
	cancelEvent.setOnAction(e->{
		
		LoadTimeline ctl = new LoadTimeline();
		try {
			ctl.start(primaryStage);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		
		primaryStage.show();	
		
	});
	
	BorderPane borderP = new BorderPane();
	AddEvent.delete = new Button("Delete");

	AddEvent.delete.setFont(new Font(15));
	AddEvent.delete.setOnAction(e -> {
		Alert alet = new Alert(AlertType.CONFIRMATION);
		alet.setTitle("Comfrimation");
		alet.setHeaderText(null);

		alet.setContentText("Are u sure u wanna delete?");
		Optional<ButtonType> action = alet.showAndWait();
		if (action.get() == ButtonType.OK) {
			try {

				String query = "delete from SaveEvent where Name= ?";
				AddEvent.pre = AddEvent.con.prepareStatement(query);

				AddEvent.pre.setString(1, AddEvent.name.getText());

				AddEvent.pre.executeUpdate();

				AddEvent.pre.close();
			} catch (SQLException e2) {

				e2.printStackTrace();
			}
			AddEvent.ClearFields();
			AddEvent.refresTable();

		}
	});

	AddEvent.load = new Button("load");
	AddEvent.load.setFont(new Font(15));
	AddEvent.load.setOnAction(e -> {

		AddEvent.refresTable();
	});

	AddEvent.table.setOnMouseClicked(e -> {
	Event user = (Event) AddEvent.table.getSelectionModel().getSelectedItem();
		if (AddEvent.table.getSelectionModel().getSelectedItem() == null) {

			Alert alet = new Alert(AlertType.INFORMATION);
			alet.setTitle("infor dialog");
			alet.setHeaderText(null);
			alet.setContentText("Load table to chose an event to edit and update");
			alet.showAndWait();

		} else {

			String q = "select* from SaveEvent where Name =?";
			try {
				AddEvent.pre = AddEvent.con.prepareStatement(q);
				AddEvent.pre.setString(1, user.getName());
				AddEvent.result = AddEvent.pre.executeQuery();
				while (AddEvent.result.next()) {
					AddEvent.name.setText(AddEvent.result.getString("Name"));
					AddEvent.description.setText(AddEvent.result.getString("Description"));

					AddEvent.sdate.getEditor().setText(AddEvent.result.getString("StartDate"));
					AddEvent.edate.getEditor().setText(AddEvent.result.getString("EndDate"));

					AddEvent.pre.close();
					AddEvent.result.close();
				}

			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
	});

	borderP.setCenter(AddEvent.table);
	borderP.setPadding(new Insets(20, 20, 20, 20));

	GridPane grigp = new GridPane();
	grigp.add(AddEvent.name, 0, 1);
	grigp.setVgap(10);
	grigp.add(new Label("Description:"), 0, 3);

	GridPane.setConstraints(AddEvent.description, 0, 4);
	grigp.getChildren().add(AddEvent.description);

	grigp.setAlignment(Pos.CENTER);

	grigp.add(AddEvent.address, 0, 5);
	grigp.add(AddEvent.brows, 1, 5);
	grigp.setHgap(10);

	Label startDate = new Label("Start Date");
	AddEvent.sdate.setPadding(new Insets(7, 7, 7, 7));
	AddEvent.sdate.setValue(LocalDate.now());
	Label endDate = new Label("End Date");
	endDate.setAlignment(Pos.CENTER);

	AddEvent.edate.setPadding(new Insets(7, 7, 7, 7));
	AddEvent.edate.setValue(LocalDate.now());
	HBox hb = new HBox();
	hb.getChildren().addAll(cancelEvent, AddEvent.update, AddEvent.delete, AddEvent.load);
	hb.setSpacing(5);

	VBox vbox = new VBox();
	vbox.getChildren().addAll(startDate, AddEvent.sdate, endDate, AddEvent.edate);
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

private void CheckConection() throws SQLException {

	AddEvent.con = Sqlconnection.DbConnector();
	if (AddEvent.con == null) {
		System.out.println("Not connected");
		System.exit(1);
	} else {
		System.out.println("connected");
	}

}

}

