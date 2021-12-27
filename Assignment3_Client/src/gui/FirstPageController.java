package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import client.ClientController;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import javafx.event.ActionEvent;

public class FirstPageController implements Initializable{
	public static String ip;
	public static ClientController chat;
	@FXML
	private TextField IPAddress;

	@FXML
	void viewOrders(ActionEvent event) throws Exception {
		ip = (String) IPAddress.getText();
		ClientUI.chat = new ClientController(FirstPageController.ip, 5555);
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		LogInForm LIF=new LogInForm();
		Stage primaryStage = new Stage();
		LIF.start1(primaryStage);
	}
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/gui/FirstPage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("FirstPage");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			IPAddress.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
