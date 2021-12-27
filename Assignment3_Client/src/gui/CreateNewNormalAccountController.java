package gui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.Normal;
import common.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import common.Visa;

public class CreateNewNormalAccountController implements Initializable {

	@FXML
	private Button Subm;

	@FXML
	private Text ExistsA;

	@FXML
	private Button Back;

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
	private TextField CardholderName;

	@FXML
	private TextField CardNumber;

	@FXML
	private ComboBox<Integer> Month;

	@FXML
	private ComboBox<Integer> Year;

	@FXML
	private TextField CVV;

	@FXML
	private CheckBox PutVisa;

	@FXML
	private TextField W4C;

	@FXML
	private ComboBox<String> EmailType;

	@FXML
	private Text ErrorText;

	public static Connection conn;

	private boolean flag = false;

	@FXML
	void BackBt(ActionEvent event) {
		if(CeoHomePageController.GetCEO==null) {

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		BranchManagerHomePageController aFrame = new BranchManagerHomePageController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}}
		else
		{
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();		}
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/CreateNewNormalAccount.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Create new account");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void UserNameExists() {
		Alert alert = new Alert(AlertType.NONE, "", ButtonType.CLOSE);
		alert.setTitle("Message");
		alert.setContentText("This user name is already exists");
		alert.getDialogPane().setPrefSize(200, 100);
		alert.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		W4C.setText(String.valueOf(ChatClient.W4CC));
		Month.getItems().removeAll(Month.getItems());
		Month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		Year.getItems().removeAll(Year.getItems());
		Year.getItems().addAll(2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030);
		EmailType.getItems().removeAll(EmailType.getItems());
		EmailType.getItems().addAll("@hotmail.com", "@gmail.com", "@e.braude.ac.il", "@outlook.com");
		EmailType.getSelectionModel().select("@hotmail.com");
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

	@FXML
	void submitBt(ActionEvent event) {
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
				if (!PutVisa.isSelected()) {
					User user = new User(UserID.getText(), Username.getText(), Password.getText(), "Normal", 0, 1,"Active");
					Normal NorUser = new Normal(UserID.getText(), FirstName.getText(), FamilyName.getText(),
							Email.getText() + EmailType.getValue(), PhoneNumber.getText(), 0,
							Float.parseFloat(W4C.getText()));
					MessagesClass msg = new MessagesClass(Messages.CreateNewNormalAccountWithOutVisa, user,NorUser);
					ClientUI.chat.accept(msg);
					String msg1;
					msg1 = (String) ChatClient.ErrorMessage;
					if(msg1.equals("UserNameAlreadyExists")) {
						ErrorText.setText("Username is already exists");
						return;
					}
					if(msg1.equals("IDAlreadyExists")) {
						ErrorText.setText("This ID Already have an account");
						return;
					}
					ErrorText.setText("The account has been created");
				} else {
					if (CardholderName.getText().isEmpty() || CVV.getText().isEmpty() || CardNumber.getText().isEmpty())
						ErrorText.setText("There is lack of visa information");
					else {
						String str = CVV.getText();
						for (char c : str.toCharArray())
							if (!Character.isDigit(c)) {
								ErrorText.setText("CVV is not correct");
								return;
							}
						int cvv = Integer.parseInt(CVV.getText());
						if (cvv < 100 || cvv > 999) {
							ErrorText.setText("CVV isn't correct");
							return;
						}
						User user1 = new User(UserID.getText(), Username.getText(), Password.getText(), "Normal", 0, 1,
								"Active");
						visa = new Visa(UserID.getText(), CardNumber.getText(), Integer.parseInt(CVV.getText()),
								Year.getValue(), CardholderName.getText(), Month.getValue());
						Normal NorUser1 = new Normal(UserID.getText(), FirstName.getText(),
								FamilyName.getText(), Email.getText() + EmailType.getValue(), PhoneNumber.getText(), 1,
								Float.parseFloat(W4C.getText()));
						MessagesClass msg = new MessagesClass(Messages.CreateNewNormalAccountWithVisa, visa, user1,NorUser1);
						ClientUI.chat.accept(msg);
						String msg1;
						msg1 = (String) ChatClient.ErrorMessage;
						
						if(msg1.equals("UserNameAlreadyExists")) {
							ErrorText.setText("Username is already exists");
							return;
						}
						if(msg1.equals("IDAlreadyExists")) {
							ErrorText.setText("This ID Already have an account");
							return;
						}
						ErrorText.setText("The account has been created");
					}
				}
			}
		}
	}
}