package project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class EventView extends Application {

	String eventName;
	Stage stage = new Stage();
	private BorderPane borderp = new BorderPane();

	public EventView(String name) throws Exception {
		eventName = name;
		start(stage);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Event event;
		VBox root = new VBox();
		Label title = new Label();

		Label description = new Label();
		Label StartTime = new Label();
		Label EndTime = new Label();

		try {

			Connection con = Sqlconnection.DbConnector();
			String sql = "select * from SaveEvent ";
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet result = pre.executeQuery();
			while (result.next()) {
				event = new Event(Integer.valueOf(result.getString("ID")), result.getString("Name"),
						result.getString("Description"),

						result.getString("StartDate"), result.getString("EndDate"));

				if (event.getName().equals(eventName)) { // if statement for
					Label lb = new Label(); // selecting the
					// event
					title = new Label("Title:  " + event.getName());

					title.setFont(new Font(15));

					description = new Label("Description:  " + event.getDescription());
					description.setFont(new Font(15));

					StartTime = new Label("Start Date:  " + event.getStartDate());
					StartTime.setFont(new Font(15));

					EndTime = new Label("End Date:   " + event.getEndDate());
					EndTime.setFont(new Font(15));

					if (result.getBinaryStream("Image") != null) {
					
						InputStream in = result.getBinaryStream("Image");

						OutputStream out = new FileOutputStream(new File("photo.jpg"));
						byte[] content = new byte[1024];
						int size = 0;
						while ((size = in.read(content)) != -1) {
							out.write(content, 0, size);

						}
						in.close();

						AddEvent.im = new Image("file:photo.jpg", 100, 105, true, true);
						AddEvent.imv = new ImageView(AddEvent.im);
						AddEvent.imv.setFitHeight(200);
						AddEvent.imv.setFitWidth(500);
						AddEvent.imv.setPreserveRatio(true);
						borderp.setCenter(AddEvent.imv);
						BorderPane.setAlignment(AddEvent.imv, Pos.CENTER);
					
					}

				} else {
					System.out.println("DEBUG: Event could not be found ! ");
				}

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		root.getChildren().addAll(title, description, StartTime, EndTime);
		root.setStyle("-fx-background-color:  #D1DBBD;");
		root.setPadding(new Insets(20, 20, 20, 20));
		borderp.setTop(root);
	
		borderp.setStyle("-fx-background-color:  #3E606F;");
		
		Scene scene = new Scene(borderp, 200, 350);
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.show();

	}

}
