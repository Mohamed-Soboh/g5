package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.BussinessUser;

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

public class bussinessForm implements Initializable {

	@FXML
	private Text balance;
	static BussinessUser Bussinessuser;
	@FXML
	private Text username;

	public void start(Stage primaryStage) throws Exception {
		System.out.println("555");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/bussinessHome.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("bussiness page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void OrderHistory(ActionEvent event) {

	}

	@FXML
	void logout(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user));
		LogInForm aFrame = new LogInForm(); 
		Stage primaryStage = new Stage();
		try {
			aFrame.start1(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void newOrder(ActionEvent event) {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		MessagesClass msg = new MessagesClass(Messages.GetbissnessUser, ChatClient.user);
		ClientUI.chat.accept(msg);
		Bussinessuser=ChatClient.Bussinessuser;
		username.setText(Bussinessuser.getFirstName()+" From "+Bussinessuser.getCompany());
		balance.setText(""+Bussinessuser.getW4C());
	}

}
