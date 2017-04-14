package plugg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jb223pt_assign3.javaFx.UpDownPane;

public class MainTest extends Application {

public void start(Stage primaryStage){
		
		Timeline test1 = new Timeline("T1");
		Event e1 = new Event(1,2,"e1");
		Event e2 = new Event(2,1,"e2");
		Event e3 = new Event(2,1,"e3");
		Event e4 = new Event(2123,3231,"e4");
		Event e5 = new Event(332,11212,"e5");
		Event e6 = new Event(54354352,12,"e6");
		
		test1.addEvent(e1);
		test1.addEvent(e2);
		test1.addEvent(e3);
		test1.addEvent(e4);
		test1.addEvent(e5);
		test1.addEvent(e6);
		
		
		test1.getEvent(e1);
		System.out.println("Is empty?:"+test1.isEmpty());
		System.out.println("Desc of event 5 :"+e5.getDesc()+"\nend duration of event 5: "+e5.getEnd());
		e5.setDesc("new description");
		System.out.println("Desc of event 5 :"+e5.getDesc()+"\nend duration of event 5: "+e5.getEnd());
		Scene sc = test1.graphical();
		primaryStage.setTitle("asdasdasd");
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
		

	}




}
