package Back;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {
		GridPane gp = new GridPane();

		CheckConection();

		Scene scene = new Scene(gp, 700, 500, Color.rgb(200, 19, 128));

		stage.setScene(scene);
		stage.show();
		stage.setTitle("Timeline with Events");
		VBox root = new VBox();
		root.setStyle("-fx-background-color: LIGHTGREY");
		root.setSpacing(10);

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

		BorderPane borderP = new BorderPane();
		AddEvent.load = new Button("load");
		AddEvent.load.setFont(new Font(25));

		AddEvent.load.setOnAction(e -> {

			AddEvent.refresTable();
		});

		Button editimeline = new Button("EditTimeline");
		editimeline.setPadding(new Insets(10, 10, 10, 10));

		editimeline.setOnAction(e -> {

			EditTimeline timeline = new EditTimeline();

			try {
				timeline.start(stage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			stage.show();
			stage.setTitle("Edit Timeline");
		});

		Button addevent = new Button("AddEvents");
		// addevent .setFont(new Font(20));
		addevent.setPadding(new Insets(10, 10, 10, 10));
		addevent.setOnAction(e -> {

			AddEvent add = new AddEvent();
			try {
				add.start(stage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			stage.show();

		});

		Button exit = new Button("Exit");
		exit.setFont(new Font(25));

		exit.setOnAction(e -> {

			TimelineBuilder builder = new TimelineBuilder();
			try {
				builder.start(stage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			stage.show();

		});

		Button editevent = new Button("EditEvent");
		editevent.setPadding(new Insets(10, 10, 10, 10));
		// editevent.setFont(new Font(20));
		editevent.setOnAction(e -> {

			EditEvent event = new EditEvent();

			try {
				event.start(stage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			stage.show();
			stage.setTitle("Edit Event");
		});
		VBox vbox = new VBox();
		vbox.getChildren().addAll(addevent, editevent, editimeline, exit, AddEvent.load);
		vbox.setSpacing(20);

		borderP.setPadding(new Insets(20, 20, 20, 20));
		Scene scene1 = new Scene(borderP, 500, 500, Color.rgb(200, 139, 128));

		stage.setScene(scene1);
		stage.show();

		borderP.setLeft(vbox);
		borderP.setCenter(AddEvent.table);

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
