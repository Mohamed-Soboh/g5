package client;
 
import gui.FirstPageController;
import gui.LogInForm;
 import javafx.application.Application;
import javafx.stage.Stage;


public class ClientUI extends Application {
	public static ClientController chat; //only one instance
	public static FirstPageController aFrame;
	public static LogInForm a;

	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		//chat=new ClientController("localhost",5555);
		aFrame = new FirstPageController();
		aFrame.start(primaryStage);		
	}
	
	
}
