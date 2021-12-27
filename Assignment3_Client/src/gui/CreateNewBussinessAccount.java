package gui;

import java.io.IOException; 
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.User;
import common.Visa;
import common.BussinessUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateNewBussinessAccount implements Initializable {

	@FXML
	private TextField month;

	@FXML
	private TextField UserID;

	@FXML
	private TextField FirstName;

	@FXML
	private TextField FamilyName;

	@FXML
	private TextField Email;

	@FXML
	private TextField PhoneNumber;

	@FXML
	private TextField Username;

	@FXML
	private TextField Password;

	@FXML
	private TextField PasswordC;

	@FXML
	private TextField Company;
	@FXML
	private ComboBox<String> EmailType;
	@FXML
	private Text ErrorText;
	
	User user;
	BussinessUser bussinessUser;

	@FXML
	void submitBt(ActionEvent event) throws IOException {
		if (UserID.getText().length() != 9) {
			ErrorText.setText("ID isn't correct");
			return;
		}
		if (PhoneNumber.getText().length() != 10) {
			ErrorText.setText("Your Phone number is not correct");
			return;
		}
		String password1, password2;
		Visa visa;
		if (UserID.getText().isEmpty() || FirstName.getText().isEmpty() || FamilyName.getText().isBlank()
				|| Username.getText().isEmpty() || Email.getText().isEmpty() || PhoneNumber.getText().isEmpty()
				|| Password.getText().isEmpty() || PasswordC.getText().isEmpty())
			ErrorText.setText("There is lack of information");
		else {
			password1 = Password.getText();
			password2 = PasswordC.getText();
			if (!password1.equals(password2)) {
				ErrorText.setText("Password dose not match");
			} else {
				bussinessUser = new BussinessUser(UserID.getText(), FirstName.getText(), FamilyName.getText(),
						PhoneNumber.getText(), Email.getText() + EmailType.getValue(),
						Float.parseFloat(month.getText()), Company.getText(),0);
				
				User user = new User(UserID.getText(),Username.getText(),Password.getText(),"Bussiness",0,0,"Frozen");;
				MessagesClass msg = new MessagesClass(Messages.CreateNewBussinessAccont, user,bussinessUser);
				System.out.println(msg.getMsgData());
				ClientUI.chat.accept(msg);
				ErrorText.setText("The account created");
			}
		}

	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/CreateNewBussinessAccount.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void BackBt(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/BranchManager_Home_Page.fxml").openStream());
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		EmailType.getItems().removeAll(EmailType.getItems());
		EmailType.getItems().addAll("@hotmail.com", "@gmail.com", "@e.braude.ac.il", "@outlook.com");
		EmailType.getSelectionModel().select("@hotmail.com");
	}

}