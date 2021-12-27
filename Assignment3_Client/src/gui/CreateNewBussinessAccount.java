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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateNewBussinessAccount implements Initializable {

	@FXML
	private TextField Amount;

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
	@FXML
	private TextField W4C;

	@FXML
	private CheckBox PutVisa;

	@FXML
	private TextField CardholderName;

	@FXML
	private TextField CardNumber;

	@FXML
	private ComboBox<Integer> Month;

	@FXML
	private ComboBox<Integer> Year;

	@FXML
	private TextField CVV;
	private boolean flag = false;
	 @FXML
	    private Text pathtext;

	User user;
	BussinessUser bussinessUser;

	@FXML
	void submitBt(ActionEvent event) throws IOException {
		String str = CardNumber.getText();
		for (char c : str.toCharArray())
			if (!Character.isDigit(c)) {
				ErrorText.setText("Card number is not correct");
				return;
			}
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
				|| Password.getText().isEmpty() || PasswordC.getText().isEmpty() || Company.getText().isEmpty()
				|| Amount.getText().isEmpty())
			ErrorText.setText("There is lack of information");
		else {
			password1 = Password.getText();
			password2 = PasswordC.getText();
			if (!password1.equals(password2)) {
				ErrorText.setText("Password dose not match");
				return;
			} else {
				if (!PutVisa.isSelected()) {
					bussinessUser = new BussinessUser(UserID.getText(), FirstName.getText(), FamilyName.getText(),
							PhoneNumber.getText(), Email.getText() + EmailType.getValue(),
							Float.parseFloat(Amount.getText()), Company.getText(), 0);
					User user = new User(UserID.getText(), Username.getText(), Password.getText(), "Bussiness", 0, 0,
							"Frozen");
					String IDW4C = W4C.getText();
					MessagesClass msg = new MessagesClass(Messages.CreateNewBussinessAccont, user, bussinessUser,IDW4C);
					ClientUI.chat.accept(msg);
					String msg1;
					msg1 = (String) ChatClient.ErrorMessage;
					if (msg1.equals("UserNameAlreadyExists")) {
						ErrorText.setText("Username is already exists");
						return;
					}
					if (msg1.equals("IDAlreadyExists")) {
						ErrorText.setText("This ID Already have an account");
						return;
					}
					ErrorText.setText("The account has been created");
				} else {
					if (CardholderName.getText().isEmpty() || CVV.getText().isEmpty() || CardNumber.getText().isEmpty())
						ErrorText.setText("There is lack of visa information");
					else {
						String str1 = CVV.getText();
						for (char c : str1.toCharArray())
							if (!Character.isDigit(c)) {
								ErrorText.setText("CVV is not correct");
								return;
							}
						int cvv = Integer.parseInt(CVV.getText());
						if (cvv < 100 || cvv > 999) {
							ErrorText.setText("CVV isn't correct");
							return;
						}
						bussinessUser = new BussinessUser(UserID.getText(), FirstName.getText(), FamilyName.getText(),
								PhoneNumber.getText(), Email.getText() + EmailType.getValue(),
								Float.parseFloat(Amount.getText()), Company.getText(), 1);
						User user = new User(UserID.getText(), Username.getText(), Password.getText(), "Bussiness", 0, 0,
								"Frozen");
						visa = new Visa(UserID.getText(), CardNumber.getText(), Integer.parseInt(CVV.getText()),
								Year.getValue(), CardholderName.getText(), Month.getValue());
						String IDW4C = W4C.getText();
						MessagesClass msg = new MessagesClass(Messages.CreateNewBussinessAccontWithVisa,visa, user, bussinessUser,IDW4C);
						ClientUI.chat.accept(msg);
						String msg1;
						msg1 = (String) ChatClient.ErrorMessage;
						if (msg1.equals("UserNameAlreadyExists")) {
							ErrorText.setText("Username is already exists");
							return;
						}
						if (msg1.equals("IDAlreadyExists")) {
							ErrorText.setText("This ID Already have an account");
							return;
						}
						ErrorText.setText("The account has been created");
					}
				}
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
		pathtext.setText("ManagerPage/AddNewB-Account");
		W4C.setText(String.valueOf(ChatClient.W4CC));
		EmailType.getItems().removeAll(EmailType.getItems());
		EmailType.getItems().addAll("@hotmail.com", "@gmail.com", "@e.braude.ac.il", "@outlook.com");
		EmailType.getSelectionModel().select("@hotmail.com");
		Month.getItems().removeAll(Month.getItems());
		Month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		Year.getItems().removeAll(Year.getItems());
		Year.getItems().addAll(2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030);
		CardholderName.setVisible(flag);
		CardNumber.setVisible(flag);
		Month.setVisible(flag);
		Year.setVisible(flag);
		CVV.setVisible(flag);
	}

	@FXML
	void checkboxbt(ActionEvent event) {
		flag = !flag;
		CardholderName.setVisible(flag);
		CardNumber.setVisible(flag);
		Month.setVisible(flag);
		Year.setVisible(flag);
		CVV.setVisible(flag);
	}

}