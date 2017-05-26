package project;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TimelineBuilder extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws SQLException {

		Button nwTimeline = new Button("New Timeline");
		

		/* Drop Shadow and header effects */
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		Text header = new Text("Timeline Builder");
		header.setEffect(ds);
		header.setCache(true);
		header.setFont(Font.font(20));

		/* Playing around with Positioning */
		VBox root = new VBox();
		root.setStyle("-fx-background-color: LIGHTGREY");
		VBox buttns = new VBox();
		root.setAlignment(Pos.CENTER);
		buttns.setAlignment(Pos.CENTER);
		buttns.setSpacing(10);
		root.setSpacing(10);
		nwTimeline.setAlignment(Pos.CENTER);
		nwTimeline.setPadding(new Insets(10, 10, 10, 10));
	

		AddEvent.load = new Button("load Timeline");
		AddEvent.load.setPadding(new Insets(10, 10, 10, 10));
		AddEvent.load.setOnAction(e -> {

			LoadTimeline dis = new LoadTimeline();
			try {
				dis.start(primaryStage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			primaryStage.show();
		//	AddEvent.refreshTable();
		});

		buttns.getChildren().addAll(header, nwTimeline, AddEvent.load);
		root.getChildren().add(buttns);
		Scene scene = new Scene(root, 300, 200);

		/* Here is what the buttons will do */
		nwTimeline.setOnAction(e -> {
       
			CreateTimeline ctl = new CreateTimeline();
			try {
				ctl.start(primaryStage);
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			primaryStage.show();

		});

		primaryStage.setTitle("TimelineBuilder");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}
}
