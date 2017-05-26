package project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

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
		title.setTextFill(Color.BLUE);
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
															// selecting the
															// event
					title = new Label("Title: " + event.getName());
					title.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
					
					description = new Label("Description: " + event.getDescription());
					description.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
					
					StartTime = new Label("Start Date: " + event.getStartDate());
					StartTime.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
					
					EndTime = new Label("End Date: " + event.getEndDate());
					EndTime.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

					
					if(result.getBinaryStream("Image") != null){
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
					AddEvent.imv.setFitHeight(150);
					AddEvent.imv.setFitWidth(100);
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
		root.setStyle("-fx-background-color:  #FFFFFF;");
		borderp.setTop(root);
		Scene scene = new Scene(borderp, 250, 200);
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.show();

	}

}