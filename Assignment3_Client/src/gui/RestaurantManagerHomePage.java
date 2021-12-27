package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Messages;
import common.WorkerUser;

import common.MessagesClass;
import common.RestaurantManager;
import common.Resturaunt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RestaurantManagerHomePage implements Initializable{
    @FXML
    private Button CreateReport;
    @FXML
    private Text managername;

    @FXML
    private Button creatrmenu;

    @FXML
    private Button edittworker;

    @FXML

    Resturaunt Resturauntm;
    @FXML
    private Text resId;
    RestaurantManager restaurantmanager;
    WorkerUser WorkerUser;
    @FXML
    void CreateReport(ActionEvent event) throws IOException {
    	RestaurantManager manager;
    	MessagesClass msg = new MessagesClass(Messages.getRestaurantManager,ChatClient.user);
    	ClientUI.chat.accept(msg);
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ReportListForRestaurantController aFrame1 = new ReportListForRestaurantController(); 
		Stage primaryStage11 = new Stage();
		try {
			aFrame1.start(primaryStage11);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	public void start(Stage primaryStage) throws Exception {;
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/RestaurantManagerHomePage.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Home");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	  @FXML
	    void Editworkerbt(ActionEvent event) {

	    }

	    @FXML
	    void logout(ActionEvent event) {
	    	//FXMLLoader loader = new FXMLLoader();
	    	Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	
	    
			stage.close();	
			ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user));
			LogInForm aFrame = new LogInForm(); // create StudentFrame
			Stage primaryStage = new Stage();
			try {
				aFrame.start1(primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	    @FXML
	    void updatecreatemenu(ActionEvent event) {

	    }
	    //restaurantManager

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			if( ChatClient.user.getUserType().equals("RestaurantManager")) {
			MessagesClass msg = new MessagesClass(Messages.getRestaurantManager, ChatClient.user);
			ClientUI.chat.accept(msg);
			restaurantmanager = ChatClient.restaurantManager;
			System.out.println(restaurantmanager+"dfdsfdufbdufbdsufewbfuebfewufv");
			MessagesClass msg1 = new MessagesClass(Messages.getrestaurantname, restaurantmanager.getIDRestaurant());
 			ClientUI.chat.accept(msg1);
 			Resturauntm=ChatClient.Resturaunt;
			managername.setText("Manager ,"+restaurantmanager.getFirstName());
			resId.setText(ChatClient.Resturaunt.getResturaunt_Name());	}
			else
			{
				MessagesClass msg = new MessagesClass(Messages.getRestaurantWorker, ChatClient.user);
				ClientUI.chat.accept(msg);
				WorkerUser=ChatClient.WorkerUser;
				System.out.println(WorkerUser);
				MessagesClass msg1 = new MessagesClass(Messages.getrestaurantname, WorkerUser.getIDRestaurant());
	 			ClientUI.chat.accept(msg1);
	 			Resturauntm=ChatClient.Resturaunt;
	 			managername.setText("Worker ,"+WorkerUser.getFirstName());
				resId.setText(ChatClient.Resturaunt.getResturaunt_Name());
				CreateReport.setDisable(true);
				edittworker.setDisable(true);
				
			}
		}
}

