package timline;

	import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exampl.Sqlconnect;
import exampl.User;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
	import javafx.event.EventHandler;
	import javafx.geometry.Insets;
	import javafx.geometry.Pos;
	import javafx.scene.Scene;
	import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
	import javafx.scene.layout.VBox;
	import javafx.scene.paint.Color;
	import javafx.scene.text.Font;
	import javafx.scene.text.Text;
	import javafx.stage.Stage; 
	public class TimelineBuilder extends Application {
	
		Connection con = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		TextField id, fn, sn, un, em, sef;
	
		DatePicker date;
		TableView<User> table;
		ObservableList<User> data;
		
		public static void main(String[] args) {
			launch(args); 

		}
       	@Override
		public void start(Stage primaryStage) throws SQLException{ 
       
       		
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
					try {
						ctl.start(primaryStage);
					} catch (SQLException e) {
					
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
	}
