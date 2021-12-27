package gui;

import java.io.IOException;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LogInForm {

	@FXML
	private TextField usertxt;
	@FXML
	private Text statustext;
	@FXML
	private PasswordField passwordtxt;

	@FXML
	void ExitBt(ActionEvent event) throws IOException {
		System.out.println("Close LogIn Page");
		ClientUI.chat.client.quit();
		System.exit(0);
	}

	@FXML
	void alertbt(ActionEvent event) {
		Alert alert = new Alert(AlertType.NONE, "", ButtonType.CLOSE);
		alert.setTitle("Message");
		alert.setContentText("Hi, You are on LogIn Page Please Put Your Username and Password to Executing a Order ");
		alert.getDialogPane().setPrefSize(400, 200);
		alert.showAndWait();
		// System.out.println(updatelist.toString());

	}

	@FXML
	void registerbt(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		CreateNewNormalAccountController aFrame = new CreateNewNormalAccountController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void singin(ActionEvent event) throws IOException {
		ChatClient.user = null;
		if (usertxt.getText().isEmpty()) {
			statustext.setText("Please Enter your username");
			return;
		}
		if (passwordtxt.getText().isEmpty()) {
			statustext.setText("Please Enter you password");
			return;
		}

		User user = null;
		user = new User(usertxt.getText(), passwordtxt.getText());
		MessagesClass msg = new MessagesClass(Messages.Login, user);
		ClientUI.chat.accept(msg);
	 
		if (ChatClient.user != null) {
			FXMLLoader loader = new FXMLLoader();
			Stage primaryStage = new Stage();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			switch (ChatClient.user.getUserType()) {
			case "Normal":

				NormalUserHomePageController aFrame = new NormalUserHomePageController();
				Stage primaryStage1 = new Stage();
				try {
					aFrame.start(primaryStage1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "Bussiness":
				NormalUserHomePageController aFrame11 = new NormalUserHomePageController();
				Stage primaryStage111 = new Stage();
				try {
					aFrame11.start(primaryStage111);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "BranchManager":
				BranchManagerHomePageController aFrame1 = new BranchManagerHomePageController(); // create
																									// StudentFrame
				Stage primaryStage11 = new Stage();
				try {
					aFrame1.start(primaryStage11);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "HR":
				firstHRpage aFrame22 = new firstHRpage(); // create StudentFrame
				Stage primaryStage22 = new Stage();
				try {
					aFrame22.start(primaryStage22);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "RestaurantManager":
				RestaurantManagerHomePage aFrame221 = new RestaurantManagerHomePage(); // create StudentFrame
				Stage primaryStage221 = new Stage();
				try {
					aFrame221.start(primaryStage221);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Worker":
				RestaurantManagerHomePage aFrame21 = new RestaurantManagerHomePage(); // create StudentFrame
				Stage primaryStage21 = new Stage();
				try {
					aFrame21.start(primaryStage21);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "CEO":
				CeoHomePageController aFrame12 = new CeoHomePageController(); // create StudentFrame
				Stage primaryStage12 = new Stage();
				try {
					aFrame12.start(primaryStage12);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			

			}

		}

		if(ChatClient.userceo!=null)
		{Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
			CeoHomePageController aFrame12 = new CeoHomePageController(); // create StudentFrame
			Stage primaryStage12 = new Stage();
			try {
				aFrame12.start(primaryStage12);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			
			
		}
		if (ChatClient.user == null&&ChatClient.userceo==null) {
			statustext.setText("Ops! Something error");
			return;
		}
		 
	}

	public void start1(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/LogIn.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("MainPage");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
