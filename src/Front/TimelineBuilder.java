package Front;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage; 
public class TimelineBuilder extends Application {
	@Override
	public void start(Stage primaryStage){ 
		Button nwTimeline = new Button("New Timeline"); 
		Button loadTimeline = new Button("Load Timeline");
		
		/* Drop Shadow and header effects */
		DropShadow ds = new DropShadow(); 
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f,0.4f,0.4f)); 
		Text header = new Text("Timeline Builder");
		header.setEffect(ds); 
		header.setCache(true);
		header.setFont(Font.font(20));
		
		/*Playing around with Positioning*/
		VBox root = new VBox(); 
		root.setStyle("-fx-background-color: LIGHTGREY");
		VBox buttns = new VBox(); 
		root.setAlignment(Pos.CENTER);
		buttns.setAlignment(Pos.CENTER);
		buttns.setSpacing(10);
		root.setSpacing(10);
		nwTimeline.setAlignment(Pos.CENTER); 
		nwTimeline.setPadding(new Insets(10,10,10,10));
		loadTimeline.setPadding(new Insets(10,10,10,10));
		
		buttns.getChildren().addAll(header,nwTimeline,loadTimeline); 
		root.getChildren().add(buttns); 
		Scene scene = new Scene(root, 300, 200);
		
		/* Here is what the buttons will do */
		nwTimeline.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override 
			public void handle(ActionEvent event) { 
				CreateTimeline ctl = new CreateTimeline(); 
				ctl.start(primaryStage);
				primaryStage.show(); 	 	
			}
		});
		loadTimeline.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent event){ 
			Label label = new Label("Load new Timeline script here");
			label.setTextFill(Color.RED);
			buttns.getChildren().clear(); 
			buttns.getChildren().addAll(label); 
			primaryStage.show(); 
			}
		}); 
		
		/* Displaying the stage */
		primaryStage.setTitle("TimelineBuilder");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}
	public static void main(String[] args) {
		launch(args); 

	}

}
