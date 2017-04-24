package Back;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Front.TimelineBuilder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateTimeline extends Application {
	Connection con = null;
	PreparedStatement pre = null;
	ResultSet result = null;

	DatePicker startDatePicker, endDatePicker;
	TableView<Timeline> table;
	ObservableList<Timeline> data;
	TextField description;
	TextField title;

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws SQLException, FileNotFoundException {

		CheckConnection();

		Button createTimeline = new Button("Create");
		Button cancelTimeline = new Button("Cancel");

		/* Playing around with Positioning */
		VBox root = new VBox();
		root.setStyle("-fx-background-color: LIGHTGREY");
		root.setSpacing(10);

		/* Pane Positioning to get buttons beside each other */
		Pane buttns = new Pane();
		createTimeline.setTranslateX(75);
		createTimeline.setTranslateY(250);
		cancelTimeline.setTranslateX(205);
		cancelTimeline.setTranslateY(250);
		createTimeline.setPadding(new Insets(10, 10, 10, 10));
		cancelTimeline.setPadding(new Insets(10, 10, 10, 10));

		/* Grid with title and description */
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 0, 0, 80));
		title = new TextField();
		title.setPromptText("Title");
		title.setAlignment(Pos.BASELINE_LEFT);
		GridPane.setConstraints(title, 0, 1);
		grid.getChildren().add(title);

		description = new TextField();
		description.setPromptText("Description ");
		description.setAlignment(Pos.BASELINE_LEFT);
		GridPane.setConstraints(description, 0, 2);
		grid.getChildren().add(description);

		/* Datepicker */
		startDatePicker = new DatePicker();
		endDatePicker = new DatePicker();

		startDatePicker.setValue(LocalDate.now());
		endDatePicker.setValue(startDatePicker.getValue().plusDays(1));

		grid.add(new Label("Start Date:"), 0, 3);
		GridPane.setConstraints(startDatePicker, 0, 4);
		grid.getChildren().add(startDatePicker);

		grid.add(new Label("End Date:"), 0, 5);
		GridPane.setConstraints(endDatePicker, 0, 6);
		grid.getChildren().add(endDatePicker);

		buttns.getChildren().addAll(grid, createTimeline, cancelTimeline);
		root.getChildren().addAll(buttns);
		Scene scene = new Scene(root, 300, 300);

		table = new TableView<>();
		data = FXCollections.observableArrayList();

		@SuppressWarnings("rawtypes")
		TableColumn col1 = new TableColumn<>("Title");
		col1.setMaxWidth(100);
		col1.setCellValueFactory(new PropertyValueFactory<>("Title"));

		@SuppressWarnings("rawtypes")
		TableColumn col2 = new TableColumn<>("Description");
		col2.setMaxWidth(100);
		col2.setCellValueFactory(new PropertyValueFactory<>("Description"));

		@SuppressWarnings("rawtypes")
		TableColumn col3 = new TableColumn<>("startTime");
		col3.setMaxWidth(100);
		col3.setCellValueFactory(new PropertyValueFactory<>("startTiime"));

		@SuppressWarnings("rawtypes")
		TableColumn col4 = new TableColumn<>("endTime");
		col4.setMaxWidth(100);
		col4.setCellValueFactory(new PropertyValueFactory<>("endTime"));

		table.getColumns().addAll(col1, col2, col3, col4);
		table.setTableMenuButtonVisible(true);
		col1.getTypeSelector();

		createTimeline.setOnAction(e -> {

			if (checktDescription()) {
			} else {

				String query = "INSERT INTO TimeLine (Title, Description, StartTime, EndTime) VALUES (?,?,?,?)";
				try {
					pre = con.prepareStatement(query);
					pre.setString(1, title.getText());
					pre.setString(2, description.getText());

					pre.setString(3, startDatePicker.getEditor().getText());

					pre.setString(4, endDatePicker.getEditor().getText());

					Alert alet = new Alert(AlertType.INFORMATION);
					alet.setTitle("infor dialog");
					alet.setHeaderText(null);
					alet.setContentText("Timeline created");
					alet.showAndWait();

					pre.execute();

					pre.close();

					ClearFields();
					refreshTable();

					// hasEvents();
				} catch (SQLException e1) { e1.printStackTrace(); 
				} 
			}

		});

		cancelTimeline.setOnAction(e -> {

			TimelineBuilder builder = new TimelineBuilder();
			builder.start(primaryStage);
		});

		/* Displaying the stage */
		primaryStage.setTitle("Create Timeline");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void refreshTable() {
		data.clear();

		try {
			String sql = "select * from TimeLine ";
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				data.add(new Timeline(result.getString("Title"), result.getString("Description"),
						result.getString("startTime"), result.getString("endTime")));

				table.setItems(data);

			}
			pre.close();
			result.close();
		} catch (SQLException e1) {

			e1.printStackTrace();

		}
	}

	public boolean hasEvents() {
		if (Timeline.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	private void ClearFields() {
		title.clear();
		description.clear();
		startDatePicker.setValue(null);
		endDatePicker.setValue(null);
	}

	public boolean checktDescription() {
		if (description.getLength() > 0) {

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Description must have a value");

			alert.showAndWait();
		}
		return false;
	}

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
