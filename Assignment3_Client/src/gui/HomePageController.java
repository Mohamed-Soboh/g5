package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePageController implements Initializable{
	public static ClientController chat;
	@FXML
	private Text username_homepage_txt;
	
	@FXML
	private Button new_order_btn;

	@FXML
	private Button order_history_btn;

	@FXML
	private Button ext_home_page_btn;

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/gui/Home_Page.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void ExitHomePage(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void GoToResturaunts(ActionEvent event) throws IOException {
		//chat.accept("View All Resturaunts");
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Resturaunt_Menu.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Resturaunt's Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void OrdersHistory(ActionEvent event) throws IOException {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		username_homepage_txt.setText("Welcome, "+"Pink Horse69");
	}

}
