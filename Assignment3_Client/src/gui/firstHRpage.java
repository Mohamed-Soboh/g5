package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.HRUser;
import common.Messages;
import common.MessagesClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class firstHRpage implements Initializable{

	public static HRUser HRManager;
    @FXML
    private Text username;
    
    public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/gui/HRFirstHomePage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("FirstPage");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
    @FXML
    void NewBussinessBT(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		HrHomePageController aFrame = new HrHomePageController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
     }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MessagesClass msg = new MessagesClass(Messages.GetHRManager, ChatClient.user);
		ClientUI.chat.accept(msg);
		HRManager = ChatClient.HRmanager1;
		System.out.print(HRManager);
	}
	
	 @FXML
	    void logOut(ActionEvent event) {
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
}
